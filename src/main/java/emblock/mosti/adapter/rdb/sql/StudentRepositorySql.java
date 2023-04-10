package emblock.mosti.adapter.rdb.sql;

public class StudentRepositorySql {
    public static final String SQL_목록조회 = "select * from student";
    public static final String SQL_조회 = "SELECT * FROM student WHERE user_id = ?";

    public static final String SQL_이름학번조회 = "SELECT * FROM student WHERE name = ? AND student_id = ?";

    public static final String SQL_추가 = """
        insert into student (
            user_id, name, student_id, school, major, created_on, updated_on)
        values ( ?, ?, ?, ?, ?, ?, ? )
    """;

    public static final String SQL_수정 = """
        UPDATE student
           SET name = ?             
              ,school = ? 
              ,major = ?
              ,updated_on = ? 
         WHERE user_id = ?    
    """;
}
