package emblock.mosti.application.port.out;

import emblock.mosti.application.domain.TokenAccessAuth;

public interface ITokenAccessAuthRepository {
    TokenAccessAuth 조회(long studentId);
    void 추가(TokenAccessAuth tokenAccessAuth);
}
