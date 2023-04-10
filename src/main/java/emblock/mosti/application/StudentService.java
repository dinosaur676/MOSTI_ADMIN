package emblock.mosti.application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.application.port.out.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import emblock.framework.exception.ApplicationException;
import emblock.mosti.application.domain.Student;
import emblock.mosti.application.dto.request.student.StudentCreateReqDto;
import emblock.mosti.application.dto.request.student.StudentUpdateReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.port.in.IStudentService;
import emblock.mosti.application.port.out.IStudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService implements IStudentService{
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private IStudentRepository studentRepository;

    private IUserRepository userRepository;

    @Override
    public List<StudentRespDto> 목록조회() {
        return this.studentRepository.목록조회().stream().map(StudentRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public StudentRespDto 조회(String id) {
        Student student = this.studentRepository.조회(Long.valueOf(id));
        if(Do.비었음(student))
        {
            return null;
        }

        return StudentRespDto.생성(student);
    }

    @Override
    public StudentRespDto 이름학번조회(String name, String studentId) {
        Student student = this.studentRepository.이름학번조회(name, studentId);
        if(Do.비었음(student))
        {
            return null;
        }

        return StudentRespDto.생성(student);
    }

    @Override
    public Student 추가(StudentCreateReqDto studentCreateReqDto) {
        long userId = Long.parseLong(studentCreateReqDto.userId());
        Student student = Student.Builder.builder등록(userId, studentCreateReqDto.userName(), studentCreateReqDto.studentId(), studentCreateReqDto.school(), studentCreateReqDto.major()).build();
        this.studentRepository.추가(student);

        return student;
    }

    @Override
    public void 수정(String id, StudentUpdateReqDto studentUpdateReqDto) {
        Student student = Student.Builder.builder수정(Long.valueOf(id), studentUpdateReqDto.name(), studentUpdateReqDto.school(), studentUpdateReqDto.major()).build();        
        this.studentRepository.수정(student);
    }


}
