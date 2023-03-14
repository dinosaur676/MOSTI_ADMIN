package emblock.framework.helper;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

public class StringHelper {
    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isBlankString(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static int convertToInt(String strVal) {
        int resVal = 0;
        try {
            resVal = new BigDecimal(strVal).intValue();
        } catch (NumberFormatException nfe) {
            resVal = 0;// -1
        }

        return resVal;
    }

    public static BigDecimal convertToBigDecimal(String strVal) {
        BigDecimal resVal = BigDecimal.ZERO;
        try {
            resVal = new BigDecimal(strVal);
        } catch (NumberFormatException nfe) {
            resVal = BigDecimal.ZERO;
        }

        return resVal;
    }

    public static String toRemovedMinusString(String target) {
        if (target == null || target.length() == 0) return "";
        return target.replaceAll("-", "").replaceAll(" ", "");
    }

    public static String 앞부분0제거(String target) {
        if (target == null || target.length() == 0) return "";

        return target.replaceFirst("^0+(?!$)", "");
    }

    public static String 마지막글자제거(String target) {
        return (target == null || target.length() == 0) ? null : (target.substring(0, target.length() - 1));
    }

    public static boolean 휴대폰번호여부(String target) {
        if (target == null || target.length() == 0)
            return false;
        String regEx = "(010|011|016|017|018|019)(.+)(.{4})";
        return Pattern.matches(regEx, target);
    }

    public static String toSafeBase64Encoded(String original) {
        //byte[] message = original.getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
    }

    public static String toSafeBase64Decoded(String encoded) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static final int getByteSizeToComplex(String str) {
        //자바는 기본적으로 문자에 대해서는 유니코드를 사용하기 때문에 , 영문이든 한글이든 2바이트로 처리합니다.
        //다른 시스템과 연동되는 부분등과 같은 곳에서 쓰이기 위해서 한글은 2바이트 영문은 1바이트로 사이즈를 구해야.

        int en = 0;
        int ko = 0;
        int etc = 0;

        char[] string = str.toCharArray();

        for (int j=0; j<string.length; j++) {
            if (string[j]>='A' && string[j]<='z') {
                en++;
            } else if (string[j]>='\uAC00' && string[j]<='\uD7A3') {
                ko++;
                ko++;
            } else {
                etc++;
            }
        }

        return (en + ko + etc);
    }

    public static String arrayToCommaList(String[] list) {
        if (list ==null)
            return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< list.length;i++) {
            sb.append(list[i]);
            if (i < list.length - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    public static String getSalt(){
        //String today = DateUtil.getDateString(); //날짜를 추가하여 이용할 수도 있음.
        //For salt (random)
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer salt = new StringBuffer();
        java.util.Random rnd = new java.util.Random();
        // build a random 9 chars salt
        while (salt.length() < 9)
        {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.substring(index, index+1));
        }
        String saltStr=salt.toString();
        //session.setAttribute("ran",saltStr);  // Salt String is stored in session so that we can retrieve in the serverside which is used to calculate MD5 hash of  salted Password.
        return saltStr;
    }

}
