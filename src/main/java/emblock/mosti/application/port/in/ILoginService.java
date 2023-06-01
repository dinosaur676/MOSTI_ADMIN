package emblock.mosti.application.port.in;

import emblock.mosti.application.security.jwt.JWTInfo;
import org.springframework.transaction.annotation.Transactional;

public interface ILoginService {

    @Transactional
    public JWTInfo login(String memberId, String password);
}
