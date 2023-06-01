package emblock.mosti.application.security.jwt;

public class JWTInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public String getGrantType() {
        return grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public JWTInfo(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String toJson() {
        return """
                {
                "grantType": %s,
                "accessToken": %s,
                "refreshToken": %s
                }
                """.formatted(grantType, accessToken, refreshToken);
    }
}

