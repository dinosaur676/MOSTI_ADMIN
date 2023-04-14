package emblock.mosti.application;

import emblock.framework.exception.DomainException;
import emblock.framework.exception.TokenException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.rdb.SchoolRepository;
import emblock.mosti.application.domain.School;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.dto.response.token.TokenInfoRespDto;
import emblock.mosti.application.dto.response.token.TokenTypeRespDto;
import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import emblock.mosti.application.port.in.ITokenControlService;
import emblock.mosti.application.port.out.ISchoolRepository;
import emblock.mosti.application.port.out.ITokenControlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenControlService implements ITokenControlService {

    private final ITokenControlRepository tokenControlRepository;
    private final ISchoolRepository schoolRepository;

    public TokenControlService(ITokenControlRepository tokenControlRepository, ISchoolRepository schoolRepository) {
        this.tokenControlRepository = tokenControlRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public TokenInfoRespDto 발행한토큰조회(ContractType type, long tokenId) {
        return TokenInfoRespDto.생성(tokenControlRepository.발행한토큰조회(type, tokenId));
    }

    @Override
    public TokenInfoRespDto 발행한토큰조회(String tokenOwner, long tokenType, ContractType contractType) {
        return TokenInfoRespDto.생성(tokenControlRepository.발행한토큰조회(tokenOwner, tokenType, contractType));
    }

    @Override
    public List<TokenInfoRespDto> 발행한토큰목록조회(String tokenOwner, ContractType type) {
        return tokenControlRepository.발행한토큰목록조회(tokenOwner, type).stream().map(TokenInfoRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public void 토큰생성(TokenInfo tokenInfo) {
        ContractType contractType = ContractType.getContractType(tokenInfo.getContractType());
        if(contractType == null) {
            throw new TokenException("Contract 타입이 없습니다.");
        }

        TokenInfo findToken = tokenControlRepository.발행한토큰조회(contractType, tokenInfo.getTokenId());

        if(Do.있음(findToken)) {
            throw new DomainException("존재하는 토큰입니다.");
        }

        tokenControlRepository.토큰생성(tokenInfo);
    }

    @Override
    public UserTokenRespDto 사용자소유토큰조회(String address, long tokenId, ContractType type) {
        UserToken userToken = tokenControlRepository.사용자소유토큰조회(address, tokenId, type);
        if(Do.비었음(userToken))
            return null;

        return UserTokenRespDto.실행(userToken);
    }

    @Override
    public List<UserTokenRespDto> 사용자소유토큰목록조회(String address, ContractType type) {
        return tokenControlRepository.사용자소유토큰목록조회(address, type).stream().map(UserTokenRespDto::실행).collect(Collectors.toList());
    }

    @Override
    public void 사용자토큰추가(UserToken userToken) {
        ContractType contractType = ContractType.getContractType(userToken.getTokenInfo().getContractType());
        if(contractType == null) {
            throw new TokenException("Contract 타입이 없습니다.");
        }

        UserToken findToken = tokenControlRepository.사용자소유토큰조회(userToken.getAddress(), userToken.getTokenInfo().getTokenId(), contractType);

        if(Do.있음(findToken)) {
            throw new DomainException("존재하는 토큰입니다.");
        }

        tokenControlRepository.사용자토큰추가(userToken);
    }

    @Override
    public void 사용자토큰삭제(UserToken userToken) {
        ContractType contractType = ContractType.getContractType(userToken.getTokenInfo().getContractType());
        if(contractType == null) {
            throw new TokenException("Contract 타입이 없습니다.");
        }

        UserToken findToken = tokenControlRepository.사용자소유토큰조회(userToken.getAddress(), userToken.getTokenInfo().getTokenId(), contractType);

        if(Do.비었음(findToken)) {
            throw new DomainException("존재하지 않는 토큰입니다.");
        }

        tokenControlRepository.사용자토큰삭제(userToken);
    }

    @Override
    public List<TokenTypeRespDto> 토큰타입목록조회() {
        return tokenControlRepository.토큰타입목록조회().stream().map(TokenTypeRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public TokenTypeRespDto 토큰타입조회(long tokenType) {
        return TokenTypeRespDto.생성(tokenControlRepository.토큰타입조회(tokenType));
    }

    @Override
    public void 토큰타입추가(String description) {
        //조회해서 있는 Label인지 체크


        tokenControlRepository.토큰타입추가(description);
    }

    @Override
    public void 토큰타입수정(int typeId, String description) {
        //조회해서 있는 typeId인지 체크

        tokenControlRepository.토큰타입수정(typeId, description);
    }
}
