package io.github.grooters.seatOccupied.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Weber {

	@GetMapping("/requestSeat")
	public String requestSeat() {
		return "request_seat";
	}

	@GetMapping("/atticSeat")
	public String login() {
		return "atticSeat";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/codes")
	public String codes() {
		return "codes";
	}

}
