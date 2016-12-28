package calculator;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
@Controller
public class HelloWorldController {

	    @RequestMapping("/helloWorld")
	        public String helloWorld(Model model) {
			        model.addAttribute("message", "Hello World!");
			        return "helloWorld";
		    }
}
