package emblock.mosti.application.port.in;

import emblock.mosti.adapter.blockchain.GatewayResponse;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.UserToken;

import java.time.LocalDateTime;

public interface IGatewayService {

    String onwerOfInPublic(long tokenId);
    int balanceOfInPublic(String to, long tokenId);
    GatewayResponse createTokenInPublic(String tokenOwner, String metaData);
    GatewayResponse mintTokenInPublic(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn);
    GatewayResponse burnTokenInPublic(String tokenOwner, String toAddress, long tokenId);
}
