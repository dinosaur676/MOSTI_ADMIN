package emblock.mosti.application.dto.response;

import java.time.LocalDateTime;

import emblock.mosti.application.domain.Student;

public record StudentRespDto (
    String id,
    String name,
    String studentId,
    String school,
    String major,
    LocalDateTime createdOn,
    LocalDateTime updatedOn
){
    public static StudentRespDto 생성(Student student){
        return new StudentRespDto(
                String.valueOf(student.getId()),
                student.getName(),
                student.getStudentId(),
                student.getSchool(),
                student.getMajor(),
                student.getCreatedOn(),
                student.getUpdatedOn()
        );
    }
}
