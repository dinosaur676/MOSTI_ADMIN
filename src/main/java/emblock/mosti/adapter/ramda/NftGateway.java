// package emblock.mosti.adapter.ramda;

// import emblock.mosti.adapter.ramda.dto.response.RamdaAuthResponseDto;
// import emblock.mosti.adapter.ramda.dto.response.RamdaBaseResponseDto;
// import emblock.mosti.adapter.ramda.dto.response.RamdaErrorResponseDto;
// import emblock.mosti.adapter.ramda.dto.response.RamdaMediaResponseDto;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.FileSystemResource;
// import org.springframework.core.io.Resource;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.client.MultipartBodyBuilder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.ClientResponse;
// import org.springframework.web.reactive.function.client.WebClient;
// import reactor.core.publisher.Mono;

// import java.io.File;
// import java.util.Map;
// @Component
// public class NftGateway {

//     private final String BASE_URL;
//     private final String CONTRACT_ID;
//     private final WebClient webClient;

//     private final String ACCESS_KEY;
//     private final String SECRET_KEY;
//     private final String ENVIRONMENT_ID;

//     @Autowired
//     public NftGateway(@Value("${ramda.base-url}")String baseUrl,
//                       @Value("${ramda.contract-id}")String contractId,
//                       @Value("${ramda.access-key}") String accessKey,
//                       @Value("${ramda.secret-key}") String secretKey,
//                       @Value("${ramda.environment-id}") String environmentId){
//         this.BASE_URL = baseUrl;
//         this.CONTRACT_ID = contractId;
//         this.ACCESS_KEY = accessKey;
//         this.SECRET_KEY = secretKey;
//         this.ENVIRONMENT_ID = environmentId;
//         this.webClient = WebClient.builder()
//                 .baseUrl(BASE_URL)
//                 .defaultHeaders(httpHeaders -> {
//                     httpHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
//                     httpHeaders.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
//                 })
//                 .build();
//     }

//     public enum API {
//         AUTH("/svc/v2/auth-tokens",HttpMethod.POST,
//         """
//             {
//                 "accessKey" : "%s",
//                 "secretKey": "%s",
//                 "expiresIn": 6000
//             }
//         """
//         ),
//         CREATE_MEDIA("/svc/v2/nft/media",HttpMethod.POST,
//         """
        
//         """
//         ),
//         CREATE_META_DATA("/svc/v2/nft/metadata",HttpMethod.POST,
//         """
//             {
//                 "name": "Rems4egNFT%s",
//                 "createdBy": "%s",
//                 "createdDate": "%s",
//                 "image": "6223706423827770110",
//                 "thumbnailImage": "6223706423827770110",               
//                 "description": "PowerGeneration Info",                
//                 "properties": [],                      
//                 "maxMintLimit": 1,
//                 "onChainProperties" : {
//                     "powerGeneration": %s
//                 }
//             }
//         """
//         ),
//         MINT("/svc/v2/nft/contracts/%s/tokens",HttpMethod.POST,
//         """
//                 {
//                     "issuedTo":"%s",
//                     "metadataId":"%s"
//                 }        
//         """
//         ),
//         TRANSFER_TOKEN("", HttpMethod.POST,
//         """
//                 {
//                     "issuedTo":"%s",
//                     "metadataId":"%s"
//                 }            
//         """
//         ),
//         GET_NFT("/svc/v2/nft/contracts/%s/tokens/%s", HttpMethod.GET,"{}"),
//         CREATE_WALLET("/tx/v2.0/wallets",HttpMethod.POST,
//         """
//                 {
//                     "environmentId":"%s",
//                     "userKey":"%s"
//                 }
//         """
//         );
//         private String uri;
//         private HttpMethod method;
//         private String jsonFormat;

//         public String getUri() {
//             return uri;
//         }

//         public String getJsonFormat() {
//             return jsonFormat;
//         }
//         API(String uri, HttpMethod method, String jsonFormat) {
//             this.uri = uri;
//             this.method = method;
//             this.jsonFormat = jsonFormat;
//         }
//     }
//     private String getBodyJson(API api, Map<String,String> param){
//         return switch (api) {
//             case CREATE_WALLET ->
//                 api.CREATE_WALLET.getJsonFormat().formatted(ENVIRONMENT_ID,param.get("userId"));
//             case MINT ->
//                 api.MINT.getJsonFormat().formatted(param.get("address"), param.get("metadataId"));
//             case AUTH ->
//                 api.AUTH.getJsonFormat().formatted(this.ACCESS_KEY, this.SECRET_KEY);
//             case CREATE_META_DATA ->
//                 api.CREATE_META_DATA.getJsonFormat().formatted(
//                     param.get("name"),
//                     param.get("userName"),
//                     param.get("createdDate"),
//                     param.get("powerGeneration")
//                 );
//             default -> {
//                 yield API.CREATE_MEDIA.getJsonFormat();
//             }
//         };
//     }
//     private String getUrl(API api, Map<String,String> param){
//         String url = api.getUri();

//         return switch (api) {
//             case MINT -> url.formatted(CONTRACT_ID);
//             case GET_NFT -> url.formatted(CONTRACT_ID,param.get("tokenId"));
//             default -> url;
//         };
//     }

//     public <T extends RamdaBaseResponseDto> T requestWithPostWebClient(API api, Class<T> targetClass, Map<String,String> param){
//         WebClient.RequestBodySpec bodySpec = this.webClient.mutate()
//                 .defaultHeaders(httpHeaders -> {
//                     httpHeaders.add(HttpHeaders.AUTHORIZATION, " Bearer "+ this.getAuthKey());
//                 })
//                 .build()
//                 .method(api.method)
//                 .uri(this.getUrl(api, param));

