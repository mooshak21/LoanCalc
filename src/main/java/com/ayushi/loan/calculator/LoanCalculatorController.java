package com.ayushi.loan.calculator;

import com.ayushi.loan.service.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayushi.loan.*;
import com.ayushi.loan.exception.EmailServiceException;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.exception.PreferenceProcessException;
import com.ayushi.loan.preferences.EmailReminderPreference;
import com.ayushi.loan.preferences.LoanIdPreference;

import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;
import com.ayushi.loan.preferences.LocationPreference;
import com.ayushi.loan.preferences.ReminderFrequencyPreference;
import com.ayushi.loan.preferences.WebServicePreference;
import com.ayushi.loan.preferences.RiskTolerancePreference;
import com.ayushi.loan.preferences.TimeHorizonPreference;
import com.ayushi.loan.preferences.AmountPreference;
import com.ayushi.loan.preferences.LenderPreference;
import com.ayushi.loan.preferences.AirPreference;
import com.ayushi.loan.preferences.YearsPreference;
import com.ayushi.loan.preferences.StatePreference;
import com.ayushi.loan.preferences.PasswordPreference;

import javax.servlet.http.Cookie;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@SessionAttributes({"loan", "amortizeloan", "payoffOn", "payoffAmt", "amortizeOn", "userEmail", "loans"})
public class LoanCalculatorController implements ServletContextAware {

    private ServletContext context;

    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }

    private static final Logger logger = Logger.getLogger(LoanCalculatorController.class);


    @RequestMapping(value = "/")
    public String home(@CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
                       Model model, HttpServletRequest request) {
	if(emailCookie != null && !emailCookie.equals("")){
	        model.addAttribute("message", "Login Form");
		model.addAttribute("userEmail", emailCookie);
        	request.getCookies();

        	return "login";
	}else{
		model.addAttribute("message", "Register with preferences");
        	return "viewpreferences";
	}
    }


    //--------------------------------------------------------------------------------------------


    @RequestMapping(value = "/createloan", method = RequestMethod.GET)
    public String createloan(Model model) {
        model.addAttribute("message", "Create Loan");
        return "createloan";
    }

    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    public String loan() {
        return "createloan";
    }

    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    public String loan(
            @RequestParam("airVal") String airVal,
            @RequestParam("lender") String lender,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("state") String state,
            @RequestParam("loanType") String loanType,
            @RequestParam("numOfYears") String numOfYears, Model model) {
        boolean allVal = false;
        Loan loanQryObject = new Loan();
        Loan loanObject = null;
        if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
                && lender != null && !lender.equals("") && state != null && !state.equals("")
                && numOfYears != null && !numOfYears.equals("") && loanType != null && !loanType.equals("")) {
            allVal = true;
            loanQryObject.setAmount(Double.valueOf(loanAmt));
            loanQryObject.setLender(lender);
            loanQryObject.setState(state);
            loanQryObject.setLoanType(loanType);
            loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
            loanQryObject.setAPR(Double.valueOf(airVal));
        }
        if (allVal) {
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            LoanWebService loanWebService = (LoanWebService) appCtx.getBean("loanWebService");
            try {
                loanObject = loanWebService.calculateLoan(loanQryObject);
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
                model.addAttribute("message", "Calculate Loan Failed!");
                return "createloan";
            }
                    /*GsonBuilder gsonb = new GsonBuilder();
					Gson gson = gsonb.create();
					Loan loanObject = gson.fromJson(loan, Loan.class);*/
            if (loanObject != null) {
                LoanService loanService = (LoanService) appCtx.getBean("loanService");
                try {
                    loanObject.setLoanType(loanType);
                    loanService.createLoan(loanObject);
                } catch (LoanAccessException lae) {
                    lae.printStackTrace();
                    model.addAttribute("message", "Create Loan Failed!");
                    return "createloan";
                }
                LoanApp loanApp = new LoanApp(loanObject);
                loanObject.setLoanApp(loanApp);
                model.addAttribute("message", "Create Loan");
                model.addAttribute("loan", loanObject);
            } else {
                model.addAttribute("message", "Create Loan Failed!");
            }
        } else {
            model.addAttribute("message", "Create Loan : Required Parameters not entered!");
        }
        return "createloan";
    }

    //--------------------------------------------------------------------------------------------


    @RequestMapping(value = "/loanamortizeask")
    public String loanamortizeask(Model model) {
        model.addAttribute("message", "Amortize Loan");
        java.util.Calendar calToday = java.util.Calendar.getInstance();
        String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
        model.addAttribute("amortizeOn", calTodayStr);

        return "amortizeloan";
    }


    @RequestMapping(value = "/amortizeloan", method = RequestMethod.GET)
    public String amortizeloan(
            @RequestParam("airVal") String airVal,
            @RequestParam("lender") String lender,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("state") String state,
            @RequestParam("numOfYears") String numOfYears,
            @RequestParam("amortizeOn") String amortizeOn, Model model) {
        boolean allVal = false;
        Loan loanQryObject = new Loan();
        if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
                && lender != null && !lender.equals("") && state != null && !state.equals("")
                && numOfYears != null && !numOfYears.equals("") && amortizeOn != null && !amortizeOn.equals("")) {
            allVal = true;
            loanQryObject.setAmount(Double.valueOf(loanAmt));
            loanQryObject.setLender(lender);
            loanQryObject.setState(state);
            loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
            loanQryObject.setAPR(Double.valueOf(airVal));
        }
        if (allVal) {
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            LoanWebService loanWebService = (LoanWebService) appCtx.getBean("loanWebService");
            AmortizedLoan loanObject = null;
            try {
                loanObject = loanWebService.amortizeLoan(loanQryObject, amortizeOn);
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
                model.addAttribute("message", "Amortize Loan Failed!");
                return "amortizeloan";
            }

            if (loanObject != null) {
                LoanApp loanApp = new LoanApp(loanObject);
                loanObject.setLoanApp(loanApp);
                model.addAttribute("amortizeloan", loanObject);
                model.addAttribute("message", "Amortize Loan");
            } else {
                model.addAttribute("message", "Amortize Loan Failed!");
            }
        } else {
            model.addAttribute("message", "Amortize Loan : Required Parameters not entered!");
        }
        model.addAttribute("amortizeOn", amortizeOn);
        return "amortizeloan";
    }


    @RequestMapping(value = "/searchloan", method = RequestMethod.GET)
    public String searchLoan(Model model, RedirectAttributes redirectAttributes) {
        return "searchloan";
    }

