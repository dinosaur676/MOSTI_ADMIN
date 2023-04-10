package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.rdb.mapper.TokenTypeRowMapper;
import emblock.mosti.adapter.rdb.mapper.UserTokenRowMapper;
import emblock.mosti.adapter.rdb.mapper.TokenInfoRowMapper;
import emblock.mosti.adapter.rdb.sql.TokenControlSQL;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.TokenType;
import emblock.mosti.application.domain.UserToken;
import emblock.mosti.application.port.out.ITokenControlRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TokenControlRepository implements ITokenControlRepository {

    private JdbcCommand jdbcCommand;
    private JdbcQuery<UserToken> userTokenJdbcQuery;
    private JdbcQuery<TokenInfo> tokenInfoJdbcQuery;
    private JdbcQuery<TokenType> tokenTypeJdbcQuery;

    public TokenControlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.userTokenJdbcQuery = new JdbcQuery<>(jdbcTemplate, new UserTokenRowMapper());
        this.tokenInfoJdbcQuery = new JdbcQuery<>(jdbcTemplate, new TokenInfoRowMapper());
        this.tokenTypeJdbcQuery = new JdbcQuery<>(jdbcTemplate, new TokenTypeRowMapper());
    }

    @Override
    public TokenInfo 발행한토큰조회(ContractType type, long tokenId) {
        return tokenInfoJdbcQuery.조회(TokenControlSQL.발행한토큰조회, tokenId, type);
    }

    @Override
    public List<TokenInfo> 발행한토큰목록조회(String tokenOwner) {
        return tokenInfoJdbcQuery.목록조회(TokenControlSQL.발행한토큰목록조회, tokenOwner);
    }

    @Override
    public void 토큰생성(TokenInfo tokenInfo) {
        jdbcCommand.실행(TokenControlSQL.토큰생성,
                tokenInfo.getTokenId(),
                tokenInfo.getMetaData(),
                tokenInfo.getCreatedOn(),
                tokenInfo.getTokenOwner(),
                tokenInfo.getType(),
                tokenInfo.getContractType());
    }

    @Override
    public UserToken 사용자소유토큰조회(String address, long tokenId, ContractType type) {
        return userTokenJdbcQuery.조회(TokenControlSQL.사용자소유토큰조회,
                address, tokenId, type.getType());
    }

    @Override
    public List<UserToken> 사용자소유토큰목록조회(String address, ContractType type) {
        return userTokenJdbcQuery.목록조회(TokenControlSQL.사용자소유토큰목록조회,
                address, type.getType());
    }

    @Override
    public void 사용자토큰추가(UserToken userToken) {
        jdbcCommand.실행(TokenControlSQL.사용자토큰추가,
                userToken.getAccount(), userToken.getTokenId(), userToken.getContractType(),
                userToken.getCreatedOn(), userToken.getDeletedOn());
    }

    @Override
    public void 사용자토큰삭제(String to, long tokenId, ContractType type) {
        jdbcCommand.실행(TokenControlSQL.사용자토큰삭제,
                to, tokenId, type.getType());
    }

    @Override
    public List<TokenType> 토큰타입조회() {
        return tokenTypeJdbcQuery.목록조회(TokenControlSQL.토큰타입조회);
    }

    @Override
    public void 토큰타입추가(String description) {
        jdbcCommand.실행(TokenControlSQL.토큰타입추가, description);
    }

    @Override
    public void 토큰타입수정(int typeId, String description) {
        jdbcCommand.실행(TokenControlSQL.토큰타입수정, description, typeId);
    }
}
