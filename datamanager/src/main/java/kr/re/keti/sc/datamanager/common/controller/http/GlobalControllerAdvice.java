package kr.re.keti.sc.datamanager.common.controller.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.re.keti.sc.datamanager.common.code.DataManagerCode;
import kr.re.keti.sc.datamanager.common.code.ResponseCode;
import kr.re.keti.sc.datamanager.common.exception.BadRequestException;
import kr.re.keti.sc.datamanager.common.exception.ErrorPayload;
import kr.re.keti.sc.datamanager.common.exception.InternalServerErrorException;
import kr.re.keti.sc.datamanager.common.exception.LengthRequiredException;
import kr.re.keti.sc.datamanager.common.exception.NoExistTypeException;
import kr.re.keti.sc.datamanager.common.exception.OperationNotSupportedException;
import kr.re.keti.sc.datamanager.common.exception.ProvisionException;
import kr.re.keti.sc.datamanager.common.exception.ResourceNotFoundException;
import kr.re.keti.sc.datamanager.common.exception.ngsild.NgsiLdResourceNotFoundException;
import kr.re.keti.sc.datamanager.util.ErrorUtil;

/**
 * HTTP 에러 공통 처리 클래스
 */
@ControllerAdvice //(basePackages = {"kr.re.keti.sc.datamanager.controller", "error"}) 404 에러 처리를 위해 주석처리
public class GlobalControllerAdvice {

	@Autowired
    private ObjectMapper objectMapper;
	
    @Value("${datacore.http.binding.cause.msg.trace.key:x-detail-error-key}")
    private String debugMessageKey;
    @Value("${datacore.http.binding.cause.msg.level:1}")
    private int causeMessageLevel;
    
    private HttpHeaders headers;


