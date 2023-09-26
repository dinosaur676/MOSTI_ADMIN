package emblock.mosti.adapter.rdb.sql;

public class MintWaitRepositorySql {
    public static final String select = "select * from mint_wait where user_name = ?";
    public static final String selectByTokenId = "select * from mint_wait where user_name = ? and token_id = ?";
    public static final String insert = "insert into mint_wait(user_name, token_id, created_on, enable) values(?, ?, ?, true)";
    public static final String update = "update mint_wait set enable = false where token_id = ? and user_name = ?";
}
