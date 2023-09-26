package emblock.mosti.application;

import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import emblock.mosti.application.port.in.IUserTokenService;
import emblock.mosti.application.port.out.IUserTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTokenService implements IUserTokenService {

    final IUserTokenRepository userTokenRepository;

    public UserTokenService(IUserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public List<UserTokenRespDto> select(String walletName, String walletTag, String userName) {
        return userTokenRepository.select(walletName, walletTag, userName).stream().map(UserTokenRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public void delete(String userName, String walletName, String walletTag, long tokenId) {
        userTokenRepository.delete(userName, walletName, walletTag, tokenId);
    }

    @Override
    public void insert(String userName, String walletName, String walletTag, long tokenId, LocalDateTime createdOn, LocalDateTime deletedOn) {
        userTokenRepository.insert(userName, walletName, walletTag, tokenId, createdOn, deletedOn);
    }
}
