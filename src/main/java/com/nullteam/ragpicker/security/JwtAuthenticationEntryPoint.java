package com.nullteam.ragpicker.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p>Title: JwtAuthenticationEntryPoint.java</p>
 * <p>Package: com.nullteam.ragpicker.security</p>
 * <p>Description:</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/03/18
 * @author WangJie <i@i8e.net>
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
