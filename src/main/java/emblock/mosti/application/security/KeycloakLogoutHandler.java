package emblock.mosti.application.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KeycloakLogoutHandler implements LogoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication auth) {
        logoutFromKeycloak(auth);
    }

    private void logoutFromKeycloak(Authentication auth) {
        var user = (OidcUser) auth.getPrincipal();

        String endSessionEndpoint = "/protocol/openid-connect/logout";
        logger.debug(endSessionEndpoint);

        WebClient.builder()
                 .baseUrl(String.valueOf(user.getIssuer()))
                 .build().get()
                 .uri(uriBuilder ->
                    uriBuilder.path(endSessionEndpoint)
                        .queryParam("post_logout_redirect_uri","http://localhost:8190/")
                        .queryParam("id_token_hint", user.getIdToken().getTokenValue())
                        .queryParam("state",user.<String>getAttribute("session_state"))
                              .build()
                )
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                 .retrieve()
                 .bodyToMono(Void.class)
                 .block();

/*
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfulley logged out from Keycloak");
        } else {
            logger.error("Could not propagate logout to Keycloak");
        }*/
    }
}