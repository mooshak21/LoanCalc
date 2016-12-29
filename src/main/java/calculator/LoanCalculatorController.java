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
	        public String loan(
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
			        model.addAttribute("message", new LoanCalculatorApp().loan(airVal, loanAmt, numOfYears, lender, state).toString());
			        return "loan";
		    }
}

