package emblock.mosti.application.security;

import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.domain.User;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class AuthUser implements UserDetails, OidcUser {
    private String userName;
    private String password;
    private String loginId;
    private String email;
    private String userId;
    private List<GrantedAuthority> grantedAuthorityList;
    private List<MenuRoleMapping> menuRoleMappingList;
    private Map<String,Object> claims;
    private Map<String,Object> attributes;
    private OidcUserInfo userInfo;
    private OidcIdToken token;

    public AuthUser() {}
    public AuthUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public AuthUser(String userName, List<MenuRoleMapping> menuRoleMappingList,  OidcUser user){
        this.userName = userName;
        this.claims = user.getClaims();
        this.attributes = user.getAttributes();
        this.userInfo = user.getUserInfo();
        this.token = user.getIdToken();

        //this.attributes = attributes;
        this.menuRoleMappingList = menuRoleMappingList;
    }

    public AuthUser(String userName, String loginId, String email, long userId, String password, List<MenuRoleMapping> menuRoleMappingList) {
        this.userName = userName;
        this.loginId = loginId;
        this.email = email;
        this.userId = String.valueOf(userId);
        this.password = password;
        this.menuRoleMappingList = menuRoleMappingList;
    }

    public AuthUser(String userName, String password, List<MenuRoleMapping> menuRoleMappingList) {
        this.userName = userName;
        this.password = password;
        this.menuRoleMappingList = menuRoleMappingList;
    }

    public static AuthUser 생성(User user, List<MenuRoleMapping> menuRoleMappingList) {
       return new AuthUser(user.getUserName()
                         , user.getLoginId()
                         , user.getEmail()
                         , user.getUserId()
                         , user.getPassword(), menuRoleMappingList);
    }

    public static AuthUser builder() {
        return new AuthUser();
    }

    public AuthUser withGrantedAuthorityList(List<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
        return this;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public List<MenuRoleMapping> getMenuRoleMappingList() {
        return menuRoleMappingList;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    public AuthUser withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public AuthUser withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void 패스워드지우기(){
        this.password = "";
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.claims;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.token;
    }

    @Override
    public String getName() {
        return this.userName;
    }
}
