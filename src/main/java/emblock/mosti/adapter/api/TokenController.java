package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.exception.RoleException;
import emblock.framework.exception.TokenException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.*;
import emblock.mosti.application.dto.request.gateway.BurnTokenRequestDto;
import emblock.mosti.application.dto.request.gateway.CreateTokenRequestDto;
import emblock.mosti.application.dto.request.gateway.MintTokenRequestDto;
import emblock.mosti.application.dto.request.token.TokenInfoReqDto;
import emblock.mosti.application.dto.request.token.TokenTypeReqDto;
import emblock.mosti.application.dto.response.SchoolRespDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.dto.response.token.TokenInfoRespDto;
import emblock.mosti.application.dto.response.token.TokenTypeRespDto;
import emblock.mosti.application.dto.response.token.UserTokenRespDto;
import emblock.mosti.application.port.in.*;
import emblock.mosti.application.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/api/token")
@RestController
public class TokenController {

    private final IGatewayService gatewayService;
    private final IAccountService accountService;
    private final ITokenControlService tokenControlService;
    private final IUserService userService;
    private final ISchoolService schoolService;

    private final IStudentService studentService;

    public TokenController(IGatewayService gatewayService, IAccountService accountService,
                           ITokenControlService tokenControlService, IUserService userService,
                           ISchoolService schoolService, IStudentService studentService) {
        this.gatewayService = gatewayService;
        this.accountService = accountService;
        this.tokenControlService = tokenControlService;
        this.userService = userService;
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping("/token-info")
    public ResponseDto 발행토큰목록조회(@RequestParam("contractType") Boolean isPublic, @RequestParam("userId") String userId) {
        ContractType contractType = isPublic ? ContractType.PUBLIC : ContractType.COMMUNITY;

        Account account = accountService.findAccountById(Long.parseLong(userId), contractType);

        List<TokenInfoRespDto> respDtoList = tokenControlService.발행한토큰목록조회(account.getAddress(), contractType);

        if(respDtoList.isEmpty() || respDtoList == null) {
            return new SuccessRespDto("데이터가 없습니다.");
        }

        return new SuccessRespDto(respDtoList, "조회가 성공적으로 이루어졌습니다.");
    }

    @GetMapping("/user-token")
    public ResponseDto 소유토큰목록조회(@RequestParam("contractType") Boolean isPublic, @RequestParam("userId") String userId) {
        ContractType contractType = isPublic ? ContractType.PUBLIC : ContractType.COMMUNITY;

        Account account = accountService.findAccountById(Long.parseLong(userId), contractType);

        List<UserTokenRespDto> respDtoList = tokenControlService.사용자소유토큰목록조회(account.getAddress(), contractType);

        if(respDtoList.isEmpty() || respDtoList == null) {
            return new SuccessRespDto("데이터가 없습니다.");
        }

        return new SuccessRespDto(respDtoList, "조회가 성공적으로 이루어졌습니다.");

    }

    @GetMapping("/token-type")
    public ResponseDto 토큰타입목록조회() {
        List<TokenTypeRespDto> respDtoList = tokenControlService.토큰타입목록조회();

        if(respDtoList.isEmpty() || respDtoList == null)
        {
            throw new DomainException("데이터가 없습니다.");
        }

        return new SuccessRespDto(respDtoList, "조회가 성공적으로 완료되었습니다.");
    }


    @PostMapping("/token-type")
    public ResponseDto 토큰타입추가(@Valid @RequestBody TokenTypeReqDto tokenTypeReqDto) {
        tokenControlService.토큰타입추가(tokenTypeReqDto.description());

        return new SuccessRespDto("추가가 완료되었습니다.");
    }

    @PostMapping("/admin/token")
    public ResponseDto createTokenInPublic(@Valid @RequestBody CreateTokenRequestDto createTokenRequestDto) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRespDto userData = userService.조회(authUser.getLoginId());

        if(userData.type() != User.UserType.B)
            throw new RoleException("사용할 수 없습니다.");

        if(createTokenRequestDto.tokenType() == 1) {
            SchoolRespDto schoolRespDto = schoolService.학교이름으로조회(userData.school());

            if(Do.있음(schoolRespDto)) {
                throw new TokenException("토큰을 생성할 수 없습니다.");
            }
        }

        Account account = accountService.findAccountById(Long.parseLong(authUser.getUserId()), ContractType.PUBLIC);
        TokenInfo tokenInfo = this.gatewayService.createTokenInPublic(
                account.getAddress(),
                createTokenRequestDto.tokenType(),
                createTokenRequestDto.metaData());

        tokenControlService.토큰생성(tokenInfo);

        if(createTokenRequestDto.tokenType() == 1) {
            School school = new School(userData.school(), tokenInfo.getTokenId());
            schoolService.추가(school);
        }


        return new SuccessRespDto("토큰 생성이 완료되었습니다.");
    }

    @PutMapping("/admin/token")
    public ResponseDto mintTokenInPublic(@Valid @RequestBody MintTokenRequestDto mintTokenRequestDto) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRespDto userData = userService.조회(authUser.getLoginId());

