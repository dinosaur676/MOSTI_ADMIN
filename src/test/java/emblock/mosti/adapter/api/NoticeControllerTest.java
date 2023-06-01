package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.mosti.application.domain.Notice;
import emblock.mosti.application.dto.request.notice.NoticeRequestDto;
import emblock.mosti.application.port.in.INoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootTest
public class NoticeControllerTest {

    @Autowired
    private INoticeService noticeService;


    @Test
    @Transactional
    public ResponseDto 추가(@RequestBody NoticeRequestDto noticeRequestDto) {
        Notice notice = Notice.Builder.builder()
                .setWriter("Test")
                .setContent("Test")
                .setTitle("Test")
                .build();

        noticeService.추가(notice);

        return new SuccessRespDto("성공적으로 추가되었습니다.");
    }
}
