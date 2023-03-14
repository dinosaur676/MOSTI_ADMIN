package emblock.framework.helper;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class Do {   //Domain Object Helper 의 약칭

    public static boolean 있음(Object 도메인객체) {
        return !비었음(도메인객체);
    }

    public static boolean 비었음(Object 도메인객체) {
        return ObjectUtils.isEmpty(도메인객체);
    }

    public static boolean 빈문자열임(String 문자열) {
        return !StringUtils.hasText(문자열);
    }

    public static boolean 문자열있음(String 문자열) {
        return StringUtils.hasText(문자열);
    }

    public static boolean 같은문자임(String 문자열, String 비교문자) {
        return 비교문자.equals(문자열);
    }

    public static boolean 다른문자임(String 문자열, String 비교문자) {
        return !같은문자임(문자열, 비교문자);
    }
}
