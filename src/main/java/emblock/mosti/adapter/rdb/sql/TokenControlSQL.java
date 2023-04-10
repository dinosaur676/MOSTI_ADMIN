package emblock.mosti.adapter.rdb.sql;

public class TokenControlSQL {
    public static final String 발행한토큰조회 = "select * from token_info where token_id = ? and contract_type = ?";
    public static final String 발행한토큰목록조회 = "select * from token_info where token_owner_address = ?";

    public static final String 토큰생성 = """
            insert into token_info(token_id, meta_data, created_on, token_owner_address, 
            type, contract_type) values(?, ?, ?, ?, ?, ?)
            """;

    public static final String 사용자소유토큰조회 = """
            select * from user_token where account = ? and token_id = ? and contract_type = ?
            """;

    public static final String 사용자소유토큰목록조회 = """
            select * from user_token where account = ? and contract_type = ?
            """;

    public static final String 사용자토큰추가 = """
            insert into user_token(account, token_id, contract_type, created_on, deleted_on) values(?, ?, ?, ?, ?)
            """;

    public static final String 사용자토큰삭제 = """
             delete from user_token where account = ? and token_id = ? and contract_type = ?
             """;

    public static final String 토큰타입추가 = """
             insert into token_type(description) values(?)
             """;

    public static final String 토큰타입조회 = """
            select * from token_type
            """;

    public static final String 토큰타입수정 = """
            update token_type
            set description = ?
            where type = ?
            """;

}
