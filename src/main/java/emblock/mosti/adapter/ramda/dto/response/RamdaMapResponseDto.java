package emblock.mosti.adapter.ramda.dto.response;

import java.util.Map;

public class RamdaMapResponseDto extends RamdaBaseResponseDto{
    Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RamdaWalletResponseDto{" +
                "data=" + data +
                '}';
    }
}
