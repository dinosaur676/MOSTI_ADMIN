package emblock.mosti.adapter.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthCreateReqDto;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthValidReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.dto.response.TokenAccessAuthRespDto;
import emblock.mosti.application.port.in.IIssueService;

@RequestMapping("/api/issue")
@RestController
public class IssueController {
    @Autowired
    private IIssueService iissueService;
    
    //비밀번호 입력, 토큰 발행
    // //학생용 url 생성 및 qr코드 
    @PostMapping("/{id}")
    public ResponseDto 토큰발행(@RequestBody TokenAccessAuthCreateReqDto tokenAccessAuthCreateReqDto, Principal principal, @PathVariable String id){
        TokenAccessAuthRespDto tokenAccessAuthRespDto = this.iissueService.발급(id, tokenAccessAuthCreateReqDto);
        return new SuccessRespDto(tokenAccessAuthRespDto, "토큰발행이 성공적으로 완료되었습니다.");
    }

    //학번, 이름, 패스코드로 확인
    @PostMapping("/valid/{id}")
    public ResponseDto 검증(Model model, @RequestBody TokenAccessAuthValidReqDto tokenAccessAuthValidReqDto, Principal principal, @PathVariable String id){
        String name = this.iissueService.검증(id, tokenAccessAuthValidReqDto);
        return new SuccessRespDto(name, "사용자 조회가 성공적으로 완료되었습니다.");
    }

}
