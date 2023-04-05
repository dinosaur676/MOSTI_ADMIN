package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.TokenInfo;

import java.time.LocalDateTime;

public interface ITokenControlRepository {

    TokenInfo findToken(String tokenOwner, long tokenId);
    void createToken(TokenInfo tokenInfo);
    void putToken(String toAddress, long tokenId, LocalDateTime endDateTime);
    void putToken(String toAddress, long tokenId);


}
