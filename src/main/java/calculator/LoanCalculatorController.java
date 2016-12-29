package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoanCalculatorController {

	    @RequestMapping(value="/loan", method=RequestMethod.GET),
		@RequestParam(name="airVal") String airVal,
		@RequestParam(name="lender") String lender,
		@RequestParam(name="loanAmt") String loanAmt,
		@RequestParam(name="state") String state,
		@RequestParam(name="numOfYears") String numOfYears;
	        public String loan(Model model) {
			        model.addAttribute("message", new LoanCalculatorApp().loan(airVal, loanAmt, numOfYears, lender, state).toString());
			        return "loan";
		    }
}

