package emblock.mosti.application.dto.request;

public record AdminUserCreateReqDto(
        String name,
        String password,
        String email
){}