//         if(api.method != HttpMethod.GET) {
//             bodySpec.bodyValue(this.getBodyJson(api,param));
//         }

//         return (T) bodySpec
//                 .exchangeToMono(clientResponse -> processResponse(targetClass, clientResponse))
//                 .block()
//                 .getBody();
//     }


//     public String getAuthKey(){
//         API api = API.AUTH;
//         RamdaAuthResponseDto resp =this.webClient.mutate()
//                 .build()
//                 .method(api.method)
//                 .uri(api.getUri())
//                 .bodyValue(this.getBodyJson(api,null))
//                 .exchangeToMono(clientResponse -> processResponse(RamdaAuthResponseDto.class,clientResponse))
//                 .block()
//                 .getBody();
//         return String.valueOf((resp.getData().getAuthToken()).token());
//     }


//     public RamdaMediaResponseDto uploadMedia(Resource resource){
//         MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
//         multipartBodyBuilder.part("file", new FileSystemResource(new File("D:\\workspace\\emb_projects\\rems4eg-be\\.sourceimage\\4385707_20220.jpg")));
//         //RamdaMediaResponseDto{data=Media[id=6223706423827770110, originalFilename=4385707_20220.jpg, mimetype=image/jpeg, size=4579917, mediaUrl=https://nft-cdn.luniverse.io/public/b8f75417-5ac6-44b0-83b6-55228c5db4ec_2022-12-16T04:05:08.718Z.jpg, createdAt=2022-12-16T04:05:11.674]}
//         //RamdaMediaResponseDto{data=Media[id=8313180863880878810, originalFilename=4385707_20220.jpg, mimetype=image/jpeg, size=4579917, mediaUrl=https://nft-cdn.luniverse.io/public/18cf4b4c-0198-4b93-b7f3-524b7f0c8e1d_2022-12-16T05:51:22.003Z.jpg, createdAt=2022-12-16T05:51:24.881]}
//         return webClient.mutate()
//                 .defaultHeaders(httpHeaders -> {
//                     httpHeaders.add(HttpHeaders.AUTHORIZATION, " Bearer "+ this.getAuthKey());
//                     httpHeaders.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
//                 })
//                 .build()
//                 .post()
//                 .uri(API.CREATE_MEDIA.getUri())
//                 .contentType(MediaType.MULTIPART_FORM_DATA)
//                 .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
//                 .exchangeToMono(clientResponse -> processResponse(RamdaMediaResponseDto.class, clientResponse))
//                 .block().getBody();
//     }

//     private static  <T extends RamdaBaseResponseDto> Mono<ResponseEntity<T>> processResponse(Class<T> targetClass, ClientResponse clientResponse) {
//         if(clientResponse.statusCode().is2xxSuccessful()){
//             return clientResponse.toEntity(targetClass);
//         }else{
//             return clientResponse.bodyToMono(RamdaErrorResponseDto.class).flatMap(e -> Mono.error(new RuntimeException(e.getMessage())));
//         }
//     }

//     /**
//      * {
//      *     "result": true,
//      *     "status": 201,
//      *     "data": {
//      *         "id": "4404514225602572200",
//      *         "name": "My first NFT",
//      *         "createdBy": "mehi",
//      *         "createdDate": "2021-05-15",
//      *         "image": "https://nft-cdn.luniverse.io/public/18cf4b4c-0198-4b93-b7f3-524b7f0c8e1d_2022-12-16T05:51:22.003Z.jpg",
//      *         "imageHash": "f2174e9c2346a46cfeb04d9e0838cf5a3848f17e1441614ef92523084cabb822",
//      *         "thumbnail": "https://nft-cdn.luniverse.io/public/18cf4b4c-0198-4b93-b7f3-524b7f0c8e1d_2022-12-16T05:51:22.003Z.jpg",
//      *         "youtubeUrl": "https://www.youtube.com/watch?v=NVzV6A0Xfes",
//      *         "backgroundColor": "#C0C0C0",
//      *         "description": "Metadatadescription",
//      *         "externalUrl": "https://www.youtube.com/watch?v=NVzV6A0Xfes",
//      *         "properties": [
//      *             {
//      *                 "displayType": "date | number",
//      *                 "type": "Category",
//      *                 "value": "art"
//      *             }
//      *         ],
//      *         "editionMax": "100",
//      *         "createdAt": "2022-12-16T06:14:51.230Z",
//      *         "onChainProperties": {},
//      *         "baseUri": null
//      *     },
//      *     "code": "CREATED"
//      * }
//      *
//      * RamdaMetadataResponseDto{data=Metadata[id=9531208368738722054, name=My first NFT, createdBy=mehi, createdDate=2021-05-15, image=https://nft-cdn.luniverse.io/public/b8f75417-5ac6-44b0-83b6-55228c5db4ec_2022-12-16T04:05:08.718Z.jpg, thumbnail=https://nft-cdn.luniverse.io/public/b8f75417-5ac6-44b0-83b6-55228c5db4ec_2022-12-16T04:05:08.718Z.jpg, media=null, youtubeUrl=https://www.youtube.com/watch?v=NVzV6A0Xfes, backgroundColor=#C0C0C0, description=Metadatadescription, properties=[Lemblock.rems.adapter.ramda.dto.RamdaMetadataResponseDto$Property;@3e40bb83, maxMintLimit=null, createdAt=2022-12-16T06:44:20.833, onChainProperties={}]}
//      */



// }
