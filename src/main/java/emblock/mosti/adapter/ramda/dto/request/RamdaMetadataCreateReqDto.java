package emblock.mosti.adapter.ramda.dto.request;

public record RamdaMetadataCreateReqDto(
        String name,
        String createdBy,
        String createdDate,
        String image,
        String thumbnailImage,
        String description,
        Integer maxMintLimit

        /***
         *
         *
         *
         *         String json = """
         *     {
         *         "name": "My first NFT",
         *             "createdBy": "mehi",
         *             "createdDate": "2021-05-15",
         *             "image": "%s",
         *             "thumbnailImage": "%s",
         *             "description": "Metadata description",
         *
         *         "maxMintLimit": 100
         *
         *     }
         * """.formatted("6223706423827770110","6223706423827770110")
         */
){}
