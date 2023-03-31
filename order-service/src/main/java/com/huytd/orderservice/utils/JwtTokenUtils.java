package com.huytd.orderservice.utils;

import com.huytd.orderservice.constant.TokenField;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class JwtTokenUtils {

    public Long getUserIdFromToken() throws JOSEException, ParseException {
        JWTClaimsSet claimsSet = getClaims();
        return (Long) claimsSet.getClaim(TokenField.USER_ID);
    }

    public String getEmailFromToken() throws ParseException, JOSEException {
        JWTClaimsSet claimsSet = getClaims();
        return (String) claimsSet.getClaim(TokenField.EMAIL);
    }

    private JWTClaimsSet getClaims() throws ParseException, JOSEException {
        String token = ServletUtils.getBearerToken();
        JWSObject jwsObject = JWSObject.parse(Objects.requireNonNull(token));
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        Date expiration = claimsSet.getExpirationTime();
        if (expiration != null && expiration.before(new Date())) {
            throw new JOSEException("Token is expired");
        }
        return claimsSet;
    }
}
