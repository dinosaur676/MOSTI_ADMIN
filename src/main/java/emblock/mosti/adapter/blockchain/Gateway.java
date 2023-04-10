package emblock.mosti.adapter.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import emblock.mosti.adapter.ramda.dto.response.RamdaBaseResponseDto;
import emblock.mosti.adapter.ramda.dto.response.RamdaErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.jar.JarException;

@Component
public class Gateway {
    private final String BASE_URL;
    private final WebClient webClient;

    public enum API {
        CREATE_ACCOUNT("/accounts", HttpMethod.POST, ""),
        ADMIN_CREATE_TOKEN("/sbts/admin-create-token", HttpMethod.POST, """
                {
                    "tokenOwner" : %s,
                    "data" : %s 
                }
                """),
        ADMIN_MINT_TOKEN("/sbts/admin-mint-token", HttpMethod.POST, """
                {
                    "tokenOwner" : %s,
                    "to" : %s,
                    "tokenId" : %d 
                }
                """),
        ADMIN_BURN_TOKEN("/sbts/admin-burn-token", HttpMethod.POST, """
                {
                    "tokenOwner" : %s,
                    "to" : %s,
                    "tokenId" : %d 
                }
                """),
        USER_CREATE_TOKEN("/sbts/user-create-token", HttpMethod.POST, """
                {
                    "tokenOwner" : %s,
                    "data" : %s 
                }
                """),
        USER_MINT_TOKEN("/sbts/user-mint-token", HttpMethod.POST, """
                {
                    "tokenOwner" : %s,
                    "to" : %s,
                    "tokenId" : %d 
                }
                """),
        USER_BURN_TOKEN("/sbts/user-burn-token", HttpMethod.POST, """
                {
                    "to" : %s,
                    "tokenId" : %d 
                }
                """);

        private String uri;
        private HttpMethod method;
        private String jsonFormat;

        public String getUri() {
            return uri;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public String getJsonFormat() {
            return jsonFormat;
        }

        API(String uri, HttpMethod method, String jsonFormat) {
            this.uri = uri;
            this.method = method;
            this.jsonFormat = jsonFormat;
        }
    }


    @Autowired
    public Gateway(@Value("${gateway.base-url}") String baseUrl){
        this.BASE_URL = baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    private String getBodyJson(API api, Map<String, String> param) {
        return switch (api) {
            case ADMIN_CREATE_TOKEN ->
                    api.ADMIN_CREATE_TOKEN.getJsonFormat().formatted(
                            param.get("tokenOwner"),
                            param.get("data"));
            case USER_CREATE_TOKEN ->
                    api.USER_CREATE_TOKEN.getJsonFormat().formatted(
                            param.get("tokenOwner"),
                            param.get("data"));
            case ADMIN_MINT_TOKEN ->
                    api.ADMIN_MINT_TOKEN.getJsonFormat().formatted(
                            param.get("tokenOwner"),
                            param.get("to"),
                            Long.parseLong(param.get("tokenId")));
            case USER_MINT_TOKEN ->
                    api.USER_MINT_TOKEN.getJsonFormat().formatted(
                            param.get("tokenOwner"),
                            param.get("to"),
                            Long.parseLong(param.get("tokenId")));
            case ADMIN_BURN_TOKEN ->
                    api.ADMIN_BURN_TOKEN.getJsonFormat().formatted(
                            param.get("tokenOwner"),
                            param.get("to"),
                            Long.parseLong(param.get("tokenId")));
            case USER_BURN_TOKEN ->
                    api.USER_BURN_TOKEN.getJsonFormat().formatted(
                            param.get("to"),
                            Long.parseLong(param.get("tokenId")));
            default -> {
                yield API.CREATE_ACCOUNT.getJsonFormat();
            }
        };
    }

    private static <T> Mono<ResponseEntity<T>> processResponse(Class<T> classType, ClientResponse clientResponse) {
        if(clientResponse.statusCode().is2xxSuccessful()){
            return clientResponse.toEntity(classType);
        }
        else{
            return clientResponse.bodyToMono(RamdaErrorResponseDto.class).flatMap(e -> Mono.error(new RuntimeException(e.getMessage())));
        }
    }

    public GatewayResponse requestWithPostWebClient(API api, Map<String, String> param) {
        WebClient.RequestBodySpec bodySpec = this.webClient.mutate()
                .build()
                .method(api.getMethod())
                .uri(api.getUri());

        if(api.method != HttpMethod.GET)
        {
            bodySpec.bodyValue(this.getBodyJson(api, param));
        }

        String json =  bodySpec
                .exchangeToMono(clientResponse -> processResponse(String.class, clientResponse))
                .block()
                .getBody();


        return new GatewayResponse(json);
    }


    //계정생성


    //토큰생성(public)

    //토큰전송(public)

    //토큰삭제(public)

    //토큰생성(community)

    //토큰전송(community)

    //토큰삭제(community)
}
