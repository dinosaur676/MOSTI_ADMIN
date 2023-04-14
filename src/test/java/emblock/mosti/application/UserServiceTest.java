package emblock.mosti.application;

import emblock.framework.dto.FailRespDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IAccountService;
import emblock.mosti.application.port.in.IStudentService;
import emblock.mosti.application.port.in.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IStudentService studentService;
    @Transactional
    @Test
    void find() {

        String userId = "7048828214769999872";
        StudentUpdateReqDto studentUpdateReqDto = new StudentUpdateReqDto(
                "익은호",
                "서강대학교",
                "123123123",
                "컴퓨터공학과");

        UserRespDto userData = this.userService.조회(Long.parseLong(userId));
        UserUpdateReqDto updateReqDto = UserUpdateReqDto.이름수정(userData, studentUpdateReqDto.userName());

        this.userService.수정(updateReqDto);
        this.studentService.수정(userId, studentUpdateReqDto);
        System.out.println(userData);
        System.out.println(updateReqDto);
    }

    @Transactional
    @Test
    void delete() {
        String userId = "7048828214769999872";

        if(Do.비었음(studentService.조회(userId)))
        {
            System.out.println("비어있음");
        }

        this.studentService.삭제(Long.parseLong(userId));
        this.accountService.deleteAccount(Long.parseLong(userId), ContractType.PUBLIC);
    }
}
