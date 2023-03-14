package emblock.mosti.application;

import emblock.mosti.application.port.in.IMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    private IMenuService menuService;

    @Test
    public void find(){
        menuService.사용자별메뉴조회(2).forEach(System.out::println);
    }

}