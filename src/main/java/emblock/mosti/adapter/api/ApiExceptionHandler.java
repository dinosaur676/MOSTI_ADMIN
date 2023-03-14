package emblock.mosti.adapter.api;


import emblock.framework.dto.FailRespDto;
import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.*;
import emblock.mosti.application.domain.exception.InValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ApiExceptionHandler {
    public static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseDto handleNotFoundException(NotFoundException e) {
        return new SuccessRespDto("검색 결과가 없습니다.");
    }

    @ExceptionHandler(value = EmptyListException.class)
    @ResponseBody
    public ResponseDto handleEmptyListException(EmptyListException e) {
        return new SuccessRespDto(new ArrayList<>(), "내용이 없습니다.");
    }

    @ExceptionHandler(value = RepositoryException.class)
    @ResponseBody
    public ResponseDto handleSqlException(RepositoryException e) {
        log.error("[RepositoryException] " +  e.getMessage());
        return new FailRespDto("90", "오류가 발생하여 요청 처리가 실패하였습니다.","관리자에게 문의해주세요.");
    }

    @ExceptionHandler(value = RepositoryNotAffectedException.class)
    @ResponseBody
    public ResponseDto handleRepositoryNotAffectedException(RepositoryNotAffectedException e) {
        return new SuccessRespDto(new ArrayList<>(), "요청은 처리되었으나 변경사항은 없습니다.");
    }

    @ExceptionHandler(value = DomainException.class)
    @ResponseBody
    public ResponseDto handleDomainException(DomainException e) {
        log.error("[DomainException] " + e.getMessage());
        StackTraceElement[] elements = e.getStackTrace();
        for(StackTraceElement el: elements) {
            log.error(el.toString());
        }
        return new FailRespDto("90", "도메인 오류로 요청 처리가 실패하였습니다.", e.getMessage());
    }


    @ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public ResponseDto handleApplicationException(ApplicationException e) {
        log.error("[ApplicationException] " + e.getMessage());
        StackTraceElement[] elements = e.getStackTrace();
        for(StackTraceElement el: elements) {
            log.error(el.toString());
        }
        return new FailRespDto("90", "응용시스템 오류로 요청 처리가 실패하였습니다.", e.getMessage());
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto handleException(Exception e) {
        log.error("[Exception] " + e.getMessage());
        return new FailRespDto("99", "요청 처리가 실패하였습니다.", e.getMessage());
    }

    @ExceptionHandler({InValidException.class,MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto handleInValidException(Exception e) {
        String errMessage = "";
        if(e instanceof InValidException)
            log.error("[InValidException] " + e.getMessage());
            errMessage = e.getMessage();
        if(e instanceof MethodArgumentNotValidException) {
            log.error("[MethodArgumentNotValidException] " + e.getMessage());
            errMessage = ((MethodArgumentNotValidException)e).getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }
        return new FailRespDto("91", "유효하지 않은 요청입니다.", errMessage);
    }

    class ExceptionDto{
        private String message;

        public ExceptionDto(String message) {
            this.message = message;
        }
    }

}
