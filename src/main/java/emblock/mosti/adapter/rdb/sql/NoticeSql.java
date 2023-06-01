package emblock.mosti.adapter.rdb.sql;

public class NoticeSql {
    public static String 목록조회 = "select * from notice";
    public static String 조회 = "select * from notice where writer = ?";
    public static String 수정 = "update notice set content = ? where id = ?";
    public static String 삭제 = "delete from notice where id = ?";
    public static String 추가 = "insert into notice(writer, title, content, created_on) values(?, ?, ?, ?)";
}
