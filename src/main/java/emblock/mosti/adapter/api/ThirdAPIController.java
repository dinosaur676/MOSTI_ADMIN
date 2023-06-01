package emblock.mosti.adapter.api;


import emblock.framework.dto.FailRespDto;
import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.dto.response.SchoolRespDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.*;
import emblock.mosti.application.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/third")
@RestController
public class ThirdAPIController {

    private final IUserService userService;
    private final IAccountService accountService;
    private final IGatewayService gatewayService;
    private final ISchoolService schoolService;
    private final IStudentService studentService;

    public ThirdAPIController(IUserService userService, IAccountService accountService,
                              IGatewayService gatewayService, ISchoolService schoolService,
                              IStudentService studentService) {
        this.userService = userService;
        this.accountService = accountService;
        this.gatewayService = gatewayService;
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping("/certified/{loginId}")
    public ResponseDto certifiedStudent(@PathVariable String loginId) {
        UserRespDto userData = userService.조회(loginId);

        if(Do.비었음(userData))
        {
            return new FailRespDto("9999", "인증에 실패하였습니다.");
        }

        SchoolRespDto school = this.schoolService.학교이름으로조회(userData.school());
        if(Do.비었음(school))
        {
            return new FailRespDto("9999", "인증에 실패하였습니다.");
        }

        Account account = accountService.findAccountById(Long.parseLong(userData.userId()), ContractType.PUBLIC);
        if(Do.비었음(account))
        {
            return new FailRespDto("9999", "인증에 실패하였습니다.");
        }

        int balance = gatewayService.balanceOfInPublic(account.getAddress(), school.tokenId());

        if(balance < 1)
        {
            return new FailRespDto("9999", "인증에 실패하였습니다.");
        }

        return new SuccessRespDto("인증이 완료되었습니다.");
    }
}
