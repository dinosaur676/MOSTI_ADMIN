package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;

import java.util.List;

public interface ISchoolRepository {

    List<School> 학교목록조회();
    School 학교이름으로조회(String schoolName);
    School 유저아이디로조회(long userId);

    void 학교이름수정(School school);
    void 학교삭제(School school);

    void 추가(School school);

    School 인증(Account account);
}
