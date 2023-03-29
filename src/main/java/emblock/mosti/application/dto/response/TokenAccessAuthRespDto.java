package emblock.mosti.application.dto.response;

public record TokenAccessAuthRespDto (
    String joinUrl,
    String qrcode
){
    public static TokenAccessAuthRespDto 생성(String joinUrl, String qrcode){
        return new TokenAccessAuthRespDto(
            joinUrl, qrcode
        );
    }
}
