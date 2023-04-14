package emblock.mosti.application.port.in;

import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.UserToken;

import java.time.LocalDateTime;

public interface IGatewayService {

    Account createAccount();


    int balanceOfInPublic(String to, long tokenId);
    TokenInfo createTokenInPublic(String tokenOwner, long tokenType, String metaData);
    UserToken mintTokenInPublic(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn);
    UserToken burnTokenInPublic(String tokenOwner, String toAddress, long tokenId);

    TokenInfo createTokenInCommunity(String tokenOwner, long tokenType, String metaData);
    UserToken mintTokenInCommunity(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn);
    UserToken burnTokenInCommunity(String toAddress, long tokenId);
}
