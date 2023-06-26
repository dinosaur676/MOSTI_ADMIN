package emblock.mosti.adapter.keycloak;

import java.util.List;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserService {
    private final Keycloak keycloak;
    private static final String REALM_NAME = "mosti-dev";

    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    public List<UserRepresentation> users(){
        UsersResource resource =  keycloak.realm(REALM_NAME).users();
        return  resource.list();
    }

    public void modifyUser(String userId, String email){
        UsersResource usersResource =  keycloak.realm(REALM_NAME).users();
        UserResource resource = usersResource.get(userId);
        UserRepresentation user = usersResource.searchByEmail(email,true).get(0);
        //user.setAttributes();
        resource.update(user);
    }


}
