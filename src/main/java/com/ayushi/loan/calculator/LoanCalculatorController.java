package com.ayushi.loan.calculator;

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
import com.ayushi.loan.*;
import com.ayushi.loan.exception.EmailServiceException;
import com.ayushi.loan.service.LoanService;
import com.ayushi.loan.service.PreferenceService;
import com.ayushi.loan.service.LoanWebService;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.exception.PreferenceProcessException;
import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;
import com.ayushi.loan.preferences.LocationPreference;
import com.ayushi.loan.preferences.WebServicePreference;
import com.ayushi.loan.preferences.RiskTolerancePreference;
import com.ayushi.loan.preferences.TimeHorizonPreference;
import com.ayushi.loan.service.LoanEmailGeneratorService;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hpsf.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;


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
				Loan loanQryObject = new Loan();
				Loan loanObject = null;
				if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
					allVal = true;
					loanQryObject.setAmount(Double.valueOf(loanAmt));
					loanQryObject.setLender(lender);
					loanQryObject.setState(state);
					loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
					loanQryObject.setAPR(Double.valueOf(airVal));
				}
				if(allVal){
					ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
					LoanWebService loanWebService = (LoanWebService)appCtx.getBean("loanWebService");
					try{
						loanObject = loanWebService.calculateLoan(loanQryObject);
					}catch(LoanAccessException lae){
						lae.printStackTrace();
						model.addAttribute("message", "Calculate Loan Failed!");
					        return "createloan";
					}
					/*GsonBuilder gsonb = new GsonBuilder();
					Gson gson = gsonb.create();
					Loan loanObject = gson.fromJson(loan, Loan.class);*/
					if(loanObject != null){
						LoanService loanService = (LoanService)appCtx.getBean("loanService");
						try{
							loanService.createLoan(loanObject);
						}catch(LoanAccessException lae){
							lae.printStackTrace();
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
				Loan loanQryObject = new Loan();
				if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("") && amortizeOn != null && !amortizeOn.equals("")){
					allVal = true;
					loanQryObject.setAmount(Double.valueOf(loanAmt));
					loanQryObject.setLender(lender);
					loanQryObject.setState(state);
					loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
					loanQryObject.setAPR(Double.valueOf(airVal));
				}
				if(allVal){
					ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
					LoanWebService loanWebService = (LoanWebService)appCtx.getBean("loanWebService");
					AmortizedLoan loanObject = null;
					try{
						loanObject = loanWebService.amortizeLoan(loanQryObject, amortizeOn);
					}catch(LoanAccessException lae){
						lae.printStackTrace();
						model.addAttribute("message", "Amortize Loan Failed!");
					        return "amortizeloan";
					}

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
				AmortizedLoan loanObject = new AmortizedLoan();
				int total = 0;
				StringBuffer querySB = new StringBuffer();
				java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
				Object[] queryVals = null;
				boolean firstVal = false;
                                Double payoffAmt=null;
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
					java.util.List<Serializable> loans = null;
						LoanService loanService = (LoanService)appCtx.getBean("loanService");
						try{
							loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
						}catch(LoanAccessException lae){
							lae.printStackTrace();
					    	model.addAttribute("message","Search Loan Failed!");
					}
					if(loans != null & loans.size() > 0){
						total = loans.size();
						Loan searchloan = (Loan)loans.get(0);
						if(searchloan != null){
								AmortizedLoan amortizeLoan = new AmortizedLoan(amortizeOn, searchloan.getMonthly(), searchloan.getAmount(), searchloan.getTotal(), searchloan.getLender(), searchloan.getState(), searchloan.getInterestRate(), searchloan.getAPR(), searchloan.getNumberOfYears(), 0);
								LoanApp loanApp = new LoanApp(amortizeLoan);
								amortizeLoan.setLoanApp(loanApp);
								payoffAmt = amortizeLoan.getPayoffAmount(searchloan.getAmount(), payoffOn);
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
                                
                                // hard coded email
//                                LoanEmailGeneratorService emailService = (LoanEmailGeneratorService)appCtx.getBean("emailService");
//                                try {
//                                    Properties prop = new Properties();
//                                    prop.load(LoanCalculatorController.class.getClassLoader().getResourceAsStream("spring/email.properties"));
//                                    String message = emailService.buildMessage(loanObject, payoffAmt, payoffOn);
//                                    String subject = prop.getProperty("email.subject") + loanObject.getLoanId();
//                                    
//                                    emailService.sendMail("jain_gagan@yahoo.com", subject, message);
//                                   //   emailService.sendMail("gdosoftware@gmail.com", subject, message);
//                                } catch(IOException ioEx){}
//                                  catch(EmailServiceException emEx){};  
               
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
		   public String viewloan(@PathVariable int pageid, Model model, 
				           HttpServletRequest request, 
					           HttpServletResponse response){
			int total = 1;
			if(pageid == 1){}
			else{
			  pageid=(pageid-1)*total+1;
			}
			List loans = (List)request.getSession().getAttribute("loans");
			AmortizedLoan al = null;
			if(loans != null){
				if(loans.get(pageid-1) instanceof AmortizedLoan)
					al = (AmortizedLoan)loans.get(pageid-1);
			}

			java.util.Calendar calToday = java.util.Calendar.getInstance();
			String calTodayStr = (calToday.get(java.util.Calendar.MONTH) +1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);	
			model.addAttribute("payoffOn", calTodayStr);		
			model.addAttribute("amortizeOn", calTodayStr);		
			if(al != null)
				model.addAttribute("amortizeloan", al);
			return "viewloan";
		   }
	    @RequestMapping(value="/loanviewask")
	    	   public String loanviewask(Model model){
			   model.addAttribute("message", "View Loans");
			   return "viewloans";
		   }
	    @RequestMapping(value="/viewloanexcel/{loanid}")
		   public String loanviewexcel(@PathVariable long loanid, Model model, HttpServletRequest request, HttpServletResponse response){
			model.addAttribute("message", "View Loan in EXCEL");
			List<Loan> loans = (List<Loan>)request.getSession().getAttribute("loans");
			if(loans != null && loans.size() > 0){
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment; filename=loan.xls");
				for(Loan loan : loans){
					if(loan.getLoanId() == loanid){
						try{
							response.getWriter().write(loan.toString());
						}catch(java.io.IOException ioe){ ioe.printStackTrace(); }
						model.addAttribute("amortizeloan", loan);
						break;
					}
				}
			}
			return "viewloan";
		   }
	    @RequestMapping(value="/loanpreferenceviewask")
	    	   public String loanpreferenceviewask(Model model){
			   model.addAttribute("message", "Edit Preferences");
			   return "viewpreferences";
		   }
	    @RequestMapping(value="/vieweditpreferences", method=RequestMethod.GET)
		    public String vieweditpreferences(	
		@RequestParam("airVal") String airVal,
		@RequestParam("lender") String lender,
		@RequestParam("loanAmt") String loanAmt,
		@RequestParam("state") String state,
		@RequestParam("numOfYears") String numOfYears, 
		@RequestParam("locationPreference") String locationPreference,
		@RequestParam("webServicePreference") String webServicePreference, 
		@RequestParam("riskTolerancePreference") String riskTolerancePreference, 
		@RequestParam("timeHorizonPreference") String timeHorizonPreference,Model model) {
				boolean allVal = false;
				Loan loanQryObject = new Loan();
				if(loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
				   && lender != null && !lender.equals("") && state != null && !state.equals("")
				   && numOfYears != null && !numOfYears.equals("")){
					allVal = true;
					loanQryObject.setAmount(Double.valueOf(loanAmt));
					loanQryObject.setLender(lender);
					loanQryObject.setState(state);
					loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
					loanQryObject.setAPR(Double.valueOf(airVal));
				}
				if(allVal){
					ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
					PreferenceService prefService = (PreferenceService)appCtx.getBean("preferenceService");
					List<Preference> prefList = new ArrayList<Preference>();
					if(locationPreference != null && !locationPreference.equals("")){
						LocationPreference locPref = new LocationPreference();
						locPref.setId(1);
						locPref.setName("State");
						locPref.setValue(locationPreference);
						locPref.setFlag(true);
						locPref.setActive("Y");
						prefList.add(locPref);
					}
					if(webServicePreference != null && !webServicePreference.equals("")){
						WebServicePreference wsPref = new WebServicePreference();
						wsPref.setId(2);
						wsPref.setName("Web Service");
						wsPref.setValue(webServicePreference);
						wsPref.setFlag(true);
						wsPref.setActive("Y");
						prefList.add(wsPref);
					}
					if(riskTolerancePreference != null && !riskTolerancePreference.equals("")){
						RiskTolerancePreference rtPref = new RiskTolerancePreference();
						rtPref.setId(3);
						rtPref.setName("Interest Rate");
						rtPref.setValue(riskTolerancePreference);
						rtPref.setFlag(true);
						rtPref.setActive("Y");
						prefList.add(rtPref);
					}
					if(timeHorizonPreference != null && !timeHorizonPreference.equals("")){
						TimeHorizonPreference thPref = new TimeHorizonPreference();
						thPref.setId(4);
						thPref.setName("Time Period");
						thPref.setValue(timeHorizonPreference);
						thPref.setFlag(true);
						thPref.setActive("Y");
						prefList.add(thPref);
					}
					List<Integer> preferenceIds = null;
					Preferences prefs = new Preferences();
					prefs.setPreferences(prefList);
					StringBuffer sbPref = new StringBuffer();
					try{
						preferenceIds = prefService.processPreferences(prefs, 
											    pref -> pref.getFlag() && pref.getActive().equals("Y"));
						if(preferenceIds != null && preferenceIds.size() > 0){
							prefService.addPreferences(loanQryObject, preferenceIds);	
							for(Integer prefId : preferenceIds)
								sbPref.append (prefId);
						    	model.addAttribute("message","Preference Service Successful! " + sbPref.toString());
						}else{
						    	model.addAttribute("message","Preference Service Failed!");
						}				
					}catch(PreferenceAccessException | PreferenceProcessException pae){
						pae.printStackTrace();
						model.addAttribute("message", "Preference Service Failed!");
					        return "viewpreferences";
					}
				}else{
					model.addAttribute("message", "Preference Service : Required Parameters not entered!");
				}
				return "viewpreferences";
		    }

    
                    
}
