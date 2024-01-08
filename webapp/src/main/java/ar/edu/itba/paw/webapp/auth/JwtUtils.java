package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;

@Component
public class JwtUtils {

    private static final Duration EXPIRY_TIME = Duration.ofHours(1);
    private static final Duration REFRESH_EXPIRY_TIME = Duration.ofDays(7);

//    https://www.rfc-editor.org/rfc/rfc7519.html#page-9
    private static final String ENCODING = "UTF-8";

    private final Algorithm algorithm;
    private final JWTVerifier verifier;



    @Value("classpath:jwtsecret")
    private Resource jwtSecret;

    public JwtUtils(@Value("classpath:jwtsecret") final Resource jwtSecret) throws IOException {
        this.algorithm = Algorithm.HMAC512(FileCopyUtils.copyToString(new InputStreamReader(jwtSecret.getInputStream())));
        this.verifier = JWT.require(this.algorithm).build();
    }

    private String encodeEmail(final String email) {
        try {
            return URLEncoder.encode(email,ENCODING);
        }catch (Exception e){
            throw new IllegalStateException();
        }

    }

    private String decodeEmail(final String email) {
        try {
            return URLDecoder.decode(email, ENCODING);
        }catch (Exception e){
            throw new IllegalStateException();
        }
    }

    public String getEmail(final String token){
        try {
            final DecodedJWT decodedJWT = this.verifier.verify(token);
            return decodeEmail(decodedJWT.getSubject());
        }catch (Exception e){
            return null;
        }
    }

    public String createToken(final User user){
        final Instant instant = Instant.now();
        return JWT.create()
                .withSubject(encodeEmail(user.getEmail()))
                .withClaim("role",user.getRole())  //TODO: ver que hacemos cuando se cambia
                    //TODO: ver de agregar el URL al usuario
                .withIssuedAt(instant)
                .withExpiresAt(instant.plus(EXPIRY_TIME))
                .sign(this.algorithm);
    }

    public String createRefreshToken(final User user){
        final Instant instant = Instant.now();
        return JWT.create()
                .withSubject(encodeEmail(user.getEmail()))
                .withClaim("refresh",true)
                .withIssuedAt(instant)
                .withExpiresAt(instant.plus(REFRESH_EXPIRY_TIME))
                .sign(this.algorithm);
    }

    public boolean validateToken(final String token){
        try{
            final DecodedJWT decodedJWT = this.verifier.verify(token);
            //check if token is not expired
            return Instant.now().isBefore(decodedJWT.getExpiresAtAsInstant());
        }catch (Exception e){
            return false;
        }
    }

    public boolean isRefreshToken(final String token){
        try {
            final DecodedJWT decodedJWT = this.verifier.verify(token);
            return decodedJWT.getClaim("refresh").asBoolean();
        }catch (Exception e){
            return false;
        }
    }



}