    public GlobalControllerAdvice() {
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    /**
     * <pre>
     * URL not found 에러 공통 처리 (404)
     * 잘 못된 HTTP method + url 요청이 올 Method not allowed 경우로 처리
     * </pre>
     * @param request HttpServletRequest
     * @param e NoHandlerFoundException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorPayload> notFoundErrorException(HttpServletRequest request, NoHandlerFoundException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.METHOD_NOT_ALLOWED.getHttpStatusCode());
    }

    /**
     * Provisioning 시 오류 (400 or 500)
     * @param request HttpServletRequest
     * @param e ProvisionException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(ProvisionException.class)
    public ResponseEntity<ErrorPayload> provisionException(HttpServletRequest request, ProvisionException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        
        HttpStatus httpStatus = HttpStatus.valueOf(e.getProvisioningStatusCode());
        if(httpStatus == null) {
        	httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errorPayload, headers, httpStatus);
    }

    /**
     * 개발자 정의 에러 공통 처리
     * @param request HttpServletRequest
     * @param e BadRequestException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorPayload> badRequestException(HttpServletRequest request, BadRequestException e) {

        String errorCode = e.getErrorCode();

        if (errorCode.equals(DataManagerCode.ErrorCode.ALREADY_EXISTS.getCode())) {

            // CREATE 시, 중복 id가 있을 경우, 에러에 대한 공통 처리 (409)
            ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
            if (request.getHeader(debugMessageKey) != null) {
                errorPayload.setDebugMessage(makeDebugMessage(e));
            }
            return new ResponseEntity<>(errorPayload, headers, ResponseCode.CONFLICT.getHttpStatusCode());

        } else {

            // 잘 못된 요청에 대한 에러 공통 처리 (400)
            ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
            if (request.getHeader(debugMessageKey) != null) {
                errorPayload.setDebugMessage(makeDebugMessage(e));
            }
            return new ResponseEntity<>(errorPayload, headers, ResponseCode.BAD_REQUEST_DATA.getHttpStatusCode());

        }

    }


    /**
     * 관련된 resource를 찾지 못함에 따른 에러 공통 처리 (400)
     * @param request HttpServletRequest
     * @param e ResourceNotFoundException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorPayload> resourceNotFoundException(HttpServletRequest request, ResourceNotFoundException e) {

        // 존재하지 않는 리소스 요청에 대한 에러 공통 처리 (404)
        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.RESOURCE_NOT_FOUND.getHttpStatusCode());

    }

    /**
     * 잘 못된 HTTP Method 요청에 대한 에러 공통 처리 (405)
     * @param request HttpServletRequest
     * @param e HttpRequestMethodNotSupportedException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorPayload> httpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {


        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.METHOD_NOT_ALLOWED.getHttpStatusCode());
    }

    /**
     * Unsupported Media Type 요청에 대한 공통 에러 처리 (415)
     * @param request HttpServletRequest
     * @param e HttpMediaTypeNotSupportedException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorPayload> unsupportedMediaTypeStatusException(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));

        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.UNSUPPORTED_MEDIA_TYPE.getHttpStatusCode());
    }
//

    /**
     * POST 요청 시, Content-Length header가 없을 경우 예외 처리
     * @param request HttpServletRequest
     * @param e LengthRequiredException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(LengthRequiredException.class)
    public ResponseEntity<ErrorPayload> lengthRequiredException(HttpServletRequest request, LengthRequiredException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));

        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.BAD_REQUEST_DATA.getHttpStatusCode());
    }

    /**
     * POST body가 오류가 있을 경우, 공통 에러 처리 (400)
     * @param request HttpServletRequest
     * @param e HttpMessageNotReadableException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorPayload> httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));

        }
        return new ResponseEntity<ErrorPayload>(errorPayload, headers, ResponseCode.BAD_REQUEST_DATA.getHttpStatusCode());
    }

    /**
     * 서버 내부 오류가 있을 경우, 공통 에러 처리 (500)
     * @param request HttpServletRequest
     * @param e InternalServerErrorException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorPayload> internalServerErrorException(HttpServletRequest request, InternalServerErrorException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));

        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatusCode());
    }

    /**
     * DB 커넥션 에러 시, 공통 처리 (500)
     * @param request HttpServletRequest
     * @param e SQLException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(java.sql.SQLException.class)
    public ResponseEntity<ErrorPayload> sqlException(HttpServletRequest request, java.sql.SQLException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));

        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatusCode());
    }


    /**
     * <pre>
     * JSON 파싱 시, 에러가 날 경우 공통 처리 (400)
     *  - 5.5.4. If the request payload body is not a valid JSON document then an error of type InvalidRequest shall be raised.
     * </pre>
     * @param request HttpServletRequest
     * @param e JsonProcessingException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorPayload> jsonParseException(HttpServletRequest request, JsonProcessingException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }

        return new ResponseEntity<>(errorPayload, headers, ResponseCode.INVALID_REQUEST.getHttpStatusCode());
    }

    /**
     * <pre>
     * JSON 파싱 시, 에러가 날 경우 공통 처리 (400)
     *  - 5.5.4. If the request payload body is not a valid JSON document then an error of type InvalidRequest shall be raised.
     * </pre>
     * @param request HttpServletRequest
     * @param e JSONException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ErrorPayload> jsonException(HttpServletRequest request, JSONException e) {

        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.INVALID_REQUEST.getHttpStatusCode());
    }

    /**
     * <pre>
     * 존재하지 않는 엔티티 타입 명시 시 에러 처리 (200)
     *  ex) http://{{hostname}}/entities?type=StreetParking,
     *  요청 메시지에는 이상이 없어 검색을 수행할 수 있음.
     *  단, 해당하는 결과가 없으므로 200 OK에 empty entities array 반환
     * </pre>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param e NoExistTypeException
     * @throws IOException
     */
    @ExceptionHandler(NoExistTypeException.class)
    public void noExistTypeException(HttpServletRequest request, HttpServletResponse response, NoExistTypeException e) throws IOException {
        response.getWriter().print(objectMapper.writeValueAsString(new ArrayList<>()));
    }


    /**
     * 인증 오류 예외 처리 (401)
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param e AccessDeniedException
     * @return HTTP Response Entity
     * @throws IOException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorPayload> accessDeniedException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {

        ErrorPayload errorPayload = new ErrorPayload(ResponseCode.UNAUTHORIZED.getDetailType(), ResponseCode.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.UNAUTHORIZED.getHttpStatusCode());

    }
    /**
     * 미 개발 operation 예외 처리 (422)
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param e OperationNotSupportedException
     * @return HTTP Response Entity
     * @throws IOException
     */
    @ExceptionHandler(OperationNotSupportedException.class)
    public ResponseEntity<ErrorPayload> operationNotSupportedException(HttpServletRequest request, HttpServletResponse response, OperationNotSupportedException e) throws IOException {

        ErrorPayload errorPayload = new ErrorPayload(ResponseCode.OPERATION_NOT_SUPPORTED.getDetailType(), ResponseCode.OPERATION_NOT_SUPPORTED.getReasonPhrase(), "The operation is not supported");
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, ResponseCode.OPERATION_NOT_SUPPORTED.getHttpStatusCode());

    }


    /**
     * 관련된 resource를 찾지 못함에 따른 에러 공통 처리 (404)
     * @param request HttpServletRequest
     * @param e NgsiLdResourceNotFoundException
     * @return HTTP Response Entity
     */
    @ExceptionHandler(NgsiLdResourceNotFoundException.class)
    public ResponseEntity<ErrorPayload> resourceNotFoundException(HttpServletRequest request, NgsiLdResourceNotFoundException e) {

        // 존재하지 않는 리소스 요청에 대한 에러 공통 처리 (404)
        ErrorPayload errorPayload = ErrorUtil.convertExceptionToErrorPayload(e);
        if (request.getHeader(debugMessageKey) != null) {
            errorPayload.setDebugMessage(makeDebugMessage(e));
        }
        return new ResponseEntity<>(errorPayload, headers, HttpStatus.NOT_FOUND);

    }
    /**
     * 상세 error message(debugMessage)를 포함하는 ErrorPayload 생성
     * @param e Exception
     * @return error payload
     */
    private String makeDebugMessage(Exception e) {

        StringBuilder errorMsg = new StringBuilder();
        List<Throwable> throwableList = ExceptionUtils.getThrowableList(e);

        // 발생한 throwable 레벨보다 application cause 레벨이 클 경우
        // 발생한 cause 레벨의 길이만큼만 조회함
        int throwableLevel;
        if (causeMessageLevel > throwableList.size()) {
            throwableLevel = throwableList.size();
        } else {
            throwableLevel = causeMessageLevel;
        }

        for (int i = 0; i < throwableLevel; i++) {
            errorMsg.append(throwableList.get(i).getMessage());
        }
        return errorMsg.toString();
    }

}

