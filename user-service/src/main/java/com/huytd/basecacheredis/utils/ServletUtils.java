package com.huytd.basecacheredis.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {
    private static final String USER_ID = "user_id";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION = "authorization";
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static String getBearerToken() {
        HttpServletRequest httpServletRequest = getCurrentRequest();
        if (httpServletRequest != null) {
            String bearerToken = httpServletRequest.getHeader(AUTHORIZATION);
           if (StringUtils.isNotBlank(bearerToken)) {
               String[] parts = bearerToken.split(" ");
               if (parts.length != 2 || !parts[0].equalsIgnoreCase("Bearer")) {
                   return null;
               }
               return parts[1];
           }
           return null;
        }
        return null;
    }
}
