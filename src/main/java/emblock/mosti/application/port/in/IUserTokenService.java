package emblock.mosti.application.port.in;

import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import java.time.LocalDateTime;
import java.util.List;

public interface IUserTokenService {
    List<UserTokenRespDto> select(String walletName, String walletTag, String userName);
    void delete(String userName, String walletName, String walletTag, long tokenId);
    void insert(String userName, String walletName, String walletTag, long tokenId, LocalDateTime createdOn, LocalDateTime deletedOn);
}
