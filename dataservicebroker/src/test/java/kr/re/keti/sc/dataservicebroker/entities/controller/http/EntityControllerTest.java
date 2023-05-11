package kr.re.keti.sc.dataservicebroker.entities.controller.http;

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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdBadRequestException;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
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
public class EntityControllerTest {

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

  @Value("${entity.default.storage}")
  private String datastorage;

  @Test
  void testCreate001_01() throws Exception {

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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

  /*
   * 409 Already exist
   */
  @Test
  void testCreate001_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    queryResponseCompare = 
        "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/AlreadyExists\",\"title\":\"Already Exists\",\"detail\":\"Invalid Request Content. Already exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entities")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isConflict())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testCreate001_04() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
          .header(
            "Link",
            "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
          )
          .header("rel", "http://uri.citydatahub.kr/ngsi-ld/TestModel3")
      )
      .andExpect(status().isCreated())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
  void testCreate001_06() throws Exception {
    
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     400 Bad Request TDD
*/
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. @context parameter cannot be used when contentType=application/json\"}";
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/json")
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
  void testCreate001_07() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     201 Bad Request TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
  void testCreate001_08() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     400 Bad Request TDD
*/
    String queryResponseCompare =
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. No match attribute full uri. attribute name=testArrayBoolean, dataModel attribute uri=http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean but ingest attribute uri=null\"}";
    String inputData =
      "{\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
  void testCreate001_09() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     400 Bad Request TDD
*/
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. Link Header cannot be used when contentType=application/ld+json\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
          .header(
            "Link",
            "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
          )
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
  void testDelete002_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
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
  void testDelete002_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
 404 No Content
*/
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=thisisaninval\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .delete("/entities/thisisaninval")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
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
  void testDelete002_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
 404 No Content
*/
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
      )
      .andExpect(status().isNotFound())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
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
  void testBatchEntityCreation003_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String query_compare_response = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
       201 No Content
    */
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/create")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andExpect(content().string(query_compare_response))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_compare_response = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_compare_response))
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
  void testBatchEntityCreation003_04() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
       204 No Content
    */
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/create")
          .content(inputData)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
          .header(
            "Link",
            "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
          )
      )
      .andExpect(status().isCreated())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
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
  void testBatchEntityCreation003_07() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
       201 No Content
    */
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/create")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
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
  void testBatchEntityUpsert004_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
    201 No Content
  */
    String queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/upsert")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
          .param("options", "replace")
      )
      .andExpect(status().isCreated())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    /*
    204 No Content
   */
    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityUpsert004_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      
    //if existing then update
    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
    204 No Content
  */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/upsert")
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    /*
    204 No Content
   */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
        )
        .andExpect(status().isNoContent())
        //.andExpect(content().string("{\"id\":\"TDD\"}"))
        .andDo(print());

    // if not existing then create
    queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/upsert")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isCreated())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    /*
    204 No Content
   */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
        )
        .andExpect(status().isNoContent())
        //.andExpect(content().string("{\"id\":\"TDD\"}"))
        .andDo(print());
        
    //데이터 셋 플로우 삭제
    datasetFlowSVC.deleteDatasetFlow("/datasets/TestModel3/flow", "");

    //데이터 셋 삭제
    datasetSVC.deleteDataset("/datasets/TestModel3", "");

    //데이터 모델 삭제
    dataModelSVC.processDelete("/datamodels/TestModel3", "TestModel3",requestId, now);        
  }

  @Test
  void testBatchEntityUpsert004_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    //if existing then update
    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]},\"type\":\"TestModel3\"}]";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/upsert")
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityUpsert004_04() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    //if existing then update
    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]},\"type\":\"TestModel3\"}]";
    /*
    204 No Content
  */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/upsert")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .param("option", "update")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityUpdate005_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}]";
    /*
 204 No Content
*/
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/update")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
            .param("options", "overwrite")
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityUpdate005_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}]";
    /*
 204 No Content
*/
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/update")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
            .param("option", "NoOverwrite")
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    /*
    204 No Content
   */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityUpdate005_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    // failed
    String queryResponseCompare = 
      "{\"errors\":[{\"entityId\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"error\":{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}}]}";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    /*
 207 No content
*/
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/update")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
          .param("option", "Overwrite")
      )
      .andExpect(status().isMultiStatus())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    // succeed
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
    resultActions =
      mvc
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

    queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}]";
    /*
 204 No Content
*/
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/update")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
            .param("option", "NoOverwrite")
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    /*
    204 No Content
   */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
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
  void testBatchEntityDelete006_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 

    wait(3000);

    String queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
       201 No Content
    */
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/create")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isCreated())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
        )
        .andExpect(status().isNoContent())
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
  void testBatchEntityDelete006_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    //fail
    String queryResponseCompare = 
      "{\"errors\":[{\"entityId\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"error\":{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}}]}";
    String body = "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    /*
    207 No Content
   */
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entityOperations/delete")
          .content(body)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(body.length()))
      )
      .andExpect(status().isMultiStatus())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    queryResponseCompare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    /*
       201 No Content
    */
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/entityOperations/create")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isCreated())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
            .post("/entityOperations/delete")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(body.length()))
        )
        .andExpect(status().isNoContent())
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
  void testAppendEntityAttributes010_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\", \"datasetId\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\"}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
  void testAppendEntityAttributes010_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     204 No Content
    */
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=thisisaninvaliduri\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities/thisisaninvaliduri/attrs")
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
  void testAppendEntityAttributes010_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    /*
     404 No Content
    */
    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .post("/entities/urn:datahub:TestModel/attrs")
          .content(inputData)
          .contentType("application/ld+json")
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
          .header("Content-Length", String.valueOf(inputData.length()))
      )
      .andExpect(status().isNotFound())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\", \"datasetId\":\"TestModel3\"}";
    resultActions =
      mvc
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
     400 No Content attribute
    */
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"invalid key : testinvalid\"}";
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalid\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testUpdateEntityAttributes011_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\", \"datasetId\":\"TestModel3\"}";
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
    
    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]},\"type\":\"TestModel3\"}";
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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
  void testUpdateEntityAttributes011_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\", \"datasetId\":\"TestModel3\"}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    
    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=invalidurl\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch("/entities/invalidurl/attrs")
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
  void testUpdateEntityAttributes011_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\", \"datasetId\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    /*
   400 bad Request invalid attr
