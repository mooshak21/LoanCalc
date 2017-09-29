package com.ayushi.loan.calculator;

import com.ayushi.loan.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayushi.loan.*;
import com.ayushi.loan.exception.EmailServiceException;
import com.ayushi.loan.exception.LoanAccessException;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.exception.PreferenceProcessException;

import java.io.Serializable;

import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;
import com.ayushi.loan.preferences.LocationPreference;
import com.ayushi.loan.preferences.WebServicePreference;
import com.ayushi.loan.preferences.RiskTolerancePreference;
import com.ayushi.loan.preferences.TimeHorizonPreference;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes({"message", "loan", "amortizeloan", "payoffOn", "payoffAmt", "amortizeOn", "userEmail"})
public class LoanCalculatorController {


    @RequestMapping(value = "/")
    public String home(@CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
                       Model model) {
        model.addAttribute("userEmail", emailCookie);
        return "index";
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
            @RequestParam("numOfYears") String numOfYears, Model model) {
        boolean allVal = false;
        Loan loanQryObject = new Loan();
        Loan loanObject = null;
        if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
                && lender != null && !lender.equals("") && state != null && !state.equals("")
                && numOfYears != null && !numOfYears.equals("")) {
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
                    AmortizedLoan amortizeLoan = new AmortizedLoan(amortizeOn, searchloan.getMonthly(), searchloan.getAmount(), searchloan.getTotal(), searchloan.getLender(), searchloan.getState(), searchloan.getInterestRate(), searchloan.getAPR(), searchloan.getNumberOfYears(), 0);
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
            request.getSession().setAttribute("loans", loans);
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
                           RedirectAttributes redirectAttributes,
                           Model model, HttpServletResponse response, HttpServletRequest request) {


        AmortizedLoan loanObject = (AmortizedLoan) model.asMap().get("amortizeloan");
        Loan loan = (Loan) model.asMap().get("loan");
        Double payoffAmt = (Double) model.asMap().get("payoffAmt");
        String payoffOn = (String) model.asMap().get("payoffOn");
        String userEmail = (String) model.asMap().get("userEmail");

        if (email != null && !email.equals(userEmail)) {
            response.addCookie(new Cookie("userEmail", email));
            model.addAttribute("userEmail", email);
        }

        Properties prop = getProperties("spring/email.properties");

        if (email != null && !email.isEmpty()) {

            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            LoanEmailGeneratorService emailService = (LoanEmailGeneratorService) appCtx.getBean("emailService");
            String message = null;
            String subject = null;

            if (loanObject != null && dataType.equals("amortizedLoan")) {
                message = emailService.buildMessage(loanObject, payoffAmt, payoffOn);
                subject = prop.getProperty("email.subject") + loanObject.getLoanId();
            }

            if (loan != null && dataType.equals("Loan")) {
                message = emailService.buildMessage(loan);
                subject = prop.getProperty("email.subject") + loan.getLoanId();
            }

            if (message != null && subject != null) {
                try {
                    emailService.sendMail(email, subject, message);
                    redirectAttributes.addFlashAttribute("emailMsg", prop.getProperty("email.success"));
                } catch (EmailServiceException ex) {
                    Logger.getLogger(LoanCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
                    redirectAttributes.addFlashAttribute("emailErr", "we couldn't send you the email. Please try later!");
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("emailErr", prop.getProperty("email.error"));
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }


    //------------------------------------------------------------------------------------------------------------------------------               

    @RequestMapping(value = "/loanviewask")
    public String loanviewask(Model model) {
        model.addAttribute("message", "View Loans");
        return "viewloans";
    }


    @RequestMapping(value = "/viewloanentries/{pageid}")
    public String viewloanentries(@PathVariable int pageid, Model model,
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
        return "viewloans";
    }


    @RequestMapping(value = "/viewloan/{pageid}")
    public String viewloan(@PathVariable int pageid, Model model,
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
                        loan.getInterestRate(), loan.getAPR(), loan.getNumberOfYears(), 0);
                model.addAttribute("payoffAmt", !payoffOn.isEmpty() ? ((al.getPayoffAmount(loan.getAmount(), payoffOn) != null) ? al.getPayoffAmount(loan.getAmount(), payoffOn) : "-1.0") : "-1.0");
                model.addAttribute("amortizeloan", al);
                al.setLoanId(loan.getLoanId());
            }
        }

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
    public String loanpreferenceviewask(Model model) {
        model.addAttribute("message", "Edit Preferences");
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
            HttpServletRequest request, HttpServletResponse response, Model model) {
        boolean allVal = false;
        Loan loanQryObject = new Loan();
        if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("")
                && lender != null && !lender.equals("") && state != null && !state.equals("")
                && numOfYears != null && !numOfYears.equals("")) {
            allVal = true;
            loanQryObject.setAmount(Double.valueOf(loanAmt));
            loanQryObject.setLender(lender);
            loanQryObject.setState(state);
            loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
            loanQryObject.setAPR(Double.valueOf(airVal));
        }

        if (email != null && !email.equals("")) {
            model.addAttribute("userEmail", email);
            response.addCookie(new Cookie("userEmail", email));
        }

        if (allVal) {
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            PreferenceService prefService = (PreferenceService) appCtx.getBean("preferenceService");
            List<Preference> prefList = new ArrayList<Preference>();
            if (locationPreference != null && !locationPreference.equals("")) {
                LocationPreference locPref = new LocationPreference();
                locPref.setId(1);
                locPref.setName("State");
                locPref.setValue(locationPreference);
                locPref.setFlag(true);
                locPref.setActive("Y");
                prefList.add(locPref);
            }
            if (webServicePreference != null && !webServicePreference.equals("")) {
                WebServicePreference wsPref = new WebServicePreference();
                wsPref.setId(2);
                wsPref.setName("Web Service");
                wsPref.setValue(webServicePreference);
                wsPref.setFlag(true);
                wsPref.setActive("Y");
                prefList.add(wsPref);
            }
            if (riskTolerancePreference != null && !riskTolerancePreference.equals("")) {
                RiskTolerancePreference rtPref = new RiskTolerancePreference();
                rtPref.setId(3);
                rtPref.setName("Interest Rate");
                rtPref.setValue(riskTolerancePreference);
                rtPref.setFlag(true);
                rtPref.setActive("Y");
                prefList.add(rtPref);
            }
            if (timeHorizonPreference != null && !timeHorizonPreference.equals("")) {
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
            try {
                preferenceIds = prefService.processPreferences(prefs,
                        pref -> pref.getFlag() && pref.getActive().equals("Y"));
                if (preferenceIds != null && preferenceIds.size() > 0) {
                    prefService.addPreferences(loanQryObject, preferenceIds);
                    for (Integer prefId : preferenceIds)
                        sbPref.append(prefId);
                    model.addAttribute("message", "Preference Service Successful! " + sbPref.toString());
                } else {
                    model.addAttribute("message", "Preference Service Failed!");
                }
            } catch (PreferenceAccessException | PreferenceProcessException pae) {
                pae.printStackTrace();
                model.addAttribute("message", "Preference Service Failed!");
                return "viewpreferences";
            }
        } else {
            model.addAttribute("message", "Preference Service : Required Parameters not entered!");
        }
        return "viewpreferences";
    }

    //---------------------------------------------------------------------------------------
    private Properties getProperties(String fileProp) {
        Properties prop = new Properties();
        try {
            prop.load(LoanCalculatorController.class.getClassLoader().getResourceAsStream(fileProp));
        } catch (IOException ex) {
            Logger.getLogger(LoanCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }
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
            }
            if(loanAgg == null && loanAgg.getEmail() == null){
                model.addAttribute("email", emailCookie);
            }

            model.addAttribute("message", "");
        } else {
            model.addAttribute("message", "No Record Found");
            return "aggregateloan";
        }
        return "aggregateloan";
    }

    @RequestMapping(value = "/aggregateloanask")
    public String aggregateloan(Model model) {
        model.addAttribute("message", "");
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
        if (loanAggDetails == null && loanAggRemovedCounter == 0) {
            model.addAttribute("message", "Please select Loan For Aggregation!");
        }
        return "aggregateloan";
    }

}











