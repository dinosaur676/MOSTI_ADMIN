package emblock.framework.domain;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class GuidFactory {

    public static String 시간기준_생성() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString().replace("-", "");
    }

    public static long 스노우플레이크_생성() {
        return SnowflakeIdGenerator.genId();
    }

    public static String 스노우플레이크문자열_생성() {
        return String.valueOf(스노우플레이크_생성());
    }
}
