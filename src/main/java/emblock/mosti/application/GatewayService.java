package emblock.mosti.application;

import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.blockchain.Gateway;
import emblock.mosti.adapter.blockchain.GatewayResponse;
import emblock.mosti.adapter.rdb.TokenControlRepository;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.port.in.IGatewayService;
import emblock.mosti.application.port.out.ITokenControlRepository;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class GatewayService implements IGatewayService {

    private Gateway gateWay;
    private ITokenControlRepository tokenControlRepository;

    public GatewayService(Gateway gateWay, ITokenControlRepository tokenControlRepository) {
        this.gateWay = gateWay;
        this.tokenControlRepository = tokenControlRepository;
    }

    @Override
    public Account createAccount() {
        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.CREATE_ACCOUNT, null);

        return new Account(gatewayResponse.getData("privateKey"),
                gatewayResponse.getData("address"));
    }

    @Override
    public TokenInfo createTokenInPublic(String tokenOwner, long tokenType, String metaData) {
        Map<String, String> param = new HashMap<>();
        param.put("tokenOwner", tokenOwner);
        param.put("data", metaData);

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_CREATE_TOKEN, param);
        TokenType tokenTypeDescription = tokenControlRepository.토큰타입조회(tokenType);

        if(Do.비었음(tokenTypeDescription))
            throw new DomainException("해당 토큰 타입은 존재하지 않습니다.");

        TokenInfo tokenInfo = new TokenInfo(
                Long.parseLong(gatewayResponse.getData("tokenId")),
                tokenTypeDescription.getDescription(),
                gatewayResponse.getData("metaData"),
                gatewayResponse.getData("tokenOwner"),
                ContractType.PUBLIC.getType()
        );


        return tokenInfo;
    }

    @Override
    public UserToken mintTokenInPublic(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn) {
        Map<String, String> param = new HashMap<>();

        param.put("tokenOwner", tokenOwner);
        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_MINT_TOKEN, param);

        UserToken userToken = new UserToken(
                gatewayResponse.getData("to"),
                Long.parseLong(gatewayResponse.getData("tokenId")),
                ContractType.PUBLIC.getType(),
                deletedOn
        );

        return userToken;
    }

    @Override
    public UserToken burnTokenInPublic(String tokenOwner, String toAddress, long tokenId) {
        Map<String, String> param = new HashMap<>();

        param.put("tokenOwner", tokenOwner);
        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_BURN_TOKEN, param);

        UserToken userToken = new UserToken(
                gatewayResponse.getData("to"),
                Long.parseLong(gatewayResponse.getData("tokenId")),
                ContractType.PUBLIC.getType()
        );

        return userToken;
    }

    @Override
    public TokenInfo createTokenInCommunity(String tokenOwner, long tokenType, String metaData) {

        Map<String, String> param = new HashMap<>();
        param.put("tokenOwner", tokenOwner);
        param.put("data", metaData);

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.USER_CREATE_TOKEN, param);
        TokenType tokenTypeDescription = tokenControlRepository.토큰타입조회(tokenType);

        if(Do.비었음(tokenTypeDescription))
            throw new DomainException("해당 토큰 타입은 존재하지 않습니다.");

        TokenInfo tokenInfo = new TokenInfo(
                Long.parseLong(gatewayResponse.getData("tokenId")),
                tokenTypeDescription.getDescription(),
                gatewayResponse.getData("metaData"),
                gatewayResponse.getData("tokenOwner"),
                ContractType.COMMUNITY.getType()
        );


        return tokenInfo;
    }

    @Override
    public UserToken mintTokenInCommunity(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn) {
        Map<String, String> param = new HashMap<>();

        param.put("tokenOwner", tokenOwner);
        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.USER_MINT_TOKEN, param);

        UserToken userToken = new UserToken(
                gatewayResponse.getData("to"),
                Long.parseLong(gatewayResponse.getData("tokenId")),
                ContractType.COMMUNITY.getType(),
                deletedOn
        );

        return userToken;
    }

    @Override
    public UserToken burnTokenInCommunity(String toAddress, long tokenId) {
        Map<String, String> param = new HashMap<>();

        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.USER_BURN_TOKEN, param);

        UserToken userToken = new UserToken(
                gatewayResponse.getData("to"),
                Long.parseLong(gatewayResponse.getData("tokenId")),
                ContractType.COMMUNITY.getType()
        );

        return userToken;
    }
}
