package emblock.mosti.adapter.api;


import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.token.TokenRequestDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.port.in.IAccountService;
import emblock.mosti.application.port.in.IStudentService;
import emblock.mosti.application.port.in.ITokenControlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/token")
@RestController
public class TokenController {
    private ITokenControlService tokenControlService;
    private IAccountService accountService;

    private IStudentService studentService;

    public TokenController(ITokenControlService tokenControlService, IAccountService accountService, IStudentService studentService) {
        this.tokenControlService = tokenControlService;
        this.accountService = accountService;
        this.studentService = studentService;
    }

    @PostMapping("/certified")
    public ResponseDto certifiedStudent(@Valid @RequestBody TokenRequestDto tokenRequestDto) {

        StudentRespDto studentRespDto = studentService.이름학번조회(tokenRequestDto.name(), tokenRequestDto.studentId());

        if(Do.비었음(studentRespDto))
        {
            //TODO : exeption
        }

        Account publicAccount = accountService.findAccountById(Long.parseLong(studentRespDto.userId()), ContractType.PUBLIC);

        if(Do.비었음(publicAccount))
        {
            //TODO : exeption
        }

        UserToken userToken = tokenControlService.사용자소유토큰조회(publicAccount.getAddress(), tokenRequestDto.tokenId(), tokenRequestDto.type());

        if(Do.비었음(userToken)){
            //TODO : exeption
        }

        return new SuccessRespDto("인증이 완료되었습니다.");
    }


}
