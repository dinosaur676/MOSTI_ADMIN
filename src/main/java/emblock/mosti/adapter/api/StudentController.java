package emblock.mosti.adapter.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.beust.ah.A;
import emblock.framework.dto.FailRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.dto.request.token.TokenRequestDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.SchoolRespDto;
import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import emblock.mosti.application.port.in.*;
import emblock.mosti.application.port.out.IAccountRepoitory;
import emblock.mosti.application.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.EmptyListException;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.dto.response.UserRespDto;

@RequestMapping("/api/students")
@RestController
public class StudentController {
    private final IStudentService studentService;
    private final IGatewayService gatewayService;
    private final IAccountService accountService;
    private final ITokenControlService tokenControlService;
    private final ISchoolService schoolService;
    private final IUserService userService;

    public StudentController(IStudentService studentService, IGatewayService gatewayService,
                             IAccountService accountService, ITokenControlService tokenControlService,
                             ISchoolService schoolService, IUserService userService) {
        this.studentService = studentService;
        this.gatewayService = gatewayService;
        this.accountService = accountService;
        this.tokenControlService = tokenControlService;
        this.schoolService = schoolService;
        this.userService = userService;
    }

    //user에 학생을 추가할 필요 없을 듯.
    //A 2 : admin
    //B 0 : 교직원
    //S 1 : 학생 
    
    @GetMapping
    public ResponseDto 목록조회(Model model, Principal principal){
        List<StudentRespDto> studentList = this.studentService.목록조회();
        if(studentList == null || studentList.isEmpty() ) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(studentList, "사용자 조회가 성공적으로 완료되었습니다.");
    }

    @GetMapping("/{userId}")
    public ResponseDto 조회(Model model, Principal principal, @PathVariable String userId){
        StudentRespDto studentRespDto = this.studentService.조회(userId);
        if(studentRespDto == null) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(studentRespDto, "사용자 조회가 성공적으로 완료되었습니다.");
    }

    //create
    @PostMapping
    public ResponseDto 추가(@Valid @RequestBody StudentCreateReqDto studentCreateReqDto){
        long userId = Long.parseLong(studentCreateReqDto.userId());
        StudentRespDto studentRespDto = this.studentService.조회(studentCreateReqDto.userId());

        UserRespDto userData = this.userService.조회(userId);
        if(!userData.userName().equals(studentCreateReqDto.userName()))
        {
            return new FailRespDto("99","이름이 다릅니다.");
        }

        if(Do.있음(studentRespDto))
        {
            throw new DomainException("이미 존재하는 학생입니다."); //Exception;
        }


        Account newAccount = gatewayService.createAccount();

        if(Do.비었음(newAccount))
        {
            throw new DomainException("계정 생성 실패");
        }

        Student newStudent = this.studentService.추가(studentCreateReqDto);
        newAccount.setUser_id(newStudent.getUserId());
        newAccount.setCreatedOn(newStudent.getCreatedOn());

        accountService.addAccount(newAccount, ContractType.PUBLIC);

        return new SuccessRespDto("학생 등록이 성공적으로 완료되었습니다.");
    }

    @PutMapping("/{userId}")
    public ResponseDto 수정(@Valid @RequestBody StudentUpdateReqDto studentUpdateReqDto, @PathVariable String userId){

        if(Do.비었음(studentService.조회(userId)))
        {
            return new FailRespDto("9999","존재하지 않는 학생입니다.");
        }

        UserRespDto userData = this.userService.조회(Long.parseLong(userId));
        UserUpdateReqDto updateReqDto = UserUpdateReqDto.이름수정(userData, studentUpdateReqDto.userName());

        this.userService.수정(updateReqDto);
        this.studentService.수정(userId, studentUpdateReqDto);
        return new SuccessRespDto("수정이 성공적으로 완료되었습니다.");
    }

    @DeleteMapping("/{userId}")
    public ResponseDto 삭제(@PathVariable String userId) {
        if(Do.비었음(studentService.조회(userId)))
        {
            return new FailRespDto("9999","존재하지 않는 학생입니다.");
        }

        this.studentService.삭제(Long.parseLong(userId));
        this.accountService.deleteAccount(Long.parseLong(userId), ContractType.PUBLIC);

        return new SuccessRespDto("삭제가 성공적으로 완료되었습니다.");
    }



}
