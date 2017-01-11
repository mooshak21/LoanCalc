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
import com.google.gson.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;

@Controller
public class LoanCalculatorController{

	    @RequestMapping(value="/loan", method=RequestMethod.POST)
	        public String loan(
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
			RestTemplate restTemplate = new RestTemplate();
Loan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/calculateloan?airVal=" + airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, Loan.class);
				/*GsonBuilder gsonb = new GsonBuilder();
				Gson gson = gsonb.create();
				Loan loanObject = gson.fromJson(loan, Loan.class);*/

		/*		ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
					SessionFactory sessionFactory = (SessionFactory)appCtx.getBean("sessionFactory");
					HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
					hibernateTemplate.saveOrUpdate(loanObject);*/
				LoanApp loanApp = new LoanApp(loanObject);
				loanObject.setLoanApp(loanApp);
				model.addAttribute("loan", loanObject);
			        return "createloan";
		    
			}
	    @RequestMapping(value="/createloan", method=RequestMethod.GET)
		    public String createloan(Model model){
			    model.addAttribute("message", "Create Loan");
			    return "createloan";
			}
	    @RequestMapping(value="/amortizeloan", method=RequestMethod.GET)
		    public String amortizeloan(	
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
			    	model.addAttribute("message","Amortize Loan");
		RestTemplate restTemplate = new RestTemplate();
AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, AmortizedLoan.class);
				LoanApp loanApp = new LoanApp(loanObject);
				loanObject.setLoanApp(loanApp);
				model.addAttribute("amortizeloan", loanObject);			
				return "amortizeloan";
		    }
	    @RequestMapping(value="/")
	    	   public String home(){
			   return "index";
		   }
	    @RequestMapping(value="/loanamortizeask")
	    	   public String loanamortizeask(Model model){
			    model.addAttribute("message", "Amortize Loan");
			   return "amortizeloan";
		   }
}
