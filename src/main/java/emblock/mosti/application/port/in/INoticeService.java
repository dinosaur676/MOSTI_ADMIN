package emblock.mosti.application.port.in;

import emblock.mosti.application.domain.Notice;
import emblock.mosti.application.dto.response.NoticeRespDto;

import java.util.List;

public interface INoticeService {
    List<NoticeRespDto> 목록조회();
    List<NoticeRespDto> 작성자조회(String writer);
    void 추가(Notice notice);
    void 삭제(Notice notice);
    void 수정(Notice notice);
}
