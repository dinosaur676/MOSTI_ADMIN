package emblock.mosti.application.port.out;

import java.util.List;

import emblock.mosti.application.domain.Student;

public interface IStudentRepository {
    List<Student> 목록조회();
    Student 조회(long userId);
    Student 이름학번조회(String name, String studentId);
    void 추가(Student student);
    void 수정(Student student);
}
