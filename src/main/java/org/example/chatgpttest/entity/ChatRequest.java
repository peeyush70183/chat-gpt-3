package org.example.chatgpttest.entity;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
	private String model;
	private List<Message> messages;
	private int n;
	private double temperature;

	public ChatRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}
	public ChatRequest(String model, String prompt, int n) {
		this.model = model;
		this.n = n;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}

	public ChatRequest(String model, String prompt, int n, double temperature) {
		this.model = model;
		this.n = n;
		this.temperature = temperature;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "ChatRequest{" +
				"model='" + model + '\'' +
				", messages=" + messages +
				", n=" + n +
				", temperature=" + temperature +
				'}';
	}
}
