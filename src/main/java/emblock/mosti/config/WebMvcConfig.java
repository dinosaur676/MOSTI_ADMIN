package emblock.mosti.config;

import emblock.mosti.application.security.ApiRoleCheckInterceptor;
import emblock.mosti.application.security.MenuRoleCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

        registry.addInterceptor(menuRoleCheckInterceptor)
                .addPathPatterns("/page/**")
                .excludePathPatterns("/page/login", "/page/logout", "/api/third/**");
    }




}
