package emblock.mosti.application;

import emblock.mosti.application.domain.MintWait;
import emblock.mosti.application.dto.response.token.MintWaitRespDto;
import emblock.mosti.application.port.in.IMintWaitService;
import emblock.mosti.application.port.out.IMintWaitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MintWaitService implements IMintWaitService {

    final IMintWaitRepository mintWaitRepository;

    public MintWaitService(IMintWaitRepository mintWaitRepository) {
        this.mintWaitRepository = mintWaitRepository;
    }

    @Override
    public List<MintWaitRespDto> select(String userName) {
        return mintWaitRepository.select(userName).stream().map(MintWaitRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public MintWaitRespDto selectByTokenId(String userName, long tokenId) {
        MintWait mintWait = mintWaitRepository.selectByTokenId(userName, tokenId);

        if(mintWait == null)
            return null;

        return MintWaitRespDto.생성(mintWait);
    }

    @Override
    public void insert(String userName, long tokenId) {
        mintWaitRepository.insert(userName, tokenId);
    }

    @Override
    public void update(String userName, long tokenId) {
        mintWaitRepository.update(userName, tokenId);
    }
}
