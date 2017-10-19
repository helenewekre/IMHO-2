package server.endpoints;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class Filter implements ContainerRequestFilter {
    ContainerRequestContext localContainerRequestContext;
    String httpMethodType;
    private static final String AUTHENTICATION_SCHEME = "Bearer";


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        localContainerRequestContext = containerRequestContext;
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        httpMethodType = containerRequestContext.getMethod().toString();

        try {
            if(validateToken(token) == true){
                // System.out.println("hej");
            }
            else {
                localContainerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateToken(String token) throws Exception {
        boolean isValid = true;

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("IMHO").build();

            DecodedJWT jwt = verifier.verify(token);

            if(jwt.getExpiresAt().before(new Date(System.currentTimeMillis()*1000))) {
                localContainerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch(UnsupportedEncodingException exception) {
            isValid = false;

        } return isValid;
    }
}
