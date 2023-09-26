package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.UserToken;

import java.time.LocalDateTime;
import java.util.List;

public interface IUserTokenRepository {
    List<UserToken> select(String walletName, String walletTag, String userName);
    void delete(String userName, String walletName, String walletTag, long tokenId);
    void insert(String userName, String walletName, String walletTag, long tokenId, LocalDateTime createdOn, LocalDateTime deletedOn);
}
