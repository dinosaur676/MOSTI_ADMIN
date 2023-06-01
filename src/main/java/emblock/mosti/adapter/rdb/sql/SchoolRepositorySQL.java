package emblock.mosti.adapter.rdb.sql;

public class SchoolRepositorySQL {

    public static String 학교목록조회 = "select * from school";
    public static String 학교이름으로조회 = "select * from school where school_name = ?";
    public static String 유저아이디로조회 = "select * from school where user_id = ?";

    public static String 추가 = "insert into school(school_name, school_token_id, created_on) values (?, ?, ?)";

    public static String 학교이름수정 = "update school set school_name = ? where school_token_id = ?";
    public static String 학교삭제 = "delete from school where school_name = ?";
    public static String 인증 = """
            select * from school sc, user_token ut, token_info ti
            where ut.token_id = ti.token_id
            and ti.token_id = sc.school_token_id
            and ti.type = 1
            and ut.address = ?
            and ti.contract_type = 'P'
            """;
}
