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
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtils {

    @Value("${jwt.tokenExpiration:7200}")
    private Long tokenExpiration;

    private KeyPair keyPair;

    @Value("${jwt.public-key}")
    private String publicKeyString;

    @Value("${jwt.private-key}")
    private String privateKeyString;

    @PostConstruct
    public void init() throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", ""));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        keyPair = new KeyPair(publicKey, privateKey);
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
//        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyPair.getPublic());
//        if (!jwsObject.verify(verifier)) {
//            throw new JOSEException("Invalid signature");
//        }
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
