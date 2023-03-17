package com.huytd.basecacheredis.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

public class ServletUtils {
    private static final String USER_ID = "user_id";
    private static final String BEARER_PREFIX = "Bearer ";
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static Long getCurrentUserId() {
        HttpServletRequest httpServletRequest = getCurrentRequest();
        if (httpServletRequest != null) {
            Enumeration<String> uidHeaders = httpServletRequest.getHeaders(USER_ID);
            if (uidHeaders.hasMoreElements()) {
                return Long.parseLong(uidHeaders.nextElement());
            }
        }
        return null;
    }
}
