package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.AdminUser;

import java.util.List;

public interface IAdminUserRepository {
    List<AdminUser> 목록조회();
    AdminUser 조회(String 이름);
    void 추가(AdminUser adminUser);
    void 수정(AdminUser adminUser);
    void 삭제(String id);
    void 암호수정(String id, String 암호);
}
