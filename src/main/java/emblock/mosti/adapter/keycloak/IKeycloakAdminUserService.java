package emblock.mosti.adapter.keycloak;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface IKeycloakAdminUserService {
    @PostExchange(url = "/realms/master/protocol/openid-connect/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> getToken(@RequestParam String client_id, @RequestParam String username, @RequestParam String password, @RequestParam String grant_type);
    //ResponseEntity<String> getToken(@RequestParam Map param);

    @GetExchange(url = "/admin/realms/mosti-dev/users")
    ResponseEntity<List<Map>> getUsers(@RequestHeader(value = "Authorization") String token);

}
