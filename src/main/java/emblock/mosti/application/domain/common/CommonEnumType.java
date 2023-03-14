package emblock.mosti.application.domain.common;


import java.util.stream.Stream;

public interface CommonEnumType {
    String getCode();
    String getCodeName();
    static  <E extends Enum<E> & CommonEnumType> E jsonCreator(String value, Class<E> type) {
        if (value == null) {
            return null;
        }
        return Stream.of(type.getEnumConstants()).filter(e-> e.name().equals(value)).findFirst().orElse(null);
    }

}