package com.sparta.doing.jwt;

import com.sparta.doing.exception.ExceptionCode;
import com.sparta.doing.exception.apierror.ApiError;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        // response.sendRedirect("/exception/entrypoint");

        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            setResponse(response, ExceptionCode.UNKNOWN_ERROR);
        }
        // 잘못된 타입의 토큰인 경우
        else if (exception.equals(ExceptionCode.INVALID_SIGNATURE_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.INVALID_SIGNATURE_TOKEN);
        }
        // 토큰 만료된 경우
        else if (exception.equals(ExceptionCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.EXPIRED_TOKEN);
        }
        // 지원되지 않는 토큰인 경우
        else if (exception.equals(ExceptionCode.UNSUPPORTED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
        }
        // 토큰이 없거나 이상한 값이 들어온 경우
        else if (exception.equals(ExceptionCode.WRONG_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.WRONG_TOKEN);
        } else {
            setResponse(response, ExceptionCode.ACCESS_DENIED);
        }
    }

    // 한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response,
                             ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", exceptionCode.getCode());
        responseJson.put("timestamp", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        responseJson.put("message", exceptionCode.getMessage());

        response.getWriter().print(responseJson);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
