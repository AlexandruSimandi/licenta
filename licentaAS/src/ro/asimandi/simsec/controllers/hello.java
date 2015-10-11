package ro.asimandi.simsec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
public class hello{
 
	@RequestMapping("/hello")
	public String helloWorld(@RequestParam String code) {
		System.out.println(code);
		return "hello";
	}
}