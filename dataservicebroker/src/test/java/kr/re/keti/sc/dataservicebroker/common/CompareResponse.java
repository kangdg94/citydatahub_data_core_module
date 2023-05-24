package kr.re.keti.sc.dataservicebroker.common;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.ErrorCode;
import kr.re.keti.sc.dataservicebroker.common.exception.BadRequestException;

@Component
public class CompareResponse {

    @Autowired
    private ObjectMapper objectMapper;

    public void compareResponseBody(ResultActions resultActions, String responseCompare) throws Exception {
    
        //response body 가져오기
        MvcResult mvcResult = resultActions.andReturn();
        // mvcResult.getResponse().getContentAsString();
        
        HashMap<String, Object> jsonResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),HashMap.class);
        

        HashMap<String, Object> jsonResponseCompare = objectMapper.readValue(responseCompare, HashMap.class);

        //key값의 개수가 같은지 비교
        if (jsonResponse.size() == jsonResponseCompare.size()) {
        
        for(HashMap.Entry<String, Object> entry : jsonResponse.entrySet()) {
            if(jsonResponseCompare.get(entry.getKey()).equals(entry.getValue())){
            continue;
            } else {
            throw new BadRequestException(ErrorCode.REQUEST_MESSAGE_PARSING_ERROR, "ResponseBody Not Matched");
            }
                
            }

        
        } else {
        throw new BadRequestException(ErrorCode.REQUEST_MESSAGE_PARSING_ERROR, "ResponseBody Not Matched");
        
        }
        
    }
    
}
