package emblock.mosti.application.port.in;

import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.dto.response.SchoolRespDto;

import java.util.List;

public interface ISchoolService {

    List<SchoolRespDto> 학교목록조회();
    SchoolRespDto 학교이름으로조회(String schoolName);
    SchoolRespDto 유저아이디로조회(long userId);

    void 학교이름수정(School school);
    void 학교삭제(School school);

    void 추가(School school);

    SchoolRespDto 인증(Account account);
}