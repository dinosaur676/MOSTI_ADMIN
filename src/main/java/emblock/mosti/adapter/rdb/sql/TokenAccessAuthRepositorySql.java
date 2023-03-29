package emblock.mosti.adapter.rdb.sql;

public class TokenAccessAuthRepositorySql {
    public static final String SQL_조회 = "SELECT * FROM token_access_auth WHERE student_id = ? ORDER BY created_on DESC LIMIT 1";
    
    public static final String SQL_추가 = """
        insert into token_access_auth (
            id, auth_key, student_id, created_on, expired_on)
        values ( ?, ?, ?, ?, ? )
    """;

}