*/
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"invalid key : testinvalidattr\"}";
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
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    /*
   400 bad request invalid id
*/
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=invalidid\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch("/entities/invalidid/attrs")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testPartialUpdateWithAttrId012_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
    /*
   204 No Content
*/
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs/testArrayBoolean"
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

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[true,true]}}";
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
  void testPartialUpdateWithAttrId012_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
    /*
   204 No Content
*/
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"invalid key : testinvalidattr\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs/testArrayBoolean"
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

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=invalidurl\"}";
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch("/entities/invalidurl/attrs/testArrayBoolean")
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
  void testPartialUpdateWithAttrId012_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
    /*
   204 No Content
*/
    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"invalid key : testinvalidattr\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs/testArrayBoolean"
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

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");


    queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_testinvalid\"}";
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .patch(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_testinvalid/attrs/testArrayBoolean"
            )
            .content(inputData)
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNotFound())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
  void testDeleteAttr013_02() throws Exception {
    if(datastorage.equals("rdb")) {
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
      "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
      datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

      //데이터 셋 플로우 조회
      DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
      if (datasetFlowBaseVO == null) {
        throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
        
      } 
      String inputData =
        "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

      String queryResponseCompare = 
        "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

      queryResponseCompare = 
        "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=invalidurl\"}";
      resultActions =
        mvc
          .perform(
            MockMvcRequestBuilders
              .delete("/entities/invalidurl/attrs/testArrayBoolean")
              .contentType("application/json")
              .accept(MediaType.APPLICATION_JSON)
              .characterEncoding("utf-8")
          )
          .andExpect(status().isBadRequest())
          .andExpect(content().string(queryResponseCompare))
          .andDo(print());

      MvcResult mvcResult = resultActions.andReturn();
      System.out.println("=====================Post=====================");
      System.out.println(mvcResult.getResponse().getContentAsString());
      System.out.println("=====================End=====================");

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

  @Test
  // @ConditionalOnExpression(datastorage.equals())
  void testDeleteAttr013_03() throws Exception {
    if(datastorage.equals("rdb")) {
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
        "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
        datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);
    
        //데이터 셋 플로우 조회
        DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
        if (datasetFlowBaseVO == null) {
          throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
          
        } 
        String inputData =
          "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
        String queryResponseCompare = 
          "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
    
        queryResponseCompare = 
          "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_test\"}";
        resultActions =
          mvc
            .perform(
              MockMvcRequestBuilders
                .delete(
                  "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_test/attrs/testArrayBoolean"
                )
                .contentType("application/ld+json")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string(queryResponseCompare))
            .andDo(print());
    
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("=====================Post=====================");
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("=====================End=====================");
    
        queryResponseCompare = 
          "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Not exists Entity Attribute. entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e, attrId=testArrayBooleanNotknown\"}";
        resultActions =
          mvc
            .perform(
              MockMvcRequestBuilders
                .delete(
                  "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e/attrs/testArrayBooleanNotknown"
                )
                .contentType("application/ld+json")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string(queryResponseCompare))
            .andDo(print());
    
        mvcResult = resultActions.andReturn();
        System.out.println("=====================Post=====================");
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("=====================End=====================");
    
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

  @Test
  void testGetEntityById018_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}"; 
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

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById018_02() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid request parameter. entityId is not in URN format. id=invalidurl\"}"; 
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/invalidurl")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById018_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"There is no Entity instance with the requested identifier.\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_test"
            )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById018_04() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":[false,true]}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
            .param("options", "keyValues")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById018_06() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
            .header(
              "Link",
              "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById019_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .param("attrs", "testArrayBoolean")
            .contentType("application/ld+json")
            .header(
              "Link",
              "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById019_03() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid request parameter. entityId is not in URN format. id=invalidurl\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "invalidurl")
            .param("attrs", "testArrayBoolean")
            .contentType("application/ld+json")
            .header(
              "Link",
              "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
            )
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityById019_04() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
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
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare =
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":[false,true]}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("options", "keyValues")
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .contentType("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityTypes022_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    queryResponseCompare = 
      "{\"id\":\"urn:ngsi-ld:EntityTypeList:37418953\",\"type\":\"EntityTypeList\",\"typeList\":[\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\"]}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/types")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityTypes023_01() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    queryResponseCompare = 
      "[{\"id\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"type\":\"EntityType\",\"typeName\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"attributeNames\":[\"https://uri.etsi.org/ngsi-ld/location\",\"https://uri.etsi.org/ngsi-ld/hasObjects\",\"https://uri.etsi.org/ngsi-ld/observationSpace\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\",\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\",\"http://uri.citydatahub.kr/ngsi-ld/testDate\",\"http://uri.citydatahub.kr/ngsi-ld/testDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\",\"http://uri.citydatahub.kr/ngsi-ld/testInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\",\"http://uri.citydatahub.kr/ngsi-ld/testString\"]}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/types")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json")
            .param("details", "True")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(queryResponseCompare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntityCount() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 

    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"totalCount\":1}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entitycount")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetEntity() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entities")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetAttribute() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = 
      "{\"id\":\"urn:ngsi-ld:AttributeList:58208329\",\"type\":\"AttributeList\",\"attributeList\":[\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\",\"https://uri.etsi.org/ngsi-ld/observationSpace\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\",\"http://uri.citydatahub.kr/ngsi-ld/testDate\",\"http://uri.citydatahub.kr/ngsi-ld/testString\",\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\",\"https://uri.etsi.org/ngsi-ld/location\",\"http://uri.citydatahub.kr/ngsi-ld/testDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\",\"https://uri.etsi.org/ngsi-ld/hasObjects\"]}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
  void testGetAttributeInformation() throws Exception {
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
    "{\"historyStoreType\":\"all\",\"enabled\":true,\"bigDataStorageTypes\":[\""+ datastorage + "\"]}";
    datasetFlowSVC.createDatasetFlow("/datasets/TestModel3/flow", inputData_datasetflow, requestId, now);

    //데이터 셋 플로우 조회
    DatasetFlowBaseVO datasetFlowBaseVO = datasetFlowRetrieveSVC.getDatasetFlowBaseVOById(datasetId);
    if (datasetFlowBaseVO == null) {
      throw new BadRequestException(ErrorCode.NOT_EXIST_ID, "Not Exists data set flow. datasetId = " + datasetId);
      
    } 

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String queryResponseCompare = "{\"attributeCount\":0}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes/urn:ngsi-ld:AttributeList:58208329")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(queryResponseCompare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
