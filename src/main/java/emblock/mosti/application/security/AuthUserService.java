package emblock.mosti.application.security;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.port.in.IMenuService;
import emblock.framework.helper.Do;
import emblock.mosti.application.port.out.IAdminUserRepository;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUserService implements UserDetailsService {
    private static Logger log = LoggerFactory.getLogger(EmbAuthenticationProvider.class);
    
    private final IAdminUserRepository adminUserRepository;
    private final IUserRepository userRepository;
    private final IMenuService menuService;
    public AuthUserService(IAdminUserRepository adminUserRepository, IUserRepository userRepository, IMenuService menuService) {
        
        this.adminUserRepository = adminUserRepository;
        this.userRepository = userRepository;
        this.menuService = menuService;
    }

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
  
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        User user = this.userRepository.조회(loginId.toLowerCase());
        if (Do.비었음(user))
            throw new UsernameNotFoundException("User " + loginId + " does not exists");

        String role = user.getType().getCodeName();
        List<MenuRoleMapping> menuRoleMappingList = this.menuService.사용자별메뉴조회(user.getRoleId());
        List<ApiRoleMapping> apiRoleMappingList = this.menuService.사용자별api리스트조회(user.getRoleId());

        session.setAttribute("apiAuthorityList", apiRoleMappingList);

        return createAuthUser(user, menuRoleMappingList, role, user.getType().name());
    }

    private AuthUser createAuthUser(User user, List<MenuRoleMapping> menuRoleMappingList, String... role) {
        return AuthUser.생성(user, menuRoleMappingList)
                .withGrantedAuthorityList(Arrays.stream(role)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
