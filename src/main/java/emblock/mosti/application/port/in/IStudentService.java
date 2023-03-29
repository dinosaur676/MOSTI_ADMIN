package emblock.mosti.application.port.in;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;

public interface IStudentService {

    List<StudentRespDto> 목록조회();
    StudentRespDto 조회(String id);
    StudentRespDto 이름학번조회(String name, String studentId);
    @Transactional
    void 추가(StudentCreateReqDto studentCreateReqDto);
    @Transactional
    void 수정(String id, StudentUpdateReqDto studentUpdateReqDto);
}
