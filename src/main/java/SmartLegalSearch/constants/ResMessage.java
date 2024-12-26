package SmartLegalSearch.constants;

public enum ResMessage {
	// 格式: 全大寫英文(code, message);
	SUCCESS(200, "Success!!"),//
	DATE_ERROR(400, "Date error!!"),//
	EMAIL_DUPLICATED(400, "Email duplicated!!");

	private int code;

	private String message;

	// 需要有建構方法和 get 方法， set 就不用
	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
