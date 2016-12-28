package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoanCalculatorController {

	    @RequestMapping(value="/helloWorld", method=RequestMethod.GET)
	        public String helloWorld(Model model) {
			        model.addAttribute("message", "Hello World!");
			        return "helloWorld";
		    }
}

