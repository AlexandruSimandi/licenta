package ro.asimandi.simsec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
public class hello{
 
	@RequestMapping("/")
	public String helloWorld(@RequestParam(required = false) String code) {
		if(code != null){
			System.out.println(code);	
		}
		return "welcome";
	}
}