package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.FileMapper;

@Service
public class FileService {
	private final FileMapper fileMapper;

	public FileService(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	/**
	 * @param username
	 * @return
	 */
	public List<File> getFiles(Integer userid) {
		return fileMapper.getFilesByUser(userid);
	}

	/**
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public int addFile(MultipartFile fileUpload, Integer userId) throws IOException {

		return fileMapper.insert(new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
				Long.toString(fileUpload.getSize()), userId, fileUpload.getBytes()));
	}

	public void deleteFile(Integer fileId) {
		fileMapper.deleteFile(fileId);
	}

	public boolean isFilePresentAlready(String originalFilename, Integer userId) {
		return fileMapper.getFileNamesForUser(userId).contains(originalFilename);
	}

	public File getFilesByIdAndUser(Integer userId, Integer fileId) {
		return fileMapper.getFileByIdAndUser(userId, fileId);
	}
}
