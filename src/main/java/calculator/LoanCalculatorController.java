package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoanCalculatorController {

	    @RequestMapping(value="/loan", method=RequestMethod.GET)
		    
	        public String loan(Model model) {
			        model.addAttribute("message", new Loan().toString());
			        return "helloWorld";
		    }
}

