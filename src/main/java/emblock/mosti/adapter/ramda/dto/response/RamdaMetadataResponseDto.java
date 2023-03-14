package emblock.mosti.adapter.ramda.dto.response;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class RamdaMetadataResponseDto extends RamdaBaseResponseDto{
    private Metadata data;

    public Metadata getData() {
        return data;
    }

    public void setData(Metadata data) {
        this.data = data;
    }

    public record Metadata(
        String id,
        String name,
        String createdBy,
        String createdDate,
        String image,
        String thumbnail,
        String media,
        String youtubeUrl,
        String backgroundColor,
        String description,
        Property[] properties,
        String maxMintLimit,
        LocalDateTime createdAt,
        LinkedHashMap<String,Object> onChainProperties
    ){}

    record Property(
            String displayType,
            String type,
            String value
    ) {}

    @Override
    public String toString() {
        return "RamdaMetadataResponseDto{" +
                "data=" + data +
                '}';
    }
}
