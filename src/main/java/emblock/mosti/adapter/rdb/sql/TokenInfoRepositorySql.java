package emblock.mosti.adapter.rdb.sql;

public class TokenInfoRepositorySql {
    public static final String select = "select * from token_info where user_name = ?";
    public static final String selectByTokenId = "select * from token_info where token_id = ?";

    public static final String insert = "insert into token_info(token_id, meta_data, user_name, created_on) values(?, ?, ?, ?)";
}
