package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.exception.EmptyListException;
import emblock.framework.helper.Do;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.dto.request.school.SchoolRequestDto;
import emblock.mosti.application.dto.response.SchoolRespDto;
import emblock.mosti.application.port.in.ISchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final ISchoolService schoolService;

    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public ResponseDto 학교목록조회() {
        List<SchoolRespDto> schoolRespDtos = schoolService.학교목록조회();
        if(schoolRespDtos == null || schoolRespDtos.isEmpty())
        {
            throw new EmptyListException("NO DATA");
        }

        return new SuccessRespDto(schoolRespDtos, "학교 조회가 성공적으로 이루어졌습니다.");
    }

    @PostMapping
    public ResponseDto 학교추가(@RequestBody SchoolRequestDto schoolRequestDto) {
        SchoolRespDto dto = schoolService.학교이름으로조회(schoolRequestDto.schoolName());

        if(Do.있음(dto))
        {
            throw new DomainException("이미 존재하는 학교입니다.");
        }

        schoolService.추가(new School(schoolRequestDto.schoolName(), schoolRequestDto.tokenId()));

        return new SuccessRespDto("학교 추가가 성공적으로 이루어졌습니다.");
    }

    @PutMapping
    public ResponseDto 학교이름수정(@RequestBody SchoolRequestDto schoolRequestDto) {
        schoolService.학교이름수정(new School(schoolRequestDto.schoolName(), schoolRequestDto.tokenId()));
        return new SuccessRespDto("학교 이름이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseDto 학교삭제(@RequestBody SchoolRequestDto schoolRequestDto) {
        schoolService.학교삭제(new School(schoolRequestDto.schoolName(), schoolRequestDto.tokenId()));
        return new SuccessRespDto("학교 이름이 성공적으로 수정되었습니다.");
    }


}
