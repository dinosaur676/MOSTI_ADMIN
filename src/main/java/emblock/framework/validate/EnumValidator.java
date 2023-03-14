package emblock.framework.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<EnumVal, Enum<?>> {
    private EnumVal annotation;

    @Override
    public void initialize(EnumVal constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Stream.of(this.annotation.enumClass().getEnumConstants())
                .filter(e -> e.name() == value.name()).map(e -> true).findFirst().orElse(false);
    }
}
