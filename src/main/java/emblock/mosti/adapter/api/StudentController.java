package emblock.mosti.adapter.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.EmptyListException;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IStudentService;
import emblock.mosti.application.port.in.IUserService;

@RequestMapping("/api/students")
@RestController
public class StudentController {
    @Autowired
    private IStudentService studentService;
    
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
    public ResponseDto 추가(@Valid @RequestBody StudentCreateReqDto studentCreateReqDto, Principal principal){
        this.studentService.추가(studentCreateReqDto);
        return new SuccessRespDto("사용자 등록이 성공적으로 완료되었습니다.");
    }

    @PutMapping("/{userId}")
    public ResponseDto 수정(@Valid @RequestBody StudentUpdateReqDto studentUpdateReqDto, Principal principal, @PathVariable String userId){
        this.studentService.수정(userId, studentUpdateReqDto);
        return new SuccessRespDto("사용자 수정이 성공적으로 완료되었습니다.");
    }

}
