package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

@Controller
@RequestMapping("/credential")
public class CredentialController {

	private CredentialService credentialService;

	public CredentialController(CredentialService credentialService) {
		this.credentialService = credentialService;
	}

	@PostMapping
	public String addUpdateCredential(Authentication authentication, Credential credential) {
		User user = (User) authentication.getPrincipal();
		if (credential.getCredentialId() > 0) {
			credentialService.updateCredential(credential);
		} else {
			credentialService.addCredential(credential, user.getUserId());
		}
		return "redirect:/result?success";
	}

	@GetMapping("/delete/{id}")
	public String deleteCredential(@PathVariable("id") Integer credentialId, RedirectAttributes attr) {
		if (credentialId < 0) {
			attr.addFlashAttribute("errorMsg", "Credentials with id: "+credentialId+ " is not available.");
			return "redirect:/result?error";
		}
		credentialService.deleteCredential(credentialId);
		return "redirect:/result?success";
	}

}
