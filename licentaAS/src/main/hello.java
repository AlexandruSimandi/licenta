package main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class hello{
 
	@RequestMapping("/hello")
	public String helloWorld() {
		return "hello";
	}
}