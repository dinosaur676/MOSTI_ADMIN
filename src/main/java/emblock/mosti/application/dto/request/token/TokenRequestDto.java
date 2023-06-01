package emblock.mosti.application.dto.request.token;

import emblock.mosti.adapter.blockchain.ContractType;

import jakarta.validation.constraints.NotEmpty;

public record TokenRequestDto(
        @NotEmpty
        String name,
        @NotEmpty
        String studentId,
        @NotEmpty
        long tokenId,
        @NotEmpty
        ContractType type
) {
}
