package edu.ustc.api.core;

import java.io.Serializable;

public class PostData implements Serializable {

	private static final long serialVersionUID = 3254704418243215524L;

	private String fileName;
	private byte[] data;

	public PostData() {
		super();
	}

	public PostData(String fileName, byte[] data) {
		super();
		this.fileName = fileName;
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
