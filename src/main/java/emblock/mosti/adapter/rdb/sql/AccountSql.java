package emblock.mosti.adapter.rdb.sql;

public class AccountSql {
    public static String findAccountById = "select * from %s where user_id = ?";

    public static String addAcount = "insert into %s(user_id, address, private_key, created_on) values(?, ?, ?, ?)";
    public static String updateAccount = "update ? set address = ?, key = ? where user_id = ?";
    public static String deleteAccount = "delete from ? where user_id = ?";

}
