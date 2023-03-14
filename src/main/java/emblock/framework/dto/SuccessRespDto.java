package emblock.framework.dto;


import emblock.framework.helper.StringHelper;

public class SuccessRespDto extends ResponseDto {
	public SuccessRespDto() {
		super();
		//this(new Object(), "", "");
	}

	public SuccessRespDto(String message) {
		this(null, message, "");
	}

	public SuccessRespDto(Object data, String message) {
		this(data, message, "");
	}

	public SuccessRespDto(Object data, String message, String info) {
		super();
        this.status = SUCCESS_CODE;
        this.data = data;
        this.message = StringHelper.isBlankString(message) ? FAIL_TEXT : message;
        this.info = info;
	}
}
