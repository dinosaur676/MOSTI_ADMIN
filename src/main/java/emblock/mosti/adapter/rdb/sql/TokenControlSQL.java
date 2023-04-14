package emblock.mosti.adapter.rdb.sql;

public class TokenControlSQL {
    public static final String 발행한토큰조회 = "select * from token_info where token_id = ? and contract_type = '%c'";
    public static final String 발행한토큰조회by계정_토큰종류 = "select * from token_info where token_owner_address = ? and type = ? and contract_type = '%c'";

    public static final String 발행한토큰목록조회 = """
            select ti.token_id as token_id, ti.meta_data as meta_data, us.user_name as token_owner, 
            tt.description as type, ti.contract_type as contract_type, ti.created_on as created_on
            from token_info ti, public_account pa, token_type tt, user us
            where ti.token_owner = pa.address and pa.user_id = us.user_id and ti.type = tt.type and ti.contract_type = "%c"
            and ti.token_owner = ?
            """;

    public static final String 토큰생성 = """
            insert into token_info(token_id, meta_data, created_on, token_owner, 
            type, contract_type) values(?, ?, ?, ?, ?, '%c')
            """;

    public static final String 사용자소유토큰조회 = """
            select ti.token_id as token_id, ti.meta_data as meta_data, us.user_name as token_owner, 
            tt.description as type, ti.contract_type as contract_type, ut.created_on as created_on,
            ut.deleted_on as deleted_on, ut.address as address
            from user_token ut, token_info ti, public_account pa, token_type tt, user us
            where ti.token_owner = pa.address and pa.user_id = us.user_id and ti.type = tt.type and ti.contract_type = '%c'
            and ut.token_id = ti.token_id
            and ut.address = ?
            and ti.token_id = ?
            """;

    public static final String 사용자소유토큰목록조회 = """
            select ti.token_id as token_id, ti.meta_data as meta_data, us.user_name as token_owner, 
            tt.description as type, ti.contract_type as contract_type, ut.created_on as created_on,
            ut.deleted_on as deleted_on, ut.address as address
            from user_token ut, token_info ti, public_account pa, token_type tt, user us
            where ti.token_owner = pa.address and pa.user_id = us.user_id and ti.type = tt.type and ti.contract_type = '%c'
            and ut.token_id = ti.token_id
            and ut.address = ?
            """;

    public static final String 사용자토큰추가 = """
            insert into user_token(address, token_id, contract_type, created_on, deleted_on) values(?, ?, '%c', ?, ?)
            """;

    public static final String 사용자토큰삭제 = """
             delete from user_token where address = ? and token_id = ? and contract_type = '%c'
             """;

    public static final String 토큰타입추가 = """
             insert into token_type(description) values(?)
             """;

    public static final String 토큰타입목록조회 = """
            select * from token_type
            """;

    public static final String 토큰타입조회 = """
            select * from token_type where type = ?
            """;

    public static final String 토큰타입수정 = """
            update token_type
            set description = ?
            where type = ?
            """;

}
