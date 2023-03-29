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
  }

  @Test
  void testCreate001_06() throws Exception {
    /*
     400 Bad Request TDD
*/
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
  }

  @Test
  void testCreate001_08() throws Exception {
    /*
     400 Bad Request TDD
*/
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .delete("/entities/thisisaninval")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
      )
      .andExpect(status().isBadRequest())
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
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .delete("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .characterEncoding("utf-8")
      )
      .andExpect(status().isNotFound())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Delete=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testBatchEntityCreation003_01() throws Exception {
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
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
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
  }

  @Test
  void testBatchEntityCreation003_04() throws Exception {
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
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
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
  }

  @Test
  void testBatchEntityCreation003_07() throws Exception {
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
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
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
  }

  @Test
  void testBatchEntityUpsert004_01() throws Exception {
    String inputData =
      "[{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"datasetId\":\"TestModel3\",\"id\":\"urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}]";
    /*
    201 No Content
  */
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
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", "urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
        )
        .andExpect(status().isOk())
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
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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
    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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
      .andDo(print());

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
  }

  @Test
  void testAppendEntityAttributes010_02() throws Exception {
    /*
     204 No Content
    */
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print());
    /*
     400 No Content attribute
    */
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
    /*
   204 No Content
*/
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
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print());

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    /*
   400 bad Request invalid attr
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

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"type\":\"TestModel3\",\"datasetId\":\"TestModel3\",\"testArrayBoolean\":{\"type\":\"Property\",\"value\":[false,true]},\"type\":\"TestModel3\"}";
    /*
   400 bad request invalid id
*/
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

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
        .andExpect(status().isBadRequest())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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

    inputData =
      "{\"@context\":[\"http://uri.citydatahub.kr/ngsi-ld/testmodel2.jsonld\",\"https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld\"],\"testinvalidattr\":{\"type\":\"Property\",\"value\":[false,true]}}";
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
        .andExpect(status().isBadRequest())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
        .andDo(print());

    mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
            .delete("/entities/invalidurl/attrs/testArrayBoolean")
            .contentType("application/json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isBadRequest())
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
            .delete(
              "/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e_test/attrs/testArrayBoolean"
            )
            .contentType("application/ld+json")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
        )
        .andExpect(status().isNotFound())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Post=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/invalidurl")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest())
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

    resultActions =
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/entities/urn:datahub:TestModel3:70-b3-d5-67-60-00-5c-1e")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");

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
            .get("/types")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json")
        )
        .andExpect(status().isOk())
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
            .get("/types")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json")
            .param("details", "True")
        )
        .andExpect(status().isOk())
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
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entitycount")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testGetEntity() throws Exception {
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/entities")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testGetAttribute() throws Exception {
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }

  @Test
  void testGetAttributeInformation() throws Exception {
    ResultActions resultActions = mvc
      .perform(
        MockMvcRequestBuilders
          .get("/attributes/urn:ngsi-ld:AttributeList:58208329")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("=====================Query=====================");
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println("=====================End=====================");
  }
}
