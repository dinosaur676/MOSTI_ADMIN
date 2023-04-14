package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;

public interface ISchoolRepository {

    School 학교이름으로조회(String schoolName);
    School 유저아이디로조회(long userId);

    void 추가(School school);

    School 인증(Account account);
}
