package emblock.mosti.adapter.api;

import emblock.framework.dto.FailRespDto;
import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.dto.request.gateway.BurnTokenRequestDto;
import emblock.mosti.application.dto.request.gateway.CreateTokenRequestDto;
import emblock.mosti.application.dto.request.gateway.MintTokenRequestDto;
import emblock.mosti.application.dto.response.token.TokenInfoRespDto;
import emblock.mosti.application.dto.response.token.TokenTypeRespDto;
import emblock.mosti.application.port.in.IAccountService;
import emblock.mosti.application.port.in.IGatewayService;
import emblock.mosti.application.port.in.ITokenControlService;
import emblock.mosti.application.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/gateway")
@RestController
public class GatewayController {

    private IGatewayService gatewayService;
    private IAccountService accountService;
    private ITokenControlService tokenControlService;

    public GatewayController(IGatewayService gatewayService, IAccountService accountService, ITokenControlService tokenControlService) {
        this.gatewayService = gatewayService;
        this.accountService = accountService;
        this.tokenControlService = tokenControlService;
    }

    @PostMapping("/token-info")
    public ResponseDto 목록조회() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findAccountById(Long.parseLong(authUser.getUserId()), ContractType.PUBLIC);

        List<TokenInfoRespDto> respDtoList = tokenControlService.발행한토큰목록조회(account.getAddress());

        if(respDtoList.isEmpty() || respDtoList == null) {
            throw new DomainException("데이터가 없습니다.");
        }

        return new SuccessRespDto(respDtoList, "조회가 성공적으로 이루어졌습니다.");
    }

    @PostMapping("/token-type")
    public ResponseDto 토큰타입목록조회() {
        List<TokenTypeRespDto> respDtoList = tokenControlService.토큰타입목록조회();

        if(respDtoList.isEmpty() || respDtoList == null)
        {
            throw new DomainException("데이터가 없습니다.");
        }

        return new SuccessRespDto(respDtoList, "조회가 성공적으로 완료되었습니다.");
    }

    @PostMapping("/admin-create-token")
    public ResponseDto createTokenInPublic(@Valid @RequestBody CreateTokenRequestDto createTokenRequestDto) {
        //Role 체크

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findAccountById(Long.parseLong(authUser.getUserId()), ContractType.PUBLIC);

        TokenInfo tokenInfo = this.gatewayService.createTokenInPublic(
                account.getAddress(),
                createTokenRequestDto.tokenType(),
                createTokenRequestDto.metaData());

        tokenControlService.토큰생성(tokenInfo);

        return new SuccessRespDto("토큰 생성이 완료되었습니다.");
    }

    @PostMapping("/admin-mint-token")
    public ResponseDto mintTokenInPublic(@Valid @RequestBody MintTokenRequestDto mintTokenRequestDto) {
        //Role 체크
        //TODO : Token 있는지 체크
        //gatewayService 에서 실패했을시 반환이 궁금
        UserToken userToken = this.gatewayService.mintTokenInPublic(
                mintTokenRequestDto.tokenOwner(),
                mintTokenRequestDto.tokenOwner(),
                mintTokenRequestDto.tokenId(),
                mintTokenRequestDto.deletedOn()
        );

        if(Do.비었음(userToken)){
            //TODO: 예외처리
        }

        tokenControlService.사용자토큰추가(userToken);

        return new SuccessRespDto("토큰 발행이 완료되었습니다.");
    }

    @PostMapping("/admin-burn-token")
    public ResponseDto burnTokenInPublic(@Valid @RequestBody BurnTokenRequestDto burnTokenRequestDto) {
        if(Do.빈문자열임(burnTokenRequestDto.tokenOwner())) {
            return new FailRespDto("9999", "토큰 소유자는 필수 입력값입니다.");
        }

        //Role 체크
        //TODO : Token 있는지 체크

        UserToken userToken = this.gatewayService.burnTokenInPublic(burnTokenRequestDto.tokenOwner(), burnTokenRequestDto.toAddress(), burnTokenRequestDto.tokenId());

        if(Do.비었음(userToken)){
            //TODO: 예외처리
        }

        ContractType contractType = ContractType.getContractType(userToken.getContractType());
        tokenControlService.사용자토큰삭제(userToken.getAccount(), userToken.getTokenId(), contractType);

        return new SuccessRespDto("토큰 삭제가 완료되었습니다.");

    }

    @PostMapping("/user-create-token")
    public ResponseDto createTokenInCommunity(@Valid @RequestBody CreateTokenRequestDto createTokenRequestDto) {
        //Role 체크
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findAccountById(Long.parseLong(authUser.getUserId()), ContractType.COMMUNITY);


        TokenInfo tokenInfo = this.gatewayService.createTokenInCommunity(
                account.getAddress(),
                createTokenRequestDto.tokenType(),
                createTokenRequestDto.metaData());

        tokenControlService.토큰생성(tokenInfo);

        return new SuccessRespDto("토큰 생성이 완료되었습니다.");
    }

    @PostMapping("/user-mint-token")
    public ResponseDto mintTokenInCommuity(@Valid @RequestBody MintTokenRequestDto mintTokenRequestDto) {
        //Role 체크
        //TODO : Token 있는지 체크
        //gatewayService 에서 실패했을시 반환이 궁금
        UserToken userToken = this.gatewayService.mintTokenInCommunity(
                mintTokenRequestDto.tokenOwner(),
                mintTokenRequestDto.tokenOwner(),
                mintTokenRequestDto.tokenId(),
                mintTokenRequestDto.deletedOn()
        );

        if(Do.비었음(userToken)){
            //TODO: 예외처리
        }

        tokenControlService.사용자토큰추가(userToken);

        return new SuccessRespDto("토큰 발행이 완료되었습니다.");
    }

    @PostMapping("/user-burn-token")
    public ResponseDto burnTokenInCommunity(@Valid @RequestBody BurnTokenRequestDto burnTokenRequestDto) {

        UserToken userToken = this.gatewayService.burnTokenInCommunity(burnTokenRequestDto.toAddress(), burnTokenRequestDto.tokenId());

        if(Do.비었음(userToken)){
            //TODO: 예외처리
        }

        ContractType contractType = ContractType.getContractType(userToken.getContractType());
        tokenControlService.사용자토큰삭제(userToken.getAccount(), userToken.getTokenId(), contractType);

        return new SuccessRespDto("토큰 삭제가 완료되었습니다.");

    }


}
