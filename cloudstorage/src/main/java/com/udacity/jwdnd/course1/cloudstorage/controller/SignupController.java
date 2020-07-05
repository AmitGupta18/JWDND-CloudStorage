package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	private final UserService userService;

	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String signup() {
		return "signup";
	}

	@PostMapping
	public String createUser(@ModelAttribute User user, Model model) {
		String signupError = null;
		if (!userService.isUserNameAvailable(user.getUsername())) {
			signupError = "Username already exist!";
		}

		if (signupError == null) {
			int rowCount = userService.createUser(user);
			if (rowCount < 0) {
				signupError = "There was an error signing you up. Please try again!";
			}
		}

		if (signupError == null) {
			model.addAttribute("signupSuccess", true);
		} else {
			model.addAttribute("signupError", signupError);
		}

		return "signup";
	}
}
