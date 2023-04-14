package emblock.mosti.application.port.in;

import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.dto.response.token.TokenInfoRespDto;
import emblock.mosti.application.dto.response.token.TokenTypeRespDto;
import emblock.mosti.application.dto.response.token.UserTokenRespDto;

import java.util.List;

public interface ITokenControlService {
    TokenInfoRespDto 발행한토큰조회(ContractType type, long tokenId);
    TokenInfoRespDto 발행한토큰조회(String tokenOwner, long tokenType, ContractType contractType);
    List<TokenInfoRespDto> 발행한토큰목록조회(String tokenOwner, ContractType type);

    void 토큰생성(TokenInfo tokenInfo);

    UserTokenRespDto 사용자소유토큰조회(String address, long tokenId, ContractType type);
    List<UserTokenRespDto> 사용자소유토큰목록조회(String address, ContractType type);
    void 사용자토큰추가(UserToken userToken);
    void 사용자토큰삭제(UserToken userToken);


    List<TokenTypeRespDto> 토큰타입목록조회();
    TokenTypeRespDto 토큰타입조회(long tokenType);
    void 토큰타입추가(String description);
    void 토큰타입수정(int typeId, String description);
}
