package emblock.mosti.adapter.api;


import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.EmptyListException;
import emblock.framework.exception.NotFoundException;
import emblock.framework.helper.Do;
import emblock.mosti.application.dto.AdminUserDto;
import emblock.mosti.application.dto.request.AdminUserCreateReqDto;
import emblock.mosti.application.dto.request.AdminUserUpdatedReqDto;
import emblock.mosti.application.port.in.IAdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-users")
public class AdminUserController {
    public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final IAdminUserService adminUserService;

    public AdminUserController(IAdminUserService adminUserService) {
        Assert.notNull(adminUserService, "adminUserService 개체가 반드시 필요!");
        this.adminUserService = adminUserService;
    }

    @GetMapping()
    public ResponseDto 목록조회() {
        List<AdminUserDto> 목록 = this.adminUserService.목록조회();

        if (목록 == null || 목록.isEmpty() )
            throw new EmptyListException("NO DATA");
        return new SuccessRespDto(목록, 목록.size() + " 건의 결과가 검색 되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseDto 조회(@PathVariable String id) {

        AdminUserDto adminUserDto = this.adminUserService.조회(id);
        if (adminUserDto == null) {
            logger.error("NOT FOUND ID: " + id);
            throw new NotFoundException(id + " 사용자 정보를 찾을 수 없습니다.");
        }

        return new SuccessRespDto(adminUserDto, "사용자 조회가 성공적으로 완료되었습니다.");
    }

    @PostMapping("")
    public ResponseDto 추가(@RequestBody AdminUserCreateReqDto reqDto) {
        String encPassword = this.passwordEncoder.encode(reqDto.password());
        this.adminUserService.추가(reqDto.name(), encPassword, reqDto.email());

        return new SuccessRespDto("사용자 추가가 성공적으로 완료되었습니다.");
    }

    @PutMapping("")
    public ResponseDto 수정(@RequestBody AdminUserUpdatedReqDto reqDto) {
        String 암호 = null;
        if (Do.문자열있음(reqDto.password()))
            암호 = this.passwordEncoder.encode(reqDto.password());

        this.adminUserService.수정(reqDto.id(), reqDto.name(), 암호, reqDto.email(), reqDto.status());
        return new SuccessRespDto("사용자 수정이 성공적으로 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseDto 삭제(@PathVariable String id) {
        this.adminUserService.삭제(id);

        return new SuccessRespDto("사용자 삭제가 성공적으로 완료되었습니다.");
    }


}
