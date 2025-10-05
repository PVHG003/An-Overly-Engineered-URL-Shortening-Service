package vn.pvhg.minilink.util;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import vn.pvhg.minilink.auth.model.User;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${jwt.private-key-location}")
    private Resource privateKeyResource;

    @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private Resource publicKeyResource;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(User user) {
        return generateToken(user, Map.of("uuid", user.getUserId()));
    }

    public String generateToken(User user, Map<String, Object> extraClaims) {
        try {
            RSAPrivateKey privateKey = loadPrivateKey();

            // Create JWT claims
            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder();
            claimsBuilder
                    .subject(user.getUsername())
                    .issuer("minilink-auth-server")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("role", user.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
                            .getFirst());

            if (extraClaims != null && !extraClaims.isEmpty()) {
                for (var claim : extraClaims.entrySet()) {
                    claimsBuilder.claim(claim.getKey(), claim.getValue());
                }
            }

            JWTClaimsSet claims = claimsBuilder.build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new RSASSASigner(privateKey));

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    private RSAPrivateKey loadPrivateKey() throws Exception {
        String key = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()));
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    public RSAPublicKey loadPublicKey() throws Exception {
        String key = new String(Files.readAllBytes(publicKeyResource.getFile().toPath()));
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }
}
