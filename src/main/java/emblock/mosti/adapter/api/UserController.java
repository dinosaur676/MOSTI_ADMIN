package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.exception.EmptyListException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IAccountService;
import emblock.mosti.application.port.in.IGatewayService;
import emblock.mosti.application.port.in.IStudentService;
import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final IUserService userService;
    private final IGatewayService gatewayService;
    private final IAccountService accountService;

    private final IStudentService studentService;

    public UserController(IUserService userService, IGatewayService gatewayService,
                          IAccountService accountService, IStudentService studentService) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.accountService = accountService;
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseDto 목록조회(Model model, Principal principal){
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRespDto respDto = userService.조회(authUser.getLoginId());


        String school = "";
        if(respDto.type() == User.UserType.B)
            school = respDto.school();

        List<UserRespDto> userList = this.userService.목록조회(school);
        if(userList == null || userList.isEmpty() ) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(userList, "사용자 조회가 성공적으로 완료되었습니다.");
    }
    @GetMapping("/{loginId}")
    public ResponseDto 조회(Model model, Principal principal, @PathVariable String loginId){
        UserRespDto user = this.userService.조회(loginId);
        if(user == null) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(user, "사용자 조회가 성공적으로 완료되었습니다.");
    }
    
    @PostMapping
    public ResponseDto 추가(@Valid @RequestBody UserCreateReqDto reqDto, Principal principal){
        User user = this.userService.추가(reqDto);
        if(reqDto.type() == User.UserType.B) {
            Account newAccount = gatewayService.createAccount();

            if(Do.비었음(newAccount))
            {
                throw new DomainException("계정 생성 실패");
            }

            newAccount.setUser_id(user.getUserId());

            accountService.addAccount(newAccount, ContractType.PUBLIC);
        }

        return new SuccessRespDto("사용자 등록이 성공적으로 완료되었습니다.");
    }

    @PutMapping
    public ResponseDto 수정(@Valid @RequestBody UserUpdateReqDto reqDto){
        this.userService.수정(reqDto);
        return new SuccessRespDto("사용자 수정이 성공적으로 완료되었습니다.");
    }

    @DeleteMapping("/{userId}")
    public ResponseDto 삭제(@PathVariable String userId) {
        long id = Long.parseLong(userId);
        this.accountService.deleteAccount(id, ContractType.PUBLIC);
        this.studentService.삭제(id);
        this.userService.삭제(id);

        return new SuccessRespDto("사용자 삭제가 성공적으로 완료되었습니다.");
    }


}
