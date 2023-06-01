package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.mosti.application.dto.request.log.LogRequestDto;
import emblock.mosti.application.dto.response.LogResponseDto;
import emblock.mosti.application.port.in.ILogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/log")
@RestController
public class LogController {

    private final ILogService logService;

    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseDto 로그목록조회(@RequestBody LogRequestDto dto)
    {
        List<LogResponseDto> logResponseDtos = logService.목록조회(dto);

        if(logResponseDtos == null || logResponseDtos.isEmpty())
        {
            throw new DomainException("NO DATA");
        }

        return new SuccessRespDto(logResponseDtos, "조회가 성공적으로 이루어졌습니다.");
    }
}
