package kr.re.keti.sc.dataservicebroker.TestModel3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.ErrorCode;
import kr.re.keti.sc.dataservicebroker.common.exception.BadRequestException;
import kr.re.keti.sc.dataservicebroker.datamodel.service.DataModelRetrieveSVC;
import kr.re.keti.sc.dataservicebroker.datamodel.service.DataModelSVC;
import kr.re.keti.sc.dataservicebroker.datamodel.vo.DataModelBaseVO;
import kr.re.keti.sc.dataservicebroker.dataset.service.DatasetRetrieveSVC;
import kr.re.keti.sc.dataservicebroker.dataset.service.DatasetSVC;
import kr.re.keti.sc.dataservicebroker.dataset.vo.DatasetBaseVO;
import kr.re.keti.sc.dataservicebroker.datasetflow.service.DatasetFlowRetrieveSVC;
import kr.re.keti.sc.dataservicebroker.datasetflow.service.DatasetFlowSVC;
import kr.re.keti.sc.dataservicebroker.datasetflow.vo.DatasetFlowBaseVO;

import java.net.BindException;
import java.util.Date;
import java.util.HashMap;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdBadRequestException;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.*;
import org.springframework.util.LinkedMultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebAppConfiguration
public class TestModel3CRUDTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private DataModelSVC dataModelSVC;

  @Autowired
  private DatasetSVC datasetSVC;

  @Autowired
  private DatasetFlowSVC datasetFlowSVC;

  @Autowired
  private DataModelRetrieveSVC dataModelRetrieveSVC;

  @Autowired
  private DatasetRetrieveSVC datasetRetrieveSVC;

  @Autowired
  private DatasetFlowRetrieveSVC datasetFlowRetrieveSVC;


  @Test
  void testCreateCR_BV_01() throws Exception {
        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
          
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
          
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
        }
        
    /*
     201 Created TDD
*/
    String inputVO =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputVO)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputVO.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
    
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
    
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCR_BV_02() throws Exception {
        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
          
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId); 
        } 
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCR_BV_03() throws Exception {
        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
        } 
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);        
  }

  @Test
  void testCreateCR_BI_01() throws Exception {

        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);   
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);          
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
        }
        
    /*
     201 Created TDD
*/
    String inputData =
        "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true],\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"testDoubleArray\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"objects\":{\"type\":\"Property\",\"value\":{\"interger\":5,\"boolean\":true,\"string\":\"test\",\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1}},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]},\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testString\":{\"type\":\"Property\",\"value\":\"test\"},\"type\":\"TestModel3\"}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"invalid key : testDoubleArray\"}";
    
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCR_BI_02() throws Exception {
    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    /*
     201 Created TDD
*/
    String inputData =
        "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":\"invalid-string\",\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":\"invalid-string\"},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"objects\":{\"type\":\"Property\",\"value\":{\"interger\":5,\"boolean\":true,\"string\":\"test\",\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1}},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]},\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testString\":{\"type\":\"Property\",\"value\":\"test\"},\"type\":\"TestModel3\"}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"fullVO to daoVO parsing ERROR. entityType=TestModel3, id=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCR_BI_03() throws Exception {

        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
          
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
          
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
          
        } 
    /*
     201 Created TDD
*/
    String inputData =
        "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true],\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":\"invalid-string\"},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"objects\":{\"type\":\"Property\",\"value\":{\"interger\":5,\"boolean\":true,\"string\":\"test\",\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1}},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]},\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testString\":{\"type\":\"Property\",\"value\":\"test\"},\"type\":\"TestModel3\"}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"fullVO to daoVO parsing ERROR. entityType=TestModel3, id=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}";  
      
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCA_BV_01() throws Exception {

        //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    /*
     204 No Content
    */
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCA_BV_02() throws Exception {

        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
          
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
          
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
          
        } 
    
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare= "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    /*
     204 No Content
    */
    inputData =
        "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCA_BV_03() throws Exception {

        //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    /*
     204 No Content
    */
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCA_BI_01() throws Exception {
    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());

    /*
     201 Created TDD
*/
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":\"invalid-string\",\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]}},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"objects\":{\"type\":\"Property\",\"value\":{\"interger\":5,\"boolean\":true,\"string\":\"test\",\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1}},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-5]},\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testString\":{\"type\":\"Property\",\"value\":\"test\"},\"type\":\"TestModel3\"}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Attribute Type. attributeId=testArrayBoolean, valueType=ARRAY_BOOLEAN, value=invalid-string\"}";  
    
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);

  }

  @Test
  void testCreateCA_BI_02() throws Exception {

    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());

    /*
     201 Created TDD
*/
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":\"invalid-string\",\"observedAt\":\"2023-06-18T15:00:00.000Z\"}}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Attribute Type. attributeId=testArrayBoolean, valueType=ARRAY_BOOLEAN, value=invalid-string\"}";  

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCA_BI_03() throws Exception {
    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);

    }
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":\"invalid-string\"},\"type\":\"TestModel3\"}";

    String queryResponseCompare = "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"fullVO to daoVO parsing ERROR. entityType=TestModel3, id=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}";
    
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
        
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCU_BV_01() throws Exception {

        //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
      String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

      resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());
    
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true]},\"type\":\"TestModel3\"}";

    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);

  }

  @Test
  void testCreateCU_BV_02() throws Exception {

    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-6]}},\"type\":\"TestModel3\"}";
    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-6.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCU_BV_03() throws Exception {

    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
      
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
	
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
    
    // resultActions =
    // mvc
    //   .perform(
    //     MockMvcRequestBuilders
    //       .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
    //       .accept(MediaType.APPLICATION_JSON)
    //   )
    //   .andExpect(status().isOk())
    //   .andExpect(content().string(queryResponseCompare))
    //   .andDo(print());
      
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12,-6]}},\"type\":\"TestModel3\"}";
    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-6.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    // resultActions =
    //   mvc
    //     .perform(
    //       MockMvcRequestBuilders
    //         .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
    //         .accept(MediaType.APPLICATION_JSON)
    //     )
    //     .andExpect(status().isOk())
    //     .andExpect(content().string(queryResponseCompare))
    //     .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCU_BI_01() throws Exception {

        //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
      
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId); 
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
    } 
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":\"invalid-String\"},\"type\":\"TestModel3\"}";
    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCU_BI_02() throws Exception {
        //데이터 모델 생성
        String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

        String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
        Date now = new Date();
    
        dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);
    
        //데이터 모델 조회
        String dataModelId = "TestModel3";
        DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
        if (dataModelBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
        } 
    
        //데이터 셋 생성
        String inputData_dataset =
          "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
          
        datasetSVC.createDataset(inputData_dataset, requestId, now);
    
        //데이터 셋 조회
        String datasetId = "TestModel3";
        DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
        if (datasetBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
        } 
    
        //데이터 셋 플로우 생성
        String inputData_datasetflow = 
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
        }
        
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";
      
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":\"invalid-Value\",\"observedAt\":\"2023-06-18T15:00:00.000Z\"},\"type\":\"TestModel3\"}";
    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);
  }

  @Test
  void testCreateCU_BI_03() throws Exception {

    //데이터 모델 생성
    String inputData_datamodel = "{\"context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"TestModel3\",\"type\":\"TestModel3\",\"typeUri\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"name\":\"TestModel3\",\"attributes\":[{\"name\":\"location\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/location\"},{\"name\":\"objects\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"interger\",\"valueType\":\"Integer\"},{\"name\":\"boolean\",\"valueType\":\"Boolean\"},{\"name\":\"string\",\"valueType\":\"String\"},{\"name\":\"date\",\"valueType\":\"Date\"},{\"name\":\"double\",\"valueType\":\"Double\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/hasObjects\"},{\"name\":\"observationSpace\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"https://uri.etsi.org/ngsi-ld/observationSpace\"},{\"name\":\"testArrayBoolean\",\"isRequired\":false,\"valueType\":\"ArrayBoolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\"},{\"name\":\"testArrayDouble\",\"isRequired\":false,\"valueType\":\"ArrayDouble\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\"},{\"name\":\"testArrayInteger\",\"isRequired\":false,\"valueType\":\"ArrayInteger\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\"},{\"name\":\"testArrayObject\",\"isRequired\":false,\"valueType\":\"ArrayObject\",\"objectMembers\":[{\"name\":\"testArrObjString\",\"valueType\":\"String\"},{\"name\":\"testArrObjInteger\",\"valueType\":\"Integer\"},{\"name\":\"testArrObjDouble\",\"valueType\":\"Double\"},{\"name\":\"testArrObjBoolean\",\"valueType\":\"Boolean\"},{\"name\":\"testArrObjDate\",\"valueType\":\"Date\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\"},{\"name\":\"testArrayString\",\"isRequired\":false,\"valueType\":\"ArrayString\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\"},{\"name\":\"testBoolean\",\"isRequired\":false,\"valueType\":\"Boolean\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testObject\",\"isRequired\":false,\"valueType\":\"Object\",\"objectMembers\":[{\"name\":\"string\",\"valueType\":\"String\"}],\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testObject\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\"},{\"name\":\"testDate\",\"isRequired\":false,\"valueType\":\"Date\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDate\"},{\"name\":\"testDouble\",\"isRequired\":false,\"valueType\":\"Double\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testDouble\"},{\"name\":\"testGeoJson\",\"isRequired\":false,\"valueType\":\"GeoJson\",\"attributeType\":\"GeoProperty\",\"hasObservedAt\":true,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\"},{\"name\":\"testInteger\",\"isRequired\":false,\"valueType\":\"Integer\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testInteger\"},{\"name\":\"testRelationshipString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Relationship\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\"},{\"name\":\"testString\",\"isRequired\":false,\"valueType\":\"String\",\"attributeType\":\"Property\",\"hasObservedAt\":false,\"hasUnitCode\":false,\"childAttributes\":[],\"attributeUri\":\"http://uri.citydatahub.kr/ngsi-ld/testString\"}],\"createdAt\":\"2022-03-29T04:04:28.836Z\",\"modifiedAt\":\"2022-10-25T04:12:09.291Z\"}";

    String requestId = "6372e9fd-c007-4882-902d-6d964f4fbd5d";
    Date now = new Date();

    dataModelSVC.processCreate("/datamodels", inputData_datamodel, requestId, now);

    //데이터 모델 조회
    String dataModelId = "TestModel3";
    DataModelBaseVO dataModelBaseVO = dataModelRetrieveSVC.getDataModelBaseVOById(dataModelId);
    if (dataModelBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. dataModelId =" + dataModelId);
    } 

    //데이터 셋 생성
    String inputData_dataset =
      "{\"id\":\"TestModel3\",\"name\":\"TestModel3\",\"updateInterval\":\"-\",\"category\":\"환경\",\"providerOrganization\":\"-\",\"providerSystem\":\"-\",\"isProcessed\":\"원천데이터\",\"ownership\":\"-\",\"license\":\"CCBY\",\"datasetItems\":\"-\",\"targetRegions\":\"-\",\"qualityCheckEnabled\":false,\"dataModelId\":\"TestModel3\"}";
      
    datasetSVC.createDataset(inputData_dataset, requestId, now);

    //데이터 셋 조회
    String datasetId = "TestModel3";
    DatasetBaseVO datasetBaseVO = datasetRetrieveSVC.getDatasetVOById(datasetId);
    if (datasetBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists. datasetId =" + datasetId);
    } 

    //데이터 셋 플로우 생성
    String inputData_datasetflow = 
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\"hive\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
    }
    
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"objects\":{\"type\":\"Property\",\"value\":{\"boolean\":true,\"date\":\"2023-06-18T15:00:00.000Z\",\"double\":0.1,\"interger\":5,\"string\":\"test\"}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testArrayBoolean\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"Property\",\"value\":[false,true]},\"testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjString\":\"string\"},{\"testArrObjInteger\":10},{\"testArrObjDouble\":0.1},{\"testArrObjBoolean\":true},{\"testArrObjDate\":\"2023-11-17T20:48:09,290+09:00\"}]},\"testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"testBoolean\":{\"type\":\"Property\",\"value\":true},\"testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T20:48:09,290+09:00\"},\"testDouble\":{\"type\":\"Property\",\"value\":0.1},\"testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"coordinates\":[-12,-5],\"type\":\"Point\"}},\"testInteger\":{\"type\":\"Property\",\"value\":10},\"testRelationshipString\":{\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\",\"type\":\"Relationship\"},\"testString\":{\"type\":\"Property\",\"value\":\"valuestring\"},\"type\":\"TestModel3\"}";

    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andDo(print());
      
    String queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"location\":{\"observedAt\": \"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":\"invalid-String\"},\"type\":\"TestModel3\"}";
    /*
       204 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"location\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"objects\":{\"type\":\"Property\",\"value\":{\"date\":\"2023-06-18T15:00:00.000Z\",\"boolean\":true,\"string\":\"test\",\"double\":0.1,\"interger\":5}},\"observationSpace\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\":{\"type\":\"Property\",\"value\":[0.0,1.1]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\":{\"type\":\"Property\",\"value\":[1,2]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\":{\"type\":\"Property\",\"value\":[{\"testArrObjInteger\":10,\"testArrObjDate\":\"2023-11-17T11:48:09.290Z\",\"testArrObjString\":\"string\",\"testArrObjDouble\":0.1,\"testArrObjBoolean\":true}]},\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\":{\"type\":\"Property\",\"value\":[\"test1\",\"test2\"]},\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\":{\"type\":\"Property\",\"value\":true},\"http://uri.citydatahub.kr/ngsi-ld/testDate\":{\"type\":\"Property\",\"value\":\"2023-11-17T11:48:09.290Z\"},\"http://uri.citydatahub.kr/ngsi-ld/testDouble\":{\"type\":\"Property\",\"value\":0.1},\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\":{\"observedAt\":\"2023-06-18T15:00:00.000Z\",\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[-12.0,-5.0]}},\"http://uri.citydatahub.kr/ngsi-ld/testInteger\":{\"type\":\"Property\",\"value\":10},\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\":{\"type\":\"Relationship\",\"object\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e1\"},\"http://uri.citydatahub.kr/ngsi-ld/testString\":{\"type\":\"Property\",\"value\":\"valuestring\"}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);        
  }
}
