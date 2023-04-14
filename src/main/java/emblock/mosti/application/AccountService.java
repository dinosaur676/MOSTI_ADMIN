package emblock.mosti.application;

import emblock.framework.exception.ApplicationException;
import emblock.framework.helper.Do;
import emblock.mosti.adapter.blockchain.ContractType;
import emblock.mosti.adapter.rdb.AccountRepository;
import emblock.mosti.application.domain.Account;
import emblock.mosti.application.port.in.IAccountService;
import emblock.mosti.application.port.out.IAccountRepoitory;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private IAccountRepoitory accountRepoitory;

    public AccountService(IAccountRepoitory accountRepoitory) {
        this.accountRepoitory = accountRepoitory;
    }

    @Override
    public Account findAccountById(long user_id, ContractType type) {
        return this.accountRepoitory.findAccountById(user_id, type);
    }

    @Override
    public void addAccount(Account account, ContractType type) {

        Account findAC = findAccountById(account.getUser_id(), type);

        if(Do.있음(findAC))
            throw new ApplicationException("이미 Public 지갑이 존재합니다.");

        this.accountRepoitory.addAccount(account, type);
    }

    @Override
    public void updateAccount(long user_id, Account account, ContractType type) {
        this.accountRepoitory.updateAccount(user_id, account, type);
    }

    @Override
    public void deleteAccount(long user_id, ContractType type) {
        this.accountRepoitory.deleteAccount(user_id, type);
    }
}
