package emblock.framework.domain;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

public class SnowflakeIdGenerator {

    /** 시간 부분의 길이 */
    private static final int TIME_LEN = 41;
    /** 데이터 센터 ID의 길이 */
    private static final int DATA_LEN = 5;
    /** 머신 id의 길이 */
    private static final int WORK_LEN = 5;
    /** 시퀀스의 길이(밀리초) */
    private static final int SEQ_LEN = 12;

    /** 시작 시간 정의  */
    private static final long START_TIME = 863128800L;
    /** ID가 마지막으로 생성된 시간 */
    private static long LAST_TIME_STAMP = -1L;
    /** 시간 부분을 왼쪽으로 22만큼 이동할 비트 수 */
    private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;

    /** 자동으로 데이터 센터 ID를 얻습니다(0-31 사이의 숫자는 수동으로 정의할 수 있음) */
    private static final long DATA_ID = getDataId();
    /** 자동 머신 ID(0-31 사이의 숫자는 수동으로 정의할 수 있음) */
    private static final long WORK_ID = getWorkId();
    /** 최대 데이터 센터 ID는 31입니다. */
    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
    /** 최대 머신 ID는 31입니다. */
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
    /** 데이터 센터 ID의 매개변수 32를 무작위로 가져옵니다. */
    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
    /** 머신 ID의 매개변수 32를 무작위로 가져옵니다. */
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
    /** 데이터 센터 ID 왼쪽 시프트 번호 17 */
    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
    /** 머신 ID를 12만큼 왼쪽으로 시프트 */
    private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;

    /** 마지막 시퀀스 값(밀리초) */
    private static long LAST_SEQ = 0L;
    /** 시퀀스의 최대값(밀리초)은 4095 */
    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);


    public synchronized static long genId(){
        long now = System.currentTimeMillis();

        //현재 시간이 마지막 ID에 의해 생성된 타임스탬프보다 작으면 시스템 시계가 롤백되었을 때 예외가 발생해야 함을 의미합니다.
        if (now < LAST_TIME_STAMP) {
            throw new RuntimeException(String.format("Incorrect system time! %d milliseconds 내에 Snowflake ID 생성 거부됨!", START_TIME - now));
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            if (LAST_SEQ == 0){
                now = nextMillis(LAST_TIME_STAMP);
            }
        } else {
            LAST_SEQ = 0;
        }

        // 마지막으로 아이디가 생성된 시간
        LAST_TIME_STAMP = now;

        return ((now - START_TIME) << TIME_LEFT_BIT) | (DATA_ID << DATA_LEFT_BIT) | (WORK_ID << WORK_LEFT_BIT) | LAST_SEQ;
    }


    /**
     * 마지막 타임스탬프와 같지 않은 다음 다른 밀리초의 타임스탬프를 가져옵니다.
     */
    public static long nextMillis(long lastMillis) {
        long now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    /**
     * 문자열 s의 바이트 배열을 가져온 다음 배열의 요소를 추가하고 (max+1)의 나머지를 취합니다.
     */
    private static int getHostId(String s, int max){
        byte[] bytes = s.getBytes();
        int sums = 0;
        for(int b : bytes){
            sums += b;
        }
        return sums % (max+1);
    }

    /**
     * 호스트 주소에 따라 나머지를 취하고 예외가 발생하면 0에서 31 사이의 난수를 얻습니다.
     */
    public static int getWorkId(){
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }
    }

    /**
     * 호스트 이름에 따라 나머지를 취하고 예외가 발생하면 0에서 31 사이의 난수를 얻습니다.
     */
    public static int getDataId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(DATA_RANDOM);
        }
    }

//    public static void main(String[] args) {
//        Set ids = new HashSet();
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 3000000; i++) {
//            ids.add(genId());
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("총 생성 id[" + ids.size() + "]个，소요 시간[" + (end - start) + "]milliseconds");
//    }
}
