package emblock.mosti.application.dto.request.user;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDto(
        @NotEmpty
        String loginId,
        @NotEmpty
        String password
) {
}
