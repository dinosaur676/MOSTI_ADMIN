package emblock.mosti.application;

import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.application.domain.Notice;
import emblock.mosti.application.dto.response.NoticeRespDto;
import emblock.mosti.application.port.in.INoticeService;
import emblock.mosti.application.port.out.INoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService implements INoticeService {
    private final INoticeRepository noticeRepository;

    public NoticeService(INoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<NoticeRespDto> 목록조회() {
        return noticeRepository.목록조회().stream().map(NoticeRespDto::실행).collect(Collectors.toList());
    }

    @Override
    public List<NoticeRespDto> 작성자조회(String writer) {
        return noticeRepository.목록조회().stream().map(NoticeRespDto::실행).collect(Collectors.toList());
    }

    @Override
    public void 추가(Notice notice) {
        noticeRepository.추가(notice);
    }

    @Override
    public void 삭제(Notice notice) {
        noticeRepository.삭제(notice);
    }

    @Override
    public void 수정(Notice notice) {
        noticeRepository.수정(notice);
    }
}
