package org.example.chatgpttest.controller;

import org.example.chatgpttest.config.ChatGPTConfig;
import org.example.chatgpttest.entity.ChatRequest;
import org.example.chatgpttest.entity.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/chat")
public class ChatController {
	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	ChatGPTConfig config;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String chat(@RequestParam(required = true, value = "prompt") String message) {
		ChatRequest request = new ChatRequest(config.getModel(), message);

		// call the API
		ChatResponse response = restTemplate.postForObject(config.getUrl(), request, ChatResponse.class);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			return "No response";
		}

		// return the first response
		return response.getChoices().get(0).getMessage().getContent();
	}
}
