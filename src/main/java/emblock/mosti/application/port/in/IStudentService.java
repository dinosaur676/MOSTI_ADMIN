package emblock.mosti.application.port.in;

import java.util.List;

import emblock.mosti.application.domain.Student;
import org.springframework.transaction.annotation.Transactional;

import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;

public interface IStudentService {

    List<StudentRespDto> 목록조회();
    StudentRespDto 조회(String userId);
    StudentRespDto 이름학번조회(String name, String studentId);
    @Transactional
    Student 추가(StudentCreateReqDto studentCreateReqDto);
    @Transactional
    void 수정(String userId, StudentUpdateReqDto studentUpdateReqDto);

    void 삭제(long userId);
}
