package com.ibsplc.rtf.vo;
import java.util.List;

/**
 * 
 * @author A-8097
 *
 */
public class ExecuteTestVO {

	private String testImageName;
	private List<String> paramterList;

	public String getTestImageName() {
		return testImageName;
	}

	public void setTestImageName(String testImageName) {
		this.testImageName = testImageName;
	}

	public List<String> getParamterList() {
		return paramterList;
	}

	public void setParamterList(List<String> paramterList) {
		this.paramterList = paramterList;
	}

}