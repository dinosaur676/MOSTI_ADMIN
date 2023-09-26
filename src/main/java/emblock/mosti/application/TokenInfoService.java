package emblock.mosti.application;

import emblock.mosti.application.dto.response.token.TokenInfoRespDto;
import emblock.mosti.application.port.in.ITokenInfoService;
import emblock.mosti.application.port.out.ITokenInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenInfoService implements ITokenInfoService {

    final ITokenInfoRepository tokenInfoRepository;

    public TokenInfoService(ITokenInfoRepository tokenInfoRepository) {
        this.tokenInfoRepository = tokenInfoRepository;
    }

    @Override
    public List<TokenInfoRespDto> select(String userName) {
        return tokenInfoRepository.select(userName).stream().map(TokenInfoRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public TokenInfoRespDto selectByTokenId(long tokenId) {
        return TokenInfoRespDto.생성(tokenInfoRepository.selectByTokenId(tokenId));
    }

    @Override
    public void insert(long tokenId, String metaData, String userName, LocalDateTime createdOn) {
        tokenInfoRepository.insert(tokenId, metaData, userName, createdOn);
    }
}
