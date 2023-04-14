package emblock.mosti.application;

import emblock.framework.helper.Do;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.dto.response.SchoolRespDto;
import emblock.mosti.application.port.in.ISchoolService;
import emblock.mosti.application.port.out.ISchoolRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolService implements ISchoolService {

    private final ISchoolRepository schoolRepository;

    public SchoolService(ISchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolRespDto 학교이름으로조회(String schoolName) {
        School school = schoolRepository.학교이름으로조회(schoolName);
        if (Do.비었음(school))
            return null;

        return SchoolRespDto.실행(school);
    }

    @Override
    public SchoolRespDto 유저아이디로조회(long userId) {
        return SchoolRespDto.실행(schoolRepository.유저아이디로조회(userId));
    }

    @Override
    public void 추가(School school) {
        schoolRepository.추가(school);
    }

    @Override
    public SchoolRespDto 인증(Account account) {
        School school = schoolRepository.인증(account);
        if (Do.비었음(school))
            return null;

        return SchoolRespDto.실행(school);
    }
}
