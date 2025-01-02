package SmartLegalSearch.entity;

public class ChatMessage {

	// 使用者名稱
	private String senderName;
	// 發送文字
	private String content;
	// 房間名稱
	private String room;

	public ChatMessage() {
		super();
	}

	public ChatMessage(String senderName, String content, String room) {
		super();
		this.senderName = senderName;
		this.content = content;
		this.room = room;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getContent() {
		return content;
	}

	public String getRoom() {
		return room;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
