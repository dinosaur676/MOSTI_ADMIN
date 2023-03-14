package emblock.mosti.adapter.rdb.sql;

public class AdminUserRepositorySql {
    public static final String SQL_목록조회 = "select * from admin_user";
    public static final String SQL_조회 = "select * from admin_user where name=?";

    public static final String SQL_추가 = """ 
        insert into admin_user(uid, name, password, email, status, created_on) values(?,?,?,?,?, ?)""";

    public static final String SQL_수정 = """ 
        UPDATE admin_user SET name=?, email=?, status=?, updated_on=? WHERE uid=?""";

    public static final String SQL_암호수정 = "UPDATE admin_user SET password=?, updated_on=? WHERE uid=?";

    public static final String SQL_삭제 = "DELETE FROM admin_user WHERE uid=?";
}
