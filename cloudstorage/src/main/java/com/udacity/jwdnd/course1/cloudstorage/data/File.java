/**
 * 
 */
package com.udacity.jwdnd.course1.cloudstorage.data;

/**
 * @author amit
 *
 */
public class File {
	Integer fileId;
	String filename;
	String contenttype;
	String filesize;
	Integer userId;
	byte[] filedata;

	public File(Integer fileId, String filename, String contenttype, String filesize, Integer userId, byte[] filedata) {
		super();
		this.fileId = fileId;
		this.filename = filename;
		this.contenttype = contenttype;
		this.filesize = filesize;
		this.userId = userId;
		this.filedata = filedata;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

}
