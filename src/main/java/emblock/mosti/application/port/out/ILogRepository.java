package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.Log;
import emblock.mosti.application.dto.request.log.LogRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ILogRepository {
    List<Log> 목록조회(LogRequestDto dto);

    void 추가(long userId, String label);
}
