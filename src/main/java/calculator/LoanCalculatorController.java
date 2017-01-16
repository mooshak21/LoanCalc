package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataAccessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoanCalculatorController{
	    @RequestMapping(value="/loan", method=RequestMethod.POST)
	        public String loan(
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, Model model) {
				boolean allVal = false;
				if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
					allVal = true;
				}
				if(allVal){
					RestTemplate restTemplate = new RestTemplate();
Loan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/calculateloan?airVal=" + airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears, Loan.class);
					/*GsonBuilder gsonb = new GsonBuilder();
					Gson gson = gsonb.create();
					Loan loanObject = gson.fromJson(loan, Loan.class);*/
					if(loanObject != null){
						ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
						SessionFactory sessionFactory = (SessionFactory)appCtx.getBean("sessionFactory");
						HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
						try{
							hibernateTemplate.saveOrUpdate(loanObject);
						}catch(DataAccessException dae){
							dae.printStackTrace();
							model.addAttribute("message", "Create Loan Failed!");
						        return "createloan";
						}
						LoanApp loanApp = new LoanApp(loanObject);
						loanObject.setLoanApp(loanApp);
						model.addAttribute("message", "Create Loan");
						model.addAttribute("loan", loanObject);
					}else{
						model.addAttribute("message", "Create Loan Failed!");
					}
				}else{
						model.addAttribute("message", "Create Loan : Required Parameters not entered!");
				}
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
		@RequestParam("numOfYears") String numOfYears, 
		@RequestParam("amortizeOn") String amortizeOn, Model model) {
				boolean allVal = false;
				if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("") && amortizeOn != null && !amortizeOn.equals("")){
					allVal = true;
				}
				if(allVal){
					RestTemplate restTemplate = new RestTemplate();
AmortizedLoan loanObject = restTemplate.getForObject("https://ayushiloancalculatorappws.herokuapp.com/amortizeloan?airVal=" + airVal + "&lender=" + lender + "&loanAmt=" + loanAmt + "&state=" + state + "&numOfYears=" + numOfYears + "&amortizeOn=" + amortizeOn, AmortizedLoan.class);
					if(loanObject != null){
						LoanApp loanApp = new LoanApp(loanObject);
						loanObject.setLoanApp(loanApp);
						model.addAttribute("amortizeloan", loanObject);
					    	model.addAttribute("message","Amortize Loan");
					}else{
					    	model.addAttribute("message","Amortize Loan Failed!");
					}				
				}else{
					model.addAttribute("message", "Amortize Loan : Required Parameters not entered!");
				}
				model.addAttribute("amortizeOn", amortizeOn);			
				return "amortizeloan";
		    }
	    @RequestMapping(value="/")
	    	   public String home(){
			   return "index";
		   }
	    @RequestMapping(value="/loanamortizeask")
	    	   public String loanamortizeask(Model model){
			   model.addAttribute("message", "Amortize Loan");
			   java.util.Calendar calToday = java.util.Calendar.getInstance();
			   String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);	
			   model.addAttribute("amortizeOn", calTodayStr);		
			   return "amortizeloan";
		   }
	    @RequestMapping(value="/searchloan", method=RequestMethod.POST)
		    public String searchloan(	
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, 
		@RequestParam("amortizeOn") String amortizeOn, 
		@RequestParam("payoffOn") String payoffOn, Model model, 
		        HttpServletRequest request, 
			        HttpServletResponse response) {
				ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
				SessionFactory sessionFactory = (SessionFactory)appCtx.getBean("sessionFactory");
				HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
				AmortizedLoan loanObject = new AmortizedLoan();
				int total = 0;
				StringBuffer querySB = new StringBuffer();
				java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
				Object[] queryVals = null;
				boolean firstVal = false;
				if(loanAmt != null && !loanAmt.equals("")){
					querySB.append("ln.amount=?");
					firstVal = true;
					queryValList.add(Double.valueOf(loanAmt));	
					loanObject.setAmount(Double.valueOf(loanAmt));
				}
				if(airVal != null && !airVal.equals("")){
					if(firstVal)
						querySB.append(" and ln.APR=?");
					else{
						querySB.append(" ln.APR=?");
						firstVal = true;
					}
					queryValList.add(Double.valueOf(airVal));
					loanObject.setAPR(Double.valueOf(airVal));
				}
				if(lender != null && !lender.equals("")){
					if(firstVal)
						querySB.append(" and ln.lender=?");
					else{
						querySB.append(" ln.lender=?");
						firstVal = true;	
					}
						
					queryValList.add(lender);	
					loanObject.setLender(lender);
				}
				if(state != null && !state.equals("")){
					if(firstVal)
						querySB.append(" and ln.state=?");
					else{
						querySB.append(" ln.state=?");
						firstVal = true;
					}
					queryValList.add(state);		
					loanObject.setState(state);					
				}
				if(numOfYears != null && !numOfYears.equals("")){
					if(firstVal)
						querySB.append(" and ln.numberOfYears=?");
					else{
						querySB.append(" ln.numberOfYears=?");
						firstVal = true;
					}
					queryValList.add(Integer.valueOf(numOfYears));	
					loanObject.setNumberOfYears(Integer.valueOf(numOfYears));
				}
				if(firstVal){
					queryVals = new Object[queryValList.size()];
					queryVals = queryValList.toArray(queryVals);
					java.util.List loans = null;
					try{
						loans = hibernateTemplate.find("select ln from Loan ln where " + querySB.toString(), queryVals);
					}catch(DataAccessException dae){
						dae.printStackTrace();
					    	model.addAttribute("message","Search Loan Failed!");
					}
					if(loans != null & loans.size() > 0){
						total = loans.size();
						Loan searchloan = (Loan)loans.get(0);
						if(searchloan != null){
								AmortizedLoan amortizeLoan = new AmortizedLoan(amortizeOn, searchloan.getMonthly(), searchloan.getAmount(), searchloan.getTotal(), searchloan.getLender(), searchloan.getState(), searchloan.getInterestRate(), searchloan.getAPR(), searchloan.getNumberOfYears(), 0);
								LoanApp loanApp = new LoanApp(amortizeLoan);
								amortizeLoan.setLoanApp(loanApp);
								Double payoffAmt = amortizeLoan.getPayoffAmount(searchloan.getAmount(), payoffOn);
								model.addAttribute("payoffAmount", payoffAmt);
								loanObject = amortizeLoan;
							}
					}else {
						loanObject = null;
					}
					model.addAttribute("message","Search Loan: " + ((loans != null) ? loans.size() : 0) + " Loans Found!");
					request.getSession().setAttribute("loans", loans);
				}else{
					model.addAttribute("message","Search Loan: " + " Loan Parameters Not Selected!");
				}
					
			   	model.addAttribute("payoffOn", payoffOn);		
				model.addAttribute("amortizeloan", loanObject);
				request.getSession().setAttribute("amortizeloan", loanObject);			
				model.addAttribute("amortizeOn", amortizeOn);
				return "searchloan";
		    }
		@RequestMapping(value="/loansearchask")
	    	   public String loansearchask(Model model){
			   model.addAttribute("message", "Search Loan");
			   java.util.Calendar calToday = java.util.Calendar.getInstance();
			   String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" 			+ calToday.get(java.util.Calendar.YEAR);	
			   model.addAttribute("amortizeOn", calTodayStr);		
			   model.addAttribute("payoffOn", calTodayStr);
			   return "searchloan";
		   }
		@RequestMapping(value="/loanpayoffask")
	    	   public String loanpayoffask(Model model){
			   model.addAttribute("message", "Payoff Loan");
			   java.util.Calendar calToday = java.util.Calendar.getInstance();
			   String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" 			+ calToday.get(java.util.Calendar.YEAR);	
			   model.addAttribute("payoffOn", calTodayStr);		
			   model.addAttribute("amortizeOn", calTodayStr);		

			   return "searchloan";
		   }
		@RequestMapping(value="/viewloanentries/{pageid}")	
		   public String viewloanentries(@PathVariable int pageid,Model model, 
				           HttpServletRequest request, 
					           HttpServletResponse response){
			int total = 12;
			if(pageid == 1){}
			else{
			  pageid=(pageid-1)*total+1;
			}
			AmortizedLoan al = (AmortizedLoan)request.getSession().getAttribute("amortizeloan");
			ArrayList<LoanEntry> loanEntries = new ArrayList<LoanEntry>(12);
			HashMap<Integer, LoanEntry> entries = al.getEntries();
			for(int idx = pageid; idx < pageid+total; idx++){
				LoanEntry entry = entries.get(idx);						
				loanEntries.add(entry);
			}
		   	al.setLoanEntries(loanEntries);
		   	java.util.Calendar calToday = java.util.Calendar.getInstance();
			String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);	
			model.addAttribute("payoffOn", calTodayStr);		
			model.addAttribute("amortizeOn", calTodayStr);		
			model.addAttribute("amortizeloan", al);
			return "viewloan";
		   }		
		@RequestMapping(value="/viewloan/{pageid}")	
		   public String viewloan(@PathVariable int pageid,Model model, 
				           HttpServletRequest request, 
					           HttpServletResponse response){
			int total = 1;
			if(pageid == 1){}
			else{
			  pageid=(pageid-1)*total+1;
			}
			AmortizedLoan al = (AmortizedLoan)((List)request.getSession().getAttribute("loans")).get(pageid);
		   	java.util.Calendar calToday = java.util.Calendar.getInstance();
			String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);	
			model.addAttribute("payoffOn", calTodayStr);		
			model.addAttribute("amortizeOn", calTodayStr);		
			model.addAttribute("amortizeloan", al);
			return "viewloan";
		   }
	    @RequestMapping(value="/loanviewask")
	    	   public String loanviewask(Model model){
			   model.addAttribute("message", "View Loans");
			   return "viewloans";
		   }

}
