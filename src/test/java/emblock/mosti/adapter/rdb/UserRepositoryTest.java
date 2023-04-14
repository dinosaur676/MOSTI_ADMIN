package emblock.mosti.adapter.rdb;

import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    void find() {
        userRepository.목록조회().forEach(System.out::print);
    }

    @Transactional
    @Test
    void delete() {
        userRepository.삭제(Long.parseLong("7048828214769999872"));
    }

    @Test
    void add() {
        User user = User.Builder.builder등록("emblock13","12345")
                .userName("천종익")
                .type(User.UserType.B)
                .status(User.UserStatus.Y)
                .email("jichun@gmail.com")
                .build();

        userRepository.추가(user);
    }

    public static void main(String[] args){
        String a = """
                (id, user_id, generation_quantity, production_date, production_time, token_id, created_on,
                                      updated_on)
                """;
        String b = Arrays.stream(a.split(","))
                .map( ch -> "?")
                .collect(Collectors.joining(", "));

        System.out.println("( ".concat(b).concat(" )"));
    }
}