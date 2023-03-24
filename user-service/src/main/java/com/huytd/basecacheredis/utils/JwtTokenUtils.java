package com.huytd.basecacheredis.utils;

import com.huytd.basecacheredis.constant.TokenField;
import com.huytd.basecacheredis.constant.TokenTypeEnum;
import com.huytd.basecacheredis.dto.Oauth2AccessToken;
import com.huytd.basecacheredis.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtils {

    @Value("${jwt.tokenExpiration:3600}")
    private Long tokenExpiration;

    private KeyPair keyPair;

    @PostConstruct
    public void init() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyPairGenerator.initialize(2048, random);
        keyPair = keyPairGenerator.generateKeyPair();
    }

    public String generateAccessToken(User user) throws JOSEException {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenExpiration * 1000);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .expirationTime(expirationDate)
                .claim("user_id", user.getId())
                .claim("email", user.getEmail())
                .claim("token_type", TokenTypeEnum.ACCESS_TOKEN.getCode())
                .claim("role", "")
                .build();

        return signTokenRS256(claimsSet);
    }

    public Long getUserIdFromToken(String token) throws JOSEException, ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyPair.getPublic());
        if (!jwsObject.verify(verifier)) {
            throw new JOSEException("Invalid signature");
        }

        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        Date expiration = claimsSet.getExpirationTime();
        if (expiration != null && expiration.before(new Date())) {
            throw new JOSEException("Token is expired");
        }

        return (Long) claimsSet.getClaim(TokenField.USER_ID);
    }

    @SneakyThrows
    public Integer getTokenType(String token) {
        JWSObject jwsObject = JWSObject.parse(token);

        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        Date expiration = claimsSet.getExpirationTime();
        if (expiration != null && expiration.before(new Date())) {
            throw new JOSEException("Token is expired");
        }

        return Integer.valueOf(claimsSet.getClaim(TokenField.TOKEN_TYPE).toString());
    }


    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    @SneakyThrows
    public Oauth2AccessToken generateOauth2AccessToken(User user) {
        return Oauth2AccessToken
                .builder()
                .accessToken(generateAccessToken(user))
                .refreshToken(generateRefreshToken(user))
                .build();
    }

    private String generateRefreshToken(User user) throws JOSEException {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenExpiration * 1000 * 2);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .expirationTime(expirationDate)
                .claim("user_id", user.getId())
                .claim("email", user.getEmail())
                .claim("token_type", TokenTypeEnum.REFRESH_TOKEN.getCode())
                .build();

        return signTokenRS256(claimsSet);
    }

    private String signTokenRS256(JWTClaimsSet claimsSet) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        RSASSASigner signer = new RSASSASigner(keyPair.getPrivate());
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }
}
