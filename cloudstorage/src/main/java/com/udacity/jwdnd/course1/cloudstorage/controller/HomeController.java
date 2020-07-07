package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
@RequestMapping("/home")
public class HomeController {

	private FileService fileService;
	private NoteService noteService;
	private CredentialService credentialService;

	public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
		this.fileService = fileService;
		this.noteService = noteService;
		this.credentialService = credentialService;
	}

	@GetMapping
	public String home(Authentication authentication, Model model) {
		User user = (User) authentication.getPrincipal();
		List<Note> notes = noteService.getNotes(user.getUserId());
		List<Credential> credentials = credentialService.getCredentials(user.getUserId());
		List<File> files = fileService.getFiles(user.getUserId());

		model.addAttribute("files", files);
		model.addAttribute("notes", notes);
		model.addAttribute("credentials", credentials);
		return "home";
	}

}
