package emblock.mosti.application.port.in;

import emblock.mosti.application.dto.response.token.TokenInfoRespDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ITokenInfoService {
    List<TokenInfoRespDto> select(String userName);
    TokenInfoRespDto selectByTokenId(long tokenId);
    void insert(long tokenId, String metaData, String userName, LocalDateTime createdOn);
}
