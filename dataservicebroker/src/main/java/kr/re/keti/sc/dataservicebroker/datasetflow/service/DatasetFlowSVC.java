package kr.re.keti.sc.dataservicebroker.datasetflow.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.re.keti.sc.dataservicebroker.common.code.Constants;
import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.BigDataStorageType;
import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.ErrorCode;
import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.StorageType;
import kr.re.keti.sc.dataservicebroker.common.exception.BadRequestException;
import kr.re.keti.sc.dataservicebroker.common.exception.InternalServerErrorException;
import kr.re.keti.sc.dataservicebroker.datamodel.DataModelManager;
import kr.re.keti.sc.dataservicebroker.datamodel.service.DataModelSVC;
import kr.re.keti.sc.dataservicebroker.datamodel.service.hive.HiveTableSVC;
import kr.re.keti.sc.dataservicebroker.datamodel.sqlprovider.BigdataTableSqlProvider;
import kr.re.keti.sc.dataservicebroker.datamodel.sqlprovider.RdbTableSqlProvider;
import kr.re.keti.sc.dataservicebroker.datamodel.vo.DataModelBaseVO;
import kr.re.keti.sc.dataservicebroker.datamodel.vo.DataModelCacheVO;
import kr.re.keti.sc.dataservicebroker.datamodel.vo.DataModelVO;
import kr.re.keti.sc.dataservicebroker.dataset.service.DatasetSVC;
import kr.re.keti.sc.dataservicebroker.dataset.vo.DatasetBaseVO;
import kr.re.keti.sc.dataservicebroker.datasetflow.dao.DatasetFlowDAO;
import kr.re.keti.sc.dataservicebroker.datasetflow.vo.DatasetFlowBaseVO;
import kr.re.keti.sc.dataservicebroker.datasetflow.vo.DatasetFlowProvisioningVO;
import kr.re.keti.sc.dataservicebroker.datasetflow.vo.DatasetFlowVO;
import kr.re.keti.sc.dataservicebroker.datasetflow.vo.RetrieveDatasetFlowBaseVO;
import kr.re.keti.sc.dataservicebroker.entities.controller.kafka.consumer.KafkaConsumerManager;
import kr.re.keti.sc.dataservicebroker.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DatasetFlowSVC {

    @Autowired
    private DatasetFlowDAO datasetFlowDAO;
    @Autowired
    private DatasetSVC datasetSVC;
    @Autowired
    private DataModelSVC dataModelSVC;
    @Autowired
    private DataModelManager dataModelManager;
    @Autowired
    private BigdataTableSqlProvider hiveDataModelSqlProvider;
    @Autowired
    private RdbTableSqlProvider rdbDataModelSqlProvider;
    @Autowired
    private KafkaConsumerManager kafkaConsumerManager;
    @Autowired(required = false)
	private HiveTableSVC hiveTableSVC;
    @Autowired
    private ObjectMapper objectMapper;

    private final Pattern URI_PATTERN_DATASET_FLOW = Pattern.compile("/datasets/(?<datasetId>.+[^/])/flow");

	@PostConstruct
	private void initDatasetFlow() {
		// 1. 데이터 셋 흐름 정보 조회
		List<DatasetFlowBaseVO> datasetFlowBaseVOs = datasetFlowDAO.getDatasetFlowBaseVOList();
		if(datasetFlowBaseVOs != null && datasetFlowBaseVOs.size() > 0) {
			for(DatasetFlowBaseVO datasetFlowBaseVO : datasetFlowBaseVOs) {
				// enabled 가 아닌 경우 pass
				if(!datasetFlowBaseVO.getEnabled()) {
					continue;
				}
				// 2. 데이터 셋 토픽에 대해 카프카 컨슈머 시작
				kafkaConsumerManager.registKafkaConsumer(datasetFlowBaseVO.getDatasetId());
			}
		}
	}


	/**
	 * 데이터 셋 흐름 생성
	 * @param to 데이터 셋 정보 수정 요청 수신 url
     * @param requestBody 요청 Body
     * @param requestId Provisioning Request Id
     * @param eventTime Provisioning Request Time
	 */
    public void createDatasetFlow(String to, String requestBody, String requestId, Date eventTime) {

    	// 1. Request URI에서 식별자 추출
		Matcher matcherForUpdate = URI_PATTERN_DATASET_FLOW.matcher(to);

    	if(matcherForUpdate.find()) {
			String datasetId = matcherForUpdate.group("datasetId");

			// 2. 수신 데이터 파싱
			DatasetFlowProvisioningVO datasetFlowProvisioningVO = null;
			try {
				datasetFlowProvisioningVO = objectMapper.readValue(requestBody, DatasetFlowProvisioningVO.class);
			} catch (IOException e) {
				throw new BadRequestException(ErrorCode.INVALID_PARAMETER,
	                    "Invalid Parameter. body=" + requestBody);
			}
			datasetFlowProvisioningVO.setDatasetId(datasetId);
			DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowVOToDatasetFlowBaseVO(datasetFlowProvisioningVO);

			// 3. 유효성 체크
			// 3-1. 데이터 셋 흐름 기 존재여부 체크
			DatasetFlowBaseVO retrieveDatasetFlowBaseVO = getDatasetFlowBaseVOById(datasetId);
			if(retrieveDatasetFlowBaseVO != null) {
				// 3. 이중화되어 있는 다른 IngestInterface 인스턴스에서 DB 입력 했는지 체크
				if(alreadyProcessByOtherInstance(requestId, eventTime, retrieveDatasetFlowBaseVO)) {
		        	// 다른 Instance에서 DB 업데이트 이미 처리했으므로 캐쉬만 로딩하고 성공 처리
					DatasetBaseVO datasetBaseVO = dataModelManager.getDatasetCache(retrieveDatasetFlowBaseVO.getDatasetId());
					dataModelManager.reloadDataModelCache(datasetBaseVO.getDataModelId());
					dataModelManager.putDatasetFlowCache(retrieveDatasetFlowBaseVO);
		            return;
		        } else {
		        	// 이미 존재하므로 업데이트
		        	updateDatasetFlow("/datasets/" + datasetId + "/flow", requestBody, requestId, eventTime);
		        	return;
	        	}
			}
			
			// 3-2. 데이터 셋 존재여부 체크
			DatasetBaseVO datasetBaseVO = datasetSVC.getDatasetVOById(datasetId);
			if(datasetBaseVO == null) {
				throw new BadRequestException(ErrorCode.INVALID_PARAMETER,
	                    "Dataset is not exists. datasetId=" + datasetId);
			}

			// 3-3. 데이터모델 존재여부 체크
			DataModelBaseVO dataModelBaseVO = dataModelSVC.getDataModelBaseVOById(datasetBaseVO.getDataModelId());
			DataModelCacheVO dataModelCacheVO = dataModelManager.getDataModelVOCacheById(dataModelBaseVO.getId());

			if(dataModelBaseVO == null || dataModelCacheVO == null) {
				throw new BadRequestException(ErrorCode.INVALID_PARAMETER,
	                    "DataModel is not exists. datasetId=" + datasetId + ", dataModel id=" + datasetBaseVO.getDataModelId());
			}

			if(datasetFlowBaseVO.getEnabled()) {
				// 4. 데이터모델 DDL 기반 테이블 생성
				if(useBigDataStorage(datasetFlowBaseVO.getBigDataStorageTypes())) {

					try {
						createBigdataTable(dataModelCacheVO.getDataModelVO(), datasetFlowBaseVO.getBigDataStorageTypes());
					} catch (Exception e) {
						throw new InternalServerErrorException(ErrorCode.CREATE_ENTITY_TABLE_ERROR,
			                    "Create Bigdata Table error. datasetId=" + datasetId + ", dataModel=" + dataModelBaseVO.getDataModel());
					}
				}
				
				if(useRdbStorage(datasetFlowBaseVO.getBigDataStorageTypes())) {
					String ddl = rdbDataModelSqlProvider.generateCreateTableDdl(dataModelCacheVO);
					dataModelSVC.executeDdl(ddl, StorageType.RDB);
				}

				// 하위버전 호환을 위해 아무 설정도 없는 경우 default RDB 사용
				if(isNullStorageType(datasetFlowBaseVO.getBigDataStorageTypes())) {
					String ddl = rdbDataModelSqlProvider.generateCreateTableDdl(dataModelCacheVO);
					dataModelSVC.executeDdl(ddl, StorageType.RDB);
				}
			}
	
	    	// 5. 데이터셋 흐름 정보 저장
			datasetFlowBaseVO.setProvisioningRequestId(requestId);
			datasetFlowBaseVO.setProvisioningEventTime(eventTime);
	        datasetFlowDAO.createDatasetFlow(datasetFlowBaseVO);

	        // 6. 데이터모델이 어느 Storage에 생성되어 있는 지 갱신
			updateDataModelStorageType(datasetFlowBaseVO, dataModelBaseVO);

			// 7. 캐쉬 등록
	        dataModelManager.putDataModelCache(dataModelBaseVO);
	        dataModelManager.putDatasetFlowCache(datasetFlowBaseVO);
	
		    if(datasetFlowBaseVO.getEnabled()) {
		        // 8. Start Kafka Consumer
	        	kafkaConsumerManager.registKafkaConsumer(datasetId);
	        }

	     // 404
    	} else {
 			throw new BadRequestException(ErrorCode.NOT_EXIST_ID);
 		}
    }

	private void updateDataModelStorageType(DatasetFlowBaseVO datasetFlowBaseVO, DataModelBaseVO dataModelBaseVO) {
		List<BigDataStorageType> oldStorageTypes = dataModelBaseVO.getCreatedStorageTypes();
		List<BigDataStorageType> newStorageTypes = null;
		if(ValidateUtil.isEmptyData(datasetFlowBaseVO.getBigDataStorageTypes())) {
			newStorageTypes = new ArrayList<>(); // default RDB
			newStorageTypes.add(BigDataStorageType.RDB);
		} else {
			newStorageTypes = datasetFlowBaseVO.getBigDataStorageTypes();
		}

		if(oldStorageTypes != null) {
			newStorageTypes.addAll(oldStorageTypes);
			newStorageTypes = newStorageTypes.stream().distinct().collect(Collectors.toList());
		}

		dataModelBaseVO.setCreatedStorageTypes(newStorageTypes);
		dataModelSVC.updateDataModelStorage(dataModelBaseVO);
	}


	/**
     * HBase 및 Hive 테이블 생성
     * @param dataModelVO 테이터모델 정보
     * @param bigDataStorageTypes 빅데이터 저장 유형 리스트
     * @throws Exception
     */
    private void createBigdataTable(DataModelVO dataModelVO, List<BigDataStorageType> bigDataStorageTypes) throws Exception {
    	String ddl = hiveDataModelSqlProvider.generateCreateTableDdl(dataModelVO);
		String[] sqls = ddl.split(" CREATEHIVETABLE ");
		
		boolean storeInHbase = false;
		boolean bothStore = false;

		if (bigDataStorageTypes != null && !bigDataStorageTypes.isEmpty()) {
			if (bigDataStorageTypes.contains(BigDataStorageType.HBASE)) {
				storeInHbase = true;

				if (bigDataStorageTypes.contains(BigDataStorageType.HIVE)) {
					bothStore = true;
				}
			}
		}
		
		log.debug("Is this flow Stored in HBase? " + (storeInHbase? "yes": "no"));

		for (String sql : sqls) {
			String fullSql = hiveTableSVC.getFullDdl(sql, storeInHbase,"");
			hiveTableSVC.createTable(fullSql);
		}

		if (bothStore) {
			storeInHbase = false;
			for (String sql : sqls) {
				String fullSql = hiveTableSVC.getFullDdl(sql, storeInHbase,"");
				hiveTableSVC.createTable(fullSql);
			}
		}    	
    }
    
    /**
     * 데이터 셋 흐름 수정
     * @param to 데이터 셋 정보 수정 요청 수신 url
     * @param requestBody 요청 Body
     * @param requestId Provisioning Request Id
     * @param eventTime Provisioning Request Time
     */
	public void updateDatasetFlow(String to, String requestBody, String requestId, Date eventTime) {

		// 1. Request URI에서 식별자 추출
		Matcher matcherForUpdate = URI_PATTERN_DATASET_FLOW.matcher(to);

    	if(matcherForUpdate.find()) {
			String datasetId = matcherForUpdate.group("datasetId");

			// 2. 수신 데이터 파싱
			DatasetFlowProvisioningVO datasetFlowProvisioningVO = null;
			try {
				datasetFlowProvisioningVO = objectMapper.readValue(requestBody, DatasetFlowProvisioningVO.class);
			} catch (IOException e) {
				throw new BadRequestException(ErrorCode.INVALID_PARAMETER,
	                    "Invalid Parameter. body=" + requestBody);
			}
			datasetFlowProvisioningVO.setDatasetId(datasetId);

			// 3. 데이터 셋 흐름 기 존재여부 체크
			DatasetFlowBaseVO retrieveDatasetFlowBaseVO = getDatasetFlowBaseVOById(datasetId);
			if(retrieveDatasetFlowBaseVO == null) {
        		createDatasetFlow(to, requestBody, requestId, eventTime);
        		return;
			}

			// 4. 이중화되어 있는 다른 IngestInterface 인스턴스에서 DB 입력 했는지 체크
			if(alreadyProcessByOtherInstance(requestId, eventTime, retrieveDatasetFlowBaseVO)) {
	        	// 다른 Instance에서 DB 업데이트 이미 처리했으므로 캐쉬만 로딩하고 성공 처리
				DatasetBaseVO datasetBaseVO = dataModelManager.getDatasetCache(retrieveDatasetFlowBaseVO.getDatasetId());
				dataModelManager.reloadDataModelCache(datasetBaseVO.getDataModelId());
				dataModelManager.putDatasetFlowCache(retrieveDatasetFlowBaseVO);
	            return;
	        }

			// 5. DatasetFlow 업데이트
			DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowVOToDatasetFlowBaseVO(datasetFlowProvisioningVO);
			datasetFlowBaseVO.setProvisioningRequestId(requestId);
			datasetFlowBaseVO.setProvisioningEventTime(eventTime);

	        int result = datasetFlowDAO.updateDatasetFlow(datasetFlowBaseVO);

	        if(result == 0) {
	        	throw new BadRequestException(ErrorCode.NOT_EXIST_ID,
	                    "Not Exists. datasetId=" + datasetId);
	        }

			// 6. 데이터모델이 어느 Storage에 생성되어 있는 지 갱신
			DatasetBaseVO datasetBaseVO = dataModelManager.getDatasetCache(retrieveDatasetFlowBaseVO.getDatasetId());
			DataModelBaseVO dataModelBaseVO = dataModelSVC.getDataModelBaseVOById(datasetBaseVO.getDataModelId());
			updateDataModelStorageType(datasetFlowBaseVO, dataModelBaseVO);

	        // 7. 캐쉬갱신
	        dataModelManager.putDataModelCache(dataModelBaseVO);
	        dataModelManager.putDatasetFlowCache(datasetFlowBaseVO);

	        if(datasetFlowBaseVO.getEnabled()) {
	        	if(!kafkaConsumerManager.isRunKafkaConsumer(datasetId)) {
	        		kafkaConsumerManager.registKafkaConsumer(datasetFlowBaseVO.getDatasetId());
	        	}
	        } else if(!datasetFlowBaseVO.getEnabled()){
	        	kafkaConsumerManager.deregistKafkaConsumer(datasetId);
	        }

	    // 404
    	} else {
 			throw new BadRequestException(ErrorCode.NOT_EXIST_ID);
 		}

    }

	/**
	 * 데이터 셋 흐름 삭제
	 * @param to 데이터 셋 정보 수정 요청 수신 url
     * @param requestBody 요청 Body
	 */
    public void deleteDatasetFlow(String to, String requestBody) {

    	Matcher matcherForDelete = URI_PATTERN_DATASET_FLOW.matcher(to);

    	if(matcherForDelete.find()) {
			String datasetId = matcherForDelete.group("datasetId");

			// 1. 파라미터 파싱 및 유효성 검사
			DatasetFlowBaseVO retrieveDatasetFlowBaseVO = new DatasetFlowBaseVO();
	    	retrieveDatasetFlowBaseVO.setDatasetId(datasetId);
			DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowDAO.getDatasetFlowBaseVOById(retrieveDatasetFlowBaseVO);

	        if (datasetFlowBaseVO != null) {
	        	// 2. DatasetFlow 정보 삭제
		        datasetFlowDAO.deleteDatasetFlow(datasetFlowBaseVO);
	        }

	        // 3. 캐쉬삭제
	        dataModelManager.removeDatasetFlowCache(datasetId);

	        // 4. Stop Kafka Consumer
	        kafkaConsumerManager.deregistKafkaConsumer(datasetId);

	     // 404
 		} else {
 			throw new BadRequestException(ErrorCode.NOT_EXIST_ID);
 		}
    }

    /**
     * 데이터 셋 흐름 목록 조회 (API 에서 사용하는 형태로 변환하여 반환)
     * @return
     */
    public List<DatasetFlowVO> getDatasetFlowVOList() {
    	
    	List<DatasetFlowBaseVO> datasetFlowBaseVOs = datasetFlowDAO.getDatasetFlowBaseVOList();

    	List<DatasetFlowVO> datasetFlowVOs = null;
    	if(datasetFlowBaseVOs != null && datasetFlowBaseVOs.size() > 0) {
    		datasetFlowVOs = new ArrayList<>(datasetFlowBaseVOs.size());
    		for(DatasetFlowBaseVO datasetFlowBaseVO : datasetFlowBaseVOs) {
    			DatasetFlowVO datasetFlowVO = datasetFlowBaseVOToDatasetFlowVO(datasetFlowBaseVO);
    			datasetFlowVOs.add(datasetFlowVO);
    		}
    	}

        return datasetFlowVOs;
    }

    /**
     * 데이터 셋 흐름 목록 조회 (DB테이블 형태 VO 그대로 반환)
     * @return
     */
    public List<DatasetFlowBaseVO> getDatasetFlowBaseVOList() {
    	return datasetFlowDAO.getDatasetFlowBaseVOList();
    }

    /**
     * 데이터 셋 흐름 조회 By Id (API 에서 사용하는 형태로 변환하여 반환)
     * @param datasetId 데이터 셋 아이디
     * @return
     */
    public DatasetFlowVO getDatasetFlowVOById(String datasetId) {

    	DatasetFlowBaseVO retrieveDatasetFlowBaseVO = new DatasetFlowBaseVO();
    	retrieveDatasetFlowBaseVO.setDatasetId(datasetId);

    	DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowDAO.getDatasetFlowBaseVOById(retrieveDatasetFlowBaseVO);

    	DatasetFlowVO datasetFlowVO = null;
    	if(datasetFlowBaseVO != null) {
    		datasetFlowVO = datasetFlowBaseVOToDatasetFlowVO(datasetFlowBaseVO);
    	}
        return datasetFlowVO;
    }

    /**
     * 데이터 셋 흐름 조회 By Id (DB테이블 형태 VO 그대로 반환)
     * @param datasetId 데이터 셋 아이디
     * @return
     */
    public DatasetFlowBaseVO getDatasetFlowBaseVOById(String datasetId) {

    	DatasetFlowBaseVO retrieveDatasetFlowBaseVO = new DatasetFlowBaseVO();
    	retrieveDatasetFlowBaseVO.setDatasetId(datasetId);

    	return datasetFlowDAO.getDatasetFlowBaseVOById(retrieveDatasetFlowBaseVO);
    }
    

    /**
     * DatasetFlow API 요청 파라미터를 DB입력 VO 로 변환
     * @param datasetFlowVO
     * @return
     */
    private DatasetFlowBaseVO datasetFlowVOToDatasetFlowBaseVO(DatasetFlowProvisioningVO datasetFlowProvisioningVO) {
    	
    	DatasetFlowBaseVO datasetFlowBaseVO = new DatasetFlowBaseVO();
    	datasetFlowBaseVO.setDatasetId(datasetFlowProvisioningVO.getDatasetId());
    	datasetFlowBaseVO.setDescription(datasetFlowProvisioningVO.getDescription());
    	datasetFlowBaseVO.setHistoryStoreType(datasetFlowProvisioningVO.getHistoryStoreType());
    	datasetFlowBaseVO.setBigDataStorageTypes(datasetFlowProvisioningVO.getBigDataStorageTypes());
    	datasetFlowBaseVO.setEnabled(datasetFlowProvisioningVO.getEnabled());

		return datasetFlowBaseVO;
	}

    /**
     * DatasetFlow dao VO 를 DatasetFlow API VO 로 변환
     * @param datasetFlowBaseVO
     * @return
     */
    private DatasetFlowVO datasetFlowBaseVOToDatasetFlowVO(DatasetFlowBaseVO datasetFlowBaseVO) {

    	DatasetFlowVO datasetFlowVO = new DatasetFlowVO();
    	datasetFlowVO.setDatasetId(datasetFlowBaseVO.getDatasetId());
    	datasetFlowVO.setHistoryStoreType(datasetFlowBaseVO.getHistoryStoreType());
    	datasetFlowVO.setBigDataStorageTypes(datasetFlowBaseVO.getBigDataStorageTypes());
    	datasetFlowVO.setDescription(datasetFlowBaseVO.getDescription());
    	datasetFlowVO.setEnabled(datasetFlowBaseVO.getEnabled());

		return datasetFlowVO;
	}

    /**
     * 데이터 모델 정보 기반으로 데이터 셋 흐름 정보 조회
     * @param namespace 데이터모델 namespace
     * @param type 데이터모델 type
     * @param version 데이터모델 version
     * @return
     */
    public List<DatasetFlowBaseVO> getDatasetFlowByDataModel(String dataModelId) {
    	RetrieveDatasetFlowBaseVO retrieveDatasetFlowBaseVO = new RetrieveDatasetFlowBaseVO();
    	retrieveDatasetFlowBaseVO.setDataModelId(dataModelId);
    	return datasetFlowDAO.getDatasetFlowBaseVOByDataModel(retrieveDatasetFlowBaseVO);
    }

    /**
     * 빅데이터 저장 여부 확인
     * @param bigDataStorageTypeList
     * @return
     */
    private boolean useBigDataStorage(List<BigDataStorageType> bigDataStorageTypeList) {
    	if(bigDataStorageTypeList == null || bigDataStorageTypeList.size() == 0) {
    		return false;
    	}

    	for(BigDataStorageType bigDataStorageType : bigDataStorageTypeList) {
    		if(bigDataStorageType == BigDataStorageType.HIVE
    				|| bigDataStorageType == BigDataStorageType.HBASE) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * RDB 저장 여부 확인
     * @param bigDataStorageTypeList
     * @return
     */
    private boolean useRdbStorage(List<BigDataStorageType> bigDataStorageTypeList) {
    	if(bigDataStorageTypeList == null || bigDataStorageTypeList.size() == 0) {
    		return false;
    	}

    	for(BigDataStorageType bigDataStorageType : bigDataStorageTypeList) {
    		if(bigDataStorageType == BigDataStorageType.RDB) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * RDB 저장 여부 확인
     * @param bigDataStorageTypeList
     * @return
     */
    private boolean isNullStorageType(List<BigDataStorageType> bigDataStorageTypeList) {
    	if(bigDataStorageTypeList == null || bigDataStorageTypeList.size() == 0) {
    		return true;
    	}

    	return false;
    }

    /**
     * 이중화되어 있는 다른 IngestInterface 인스턴스에서 DB 입력 했는지 체크
     * @param requestId Provisioning Request Id
     * @param eventTime Provisioning Event Time
     * @param retrieveDatasetFlowBaseVO DB에서 조회한 데이터 셋 흐름 정보
     * @return
     */
    private boolean alreadyProcessByOtherInstance(String requestId, Date eventTime, DatasetFlowBaseVO retrieveDatasetFlowBaseVO) {
		// 이중화되어 있는 다른 IngestInterface 인스턴스에서 DB 입력 했는지 체크
    	if(requestId.equals(retrieveDatasetFlowBaseVO.getProvisioningRequestId())
    			&& eventTime.getTime() >= retrieveDatasetFlowBaseVO.getProvisioningEventTime().getTime()) {
    		return true;
    	}
    	return false;
	}
}

