package emblock.mosti.application.domain;

public class TokenType {
    long type;
    String description;

    public TokenType(long type, String description) {
        this.type = type;
        this.description = description;
    }

    public long getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
