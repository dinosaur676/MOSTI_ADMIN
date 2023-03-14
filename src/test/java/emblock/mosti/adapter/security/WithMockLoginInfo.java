package emblock.mosti.adapter.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockLoginUserSecurityContextFactory.class)
public @interface WithMockLoginInfo {
    String name() default "g";
}
