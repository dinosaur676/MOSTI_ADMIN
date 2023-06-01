package emblock.mosti.application.dto.request.token;

import jakarta.validation.constraints.NotEmpty;

public record TokenTypeReqDto (
        @NotEmpty
        String description
){
}