//-------------------------------------------------------------------------------------------------------------------------------------	         

    @RequestMapping(value = "/loansearchask")
    public String loansearchask(Model model) {
        model.addAttribute("message", "Search Loan");
        java.util.Calendar calToday = java.util.Calendar.getInstance();
        String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
        model.addAttribute("amortizeOn", calTodayStr);
        model.addAttribute("payoffOn", calTodayStr);

        return "searchloan";
    }

    @RequestMapping(value = "/loanpayoffask")
    public String loanpayoffask(Model model) {
        model.addAttribute("message", "Payoff Loan");
        java.util.Calendar calToday = java.util.Calendar.getInstance();
        String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
        model.addAttribute("payoffOn", calTodayStr);
        model.addAttribute("amortizeOn", calTodayStr);

        return "searchloan";
    }

    @RequestMapping(value = "/searchloan", method = RequestMethod.POST)
    public String searchloan(
            @RequestParam("airVal") String airVal,
            @RequestParam("lender") String lender,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("state") String state,
            @RequestParam("numOfYears") String numOfYears,
            @RequestParam("loanType") String loanType,
            @RequestParam("amortizeOn") String amortizeOn,
            @RequestParam("payoffOn") String payoffOn,
            @CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
            Model model, HttpServletRequest request) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        AmortizedLoan loanObject = new AmortizedLoan();
        int total = 0;
        StringBuffer querySB = new StringBuffer();
        java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
        Object[] queryVals = null;
        boolean firstVal = false;
        Double payoffAmt = null;

        if (loanAmt != null && !loanAmt.equals("")) {
            querySB.append("ln.amount=?");
            firstVal = true;
            queryValList.add(Double.valueOf(loanAmt));
            loanObject.setAmount(Double.valueOf(loanAmt));
        }

        if (airVal != null && !airVal.equals("")) {
            if (firstVal)
                querySB.append(" and ln.APR=?");
            else {
                querySB.append(" ln.APR=?");
                firstVal = true;
            }
            queryValList.add(Double.valueOf(airVal));
            loanObject.setAPR(Double.valueOf(airVal));
        }
        if (lender != null && !lender.equals("")) {
            if (firstVal)
                querySB.append(" and ln.lender=?");
            else {
                querySB.append(" ln.lender=?");
                firstVal = true;
            }

            queryValList.add(lender);
            loanObject.setLender(lender);
        }
        if (state != null && !state.equals("")) {
            if (firstVal)
                querySB.append(" and ln.state=?");
            else {
                querySB.append(" ln.state=?");
                firstVal = true;
            }
            queryValList.add(state);
            loanObject.setState(state);
        }
        if (numOfYears != null && !numOfYears.equals("")) {
            if (firstVal)
                querySB.append(" and ln.numberOfYears=?");
            else {
                querySB.append(" ln.numberOfYears=?");
                firstVal = true;
            }
            queryValList.add(Integer.valueOf(numOfYears));
            loanObject.setNumberOfYears(Integer.valueOf(numOfYears));
        }
        if (loanType != null && !loanType.equals("")) {
            if (firstVal)
                querySB.append(" and ln.loanType=?");
            else {
                querySB.append(" ln.loanType=?");
                firstVal = true;
            }
            queryValList.add(loanType);
            loanObject.setLoanType(loanType);
        }
        if (firstVal) {
            queryVals = new Object[queryValList.size()];
            queryVals = queryValList.toArray(queryVals);
            java.util.List<Serializable> loans = null;
            LoanService loanService = (LoanService) appCtx.getBean("loanService");
            try {
                loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
                model.addAttribute("message", "Search Loan Failed!");
            }
            if (loans != null && loans.size() > 0) {
                total = loans.size();
                Loan searchloan = (Loan) loans.get(0);
                if (searchloan != null) {
                    AmortizedLoan amortizeLoan = new AmortizedLoan(amortizeOn, searchloan.getMonthly(), searchloan.getAmount(), searchloan.getTotal(), searchloan.getLender(), searchloan.getState(), searchloan.getInterestRate(), searchloan.getAPR(), searchloan.getNumberOfYears(), 0,searchloan.getLoanId(), searchloan.getLoanType());
                    LoanApp loanApp = new LoanApp(amortizeLoan);
                    amortizeLoan.setLoanApp(loanApp);
                    payoffAmt = amortizeLoan.getPayoffAmount(searchloan.getAmount(), payoffOn);
                    model.addAttribute("payoffAmount", payoffAmt);
                    loanObject = amortizeLoan;
                }
            } else {
                loanObject = null;
                model.addAttribute("message", "Search Loan: " + ((loans != null) ? loans.size() : 0) + " Loans Found!");
                return "searchloan";
            }
            model.addAttribute("message", "Search Loan: " + ((loans != null) ? loans.size() : 0) + " Loans Found!");
            //request.getSession().setAttribute("loans", loans);
            model.addAttribute("loans", loans);
           /* if(loans!=null && loans.size()!=0){
                model.addAttribute("loanType", ((Loan) loans.get(0)).getLoanType());
            }else{
                model.addAttribute("loanType", "");
            }*/

            model.addAttribute("amortizeloan", loanObject);
            model.addAttribute("payoffOn", payoffOn);
            model.addAttribute("payoffAmt", payoffAmt);
            model.addAttribute("amortizeOn", amortizeOn);
            model.addAttribute("userEmail", emailCookie);

            return "searchloan";
        } else {
            model.addAttribute("message", "Search Loan: " + " Loan Parameters Not Selected!");
            return "searchloan";
        }
    }


    //--------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/sendmail")
    public String sendMail(@RequestParam(value = "email", defaultValue = "") String email,
                           @RequestParam(value = "dataType") String dataType,
                           @RequestParam(value = "prevMessage") String prevMessage,
                           RedirectAttributes redirectAttributes,
                           Model model, HttpServletResponse response, HttpServletRequest request) {

        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        AmortizedLoan loanObject = (AmortizedLoan) model.asMap().get("amortizeloan");
        Loan loan = (Loan) model.asMap().get("loan");
        Double payoffAmt = (Double) model.asMap().get("payoffAmt");
        String payoffOn = (String) model.asMap().get("payoffOn");
        String userEmail = (String) model.asMap().get("userEmail");
        Long loanId=null;

        if (email != null && !email.equals(userEmail)) {
            updatePreferenceEmailAddress(email, userEmail);
            response.addCookie(new Cookie("userEmail", email));
            model.addAttribute("userEmail", email);
        }
            
        Properties prop = getProperties("spring/email.properties");

        if (email != null && !email.isEmpty()) {

            LoanEmailGeneratorService emailService = (LoanEmailGeneratorService) appCtx.getBean("emailService");
            String message = null;
            String subject = null;

            if (loanObject != null && dataType.equals("amortizedLoan")) {
                message = emailService.buildMessage(loanObject, payoffAmt, payoffOn);
                subject = prop.getProperty("email.subject") + loanObject.getLoanId();
                loanId=loanObject.getLoanId();
            }

            if (loan != null && dataType.equals("Loan")) {
                message = emailService.buildMessage(loan);
                subject = prop.getProperty("email.subject") + loan.getLoanId();
                loanId = loan.getLoanId();
            }

            if (message != null && subject != null) {
                try {
                    emailService.sendMail(email, subject, message);
                    redirectAttributes.addFlashAttribute("emailMsg", prop.getProperty("email.success"));
                    response.addCookie(new Cookie("loanId",loanId.toString()));
		    List<Preference> prefs = getPreferencesByEmailAddress(email);
		    int loanidPrefId = -1;
		    if(prefs != null){
			for(Preference pref: prefs){
				if(pref.getName().equals("LoanId")){
					loanidPrefId = pref.getId();
					break;
				}
			}
			if(loanidPrefId == -1)
				loanidPrefId = prefs.size()+1;
		    }	
                    addPreference(new LoanIdPreference(), loanidPrefId, email, "LoanId", loanId.toString());
                }
                catch (EmailServiceException ex) {
                    logger.error(ex.getMessage());
                    redirectAttributes.addFlashAttribute("emailErr", "we couldn't send you the email. Please try later!");
                } 
                catch (PreferenceAccessException ex) {
                    logger.error(ex.getMessage());
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("emailErr", prop.getProperty("email.error"));
        }
        
        redirectAttributes.addFlashAttribute("message", prevMessage);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }


    //------------------------------------------------------------------------------------------------------------------------------               
    @RequestMapping(value = "/quickview")
    public String quickView(Model model,@CookieValue(value = "loanId", defaultValue = "") String loanId){
        if(loanId != null && !loanId.equals("")){
          List<Serializable> loans = new ArrayList<>();
          ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
          LoanService loanService = (LoanService) appCtx.getBean("loanService");
          java.util.Calendar calToday = java.util.Calendar.getInstance();
          String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
            try {
                loans = loanService.findLoan("select ln from Loan ln where ln.loanId = ?",new Object[]{new Long(loanId)});
                if (loans != null && loans.size() > 0){
                    Loan searchloan = (Loan) loans.get(0);
                    AmortizedLoan amortizeLoan = new AmortizedLoan(calTodayStr, searchloan.getMonthly(), searchloan.getAmount(), searchloan.getTotal(), searchloan.getLender(), searchloan.getState(), searchloan.getInterestRate(), searchloan.getAPR(), searchloan.getNumberOfYears(), 0,searchloan.getLoanId(), searchloan.getLoanType());
                    LoanApp loanApp = new LoanApp(amortizeLoan);
                    amortizeLoan.setLoanApp(loanApp);
                    amortizeLoan.setLoanId(searchloan.getLoanId());
                    model.addAttribute("payoffAmt", amortizeLoan.getPayoffAmount(searchloan.getAmount(), calTodayStr));
                    model.addAttribute("amortizeloan", amortizeLoan);
                    model.addAttribute("loans", loans);
                    model.addAttribute("loanId", loanId);
                    model.addAttribute("message", "Search Loan in Quick View");
                }
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
                model.addAttribute("message", "Loan no longer available!");
            }
          
          
        }
        return "viewloans";
    }
            
    
    
    //-----------------------------------------------------------------
    @RequestMapping(value = "/loanviewask")
    public String loanviewask(@CookieValue(value = "loanId", defaultValue = "") String loanId,Model model) {
        model.addAttribute("message", "View Loans");
        model.addAttribute("loanId", loanId);
        return "viewloans";
    }


    @RequestMapping(value = "/viewloanentries/{pageid}")
    public String viewloanentries(@PathVariable int pageid, Model model,
                                  @CookieValue(value = "loanId", defaultValue = "") String loanId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        int total = 12;
        if (pageid == 1) {
        } else {
            pageid = (pageid - 1) * total + 1;
        }
        AmortizedLoan al = (AmortizedLoan) request.getSession().getAttribute("amortizeloan");
        ArrayList<LoanEntry> loanEntries = new ArrayList<LoanEntry>(12);
        if (al != null) {
            HashMap<Integer, LoanEntry> entries = al.getEntries();
            for (int idx = pageid; idx < pageid + total; idx++) {
                LoanEntry entry = entries.get(idx);
                loanEntries.add(entry);
            }
            al.setLoanEntries(loanEntries);
            model.addAttribute("amortizeloan", al);
        }
        java.util.Calendar calToday = java.util.Calendar.getInstance();
        String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
        model.addAttribute("payoffOn", calTodayStr);
        model.addAttribute("amortizeOn", calTodayStr);
        model.addAttribute("loanId", loanId);
         model.addAttribute("message", "View Loans");
        return "viewloans";
    }


    @RequestMapping(value = "/viewloan/{pageid}")
    public String viewloan(@PathVariable int pageid, Model model,
                           @CookieValue(value = "loanId", defaultValue = "") String loanId,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        int total = 1;
        if (pageid == 1) {
        } else {
            pageid = (pageid - 1) * total + 1;
        }
        java.util.Calendar calToday = java.util.Calendar.getInstance();
        String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/" + calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
        List loans = (List) request.getSession().getAttribute("loans");
        AmortizedLoan al = null;
        String amortizeOn = (String) model.asMap().get("amortizeOn");
        String payoffOn = (String) model.asMap().get("payoffOn");
        if (payoffOn == null || payoffOn.isEmpty()) {
            payoffOn = calTodayStr;
        }
        if (loans != null) {
            Loan loan = (Loan) loans.get(pageid - 1);
            if (amortizeOn != null) {
                al = new AmortizedLoan(amortizeOn, loan.getMonthly(), loan.getAmount(), loan.getTotal(), loan.getLender(), loan.getState(),
                        loan.getInterestRate(), loan.getAPR(), loan.getNumberOfYears(), 0,loan.getLoanId(),loan.getLoanType());
                model.addAttribute("payoffAmt", !payoffOn.isEmpty() ? ((al.getPayoffAmount(loan.getAmount(), payoffOn) != null) ? al.getPayoffAmount(loan.getAmount(), payoffOn) : "-1.0") : "-1.0");
                model.addAttribute("amortizeloan", al);
                model.addAttribute("loanId", loanId);
                al.setLoanId(loan.getLoanId());
            }
        }
        model.addAttribute("message", "View Loans");
        return "viewloan";
    }

    @RequestMapping(value = "/viewloanexcel/{loanid}")
    public String loanviewexcel(@PathVariable long loanid, RedirectAttributes redirectAttributes,
                                Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("message", "View Loan in EXCEL");
        List<Loan> loans = (List<Loan>) request.getSession().getAttribute("loans");
        if (loans != null && loans.size() > 0) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=loan.xls");
            for (Loan loan : loans) {
                if (loan.getLoanId() == loanid) {
                    try {
                        response.getWriter().write(loan.toString());
                    } catch (java.io.IOException ioe) {
                        ioe.printStackTrace();
                    }
                    model.addAttribute("amortizeloan", loan);
                    break;
                }
            }
        }

        return "viewloan";
    }

    //----------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/loanpreferenceviewask")
    public String loanpreferenceviewask(@CookieValue(value = "userEmail", defaultValue = "") String emailCookie, @CookieValue(value = "reminderFrequency", defaultValue = "") String reminderFrequency,Model model) {
        model.addAttribute("message", "Edit Preferences");
        model.addAttribute("reminderFrequency", reminderFrequency);
	List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
	ArrayList<String> prefVal = null, prefAttr = null;

	if(prefs != null){
	    prefVal = new ArrayList<String>(prefs.size());	
	    prefAttr = new ArrayList<String>(prefs.size());
	    int prefIdx = 0;
	    for(Preference pref : prefs){
		prefAttr.add(pref.getName());
		prefVal.add(pref.getValue());
	   }
	  for(prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++)
		model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
	}
        return "viewpreferences";
    }

    @RequestMapping(value = "/vieweditpreferences", method = RequestMethod.GET)
    public String vieweditpreferences(
            @RequestParam("airVal") String airVal,
            @RequestParam("lender") String lender,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("state") String state,
            @RequestParam("numOfYears") String numOfYears,
            @RequestParam("locationPreference") String locationPreference,
            @RequestParam("webServicePreference") String webServicePreference,
            @RequestParam("riskTolerancePreference") String riskTolerancePreference,
            @RequestParam("timeHorizonPreference") String timeHorizonPreference,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("reminderfreq") String reminderFreq,
            @CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
           HttpServletRequest request, HttpServletResponse response, Model model) {
	
	String numberOfYearsPreference = null, amountPreference = null, airPreference = null, lenderPreference = null, statePreference = null, passwordPreference = null;
        boolean allVal = false;
        if (loanAmt != null && !loanAmt.equals(""))
	    amountPreference = loanAmt;
	if(airVal != null && !airVal.equals(""))
	    airPreference = airVal;	
        if(lender != null && !lender.equals(""))
	    lenderPreference = lender;
	if(state != null && !state.equals(""))
	    statePreference = state;
        if(numOfYears != null && !numOfYears.equals("")) 
	    numberOfYearsPreference = numOfYears;
	if(password != null && !password.equals(""))
	    passwordPreference = password;

        if (email != null && !email.equals("")) {
            model.addAttribute("userEmail", email);
            if(!emailCookie.isEmpty() && ! emailCookie.equals(email)){
                updatePreferenceEmailAddress(email, emailCookie);
            }
            response.addCookie(new Cookie("userEmail", email));
        }
        
        if (reminderFreq != null && !reminderFreq.equals("")) {
            model.addAttribute("reminderFrequency", reminderFreq);
            response.addCookie(new Cookie("reminderFrequency", reminderFreq));
        }

            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
            List<Preference> prefList = new ArrayList<Preference>();

            if (locationPreference != null && !locationPreference.equals("")) {
                LocationPreference locPref = new LocationPreference();
                locPref.setId(1);
                locPref.setName("Location");
                locPref.setEmailAddress(email);
                locPref.setValue(locationPreference);
                locPref.setFlag(true);
                locPref.setActive("Y");
                prefList.add(locPref);
            }
            if (webServicePreference != null && !webServicePreference.equals("")) {
                WebServicePreference wsPref = new WebServicePreference();
                wsPref.setId(2);
                wsPref.setEmailAddress(email);
                wsPref.setName("WebService");
                wsPref.setValue(webServicePreference);
                wsPref.setFlag(true);
                wsPref.setActive("Y");
                prefList.add(wsPref);
            }
            if (riskTolerancePreference != null && !riskTolerancePreference.equals("")) {
                RiskTolerancePreference rtPref = new RiskTolerancePreference();
                rtPref.setId(3);
                rtPref.setEmailAddress(email);
                rtPref.setName("RiskTolerance");
                rtPref.setValue(riskTolerancePreference);
                rtPref.setFlag(true);
                rtPref.setActive("Y");
                prefList.add(rtPref);
            }
            if (timeHorizonPreference != null && !timeHorizonPreference.equals("")) {
                TimeHorizonPreference thPref = new TimeHorizonPreference();
                thPref.setId(4);
                thPref.setEmailAddress(email);
                thPref.setName("TimeHorizon");
                thPref.setValue(timeHorizonPreference);
                thPref.setFlag(true);
                thPref.setActive("Y");
                prefList.add(thPref);
            }
            
            if(email != null && !email.isEmpty()){
                EmailReminderPreference erPref = new EmailReminderPreference();
                erPref.setId(5);
                erPref.setEmailAddress(email);
                erPref.setName("EmailAddressReminder");
                erPref.setValue(email);
                erPref.setFlag(true);
                erPref.setActive("Y");
                prefList.add(erPref);
            }
            
            if(reminderFreq != null && !reminderFreq.isEmpty()){
                ReminderFrequencyPreference rfPref = new ReminderFrequencyPreference();
                rfPref.setId(6);
                rfPref.setEmailAddress(email);
                rfPref.setName("reminderFrequency");
                rfPref.setValue(reminderFreq);
                rfPref.setFlag(true);
                rfPref.setActive("Y");
                prefList.add(rfPref);
            }
        
            if (amountPreference != null && !amountPreference.equals("")) {
                AmountPreference lamtPref = new AmountPreference();
                lamtPref.setId(7);
                lamtPref.setName("Amount");
                lamtPref.setEmailAddress(email);
                lamtPref.setValue(amountPreference);
                lamtPref.setFlag(true);
                lamtPref.setActive("Y");
                prefList.add(lamtPref);
            }

            if (airPreference != null && !airPreference.equals("")) {
                AirPreference lairPref = new AirPreference();
                lairPref.setId(8);
                lairPref.setName("AIR");
                lairPref.setEmailAddress(email);
                lairPref.setValue(airPreference);
                lairPref.setFlag(true);
                lairPref.setActive("Y");
                prefList.add(lairPref);
            }

            if (lenderPreference != null && !lenderPreference.equals("")) {
                LenderPreference lenderPref = new LenderPreference();
                lenderPref.setId(9);
                lenderPref.setName("Lender");
                lenderPref.setEmailAddress(email);
                lenderPref.setValue(lenderPreference);
                lenderPref.setFlag(true);
                lenderPref.setActive("Y");
                prefList.add(lenderPref);
            }

	    if (numberOfYearsPreference != null && !numberOfYearsPreference.equals("")){
                YearsPreference lnumPref = new YearsPreference();
                lnumPref.setId(10);
                lnumPref.setName("NumberOfYears");
                lnumPref.setEmailAddress(email);
                lnumPref.setValue(numberOfYearsPreference);
                lnumPref.setFlag(true);
                lnumPref.setActive("Y");
                prefList.add(lnumPref);
	    }

            if (statePreference != null && !statePreference.equals("")) {
                StatePreference lstPref = new StatePreference();
                lstPref.setId(11);
                lstPref.setName("State");
                lstPref.setEmailAddress(email);
                lstPref.setValue(statePreference);
                lstPref.setFlag(true);
                lstPref.setActive("Y");
                prefList.add(lstPref);
            }

            if (passwordPreference != null && !passwordPreference.equals("")) {
                PasswordPreference lpwdPref = new PasswordPreference();
                lpwdPref.setId(12);
                lpwdPref.setName("Password");
                lpwdPref.setEmailAddress(email);
                lpwdPref.setValue(passwordPreference);
                lpwdPref.setFlag(true);
                lpwdPref.setActive("Y");
                prefList.add(lpwdPref);
            }

            List<Preference> preferences = null;
            Preferences prefs = new Preferences();
            prefs.setPreferences(prefList);
            try {
                preferences = prefService.processPreferences(prefs,
                        pref -> pref.getFlag() && pref.getActive().equals("Y"));
                if(preferences != null && preferences.size() > 0){
                    for(Preference p : preferences){
                        prefService.createPreference(p);
                    }
                      model.addAttribute("message", "Preference Service Successful! ");
                }else{
                    model.addAttribute("message", "Preference Service Failed!");
                }
            } catch (PreferenceAccessException | PreferenceProcessException pae) {
                pae.printStackTrace();

                model.addAttribute("message", "Preference Service Failed!");

                return "viewpreferences";
            }

        return "viewpreferences";
    }

    //---------------------------------------------------------------------------------------
    private Properties getProperties(String fileProp) {
        Properties prop = new Properties();
        try {
            prop.load(LoanCalculatorController.class.getClassLoader().getResourceAsStream(fileProp));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return prop;
    }
    
   private void addPreference(Preference pref, Integer id, String email, String name, String value) throws PreferenceAccessException{
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
        pref.setId(id);
        pref.setEmailAddress(email);
        pref.setName(name);
        pref.setValue(value);
        pref.setFlag(true);
        pref.setActive("Y");
        prefService.createPreference(pref);
   }
   
   private void updatePreferenceEmailAddress(String newEmail, String oldEmail) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
        List<Preference> preferences;
        try {
            preferences = prefService.findPreference("select p from Preference p where p.emailAddress = ?", new Object[]{oldEmail});
            if(preferences != null){
                    for(Preference p : preferences){
                        prefService.removePreference(p);
                        p.setEmailAddress(newEmail);
                        if(p instanceof EmailReminderPreference){
                            p.setValue(newEmail);
                        }
                        prefService.createPreference(p);
                     }
            }
        } catch (PreferenceAccessException ex) {
            logger.error(ex.getMessage());
        }
        
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------    
    @RequestMapping(value = "/aggregateloan", method = RequestMethod.POST)
    public String loanAgg(
            @RequestParam("loanId") String loanId,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("lender") String lender,
            @RequestParam("state") String state,
            @RequestParam("numOfYears") String numOfYears,
            @RequestParam("airVal") String airVal,
            @CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
            Model model, HttpServletRequest request) throws ParseException {

        List<Serializable> loans = searchLoanForAggregation(loanId, loanAmt, lender, state, numOfYears, airVal);

        if (loans != null && loans.size() > 0) {
            java.util.List<Serializable> loanRelationship = null;
            LoanAgg loanAgg = null;
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            AggregationSummary aggregationSummary = new AggregationSummary();
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            LoanWebService loanWebService = (LoanWebService) appCtx.getBean("loanWebService");

            List<Loan> aggregatedLoans = new ArrayList<Loan>();

            List<Long> loanIdFromLoanRelationship = new ArrayList<Long>();
            Long loanIdtoCheck=null;
            Loan loanToCheck=null;
            boolean loannotfound;
            loanRelationship = searchLoanRelationship(loans);

            if (loanRelationship != null && loanRelationship.size() > 0) {
                loanAgg = ((LoanRelationship)loanRelationship.get(0)).getLoanAgg();

                for (int counter = 0; counter < loanRelationship.size(); counter++) {
                    loannotfound=true;
                    loanIdtoCheck = ((LoanRelationship)loanRelationship.get(counter)).getLoanId();
                    loanIdFromLoanRelationship.add(loanIdtoCheck);

                    for (int lnctr = 0; lnctr < loans.size(); lnctr++) {
                        loanToCheck = (Loan)loans.get(lnctr);
                        if ( loanIdtoCheck.equals(loanToCheck.getLoanId())) {
                            loannotfound = false;
                            aggregatedLoans.add((Loan)loans.remove(lnctr));
                            break;
                        }
                    }

                    if ( loannotfound ) {
                        List<Serializable> searchedLoan = searchLoanForAggregation(loanIdtoCheck.toString(), null, null, null, null, null);

                        if ( searchedLoan != null) {
                            aggregatedLoans.add((Loan)searchedLoan.get(0));
                        }
                    }
                }

                try{
                    aggregationSummary = loanWebService.aggregationSummary(aggregatedLoans, loanAgg.getStartDate());

                    if ( aggregationSummary != null ) {
                        model.addAttribute("totalAmount",aggregationSummary.getTotalAmount());
                        model.addAttribute("amountPaid", aggregationSummary.getAmountPaid());
                        model.addAttribute("remainingAmount", aggregationSummary.getRemainingAmount());
                        model.addAttribute("remainingPercent", aggregationSummary.getRemainingPercent());
                        model.addAttribute("maximumNumOfYears", aggregationSummary.getMaximumNumOfYear());
                        model.addAttribute("payoff", formatter.format(aggregationSummary.getPayoffDate().getTime()));
                    }
                } catch (LoanAccessException lae) {
                    lae.printStackTrace();
                    model.addAttribute("message", "Calculate Summary Failed!");
                    return "aggregateloan";
                }


            }

            model.addAttribute("loanEntries1", loans);
            model.addAttribute("loanEntries2", aggregatedLoans);

            if (loanAgg != null) {
                model.addAttribute("startDateForSummary", formatter.format(loanAgg.getStartDate().getTime()));
                model.addAttribute("loanAggId", loanAgg.getLoanAggId());
                model.addAttribute("name", loanAgg.getName());
                model.addAttribute("type", loanAgg.getType());
                model.addAttribute("term", loanAgg.getTerm());
                model.addAttribute("startDate", formatter.format(loanAgg.getStartDate().getTime()));
                model.addAttribute("email", loanAgg.getEmail());
                model.addAttribute("NoOfLoansInRelation", aggregatedLoans.size());
            }
            if(loanAgg == null || loanAgg.getEmail() == null){
                model.addAttribute("email", emailCookie);
            }

            model.addAttribute("message", "Loan Aggregation Created!");
        } else {
            model.addAttribute("message", "No Record Found");
            return "aggregateloan";
        }
        return "aggregateloan";
    }

    @RequestMapping(value = "/aggregateloanask")
    public String aggregateloan(Model model) {
        model.addAttribute("message", "Aggregate Loan");
        return "aggregateloan";
    }

    private java.util.List<Serializable> searchLoanRelationship(List<Serializable> loan){
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        StringBuffer querySB = new StringBuffer();
        java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
        Object[] queryVals = null;
        java.util.List<Serializable> loanRelationship = null;
        java.util.List<Serializable> loanRelationshipUsingLoanAgg = null;
        LoanRelationshipService loanRelationshipService = (LoanRelationshipService) appCtx.getBean("loanRelationshipService");
        for (int i=0;i<loan.size();i++) {
            if (i == 0 && (i == loan.size() - 1)) {
                querySB.append("?)");
                queryValList.add(Long.valueOf(((Loan)loan.get(i)).getLoanId()));
                break;
            } else if (i < loan.size() - 1) {
                querySB.append("?,");
                queryValList.add(Long.valueOf(((Loan)loan.get(i)).getLoanId()));
            } else if (i != 0 && (i == loan.size() - 1)) {
                querySB.append("?)");
                queryValList.add(Long.valueOf(((Loan)loan.get(i)).getLoanId()));
            }
        }
        queryVals = new Object[queryValList.size()];
        queryVals = queryValList.toArray(queryVals);
        try {
            loanRelationship = loanRelationshipService.findLoanRelation("select ls from LoanRelationship ls where loanId IN(" + querySB.toString(), queryVals);

            StringBuffer querySBForAgg = new StringBuffer();
            Object[] queryValsForAgg = null;
            java.util.List<Object> queryValListForAgg = new java.util.ArrayList<Object>();
            querySBForAgg.append("la.loanAgg=?");
            if (loanRelationship != null && loanRelationship.size() > 0) {
                queryValListForAgg.add(((LoanRelationship)loanRelationship.get(0)).getLoanAgg());
                queryValsForAgg = new Object[queryValListForAgg.size()];
                queryValsForAgg = queryValListForAgg.toArray(queryValsForAgg);
                loanRelationshipUsingLoanAgg = loanRelationshipService.findLoanRelation("select la from LoanRelationship la where " + querySBForAgg.toString(), queryValsForAgg);
            }

        } catch (LoanAccessException lae) {
            lae.printStackTrace();
        }
        return loanRelationshipUsingLoanAgg;
    }

    private java.util.List<Serializable> searchLoanForAggregation(String loanId, String loanAmt, String lender, String
            state, String numOfYears, String airVal) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        AmortizedLoan loanObject = new AmortizedLoan();
        int total = 0;
        StringBuffer querySB = new StringBuffer();
        java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
        Object[] queryVals = null;
        boolean firstVal = false;
        Double payoffAmt = null;

        if (loanId != null && !loanId.equals("")) {
            querySB.append("ln.loanId=?");
            firstVal = true;
            queryValList.add(Long.valueOf(loanId));
            loanObject.setLoanId(Long.valueOf(loanId));
        }

        if (loanAmt != null && !loanAmt.equals("")) {
            if (firstVal)
                querySB.append(" and ln.amount=?");
            else {
                querySB.append(" ln.amount=?");
                firstVal = true;
            }

            queryValList.add(Double.valueOf(loanAmt));
            loanObject.setAmount(Double.valueOf(loanAmt));
        }
        if (lender != null && !lender.equals("")) {
            if (firstVal)
                querySB.append(" and ln.lender=?");
            else {
                querySB.append(" ln.lender=?");
                firstVal = true;
            }

            queryValList.add(lender);
            loanObject.setLender(lender);
        }
        if (state != null && !state.equals("")) {
            if (firstVal)
                querySB.append(" and ln.state=?");
            else {
                querySB.append(" ln.state=?");
                firstVal = true;
            }
            queryValList.add(state);
            loanObject.setState(state);
        }
        if (numOfYears != null && !numOfYears.equals("")) {
            if (firstVal)
                querySB.append(" and ln.numberOfYears=?");
            else {
                querySB.append(" ln.numberOfYears=?");
                firstVal = true;
            }
            queryValList.add(Integer.valueOf(numOfYears));
            loanObject.setNumberOfYears(Integer.valueOf(numOfYears));
        }

        if (airVal != null && !airVal.equals("")) {
            if (firstVal)
                querySB.append(" and ln.APR=?");
            else {
                querySB.append(" ln.APR=?");
                firstVal = true;
            }
            queryValList.add(Double.valueOf(airVal));
            loanObject.setAPR(Double.valueOf(airVal));
        }
        java.util.List<Serializable> loans = null;
        if (firstVal) {
            queryVals = new Object[queryValList.size()];
            queryVals = queryValList.toArray(queryVals);
            LoanService loanService = (LoanService) appCtx.getBean("loanService");
            try {
                loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
            }
        }
        return loans;
    }


    @RequestMapping(value = "/updateaggregate", method = RequestMethod.POST)
    public String updateLoanAgg(
            @RequestParam("loanAggId") String loanAggId,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("email") String email,
            @RequestParam("startDate") String startDate,
            @RequestParam("term") String term,
            @RequestParam("loanIds") String loanIds,
            @RequestParam("loansId") String loansId,
            @CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
            Model model, HttpServletRequest request) throws ParseException {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        List<Serializable> loanAggDetails = null;
        List<Loan> loans1 = new ArrayList<Loan>();
        java.util.List<Serializable> loansForAgg = null;
        List<Loan> loans2 = new ArrayList<Loan>();
        LoanAgg loanAgg = null;
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = null;
        cal = Calendar.getInstance();
        try {
            cal.setTime(formatter.parse(startDate));
        }catch(ParseException parseEx){
            parseEx.printStackTrace();
        }
        //System.out.println(loanIds);
        if (StringUtils.isNotEmpty(loanAggId)) {
            loanAgg = new LoanAgg();
            loanAgg.setLoanAggId(Long.valueOf(loanAggId));
            loanAgg.setName(name);
            loanAgg.setType(type);
            loanAgg.setEmail(email);
            loanAgg.setStartDate(cal);
            loanAgg.setTerm(term);
        }
        LoanAggService loanAggService = (LoanAggService) appCtx.getBean("loanAggService");
        LoanRelationshipService loanRelationshipService = (LoanRelationshipService) appCtx.getBean("loanRelationshipService");
        StringBuffer querySB = new StringBuffer();
        Object[] queryVals = null;
        Object[] queryValsForRemove = null;
        List<Object> queryValListForRemove = new ArrayList<Object>();
        List<Object> queryValList = new ArrayList<Object>();
        AggregationSummary aggregationSummary = new AggregationSummary();
        int loanAggRemovedCounter = 0;

        if (StringUtils.isEmpty(loanAggId) && !"[]".equals(loanIds)) {
            try {
                loanAgg = new LoanAgg();
                loanAgg.setName(name);
                loanAgg.setType(type);
                loanAgg.setEmail(email);
                loanAgg.setStartDate(cal);
                loanAgg.setTerm(term);
                loanAggId = ((Long) loanAggService.createLoanAgg(loanAgg)).toString();
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
                return "aggregateloan";
            }
        }
        if (StringUtils.isNotEmpty(loanAggId)) {
            try {
                querySB.append("la.loanAggId=?");
                queryValList.add(Long.valueOf(loanAggId));
                queryVals = new Object[queryValList.size()];
                queryVals = queryValList.toArray(queryVals);
                loanAggDetails = loanAggService.findLoanAgg("select la from LoanAgg la where " + querySB.toString(), queryVals);

                if (loanAggDetails != null && loanAggDetails.size() > 0) {
                    Set<LoanRelationship> loanRelationshipSet = ((LoanAgg) loanAggDetails.get(0)).getLoanRelationshipSet();
                    if (loanRelationshipSet != null) {
                        Iterator<LoanRelationship> loanRelationshipsIter = loanRelationshipSet.iterator();
                        while (loanRelationshipsIter.hasNext()) {
                            loanRelationshipService.removeLoanRelation(loanRelationshipsIter.next());
                        }
                    }
                }
                if (!"[]".equals(loanIds)) {
                    loanIds = loanIds.replaceAll("(\\[\")|(\"\\])", "").replaceAll("\",\"", ",");
                    String[] loanId = loanIds.split(",");

                    for (int i = 0; i < loanId.length; i++) {
                        LoanRelationship loanRelationship = new LoanRelationship();
                        loanRelationship.setLoanAgg(loanAgg);
                        loanRelationship.setName(name);
                        loanRelationship.setType(type);
                        loanRelationship.setEmail(email);
                        loanRelationship.setLoanId(Long.parseLong(loanId[i]));
                        loanRelationshipService.createLoanRelation(loanRelationship);
                    }
                    java.util.List<Serializable> loans = null;
                    LoanService loanService = (LoanService) appCtx.getBean("loanService");
                    for (int counter = 0; counter < loanId.length; counter++) {
                        StringBuffer querySBForLoan = new StringBuffer();
                        Object[] queryValsForLoan = null;
                        List<Object> queryValListForLoan = new ArrayList<Object>();
                        querySBForLoan.append("la.loanId=?");
                        queryValListForLoan.add(Long.valueOf(Long.parseLong(loanId[counter])));
                        queryValsForLoan = new Object[queryValListForLoan.size()];
                        queryValsForLoan = queryValListForLoan.toArray(queryValsForLoan);
                        loans = loanService.findLoan("select la from Loan la where " + querySBForLoan.toString(), queryValsForLoan);
                        loans1.add((Loan) loans.get(0));
                    }
                    System.out.println(loans1);
                    LoanWebService loanWebService = (LoanWebService) appCtx.getBean("loanWebService");
                    try {
                        aggregationSummary = loanWebService.aggregationSummary(loans1, cal);
                    } catch (LoanAccessException lae) {
                        lae.printStackTrace();
                        model.addAttribute("message", "Calculate Loan Failed!");
                        return "aggregateloan";
                    }
                    loanAggService.modifyLoanAgg(loanAgg);
                } else {
                    loanAggService.removeLoanAgg(loanAgg);
                    loanAggRemovedCounter = 1;
                }
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
            }
        }
        if (!"[]".equals(loansId)) {
            loansId = loansId.replaceAll("(\\[\")|(\"\\])", "").replaceAll("\",\"", ",");
            String[] loanId = loansId.split(",");
            try {
                for (int counter = 0; counter < loanId.length; counter++) {
                    StringBuffer querySBForLoanAgg = new StringBuffer();
                    Object[] queryValsForLoanAgg = null;
                    List<Object> queryValListForLoan = new ArrayList<Object>();
                    querySBForLoanAgg.append("la.loanId=?");
                    queryValListForLoan.add(Long.valueOf(Long.valueOf(Long.parseLong(loanId[counter]))));
                    queryValsForLoanAgg = new Object[queryValListForLoan.size()];
                    queryValsForLoanAgg = queryValListForLoan.toArray(queryValsForLoanAgg);
                    loansForAgg = loanAggService.findLoanAgg("select la from Loan la where " + querySBForLoanAgg.toString(), queryValsForLoanAgg);
                    loans2.add((Loan) loansForAgg.get(0));
                }
            } catch (LoanAccessException lae) {
                lae.printStackTrace();
            }
        }

        model.addAttribute("message", "");
        model.addAttribute("loanEntries1", loans2);
        model.addAttribute("loanEntries2", loans1);
        if (loanAggDetails != null && loanAggRemovedCounter == 0 && loanAggDetails.get(0) != null) {
            model.addAttribute("loanAggId", loanAgg.getLoanAggId());
            model.addAttribute("name", loanAgg.getName());
            model.addAttribute("type", loanAgg.getType());
            model.addAttribute("term", loanAgg.getTerm());
            model.addAttribute("startDate", startDate);
            model.addAttribute("email", loanAgg.getEmail());
        }
        if(loanAgg == null && loanAgg.getEmail() == null){
            model.addAttribute("email", emailCookie);
        }
        model.addAttribute("totalAmount", aggregationSummary.getTotalAmount());
        model.addAttribute("amountPaid", aggregationSummary.getAmountPaid());
        model.addAttribute("remainingAmount", aggregationSummary.getRemainingAmount());
        model.addAttribute("remainingPercent", aggregationSummary.getRemainingPercent());
        model.addAttribute("maximumNumOfYears", aggregationSummary.getMaximumNumOfYear());
        model.addAttribute("payoff", formatter.format(aggregationSummary.getPayoffDate().getTime()));
        model.addAttribute("startDateForSummary", startDate);
        model.addAttribute("NoOfLoansInRelation", loans1.size());
        if (loanAggDetails == null && loanAggRemovedCounter == 0) {
            model.addAttribute("message", "Please select Loan For Aggregation!");
        }
        return "aggregateloan";
    }
    
 @RequestMapping(value = "/logout")
    public String logout(Model model) {
        model.addAttribute("message", "Logout");
        return "logout";
    }    

 @RequestMapping(value = "/login")
     public String login(@RequestParam(value="email", defaultValue = "") String email, @RequestParam(value="password", defaultValue = "") String password,
@CookieValue(value = "userEmail", defaultValue = "") String emailCookie, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("message", "Login Form");
        if (email != null && !email.equals("") && password != null && !password.equals("")) {
            model.addAttribute("userEmail", email);
	    boolean emailPasswordFlag = checkPreferenceEmailAddress(email, password);
            if(emailPasswordFlag){
              	response.addCookie(new Cookie("userEmail", email));
		model.addAttribute("userEmail", email);
       		return "index";
	    }else
	        return "login";
        }
        return "login";
    }    

@RequestMapping(value = "/resetpasswordask")
  public String resetPassword(Model model) {
        model.addAttribute("message", "Reset Password");
        return "resetpassword";
    }

 @RequestMapping(value = "/resetpassword")
     public String resetPassword(@RequestParam(value="email", defaultValue = "") String email, @RequestParam(value="oldpassword", defaultValue = "") String oldpassword,
 @RequestParam(value="newpassword", defaultValue = "") String newpassword,
@CookieValue(value = "userEmail", defaultValue = "") String emailCookie, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("message", "Reset Password Form");
        if (email != null && !email.equals("") && oldpassword != null && !oldpassword.equals("") && newpassword != null && !newpassword.equals("")) {
            model.addAttribute("userEmail", email);
            model.addAttribute("oldpassword", oldpassword);
            model.addAttribute("newpassword", newpassword);
        }
        return "resetpassword";
    }

@RequestMapping(value = "/forgetpasswordask")
  public String forgetPassword(Model model) {
        model.addAttribute("message", "Forget Password");
        return "forgetpassword";
    }

 @RequestMapping(value = "/forgetpassword")
     public String forgetPassword(@RequestParam(value="email", defaultValue = "") String email,
@CookieValue(value = "userEmail", defaultValue = "") String emailCookie, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("message", "Forget Password Form");
        if (email != null && !email.equals("")) {
            model.addAttribute("userEmail", email);
        }
        return "forgetpassword";
    }
	private boolean checkPreferenceEmailAddress(String newEmail, String password) {
	        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		        PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
			        List<Preference> preferences;
				boolean emailFlag = false, passwordFlag = false;
				        try {
				           preferences = prefService.findPreference("select pref from Preference pref where pref.emailAddress = ?", new Object[]{newEmail});
							                if(preferences != null){
										                    for(Preference p : preferences){
													if(p.getEmailAddress().equals(newEmail))
														emailFlag = true;
													if(p instanceof PasswordPreference)
														if(p.getValue().equals(password))
															passwordFlag = true;
													if(emailFlag && passwordFlag)
														return true;
												    }
												    
												    return false;							
		    							}
				        } catch (PreferenceAccessException ex) {
                            logger.error(ex.getMessage());
				        }
				    return false;	
	}

	private List<Preference> getPreferencesByEmailAddress(String email) {
	        ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		        PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
			        List<Preference> preferences;
				        try {
				           preferences = prefService.findPreference("select pref from Preference pref where pref.emailAddress = ?", new Object[]{email});
							                if(preferences != null){
										return preferences;
		    							}
				        } catch (PreferenceAccessException ex) {
				            logger.error(ex.getMessage());
				        }
				    return null;
	}

    @RequestMapping(value = "/aggregateloanreportask" , method = RequestMethod.GET)
    public String aggregateloanReport(Model model,HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookie_jar = request.getCookies();
        for (Cookie c: cookie_jar){
            if(c.getName().equals("userEmail")){
                String email=c.getValue();
                ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
                PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
                List<Preference> preferences;
                try {
                    preferences = prefService.findPreference("select p from Preference p where p.emailAddress = ?", new Object[]{email});
                    if (preferences != null) {
                        for(Preference p:preferences) {
                            if (p.getName().equals("LoanId")){
                                model.addAttribute("loanId", p.getValue());
                            }else if(p.getName().equals("NumberOfYears")){
                                model.addAttribute("numberOfYears", p.getValue());
                            }else if(p.getName().equals("Amount")){
                                model.addAttribute("loanAmt", p.getValue());
                            }else if(p.getName().equals("Lender")){
                                model.addAttribute("lender", p.getValue());
                            }else if(p.getName().equals("State")){
                                model.addAttribute("state", p.getValue());
                            }else if(p.getName().equals("AIR")){
                                model.addAttribute("APR", p.getValue());
                            }
                        }
                    }
                }catch (PreferenceAccessException ex) {
                    logger.error(ex.getMessage());
                }
            }

        }
        model.addAttribute("message", "Aggregate Loan Report");
        return "aggregateloanreport";
    }

    @RequestMapping(value = "/aggregateloanreport", method = RequestMethod.POST)
    public String aggregateloanreport(
            @RequestParam("loanId") String loanId,
            @RequestParam("loanAmt") String loanAmt,
            @RequestParam("lender") String lender,
            @RequestParam("state") String state,
            @RequestParam("numOfYears") String numOfYears,
            @RequestParam("APR") String airVal,
            @CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
            Model model, HttpServletRequest request) throws ParseException, LoanAccessException {

        List<Serializable> loans = searchLoanForAggregation(loanId, loanAmt, lender, state, numOfYears, airVal);

        if (loans != null && loans.size() > 0) {
            java.util.List<Serializable> loanRelationship = null;
            java.util.List<Serializable> loanRelationshipforCount = null;
            LoanAgg loanAgg = null;
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            LoanRelationshipService loanRelationshipService = (LoanRelationshipService) appCtx.getBean("loanRelationshipService");
            loanRelationship = searchLoanRelationship(loans);
            if (loanRelationship != null && loanRelationship.size() > 0) {
                loanAgg = ((LoanRelationship) loanRelationship.get(0)).getLoanAgg();
                System.out.println("loan agg Id " + loanAgg.getLoanAggId());
                StringBuffer querySBForAgg = new StringBuffer();
                Object[] queryValsForAgg = null;
                java.util.List<Object> queryValListForAgg = new java.util.ArrayList<Object>();
                querySBForAgg.append("la.loanAgg=?");
                queryValListForAgg.add(((LoanRelationship)loanRelationship.get(0)).getLoanAgg());
                queryValsForAgg = new Object[queryValListForAgg.size()];
                queryValsForAgg = queryValListForAgg.toArray(queryValsForAgg);
                try {
                    loanRelationshipforCount = loanRelationshipService.findLoanRelation("select la from LoanRelationship la where " + querySBForAgg.toString(), queryValsForAgg);
                } catch (LoanAccessException e) {
                    e.printStackTrace();
                }

                model.addAttribute("loanAggId",loanAgg.getLoanAggId());
                model.addAttribute("loanId", loanId);
                model.addAttribute("numberOfYears", numOfYears);
                model.addAttribute("loanAmt", loanAmt);
                model.addAttribute("lender", lender);
                model.addAttribute("state", state);
                model.addAttribute("APR", airVal);
                model.addAttribute("NoOfLoansInRelation", loanRelationshipforCount.size());
                model.addAttribute("message", "Aggregate Loan Report");

            }else {
                model.addAttribute("message", "No Record Found");
                model.addAttribute("loanId", loanId);
                model.addAttribute("numberOfYears", numOfYears);
                model.addAttribute("loanAmt", loanAmt);
                model.addAttribute("lender", lender);
                model.addAttribute("state", state);
                model.addAttribute("APR", airVal);
            }
        }else {
                model.addAttribute("message", "No Record Found");
                return "aggregateloanreport";
            }
            return "aggregateloanreport";
    }

    @RequestMapping(value = "/generateReport", method = RequestMethod.GET)
    public void generateJasperReportPDF(@RequestParam("loanAggId") String loanAggId,HttpServletResponse response,HttpServletRequest request ) {
        JRPdfExporter exporter = new JRPdfExporter();
        Connection connection = null;
        HashMap jasperParameter = new HashMap();
        jasperParameter.put("loanAggId",Double.valueOf(loanAggId));
        logger.debug("loanAggId"+loanAggId);
        try
        {

            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            BasicDataSource dbBean = (BasicDataSource)appCtx.getBean("dataSource");

           Class.forName(dbBean.getDriverClassName());
            String postgresURL = dbBean.getUrl();
            connection = DriverManager.getConnection(postgresURL,dbBean.getUsername(),dbBean.getPassword());
            logger.debug("connection is null:"+connection==null);
        }
        catch(SQLException ex)
        {
            logger.error(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        if(connection!=null) {
            try {
                String path = context.getRealPath("/WEB-INF/jasper/report1.jrxml");
                logger.debug("jrxml path" + path);
                JasperReport jasperReport = JasperCompileManager.compileReport(path);
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
                logger.debug("jasper print" + jasperPrint == null);
                if (jasperPrint != null) {
                    response.setContentType("text/html");
                    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
                    renderHtml(new JRHtmlExporter(), jasperPrint, response.getWriter());
                    logger.debug("render html complete");
                }
            } catch (Exception ex) {
                logger.error("Error in generate report:" + ex.getMessage());
            }
        }
    }

    public static void renderHtml(JRExporter exporter, JasperPrint print, PrintWriter writer)
            throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/servlets/image?image=");
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS ,Boolean.TRUE);
        exporter.exportReport();
    }

}
