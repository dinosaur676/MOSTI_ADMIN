package emblock.mosti.application;

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
import emblock.mosti.application.domain.TokenAccessAuth;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthCreateReqDto;
import emblock.mosti.application.dto.request.issue.TokenAccessAuthValidReqDto;
import emblock.mosti.application.dto.response.StudentRespDto;
import emblock.mosti.application.dto.response.TokenAccessAuthRespDto;
import emblock.mosti.application.port.in.IIssueService;
import emblock.mosti.application.port.out.IStudentRepository;
import emblock.mosti.application.port.out.ITokenAccessAuthRepository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IssueService implements IIssueService {
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);
    @Value("${base-url}") String baseUrl;

    @Autowired
    private ITokenAccessAuthRepository tokenAccessAuthRepository;
    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public String 검증(String studentId, TokenAccessAuthValidReqDto tokenAccessAuthValidReqDto) {
        TokenAccessAuth tokenAccessAuth = this.tokenAccessAuthRepository.조회(Long.valueOf(studentId));
        //비번확인
        if(!tokenAccessAuth.getAuthKey().equals(tokenAccessAuthValidReqDto.authKey())){
            throw new ApplicationException("조회되지 않습니다.");
        }
        //학생 조회
        Student student = studentRepository.조회(Long.valueOf(studentId));
        // StudentRespDto studentRespDto = StudentRespDto.생성(student);
        return student.getName();
    }

    @Override
    public TokenAccessAuthRespDto 발급(String studentId, TokenAccessAuthCreateReqDto tokenAccessAuthCreateReqDto) {
        TokenAccessAuth tokenAccessAuth = TokenAccessAuth.Builder.builder등록(tokenAccessAuthCreateReqDto.authKey(), Long.valueOf(studentId)).build();
        this.tokenAccessAuthRepository.추가(tokenAccessAuth);

        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append("/page/token-validation?id=");
        sb.append(studentId);
        String joinUrl = sb.toString();
        String qrcode = "data:image/png;base64," + createQrcode(joinUrl);
        
        return new TokenAccessAuthRespDto(joinUrl, qrcode);
    }

    private String createQrcode(String joinUrl){
        try {
            //qr생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            //한글 데이터 처리
            String strData = new String(joinUrl.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            // 색상
            int qrcodeColor =   0xFF2e4e96; 
            // 배경
            int backgroundColor = 0xFFFFFFFF; 

            //가로,세로 200 사이즈로 qr코드 생성
            BitMatrix bitMatrix = qrCodeWriter.encode(strData, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor); 
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
            
            ImageIO.write(bufferedImage, "png", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch(IOException | WriterException e){
            log.error("qr생성에러:::, {}", e);
            throw new ApplicationException("qrcode 생성 중 오류가 발생했습니다.");
        }
    }
}
