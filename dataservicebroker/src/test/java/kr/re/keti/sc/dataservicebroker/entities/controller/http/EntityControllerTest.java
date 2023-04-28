package kr.re.keti.sc.dataservicebroker.entities.controller.http;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.BindException;
import java.util.Arrays;
import java.util.HashMap;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdBadRequestException;
// import kr.re.keti.sc.dataservicebroker.common.exception.ngsild.NgsiLdResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Test
  void testCreate001_01() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  /*
   * 409 Already exist
   */
  @Test
  void testCreate001_03() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testCreate001_04() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testCreate001_06() throws Exception {
    /*
     400 Bad Request TDD
*/
    String query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. @context parameter cannot be used when contentType=application/json\"}";
    
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testCreate001_07() throws Exception {
    /*
     201 Bad Request TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testCreate001_08() throws Exception {
    /*
     400 Bad Request TDD
*/
    String query_response_compare =
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Not Found DataModel. datasetId=null, type=TestModel3\"}";
    String inputData =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testCreate001_09() throws Exception {
    /*
     400 Bad Request TDD
*/
    String query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. Link Header cannot be used when contentType=application/ld+json\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testDelete002_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testDelete002_02() throws Exception {
    /*
 404 No Content
*/
    String query_response_compare = 
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testDelete002_03() throws Exception {
    /*
 404 No Content
*/
    String query_response_compare = 
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testBatchEntityCreation003_01() throws Exception {
    String query_compare_response = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
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
  }

  @Test
  void testBatchEntityCreation003_04() throws Exception {
    String query_response_compare = 
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityCreation003_07() throws Exception {
    String query_response_compare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpsert004_01() throws Exception {
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
    201 No Content
  */
    String query_response_compare = 
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpsert004_02() throws Exception {
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpsert004_03() throws Exception {
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    //if existing then update
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
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpsert004_04() throws Exception {
    //entity create
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
            .param("option", "update")
            .header("Content-Length", String.valueOf(inputData.length()))
        )
        .andExpect(status().isNoContent())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpdate005_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpdate005_02() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityUpdate005_03() throws Exception {
    // failed
    String query_response_compare = 
      "{\"errors\":[{\"entityId\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"error\":{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"}}]}";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    // succeed
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    query_response_compare =
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityDelete006_01() throws Exception {
    String query_response_compare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testBatchEntityDelete006_02() throws Exception {
    //fail
    String query_response_compare = 
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    query_response_compare = 
      "[\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\"]";
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testAppendEntityAttributes010_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\", \"datasetId\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\"}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());
    
    /*
     204 No Content
    */
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testAppendEntityAttributes010_02() throws Exception {
    /*
     204 No Content
    */
    String query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=thisisaninvaliduri\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
      .andExpect(content().string(query_response_compare))
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testAppendEntityAttributes010_03() throws Exception {
    /*
     404 No Content
    */
    String query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/ResourceNotFound\",\"title\":\"Resource Not Found\",\"detail\":\"Invalid Request Content. Not exists entityId=urn:datahub:TestModel\"}";
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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
      .andExpect(content().string(query_response_compare))
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());
    /*
     400 No Content attribute
    */
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testUpdateEntityAttributes011_01() throws Exception {
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
    
    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testUpdateEntityAttributes011_02() throws Exception {
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

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    
    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());
    /*
   204 No Content
*/
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testUpdateEntityAttributes011_03() throws Exception {
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    /*
   400 bad Request invalid attr
*/
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testPartialUpdateWithAttrId012_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
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

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testPartialUpdateWithAttrId012_02() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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
    
    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
    /*
   204 No Content
*/
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid Request Content. entityId is not in URN format. id=invalidurl\"}";
    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";

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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testPartialUpdateWithAttrId012_03() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
    /*
   204 No Content
*/
    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");


    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testDeleteAttr013_02() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testDeleteAttr013_03() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById018_01() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}"; 
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById018_02() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
      "{\"type\":\"https://uri.etsi.org/ngsi-ld/errors/BadRequestData\",\"title\":\"Bad request data\",\"detail\":\"Invalid request parameter. entityId is not in URN format. id=invalidurl\"}"; 
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/invalidurl")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById018_03() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";
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

    String query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById018_04() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare =
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById018_06() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById019_01() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("attrs", "testArrayBoolean")
            .contentType("application/ld+json")
            .header(
              "Link",
              "<http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld>,<https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld>"
            )
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById019_03() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityById019_04() throws Exception {
    /*
     201 Created TDD
*/
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare =
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityTypes022_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityTypes023_01() throws Exception {
    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}";
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(query_response_compare))
        .andDo(print());

    query_response_compare = 
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
        .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntityCount() throws Exception {

    String inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "{\"totalCount\":1}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entitycount")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetEntity() throws Exception {

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "[{\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"http://uri.citydatahub.kr/ngsi-ld/TestModel3\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]}}]";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entities")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetAttribute() throws Exception {

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = 
      "{\"id\":\"urn:ngsi-ld:AttributeList:58208329\",\"type\":\"AttributeList\",\"attributeList\":[\"http://uri.citydatahub.kr/ngsi-ld/testArrayInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testRelationshipString\",\"https://uri.etsi.org/ngsi-ld/observationSpace\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayString\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testInteger\",\"http://uri.citydatahub.kr/ngsi-ld/testGeoJson\",\"http://uri.citydatahub.kr/ngsi-ld/testDate\",\"http://uri.citydatahub.kr/ngsi-ld/testString\",\"http://uri.citydatahub.kr/ngsi-ld/testBoolean\",\"https://uri.etsi.org/ngsi-ld/location\",\"http://uri.citydatahub.kr/ngsi-ld/testDouble\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayBoolean\",\"http://uri.citydatahub.kr/ngsi-ld/testArrayObject\",\"https://uri.etsi.org/ngsi-ld/hasObjects\"]}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(query_response_compare))
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
  }

  @Test
  void testGetAttributeInformation() throws Exception {

    String inputData =
    "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"type\":\"TestModel3\"}";

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

    String query_response_compare = "{\"attributeCount\":0}";
    resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes/urn:ngsi-ld:AttributeList:58208329")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(query_response_compare))
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
  }
}
