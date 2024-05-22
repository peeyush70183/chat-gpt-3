package org.example.chatgpttest.controller;

import org.example.chatgpttest.entity.ChatGPTCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.stream.Collectors;

@RestController
public class FileReaderController {
	private static final String DIR = "Uploads";
	private static final String OUTPUT_DIR = "output";

	@Autowired
	ChatGPTCall chatGPTCall;

	@RequestMapping(value = "/readFile", method = RequestMethod.GET)
	public ResponseEntity<String> readFile(@RequestParam("filename") String filename) {
		String filePath = System.getProperty("user.dir") + File.separator + DIR + File.separator + filename;
		String outputFileDir = System.getProperty("user.dir")  + File.separator + OUTPUT_DIR;
		File outputDir = new File(outputFileDir);
		if(!outputDir.exists()) {
			outputDir.mkdirs();
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileDir + File.separator + filename))) {
			String line;
			while((line = reader.readLine()) != null) {
				writer.write(line.trim() + "\t" + chatGPTCall.getResponse(line.trim()));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("File Written successfully : " + filePath);
	}
}
