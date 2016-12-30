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

	    @RequestMapping(value="/getloan", method=RequestMethod.GET)
	        public String getloan(
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
				RestTemplate restTemplate = new RestTemplate();
			String loan = restTemplate.getForObject("https://ayushiloancalculatorapp.herokuapp.com/calculateloan?airVal=" + airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, String.class);
				GsonBuilder gsonb = new GsonBuilder();
				Gson gson = gsonb.create();
				Loan loanObject = gson.fromJson(loan, Loan.class);
				ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
					SessionFactory sessionFactory = (SessionFactory)appCtx.getBean("sessionFactory");
					HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
					hibernateTemplate.saveOrUpdate(loanObject);
				model.addAttribute("loan", loanObject);
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

