package emblock.mosti.adapter.rdb.sql;

public class UserRepositorySql {
    public static final String SQL_목록조회 = "SELECT * FROM user";

    public static final String SQL_학생목록조회 = "select * from user where school = ? and type = 'S'";
    public static final String SQL_조회 = "SELECT * FROM user WHERE login_id = ?";

    public static final String SQL_유저아이디로조회 = "select * from user where user_id = ?";
    public static final String SQL_역할별사용자목록조회 = "SELECT * FROM user WHERE role_id = ?";

    public static final String SQL_추가 = """
        insert into user (user_id, login_id, password, user_name, email, address, phone, cell_phone, type, status, role_id, school, created_on,
                  updated_on)
                  values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
    """;

    public static final String SQL_삭제 = """
            delete from user
            where user_id = ?
            """;

    public static final String SQL_수정 = """
        UPDATE user
           SET login_id = ?
               %s
              ,user_name = ?              
              ,email = ? 
              ,address = ?
              ,phone =?
              ,cell_phone =? 
              ,type = ? 
              ,status = ? 
              ,updated_on = ? 
         WHERE user_id = ?    
    """;
    public static final String SQL_지갑정보수정 = """
        UPDATE user
           SET wallet_id = ?
              ,public_address = ? 
              ,updated_on = ? 
         WHERE user_id = ?        
    """;

}
