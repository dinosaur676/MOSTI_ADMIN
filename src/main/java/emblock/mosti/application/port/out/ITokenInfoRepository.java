package emblock.mosti.application.port.out;


import emblock.mosti.application.domain.TokenInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface ITokenInfoRepository {
    List<TokenInfo> select(String userName);
    TokenInfo selectByTokenId(long tokenId);
    void insert(long tokenId, String metaData, String userName, LocalDateTime createdOn);
}
