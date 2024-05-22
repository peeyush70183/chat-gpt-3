package org.example.chatgpttest.entity;

import org.example.chatgpttest.config.ChatGPTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ChatGPTCall {
	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	ChatGPTConfig config;

	public String getResponse(String prompt) {
		ChatRequest request = new ChatRequest(config.getModel(), prompt, 100);

		ChatResponse response = restTemplate.postForObject(config.getUrl(), request, ChatResponse.class);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			return "No response";
		}
		return response.getChoices().get(0).getMessage().getContent();
	}
}
