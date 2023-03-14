package emblock.mosti.application.event;

import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.domain.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RamdaEventHandler {
    private final IUserService userService;

    public RamdaEventHandler(IUserService userService) {
        this.userService = userService;
    }


    @EventListener
    @Async
    void onCreateUser(User user){
        this.userService.지갑정보생성및수정(user.getUserId());
    }
/*
    @EventListener
    @Async
    void onRegisterPowerGeneration(String address, PowerGeneration pg){
        this.powerGenerationService.NFT발급및정보수정(address,pg);
    }*/

}
