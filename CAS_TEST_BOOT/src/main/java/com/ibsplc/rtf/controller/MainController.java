package com.ibsplc.rtf.controller;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ibsplc.rtf.vo.ExecuteTestVO;

@RestController
public class MainController {
	
	@Value("${rapidtest.executeTest.password}")
	private String rtfPassword;
	
	@Value("${rapidtest.executeTest.username}")
	private String rtfUserName;

	@Value("${rapidtest.executeTest.rtfExecutionFilePath}")
	private String rtfExecutionFilePath;

	@Value("${rapidtest.executeTest.rftFileName}")
	private String rftFileName;
	
	@GetMapping(path="/test")
	public String test() {
		return "Completed";
	}
	
	/**
	 * Method to execute the RTF test
	 * 
	 * @param userName
	 * @param password
	 */
	@PostMapping(path = "/executeTest")
	public void executeTest(@RequestHeader("userName") String userName, @RequestHeader("password") String password,
			@RequestBody ExecuteTestVO executeTestVO) {
		if (null != userName && null != password) {
			Base64.Decoder decoder = Base64.getDecoder();
			String decodedPassword = new String(decoder.decode(rtfPassword));
			if (userName.equals(rtfUserName) && decodedPassword.equals(password)) {
				File dir = new File(rtfExecutionFilePath);
				ProcessBuilder pb;
				if (rftFileName.endsWith(".sh")) {
					pb = new ProcessBuilder("/bin/bash", rftFileName, executeTestVO.getTestImageName(),
							String.join(", ", executeTestVO.getParamterList()));
				} else {
					pb = new ProcessBuilder("cmd.exe", "/C", "Start /WAIT", rftFileName,
							executeTestVO.getTestImageName(), String.join(",", executeTestVO.getParamterList()));
				}
				pb.directory(dir);
				try {
					Process p = pb.start();
					System.out.println(Thread.currentThread().getName());
					Thread.sleep(100000);
//					int exitValue = p.waitFor();
//					System.out.println("Script executed with exit value-->" + exitValue);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
