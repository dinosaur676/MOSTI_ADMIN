package emblock.mosti.application;

import emblock.mosti.application.dto.request.log.LogRequestDto;
import emblock.mosti.application.dto.response.LogResponseDto;
import emblock.mosti.application.port.in.ILogService;
import emblock.mosti.application.port.out.ILogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService implements ILogService {
    private final ILogRepository logRepository;

    public LogService(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<LogResponseDto> 목록조회(LogRequestDto dto) {
        return logRepository.목록조회(dto).stream().map(LogResponseDto::실행).collect(Collectors.toList());
    }

    @Override
    public void 추가(long userId, String label) {
        logRepository.추가(userId, label);
    }
}
