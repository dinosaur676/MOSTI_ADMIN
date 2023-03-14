package emblock.mosti.application;

import emblock.mosti.application.port.in.IAdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AdminUserServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAdminUserService userService;

    @Test
    public void test추가() {
        this.userService.추가("g", passwordEncoder.encode("1"), "gskim@emblock.co.kr");
    }

}
