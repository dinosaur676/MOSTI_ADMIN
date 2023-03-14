package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.User;

import java.util.List;

public interface IUserRepository {
    List<User> 목록조회();
    User 조회(String loginId);
    void 추가(User user);
    void 수정(User user);
    void 삭제(long id);
    void 지갑정보수정(String walletId, String publicAddress, long userId);

}
