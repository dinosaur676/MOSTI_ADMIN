package emblock.framework.dto;

public abstract class ResponseDto {
	protected static final String SUCCESS_CODE = "00";
	protected static final String FAIL_CODE = "90";
	protected static final String SUCCESS_TEXT = "SUCCESS";
	protected static final String FAIL_TEXT = "FAIL";

	protected String status;
	protected String message;
	protected String info;
	protected Object data;

	public ResponseDto() {
		this.status = SUCCESS_CODE;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


}
