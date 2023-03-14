package emblock.framework.helper;


import emblock.framework.exception.DomainException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {
    public static String 현재일8문자열() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    public static String 현재일시14문자열() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }

    public static String 현재일시20문자열() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS");
        return LocalDateTime.now().format(formatter);
    }

    public static String 일시14문자열(LocalDateTime 일시) {
        if (일시 == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return 일시.format(formatter);
    }

    public static String 일자8문자열(LocalDateTime 일시) {
        if (일시 == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return 일시.format(formatter);
    }

    public static String 월일시분문자열(LocalDateTime 일시) {
        return 일시.format(DateTimeFormatter.ofPattern("M월 d일 H시 m분"));
    }

    public static String 월일시분문자열(String 년월일시분초) {
        return by14문자열(년월일시분초).format(DateTimeFormatter.ofPattern("M월 d일 H시 m분"));
    }

    public static LocalDateTime by14문자열(String 년월일시분초) {
        return LocalDateTime.parse(년월일시분초, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static long 밀리초_분으로_환산(long 밀리초) {
        return TimeUnit.MILLISECONDS.toMinutes(밀리초);
    }

    public static long 일자시간_차이계산(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null)
            throw new DomainException("INVALID from or to");
        return ChronoUnit.MINUTES.between(from, to);
    }

    public static long 시간차이_분(LocalDateTime from, LocalDateTime to) {
        Duration duration = Duration.between(from, to);
        return duration.toMinutes();
    }


}
