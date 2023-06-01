package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.Notice;

import java.util.List;

public interface INoticeRepository {

    List<Notice> 목록조회();
    List<Notice> 조회(String writer);
    void 추가(Notice notice);
    void 삭제(Notice notice);
    void 수정(Notice notice);


}
