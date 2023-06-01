package emblock.mosti.application.dto.request.log;

import java.time.LocalDateTime;

public record LogRequestDto (

        String userName,
        String label,
        String startDate,
        String endDate
){
}
