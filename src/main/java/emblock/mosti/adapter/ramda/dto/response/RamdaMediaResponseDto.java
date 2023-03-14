package emblock.mosti.adapter.ramda.dto.response;

import java.time.LocalDateTime;

public class RamdaMediaResponseDto extends RamdaBaseResponseDto{
    public Media data;

    public Media getData() {
        return data;
    }

    public void setData(Media data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RamdaMediaResponseDto{" +
                "data=" + data +
                '}';
    }

    public record Media(
        String id,
        String originalFilename,
        String mimetype,
        long size,
        String mediaUrl,
        LocalDateTime createdAt
    ){}

}
