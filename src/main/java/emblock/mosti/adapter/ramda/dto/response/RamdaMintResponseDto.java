package emblock.mosti.adapter.ramda.dto.response;

public class RamdaMintResponseDto extends RamdaBaseResponseDto{


    public Token data;

    public Token getData() {
        return data;
    }

    public void setData(Token data) {
        this.data = data;
    }

    public record Token(
       MintResp token
    ){}

    public record MintResp(
       String id,
       String currencyCode,
       String tokenId,
       String issuedTo,
       String transactionHash,
       String transactionStatus,
       String currentOwner,
       String lastTransactionHash,
       String editionId,
       RamdaMetadataResponseDto.Metadata metadata,
       String contractName,
       String contractAddress,
       CreateBy createBy,
       String createdAt,
       String callbackUrl,
       String callbackStatus,
       String callbackRetryCount
    ){}

    public record CreateBy(
        String accountId,
        String iamUserId
    ){}

    @Override
    public String toString() {
        return "RamdaMintResponseDto{" +
                "data=" + data +
                '}';
    }
}
