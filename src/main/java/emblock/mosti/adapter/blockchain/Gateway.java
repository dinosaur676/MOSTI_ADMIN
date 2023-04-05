package emblock.mosti.adapter.blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Gateway {
    private final String BASE_URL;
    
    @Autowired
    public Gateway(@Value("${gateway.base-url}") String baseUrl){
        this.BASE_URL = baseUrl;
    }
    //계정생성(public)
    
    //토큰생성(public)

    //토큰전송(public)

    //계정생성(private)

    //토큰생성(private)

    //토큰전송(private)

}
