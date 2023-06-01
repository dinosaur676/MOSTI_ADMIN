package emblock.mosti.adapter.rdb.sql;

public class LogSql {
    public static final String 목록조회 = """
            select user.user_id as user_id, user.login_id as login_id, user.user_name as user_name, log.label as label, log.created_on as created_on, user.type as type, user.status as status
            from user, log
            where user.user_id = log.user_id and log.created_on between ? and ? 
            """;
    public static final String 조건_유저이름 = "and user.user_name = \"%s\" ";
    public static final String 조건_라벨 = "and log.label = \"%s\" ";

    public static final String 추가 = "insert into log(user_id, label, created_on) values(?, ?, ?)";

}
