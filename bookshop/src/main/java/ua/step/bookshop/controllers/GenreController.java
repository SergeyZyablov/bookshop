package ua.step.bookshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.step.bookshop.repositories.GenreRepasitory;

@Controller
public class GenreController {
	@Autowired
	private GenreRepasitory genreRepasitory;

	@GetMapping("/genres")
	public String getGenres(Model model) {
		model.addAttribute("genres", genreRepasitory.findAll());
		return "genres";
	}
}
