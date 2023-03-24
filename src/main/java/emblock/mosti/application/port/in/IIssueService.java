package emblock.mosti.application.port.in;

import org.springframework.transaction.annotation.Transactional;

import emblock.framework.dto.SuccessRespDto;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.domain.TokenAccessAuth;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthCreateReqDto;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthValidReqDto;
import emblock.mosti.application.dto.response.TokenAccessAuthRespDto;

public interface IIssueService {
    public String 검증(String studentId, TokenAccessAuthValidReqDto tokenAccessAuthValidReqDto);
    @Transactional
    public TokenAccessAuthRespDto 발급(String studentId, TokenAccessAuthCreateReqDto tokenAccessAuthCreateReqDto);
}
