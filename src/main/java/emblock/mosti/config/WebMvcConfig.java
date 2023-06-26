package emblock.mosti.config;

import emblock.mosti.adapter.keycloak.IKeycloakAdminUserService;
import emblock.mosti.application.security.ApiRoleCheckInterceptor;
import emblock.mosti.application.security.MenuRoleCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final MenuRoleCheckInterceptor menuRoleCheckInterceptor;
    private final ApiRoleCheckInterceptor apiRoleCheckInterceptor;

    public WebMvcConfig(MenuRoleCheckInterceptor menuRoleCheckInterceptor, ApiRoleCheckInterceptor apiRoleCheckInterceptor) {
        this.menuRoleCheckInterceptor = menuRoleCheckInterceptor;
        this.apiRoleCheckInterceptor = apiRoleCheckInterceptor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(apiRoleCheckInterceptor)
                .addPathPatterns("/api/**");*/

/*        registry.addInterceptor(menuRoleCheckInterceptor)
                .addPathPatterns("/page/**")
                .excludePathPatterns("/page/login", "/page/logout", "/api/third/**");*/
    }

    @Bean
    public IKeycloakAdminUserService keycloakWebClient(@Value("${keycloak-admin-base-url}") String baseUrl) {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(IKeycloakAdminUserService.class);
    }






}
