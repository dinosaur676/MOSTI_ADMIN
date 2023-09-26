package emblock.mosti.adapter.api;

import emblock.framework.dto.FailRespDto;
import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.blockchain.GatewayResponse;
import emblock.mosti.application.GatewayService;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.request.token.MintWaitReqDto;
import emblock.mosti.application.dto.request.token.TokenInfoReqDto;
import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import emblock.mosti.application.port.in.IGatewayService;
import emblock.mosti.application.port.in.IMintWaitService;
import emblock.mosti.application.port.in.ITokenInfoService;
import emblock.mosti.application.port.in.IUserTokenService;
import emblock.mosti.application.security.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    final IGatewayService gatewayService;
    final IUserTokenService userTokenService;
    final ITokenInfoService tokenInfoService;
    final IMintWaitService mintWaitService;

    public TokenController(IGatewayService gatewayService, IUserTokenService userTokenService,
                           ITokenInfoService tokenInfoService, IMintWaitService mintWaitService) {
        this.gatewayService = gatewayService;
        this.userTokenService = userTokenService;
        this.tokenInfoService = tokenInfoService;
        this.mintWaitService = mintWaitService;
    }

    @GetMapping("/info")
    public ResponseDto getInfoSelect(@RequestParam(required = false) String userName, @RequestParam(required = false) Long tokenId) {
        if(tokenId == null) {
            String name = ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLoginId();
            return new SuccessRespDto(tokenInfoService.select(name), "성공");
        }
        else {
            return new SuccessRespDto(tokenInfoService.selectByTokenId(tokenId.longValue()), "성공");
        }
    }

    @GetMapping("/user")
    public ResponseDto getUserTokenList(@RequestParam String userName, @RequestParam String walletName, @RequestParam String walletTag) {
        List<UserTokenRespDto> tokenRespDtoList = userTokenService.select(walletName, walletTag, userName);

        if(tokenRespDtoList.isEmpty() || tokenRespDtoList == null)
        {
            return new SuccessRespDto(new ArrayList<>(), "비었음");
        }

        return new SuccessRespDto(tokenRespDtoList, "성공");
    }

    @PostMapping("/info")
    public ResponseDto createTokenInfoData(@RequestBody TokenInfoReqDto dto) {
        String userName = ((AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLoginId();

        String address = "0xF0b0c34E1EB0c2Af4AD4f91fC4314a3eeE97e4Df";
        GatewayResponse gatewayResponse = gatewayService.createTokenInPublic(address, dto.metaData());
        tokenInfoService.insert(Long.parseLong(gatewayResponse.getData("tokenId")), dto.metaData(), userName, LocalDateTime.now());

        return new SuccessRespDto("성공");
    }

    @PutMapping("/info")
    public ResponseDto mintToken(@RequestBody MintWaitReqDto dto) {
        String owner = gatewayService.onwerOfInPublic(dto.tokenId());

        String address = "0xF0b0c34E1EB0c2Af4AD4f91fC4314a3eeE97e4Df";
        if(!owner.equals(address))
            return new FailRespDto("9999", "실패");

        mintWaitService.insert(dto.userName(), dto.tokenId());

        return new SuccessRespDto("성공");
    }



}
