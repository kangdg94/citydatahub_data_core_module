package kr.re.keti.sc.dataservicebroker.datamodel.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import kr.re.keti.sc.dataservicebroker.common.code.DataServiceBrokerCode.AttributeValueType;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectMember {
    private String name;
    private String description;
    private Boolean isRequired;
    private Double greaterThan;
    private Double greaterThanOrEqualTo;
    private Double lessThanOrEqualTo;
    private Double lessThan;
    private String maxLength;
    private String minLength;
    private AttributeValueType valueType;
    private List<Object> valueEnum;
    private List<ObjectMember> objectMembers;
}
