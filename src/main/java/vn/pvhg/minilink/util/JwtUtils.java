package vn.pvhg.minilink.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.private-key-location}")
    private Resource privateKeyResource;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private PrivateKey privateKey;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return generateToken(userDetails, claims);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        return buildToken(userDetails, extraClaims, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, new HashMap<>(), refreshExpiration);
    }

    private String buildToken(UserDetails userDetails, Map<String, Object> extraClaims, long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getPrivateKey(), Jwts.SIG.RS256)
                .compact();
    }

    private PrivateKey getPrivateKey() {
        if (privateKey == null) {
            try {
                privateKey = readPrivateKey(privateKeyResource);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load private key", e);
            }
        }
        return privateKey;
    }

    private PrivateKey readPrivateKey(Resource resource) throws Exception {
        try {
            String key = new String(Files.readAllBytes(resource.getFile().toPath()));

            String privateKeyPEM = key
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read private key from: " + resource.getFilename(), e);
        }
    }


    public Long getAccessTokenExpiry() {
        return jwtExpiration;
    }
}
