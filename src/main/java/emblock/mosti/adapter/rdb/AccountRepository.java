package emblock.mosti.adapter.rdb;

import emblock.framework.jdbc.JdbcCommand;
import emblock.framework.jdbc.JdbcQuery;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.rdb.mapper.AccountRowMapper;
import emblock.mosti.adapter.rdb.mapper.UserRowMapper;
import emblock.mosti.adapter.rdb.sql.AccountSql;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.port.out.IAccountRepoitory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository implements IAccountRepoitory {
    private final JdbcCommand jdbcCommand;
    private final JdbcQuery<Account> jdbcQuery;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcCommand = new JdbcCommand(jdbcTemplate);
        this.jdbcQuery = new JdbcQuery<>(jdbcTemplate, new AccountRowMapper());
    }

    @Override
    public Account findAccountById(long user_id, ContractType type) {
        return this.jdbcQuery.조회(AccountSql.findAccountById.formatted(type.getTableName()), user_id);
    }

    @Override
    public void addAccount(Account account, ContractType type) {
        this.jdbcCommand.실행(AccountSql.addAcount.formatted(type.getTableName()),
                account.getUser_id(),
                account.getAddress(),
                account.getPrivateKey(),
                account.getCreatedOn());
    }

    @Override
    public void updateAccount(long user_id, Account account, ContractType type) {
        this.jdbcCommand.실행(AccountSql.updateAccount.formatted(type.getTableName()),
                account.getAddress(),
                account.getPrivateKey(),
                user_id);
    }

    @Override
    public void deleteAccount(long user_id, ContractType type) {
        this.jdbcCommand.실행(AccountSql.deleteAccount.formatted(type.getTableName()),
                user_id);
    }
}
