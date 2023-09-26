package emblock.mosti.adapter.rdb.sql;

public class UserTokenRepositorySql {
    public static final String select = """
                select  ut.token_id as token_id, ut.id as id, ti.meta_data as meta_data, ut.wallet_name as wallet_name, ut.wallet_tag as wallet tag,
                    ut.user_name as user_name, ut.created_on as created_on, ut.deleted_on as deleted_on
                from user_token as ut, token_info as ti 
                where ut.user_name = ? and ut.wallet_name = ? and ut.wallet_tag = ? and ut.token_id = ti.token_id
                 
            """;
    public static final String insert = "insert into user_token(token_id, wallet_name, wallet_tag, user_name, created_on, deleted_on) " +
            "values(?, ?, ?, ?, ?, ?)";

    public static final String delete = "delete from user_token where user_name = ? and wallet_name = ? and wallet_tag = ? and token_id = ?";
}
