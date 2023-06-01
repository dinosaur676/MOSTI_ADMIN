package emblock.mosti.application.port.in;

import emblock.mosti.application.dto.request.log.LogRequestDto;
import emblock.mosti.application.dto.response.LogResponseDto;

import java.util.List;

public interface ILogService {
    List<LogResponseDto> 목록조회(LogRequestDto dto);

    void 추가(long userId, String label);
}