        if(userData.type() != User.UserType.B)
            throw new RoleException("사용할 수 없습니다.");

        long userId = Long.parseLong(userData.userId());

        Account onwerAccount = accountService.findAccountById(userId, ContractType.PUBLIC);
        Account toAccount = accountService.findAccountById(
                Long.parseLong(mintTokenRequestDto.userId()),
                ContractType.PUBLIC);

        UserTokenRespDto userTokenRespDto = tokenControlService.사용자소유토큰조회(toAccount.getAddress(),
                mintTokenRequestDto.tokenId(), ContractType.PUBLIC);

        if(Do.있음(userTokenRespDto)) {
            return new SuccessRespDto("이미 토큰을 소유하고 있습니다.");
        }

        UserToken userToken = this.gatewayService.mintTokenInPublic(
                onwerAccount.getAddress(),
                toAccount.getAddress(),
                mintTokenRequestDto.tokenId(),
                LocalDateTime.parse(mintTokenRequestDto.deletedOn(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        if(Do.비었음(userToken)){
            throw new TokenException("토큰 전달에 실패하였습니다.");
        }

        tokenControlService.사용자토큰추가(userToken);

        return new SuccessRespDto("토큰 전달이 완료되었습니다.");
    }

    @DeleteMapping("/admin/token")
    public ResponseDto burnTokenInPublic(@Valid @RequestBody BurnTokenRequestDto burnTokenRequestDto) {

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRespDto userData = userService.조회(authUser.getLoginId());

        if(userData.type() != User.UserType.B)
            throw new RoleException("사용할 수 없습니다.");

        long userId = Long.parseLong(userData.userId());

        Account onwerAccount = accountService.findAccountById(userId, ContractType.PUBLIC);
        Account toAccount = accountService.findAccountById(Long.parseLong(burnTokenRequestDto.userId()), ContractType.PUBLIC);

        UserTokenRespDto userTokenRespDto = tokenControlService.사용자소유토큰조회(toAccount.getAddress(),
                burnTokenRequestDto.tokenId(), ContractType.PUBLIC);

        if(Do.비었음(userTokenRespDto)) {
            return new SuccessRespDto("토큰을 소유하고 있지 않습니다.");
        }

        UserToken userToken = this.gatewayService.burnTokenInPublic(
                onwerAccount.getAddress(),
                toAccount.getAddress(),
                burnTokenRequestDto.tokenId());

        if(Do.비었음(userToken)){
            throw new TokenException("토큰 삭제에 실패하였습니다.");
        }

        tokenControlService.사용자토큰삭제(userToken);

        return new SuccessRespDto("토큰 삭제가 완료되었습니다.");

    }

    @PostMapping("/user/token")
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

    @PutMapping("/user/token")
    public ResponseDto mintTokenInCommuity(@Valid @RequestBody MintTokenRequestDto mintTokenRequestDto) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRespDto userData = userService.조회(authUser.getLoginId());

        long userId = Long.parseLong(userData.userId());

        Account onwerAccount = accountService.findAccountById(userId, ContractType.COMMUNITY);
        Account toAccount = accountService.findAccountById(
                Long.parseLong(mintTokenRequestDto.userId()),
                ContractType.COMMUNITY);

        UserTokenRespDto userTokenRespDto = tokenControlService.사용자소유토큰조회(
                toAccount.getAddress(),
                mintTokenRequestDto.tokenId(),
                ContractType.COMMUNITY);

        if(Do.있음(userTokenRespDto)) {
            return new SuccessRespDto("이미 토큰을 소유하고 있습니다.");
        }

        UserToken userToken = this.gatewayService.mintTokenInCommunity(
                onwerAccount.getAddress(),
                toAccount.getAddress(),
                mintTokenRequestDto.tokenId(),
                LocalDateTime.parse(mintTokenRequestDto.deletedOn(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        if(Do.비었음(userToken)){
            throw new TokenException("토큰 전달에 실패하였습니다.");
        }

        tokenControlService.사용자토큰추가(userToken);

        return new SuccessRespDto("토큰 전달이 완료되었습니다.");
    }

    @DeleteMapping("/user/token")
    public ResponseDto burnTokenInCommunity(@Valid @RequestBody BurnTokenRequestDto burnTokenRequestDto) {

        Account toAccount = accountService.findAccountById(Long.parseLong(burnTokenRequestDto.userId()), ContractType.COMMUNITY);

        UserTokenRespDto userTokenRespDto = tokenControlService.사용자소유토큰조회(
                toAccount.getAddress(),
                burnTokenRequestDto.tokenId(),
                ContractType.COMMUNITY);

        if(Do.비었음(userTokenRespDto)) {
            return new SuccessRespDto("토큰을 소유하고 있지 않습니다.");
        }

        UserToken userToken = this.gatewayService.burnTokenInCommunity(
                toAccount.getAddress(),
                burnTokenRequestDto.tokenId());

        if(Do.비었음(userToken)){
            throw new TokenException("토큰 삭제에 실패하였습니다.");
        }

        tokenControlService.사용자토큰삭제(userToken);

        return new SuccessRespDto("토큰 삭제가 완료되었습니다.");
    }

}
