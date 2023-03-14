package emblock.framework.dto;


import emblock.framework.helper.StringHelper;

public class FailRespDto extends ResponseDto {

	public FailRespDto(String 에러코드, String 메시지) {
		this(에러코드, 메시지, "");
	}

	public FailRespDto(String 에러코드, String 메시지, String 상세정보) {
		super();
		this.message = StringHelper.isBlankString(메시지) ? FAIL_TEXT : 메시지;
		this.status = StringHelper.isBlankString(에러코드) ? FAIL_CODE : 에러코드;
		this.info = StringHelper.isNull(상세정보) ? "" : 상세정보;
		//this.data = new Object();
	}
}
