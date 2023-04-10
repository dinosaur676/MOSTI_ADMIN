package emblock.mosti.application.port.in;

import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;

import java.util.List;

public interface ITokenControlService {
    TokenInfo 발행한토큰조회(ContractType type, long tokenId);
    List<TokenInfo> 발행한토큰목록조회(String tokenOwner);

    void 토큰생성(TokenInfo tokenInfo);

    UserToken 사용자소유토큰조회(String address, long tokenId, ContractType type);
    List<UserToken> 사용자소유토큰목록조회(String address, ContractType type);
    void 사용자토큰추가(UserToken userToken);
    void 사용자토큰삭제(String to, long tokenId, ContractType type);


    List<TokenType> 토큰타입조회();
    void 토큰타입추가(String description);
    void 토큰타입수정(int typeId, String description);
}
