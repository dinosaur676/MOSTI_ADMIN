package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.EmptyListException;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IUserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseDto 목록조회(Model model, Principal principal){
        List<UserRespDto> userList = this.userService.목록조회();
        if(userList == null || userList.isEmpty() ) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(userList, "사용자 조회가 성공적으로 완료되었습니다.");
    }
    @GetMapping("/{loginId}")
    public ResponseDto 조회(Model model, Principal principal, @PathVariable String loginId){
        UserRespDto user = this.userService.조회(loginId);
        if(user == null) {
            throw new EmptyListException("NO DATA");
        }
        return new SuccessRespDto(user, "사용자 조회가 성공적으로 완료되었습니다.");
    }
    
    @PostMapping
    public ResponseDto 추가(@Valid @RequestBody UserCreateReqDto reqDto, Principal principal){
        this.userService.추가(reqDto);
        return new SuccessRespDto("사용자 등록이 성공적으로 완료되었습니다.");
    }

    @PutMapping
    public ResponseDto 수정(@Valid @RequestBody UserUpdateReqDto reqDto, Principal principal){
        this.userService.수정(reqDto);
        return new SuccessRespDto("사용자 수정이 성공적으로 완료되었습니다.");
    }


}
