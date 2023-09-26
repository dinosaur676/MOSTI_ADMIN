package emblock.mosti.application;

import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.blockchain.Gateway;
import emblock.mosti.adapter.blockchain.GatewayResponse;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.port.in.IGatewayService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class GatewayService implements IGatewayService {

    private Gateway gateWay;

    public GatewayService(Gateway gateWay) {
        this.gateWay = gateWay;
    }


    @Override
    public String onwerOfInPublic(long tokenId) {
        Map<String, String> param = new HashMap<>();
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_OWNER, param);
        return gatewayResponse.getData("owner");
    }

    @Override
    public int balanceOfInPublic(String to, long tokenId) {
        Map<String, String> param = new HashMap<>();
        param.put("to", to);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_BALANCE, param);
        return Integer.parseInt(gatewayResponse.getData("balance"));
    }

    @Override
    public GatewayResponse createTokenInPublic(String tokenOwner, String metaData) {
        Map<String, String> param = new HashMap<>();
        param.put("tokenOwner", tokenOwner);
        param.put("data", metaData);

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_CREATE_TOKEN, param);


        return gatewayResponse;
    }

    @Override
    public GatewayResponse mintTokenInPublic(String tokenOwner, String toAddress, long tokenId, LocalDateTime deletedOn) {
        Map<String, String> param = new HashMap<>();

        param.put("tokenOwner", tokenOwner);
        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_MINT_TOKEN, param);

        return gatewayResponse;
    }

    @Override
    public GatewayResponse burnTokenInPublic(String tokenOwner, String toAddress, long tokenId) {
        Map<String, String> param = new HashMap<>();

        param.put("tokenOwner", tokenOwner);
        param.put("to", toAddress);
        param.put("tokenId", String.valueOf(tokenId));

        GatewayResponse gatewayResponse = gateWay.requestWithPostWebClient(Gateway.API.ADMIN_BURN_TOKEN, param);

        return gatewayResponse;
    }

}
