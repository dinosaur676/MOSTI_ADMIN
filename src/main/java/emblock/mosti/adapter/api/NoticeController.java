package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.mosti.application.domain.Notice;
import emblock.mosti.application.dto.request.notice.NoticeRequestDto;
import emblock.mosti.application.dto.request.notice.NoticeUpdateRequestDto;
import emblock.mosti.application.dto.response.NoticeRespDto;
import emblock.mosti.application.port.in.INoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private final INoticeService noticeService;

    public NoticeController(INoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseDto 목록조회() {
        List<NoticeRespDto> noticeRespDtos = noticeService.목록조회();

        if (noticeRespDtos == null || noticeRespDtos.isEmpty()) {
            return new SuccessRespDto("No Data");
        }

        return new SuccessRespDto(noticeRespDtos, "조회가 성공적으로 이루어졌습니다.");
    }

    @GetMapping("/{writer}")
    public ResponseDto 조회(@PathVariable String writer) {
        List<NoticeRespDto> noticeRespDtos = noticeService.작성자조회(writer);

        if (noticeRespDtos == null || noticeRespDtos.isEmpty()) {
            throw new DomainException("No Data");
        }

        return new SuccessRespDto(noticeRespDtos, "조회가 성공적으로 이루어졌습니다.");
    }

    @PostMapping
    public ResponseDto 추가(@RequestBody NoticeRequestDto noticeRequestDto) {
        Notice notice = Notice.Builder.builder()
                .setWriter(noticeRequestDto.writer())
                .setContent(noticeRequestDto.content())
                .setTitle(noticeRequestDto.title())
                .build();

        noticeService.추가(notice);

        return new SuccessRespDto("성공적으로 추가되었습니다.");
    }

    @PutMapping
    public ResponseDto 수정(@RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto) {
        Notice notice = Notice.Builder.builder()
                .setId(noticeUpdateRequestDto.id())
                .setTitle(noticeUpdateRequestDto.title())
                .setContent(noticeUpdateRequestDto.content())
                .build();

        noticeService.수정(notice);

        return new SuccessRespDto("성공적으로 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseDto 삭제(@RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto) {
        Notice notice = Notice.Builder.builder()
                .setId(noticeUpdateRequestDto.id())
                .setTitle(noticeUpdateRequestDto.title())
                .setContent(noticeUpdateRequestDto.content())
                .build();

        noticeService.삭제(notice);

        return new SuccessRespDto("성공적으로 삭제되었습니다.");
    }


}
