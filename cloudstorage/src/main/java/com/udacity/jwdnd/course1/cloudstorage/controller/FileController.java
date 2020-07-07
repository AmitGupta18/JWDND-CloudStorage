package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

@Controller
public class FileController {
	private FileService fileService;

	public FileController(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	@PostMapping("/file-upload")
	public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload,
			RedirectAttributes attr) throws IOException {
		User user = (User) authentication.getPrincipal();

		if (fileUpload.isEmpty()
				|| fileService.isFilePresentAlready(fileUpload.getOriginalFilename(), user.getUserId())) {
			attr.addFlashAttribute("errorMsg", "File is either not attached OR already available.");
			return "redirect:result?error";
		}

		fileService.addFile(fileUpload, user.getUserId());
		return "redirect:result?success";
	}

	@GetMapping("/file/delete/{id}")
	public String deleteFile(@PathVariable("id") Integer fileId, RedirectAttributes attr) {
		if (fileId < 0) {
			attr.addFlashAttribute("errorMsg", "File with id: " + fileId + " is not available.");
			return "redirect:/result?error";
		}
		fileService.deleteFile(fileId);
		return "redirect:/result?success";
	}

	@GetMapping("/file/download/{id}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(Authentication authentication, @PathVariable("id") Integer id) {
		User user = (User) authentication.getPrincipal();

		File file = fileService.getFilesByIdAndUser(user.getUserId(), id);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, file.getContenttype())
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(new ByteArrayResource(file.getFiledata()));
	}
}
