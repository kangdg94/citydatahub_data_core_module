package kr.re.keti.sc.datacoreui.api.externalplatformauth.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * This is the VO class used when responding to the list of external platform auth.
 * @FileName ExternalPlatformAuthListResponseVO.java
 * @Project citydatahub_datacore_ui
 * @Brief 
 * @Version 1.0
 * @Date 2022. 3. 23.
 * @Author Elvin
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalPlatformAuthListResponseVO {
	/** Total count */
	private Integer totalCount;
	/** Response list of external platform authentication  */
	private List<ExternalPlatformAuthResponseVO> externalPlatformAuthResponseVO;
}
