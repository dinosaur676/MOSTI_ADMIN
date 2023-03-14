package emblock.mosti.adapter.ramda.dto.response;

public class RamdaAuthResponseDto extends RamdaBaseResponseDto{

    AuthTokens data;

    public AuthTokens getData() {
        return data;
    }

    public void setData(AuthTokens data) {
        this.data = data;
    }

    public class AuthTokens{
        AuthToken authToken;

        public AuthToken getAuthToken() {
            return authToken;
        }

        public void setAuthToken(AuthToken authToken) {
            this.authToken = authToken;
        }

        public record AuthToken(
                String authTokenId,
                String accountId,
                String iamUserId,
                String token,
                String expiryAt
        ){}

        @Override
        public String toString() {
            return "AuthTokens{" +
                    "authToken=" + authToken +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RamdaAuthResponseDto{" +
                "data=" + data +
                ", result=" + result +
                ", code='" + code + '\'' +
                '}';
    }
}

