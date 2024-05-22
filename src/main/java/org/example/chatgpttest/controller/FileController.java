package org.example.chatgpttest.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@RestController
public class FileController {
	private static final String DIR = "Uploads";

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){

		if(file.isEmpty()){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
		}
		String fileDir = System.getProperty("user.dir") + File.separator + DIR;
		File dir = new File(fileDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String filePath = fileDir + File.separator + file.getOriginalFilename();
		System.out.println("File path: " + filePath);
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to upload file at the moment. Something went wrong...");
	}

	// Getting list of filenames that have been uploaded
	@RequestMapping(value = "/getFiles", method = RequestMethod.GET)
	public String[] getFiles()
	{
		String folderPath = System.getProperty("user.dir") + File.separator + DIR;
		File dir = new File(folderPath);
		if(!dir.exists()){
			return new String[]{"Directory does not exist"};
		}
		File directory= new File(folderPath);
		String[] filenames = directory.list();
		return filenames;

	}

	@RequestMapping(value = "/download/{path:.+}", method = RequestMethod.GET)
	public ResponseEntity downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {

		String fileUploadpath = System.getProperty("user.dir") + File.separator + DIR;
		String[] filenames = this.getFiles();
		boolean contains = Arrays.asList(filenames).contains(filename);
		if(!contains) {
			return new ResponseEntity("FIle Not Found", HttpStatus.NOT_FOUND);
		}
		String filePath = fileUploadpath+File.separator + filename;

		File file= new File(filePath);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders headers = new HttpHeaders();

		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + file.getName() + "\"";

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.body(resource);

	}
}
