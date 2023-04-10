package emblock.mosti.application;

import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.port.in.ITokenControlService;
import emblock.mosti.application.port.out.ITokenControlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenControlService implements ITokenControlService {

    private ITokenControlRepository tokenControlRepository;

    public TokenControlService(ITokenControlRepository tokenControlRepository) {
        this.tokenControlRepository = tokenControlRepository;
    }

    @Override
    public TokenInfo 발행한토큰조회(ContractType type, long tokenId) {
        return tokenControlRepository.발행한토큰조회(type, tokenId);
    }

    @Override
    public List<TokenInfo> 발행한토큰목록조회(String tokenOwner) {
        return tokenControlRepository.발행한토큰목록조회(tokenOwner);
    }

    @Override
    public void 토큰생성(TokenInfo tokenInfo) {
        ContractType contractType = ContractType.getContractType(tokenInfo.getContractType());
        if(contractType == null) {

        }
            //TODO : Exeption

        TokenInfo findToken = tokenControlRepository.발행한토큰조회(contractType, tokenInfo.getTokenId());

        if(Do.있음(findToken)) {
            //TODO: Exeption
        }

        tokenControlRepository.토큰생성(tokenInfo);
    }

    @Override
    public UserToken 사용자소유토큰조회(String address, long tokenId, ContractType type) {
        return tokenControlRepository.사용자소유토큰조회(address, tokenId, type);
    }

    @Override
    public List<UserToken> 사용자소유토큰목록조회(String address, ContractType type) {
        return tokenControlRepository.사용자소유토큰목록조회(address, type);
    }

    @Override
    public void 사용자토큰추가(UserToken userToken) {
        ContractType contractType = ContractType.getContractType(userToken.getContractType());
        if(contractType == null) {

        }
        //TODO : Exeption

        UserToken findToken = tokenControlRepository.사용자소유토큰조회(userToken.getAccount(), userToken.getTokenId(), contractType);

        if(Do.있음(findToken)) {
            //TODO: Exeption
        }

        tokenControlRepository.사용자토큰추가(userToken);
    }

    @Override
    public void 사용자토큰삭제(String to, long tokenId, ContractType type) {
        UserToken findToken = tokenControlRepository.사용자소유토큰조회(to, tokenId, type);

        if(Do.비었음(findToken)) {
            //TODO: Exeption
        }

        tokenControlRepository.사용자토큰삭제(to, tokenId, type);
    }

    @Override
    public List<TokenType> 토큰타입조회() {
        return tokenControlRepository.토큰타입조회();
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
