package emblock.mosti.adapter.ramda.dto.response;

public class RamdaErrorResponseDto extends RamdaBaseResponseDto{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RamdaErrorResponseDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
