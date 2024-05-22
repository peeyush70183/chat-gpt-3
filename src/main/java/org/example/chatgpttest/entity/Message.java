package org.example.chatgpttest.entity;

public class Message {
	private String role;
	private String content;

	public Message(String role, String content) {
		this.role = role;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Message{" +
				"role='" + role + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
