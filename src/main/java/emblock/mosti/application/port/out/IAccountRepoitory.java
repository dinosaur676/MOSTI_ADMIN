package emblock.mosti.application.port.out;

import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.application.domain.Account;
import org.springframework.transaction.annotation.Transactional;

public interface IAccountRepoitory {

    Account findAccountById(long user_id, ContractType type);

    void addAccount(Account account, ContractType type);

    void updateAccount(long user_id, Account account, ContractType type);

    void deleteAccount(long user_id, ContractType type);

}
