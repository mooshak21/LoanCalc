package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoanCalculatorController {

	    @RequestMapping(value="/loan", method=RequestMethod.GET)
	        public String loan(
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
				RestTemplate restTemplate = new RestTemplate();
				Map<String, String> params = new HashMap<String, String>();
				params.put("airVal", airVal);
				params.put("lndr", lender);
				params.put("loanAmtVal", loanAmt);
				params.put("st", state);
				params.put("numYrs", numOfYears);

				Loan loan = restTemplate.getForObject("https://ayushiloancalculatorapp.herokuapp.com/calculateloan", Loan.class, params);
				model.addAttribute("message", loan.toString());
			        //model.addAttribute("message", new LoanCalculatorApp().calculateloan(airVal, numOfYears, loanAmt, lender, state).toString());
				
			        return "viewloan";
		    }
	    @RequestMapping(value="/createloan", method=RequestMethod.GET)
		    public String createloan(Model model){
			    	model.addAttribute("message","Create Loan");

				return "createloan";
		    }
	    @RequestMapping(value="/home")
	    	   public String home(){
			   return "index";
		   }
}

