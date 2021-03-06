package com.ayushi.loan.calculator;

import com.ayushi.loan.exception.*;
import com.ayushi.loan.preferences.*;
import com.ayushi.loan.service.*;
import com.paypal.base.rest.APIContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.ayushi.loan.preferences.PasswordPreference.PASSWORD_PREFERENCE_TYPE;
import static com.ayushi.loan.preferences.SaltPreference.SALT_PREFERENCE_TYPE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ayushi.loan.*;
import javax.servlet.http.Cookie;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.mindrot.jbcrypt.BCrypt;

@Controller
@SessionAttributes({ "loan", "amortizeloan", "payoffOn", "payoffAmt", "amortizeOn", "userEmail", "loans", "loginStatus",
		"planSelected", "Plan" })
public class LoanCalculatorController implements ServletContextAware {

	protected static final String PREMIUM_PLAN = "19.99", LITE_PLAN = "9.99", FREE_PLAN = "0.0";
	private static final String PLAN = "Plan";
	private static final String USER_PREFERENCE = "UserPreference";
	private static final String REMINDER_FREQUENCY = "reminderFrequency";
	private static final String LOGIN_STATUS = "loginStatus";
	private static final String USER_EMAIL = "userEmail";
	private static final String PLAN_SELECTED = "planSelected";
	private static final String PASSWORD_VALIDATION_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$";
	private static final String INVALID_PASSWORD_MESSAGE = "Password is too weak!";

	private PreferenceService preferenceService;
  private LoanService loanService;
  private LoanRelationshipService loanRelationshipService;
  private LoanWebService loanWebService;
//  private final LoanEmailGeneratorService loanEmailGeneratorService;
  private LoanAggService loanAggService;
  private BasicDataSource dataSource;
  private PaymentService paymentService;
  private EquityService equityService;
  private EquityExternalCalculatorService equityExternalCalculatorService;
  private SiteOfferService siteOfferService;

  private ServletContext context;

	public LoanCalculatorController(PreferenceService preferenceService, LoanService loanService,
      LoanRelationshipService loanRelationshipService, LoanWebService loanWebService, LoanAggService loanAggService,
      SiteOfferService siteOfferService, PaymentService paymentService, EquityService equityService,
      EquityExternalCalculatorService equityExternalCalculatorService, BasicDataSource dataSource) {
    this.preferenceService = preferenceService;
    this.loanService = loanService;
    this.loanRelationshipService = loanRelationshipService;
    this.loanWebService = loanWebService;
//    this.loanEmailGeneratorService = loanEmailGeneratorService;
    this.loanAggService = loanAggService;
    this.dataSource = dataSource;
    this.paymentService = paymentService;
    this.equityService = equityService;
    this.equityExternalCalculatorService = equityExternalCalculatorService;
    this.siteOfferService = siteOfferService;
  }

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private static final Logger logger = Logger.getLogger(LoanCalculatorController.class);

	// PayPal client ID and secret
	String clientId = "ATA-TNQRo-8wO-APHyJVCruKLJe137gre0Tfbf8rDmN8a_e1B07kvHGe59NmwdfP91h-p5QzlIM77NCZ";
	String clientSecret = "EBkWxJfwWu1ctf7QkpO-3RtPkzqMhWGhsh1g43iBixezH-xPtgL4q6KtJwNXUVjZlTFx-xpNp4WXaZeK";
	private static APIContext paypalApicontext;

	@RequestMapping(value = "/")
	public String index(@CookieValue(value = "userEmail", defaultValue = "") String emailCookie,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@CookieValue(value = PLAN, defaultValue = "") String plan,
			@CookieValue(value = USER_PREFERENCE, defaultValue = "") String userPreference,
			@CookieValue(value = LOGIN_STATUS, defaultValue = "") String loginStatus, Model model, HttpServletRequest request) {

		String loginStatusSession = (String) request.getSession().getAttribute(LOGIN_STATUS);

		if (loginStatus != null && loginStatus.equals("Y")) {
			model.addAttribute(REMINDER_FREQUENCY, reminderFrequency);
			model.addAttribute(PLAN, plan);
			request.getSession().setAttribute(PLAN, plan);
			request.getSession().setAttribute(USER_EMAIL, emailCookie);
			String userPref = userPreference;
			request.getSession().setAttribute(USER_PREFERENCE, (userPref != null && !userPref.equals("")) ? userPref : "");
			request.getSession().setAttribute(LOGIN_STATUS, loginStatus);
			model.addAttribute(USER_EMAIL, emailCookie);
			request.getSession().setAttribute(PLAN_SELECTED, plan);
			return "index";

		} else if ((emailCookie != null && !emailCookie.equals("")) && (loginStatus != null && loginStatus.equals("Y"))) {
			model.addAttribute(REMINDER_FREQUENCY, reminderFrequency);
			model.addAttribute(PLAN, plan);
			request.getSession().setAttribute(PLAN, plan);
			request.getSession().setAttribute(USER_EMAIL, emailCookie);
			String userPref = userPreference;
			request.getSession().setAttribute(USER_PREFERENCE, (userPref != null && !userPref.equals("")) ? userPref : "");
			request.getSession().setAttribute(LOGIN_STATUS, loginStatus);
			model.addAttribute(USER_EMAIL, emailCookie);
			request.getSession().setAttribute(PLAN_SELECTED, plan);
			searchLoanBasedOnEmail(emailCookie, plan, model);
			return "bankoffersandnews";

		} else {
			model.addAttribute(REMINDER_FREQUENCY, reminderFrequency);
			model.addAttribute(PLAN, plan);
			request.getSession().setAttribute(PLAN, plan);
			request.getSession().setAttribute(USER_EMAIL, emailCookie);
			String userPref = userPreference;
			request.getSession().setAttribute(USER_PREFERENCE, (userPref != null && !userPref.equals("")) ? userPref : "");
			request.getSession().setAttribute(LOGIN_STATUS, (loginStatusSession != null && !loginStatusSession.equals("")) ? loginStatusSession : "");
			model.addAttribute(USER_EMAIL, emailCookie);
			request.getSession().setAttribute(PLAN_SELECTED, plan);

			return "index";
		}
	}

	@RequestMapping(value = "/home")
	public String home(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request) {

		request.getSession().setAttribute(LOGIN_STATUS, "Y");
		request.getSession().setAttribute(USER_EMAIL, emailCookie);
		request.getSession().setAttribute(PLAN, plan != null ? plan : "0.0");
		request.getSession().setAttribute(PLAN_SELECTED, plan != null ? plan : "0.0");
		model.addAttribute(PLAN_SELECTED, plan != null ? plan : "0.0");
		model.addAttribute(PLAN, plan != null ? plan : "0.0");
		model.addAttribute(USER_EMAIL, emailCookie);
		model.addAttribute("message", "Landing Page");
		searchLoanBasedOnEmail(emailCookie, plan, model);

		return "bankoffersandnews";
	}

	@RequestMapping(value = "/about")
	public String about() {
		return "about";
	}

	@RequestMapping(value = "/pricing", method = RequestMethod.GET)
	public String pricing(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		model.addAttribute(PLAN, plan);
		model.addAttribute(USER_EMAIL, emailCookie);
		return "pricing";
	}

	// --------------------------------------------------------------------------------------------

	@RequestMapping(value = "/createloan", method = RequestMethod.GET)
	public String createloan(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		model.addAttribute("message", "Enter Loan for Amortization Schedule");
		model.addAttribute(USER_EMAIL, emailCookie);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "createloan";
	}

	@RequestMapping(value = "/loan", method = RequestMethod.GET)
	public String loan(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "createloan";
	}

	@RequestMapping(value = "/loan", method = RequestMethod.POST)
	public String loan(@RequestParam("airVal") String airVal, @RequestParam("lender") String lender,
			@RequestParam("loanAmt") String loanAmt, @RequestParam("region") String region,
			@RequestParam("state") String state, @RequestParam("loanType") String loanType,
			@RequestParam("loanDenomination") String loanDenomination, @RequestParam("email") String email,
			@RequestParam("numOfYears") String numOfYears, @RequestParam("name") String name,
			@RequestParam("vehicleModel") String vehicleModel, @RequestParam("vehicleMake") String vehicleMake,
			@RequestParam("vehicleYear") String vehicleYear, @RequestParam("vin") String vin,
			@RequestParam("address") String address, @RequestParam("city") String city,
			@RequestParam("country") String country, @RequestParam("zipcode") String zipcode,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {

		logger.info("Create Load Function Call.");

		boolean allVal = false;
		Loan loanQryObject = new Loan();
		Loan loanObject = null;
		if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("") && lender != null
				&& !lender.equals("") && region != null && !region.equals("") && state != null && !state.equals("")
				&& numOfYears != null && !numOfYears.equals("") && loanType != null && !loanType.equals("")
				&& loanDenomination != null && !loanDenomination.equals("") && name != null && !name.equals("")) {
			allVal = true;
			loanQryObject.setAmount(Double.valueOf(loanAmt));
			loanQryObject.setLender(lender);
			loanQryObject.setRegion(region);
			loanQryObject.setState(state);
			loanQryObject.setLoanType(loanType);
			loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
			loanQryObject.setAPR(Double.valueOf(airVal));
			loanQryObject.setName(name);
			logger.info("Value set in loanQueryObject not null verification.");
		}
		if (allVal) {
			logger.info("allValue become true.");
			try {
				loanObject = loanWebService.calculateLoan(loanQryObject);
				logger.info("Loan Calculation function call successfully.");
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Calculate Loan Failed!");
				return "createloan";
			}
			/*
			 * GsonBuilder gsonb = new GsonBuilder(); Gson gson =
			 * gsonb.create(); Loan loanObject = gson.fromJson(loan,
			 * Loan.class);
			 */
			if (loanObject != null) {
				try {
					loanObject.setLoanType(loanType);
					loanObject.setRegion(region);
					loanObject.setLoanDenomination(loanDenomination);
					loanObject.setEmail(email);
					loanObject.setName(name);
					if (loanType.equals("Auto Loan")) {
						loanObject.setVehicleMake(vehicleMake);
						loanObject.setVehicleModel(vehicleModel);
						loanObject.setVehicleYear(vehicleYear);
						loanObject.setVin(vin);
					} else if (loanType.equals("Home Loan")) {
						loanObject.setAddress(address);
						loanObject.setCity(city);
						loanObject.setState(state);
						loanObject.setCountry(country);
						loanObject.setZipcode(zipcode);
					}
					logger.info("Create Loan function call with loanobject : " + loanObject);
					loanService.createLoan(loanObject);
					logger.info("Create Loan successfully");
				} catch (LoanAccessException lae) {
					lae.printStackTrace();
					List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
					addPlanFromPreferencesToModel(model, prefs);
					checkUserPrefernece(model, prefs);
					model.addAttribute("message", "Create Loan Failed!");
					logger.info("Create error in create model : " + model);
					return "createloan";
				}
				LoanApp loanApp = new LoanApp(loanObject);
				loanObject.setLoanApp(loanApp);
				model.addAttribute("message", "Create Loan");
				model.addAttribute("loan", loanObject);
				model.addAttribute(PLAN, plan);
				logger.info("Loan app Add data on the model");
			} else {
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				if (prefs != null) {
					for (Preference preference : prefs) {
						if (preference.getType().equals(PLAN)) {
							plan = preference.getValue();
						}
					}
					model.addAttribute(PLAN, plan);
				}
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Create Loan Failed!");
				logger.info("check prefrences generate the error.");
			}
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			if (prefs != null) {
				for (Preference preference : prefs) {
					if (preference.getType().equals(PLAN)) {
						plan = preference.getValue();
					}
				}
				model.addAttribute(PLAN, plan);
			}
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Create Loan : Required Parameters not entered!");
			logger.info("Required Parameters not entered!");
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		logger.info("Loan created successfully.done");
		return "createloan";
	}

	// --------------------------------------------------------------------------------------------

	@RequestMapping(value = "/loanamortizeask")
	public String loanamortizeask(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		model.addAttribute("message", "Amortize Loan");
		java.util.Calendar calToday = java.util.Calendar.getInstance();
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
				+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		model.addAttribute("amortizeOn", calTodayStr);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "amortizeloan";
	}

	@RequestMapping(value = "/amortizeloan", method = RequestMethod.GET)
	public String amortizeloan(@RequestParam("airVal") String airVal, @RequestParam("lender") String lender,
			@RequestParam("loanAmt") String loanAmt, @RequestParam("state") String state,
			@RequestParam("numOfYears") String numOfYears, @RequestParam("amortizeOn") String amortizeOn,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		boolean allVal = false;
		Loan loanQryObject = new Loan();
		model.addAttribute(PLAN, plan);
		model.addAttribute(USER_EMAIL, emailCookie);
		if (loanAmt != null && !loanAmt.equals("") && airVal != null && !airVal.equals("") && lender != null
				&& !lender.equals("") && state != null && !state.equals("") && numOfYears != null
				&& !numOfYears.equals("") && amortizeOn != null && !amortizeOn.equals("")) {
			allVal = true;
			loanQryObject.setAmount(Double.valueOf(loanAmt));
			loanQryObject.setLender(lender);
			loanQryObject.setState(state);
			loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
			loanQryObject.setAPR(Double.valueOf(airVal));
		}
		if (allVal) {
			AmortizedLoan loanObject = null;
			try {
				loanObject = loanWebService.amortizeLoan(loanQryObject, amortizeOn);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				model.addAttribute("message", "Amortize Loan Failed!");
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				checkUserPrefernece(model, prefs);
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
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("amortizeOn", amortizeOn);

		return "amortizeloan";
	}

	@RequestMapping(value = "/searchloan", method = RequestMethod.GET)
	public String searchLoan(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, RedirectAttributes redirectAttributes) {
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		model.addAttribute(PLAN, plan);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "searchloan";
	}

	// -------------------------------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/loansearchask")
	public String loansearchask(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Search Loan");
		java.util.Calendar calToday = java.util.Calendar.getInstance();
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
				+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		model.addAttribute("amortizeOn", calTodayStr);
		model.addAttribute("payoffOn", calTodayStr);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "searchloan";
	}

	private void checkUserPrefernece(Model model, List<Preference> prefs) {
		ArrayList<String> prefVal;
		ArrayList<String> prefAttr;
		if (prefs != null) {
			prefVal = new ArrayList<String>(prefs.size());
			prefAttr = new ArrayList<String>(prefs.size());
			int prefIdx = 0;
			for (Preference pref : prefs) {
				prefAttr.add(pref.getName());
				prefVal.add(pref.getValue());
			}
			for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++) {
				if (prefAttr.get(prefIdx).equals(USER_PREFERENCE) && prefVal.get(prefIdx).equals("Admin"))
					model.addAttribute(USER_PREFERENCE, prefVal.get(prefIdx));
			}
		}
	}

	@RequestMapping(value = "/loanpayoffask")
	public String loanpayoffask(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Payoff Loan");
		java.util.Calendar calToday = java.util.Calendar.getInstance();
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
				+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		model.addAttribute("payoffOn", calTodayStr);
		model.addAttribute("amortizeOn", calTodayStr);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "searchloan";
	}

	@RequestMapping(value = "/searchloan", method = RequestMethod.POST)
	public String searchloan(@RequestParam(value = "airVal", defaultValue = "") String airVal, @RequestParam(value = "lender", defaultValue = "") String lender,
			@RequestParam(value = "loanAmt", defaultValue = "") String loanAmt, @RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "numOfYears", defaultValue = "") String numOfYears, @RequestParam(value = "loanType", defaultValue = "") String loanType,
			@RequestParam(value = "amortizeOn", defaultValue = "") String amortizeOn, @RequestParam(value = "payoffOn", defaultValue = "") String payoffOn,
			@RequestParam(value = "email", defaultValue = "") String emailParam,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String userEmail,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request, @ModelAttribute("loans") Loan.Loans loansList, RedirectAttributes attributes) {
		loansList.clear();
		AmortizedLoan loanObject = new AmortizedLoan();
		int total = 0;
		StringBuffer querySB = new StringBuffer();
		java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
		Object[] queryVals = null;
		boolean firstVal = false;
		Double payoffAmt = null;
		model.addAttribute("uEmail", emailParam);
		model.addAttribute(USER_EMAIL, userEmail);

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
		if (emailParam != null && !emailParam.equals("")) {
			if (firstVal)
				querySB.append(" and ln.email=?");
			else {
				querySB.append(" ln.email=?");
				firstVal = true;
			}
			queryValList.add(emailParam);
			loanObject.setEmail(emailParam);
		}

		if (firstVal) {
			queryVals = new Object[queryValList.size()];
			queryVals = queryValList.toArray(queryVals);
			java.util.List<Serializable> loans = null;
			try {
				loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				model.addAttribute("message", "Search Loan Failed!");
			}
			if (loans != null && loans.size() > 0) {
				//				request.getSession().setAttribute("loans", loans);
				//				attributes.addFlashAttribute("loans", loans);
				loans.forEach(loan -> loansList.add((Loan) loan));
				attributes.addFlashAttribute("loans", loansList);
				//				model.addAttribute("loans", loans);
				total = loans.size();
				Loan searchloan = (Loan) loans.get(0);

				if (searchloan != null) {
					AmortizedLoan amortizeLoan = AmortizedLoan.getAmortizedLoan(amortizeOn, searchloan);
					LoanApp loanApp = new LoanApp(amortizeLoan);
					amortizeLoan.setLoanApp(loanApp);
					payoffAmt = amortizeLoan.getPayoffAmount(searchloan.getAmount(), payoffOn);
					model.addAttribute("payoffAmount", payoffAmt);
					loanObject = amortizeLoan;
				}

			} else {
				model.addAttribute("message", "Search Loan: No Loans Found!");
				model.addAttribute("loans", new ArrayList<>());
				model.asMap().remove("amortizeloan");
				List<Preference> prefs = getPreferencesByEmailAddress(emailParam);
				addPlanToModel(model, plan, prefs);
				checkUserPrefernece(model, prefs);

				return "searchloan";
			}
			model.addAttribute("message", "Search Loan: " + ((loans != null) ? loans.size() : 0) + " Loans Found!");
			model.addAttribute("loans", loans);

			model.addAttribute("amortizeloan", loanObject);
			model.addAttribute("payoffOn", payoffOn);
			model.addAttribute("payoffAmt", payoffAmt);
			model.addAttribute("amortizeOn", amortizeOn);
			model.addAttribute(USER_EMAIL, emailParam);

			List<Preference> prefs = getPreferencesByEmailAddress(emailParam);
			addPlanToModel(model, plan, prefs);
			checkUserPrefernece(model, prefs);
			return "searchloan";
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailParam);
			addPlanToModel(model, plan, prefs);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Search Loan: " + " Loan Parameters Not Selected!");

			return "searchloan";
		}
	}

	@RequestMapping(value = "/deleteloan", method = RequestMethod.DELETE)
	public String deleteloan(@RequestParam("loanId") String loanId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		List<Serializable> loans = new ArrayList<>();
		model.addAttribute(PLAN, plan);
		try {
			loans = loanService.findLoan("select ln from Loan ln where ln.loanId = ?",
					new Object[] { new Long(loanId) });
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search Site Offer Failed!");
		}
		try {
			if (loans != null && loans.size() != 0) {
				loanService.removeLoan(loans.get(0));
			}
		} catch (LoanAccessException e) {
			e.printStackTrace();
		}
		model.addAttribute(USER_EMAIL, emailCookie);

		return "searchloan";
	}

	@RequestMapping(value = "/updateloan", method = RequestMethod.POST)
	public String updateloan(@RequestParam("loanId") String loanId, @RequestParam("airVal") String airVal,
			@RequestParam("lender") String lender, @RequestParam("loanAmt") String loanAmt,
			@RequestParam("state") String state, @RequestParam("numOfYears") String numOfYears,
			@RequestParam("loanType") String loanType, @RequestParam("amortizeOn") String amortizeOn,
			@RequestParam("payoffOn") String payoffOn, @RequestParam("email") String email,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws LoanAccessException {
		Loan loanQryObject = new Loan();
		loanQryObject.setLoanId(Long.valueOf(loanId));
		loanQryObject.setAmount(Double.valueOf(loanAmt));
		loanQryObject.setLender(lender);
		loanQryObject.setState(state);
		loanQryObject.setLoanType(loanType);
		loanQryObject.setNumberOfYears(Integer.valueOf(numOfYears));
		loanQryObject.setAPR(Double.valueOf(airVal));
		loanQryObject.setEmail(email);
		Loan loan = loanService.createLoan(loanQryObject);
		AmortizedLoan loanObject = new AmortizedLoan();
		try {
			loanObject = loanWebService.amortizeLoan(loanQryObject, amortizeOn);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Amortize Loan Failed!");
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanToModel(model, plan, prefs);
			checkUserPrefernece(model, prefs);

			return "amortizeloan";
		}

		if (loanObject != null) {
			LoanApp loanApp = new LoanApp(loanObject);
			loanObject.setLoanApp(loanApp);
			model.addAttribute("amortizeloan", loanObject);
			model.addAttribute("message", "Updated Loan");
		} else {
			model.addAttribute("message", "Amortize Loan Failed!");
		}
		model.addAttribute("amortizeOn", amortizeOn);
		model.addAttribute("payoffOn", payoffOn);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		logger.info("Updated Loan");
		return "searchloan";
	}

	// --------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/sendmail")
	public String sendMail(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "dataType") String dataType, @RequestParam(value = "prevMessage") String prevMessage,
			RedirectAttributes redirectAttributes, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		AmortizedLoan loanObject = (AmortizedLoan) model.asMap().get("amortizeloan");
		Loan loan = (Loan) model.asMap().get("loan");
		Double payoffAmt = (Double) model.asMap().get("payoffAmt");
		String payoffOn = (String) model.asMap().get("payoffOn");
		String userEmail = (String) model.asMap().get(USER_EMAIL);
		Long loanId = null;

		// if (email != null && !email.equals(userEmail)) {
		// updatePreferenceEmailAddress(email, userEmail);
		// response.addCookie(new Cookie(USER_EMAIL, email));
		// model.addAttribute(USER_EMAIL, email);
		// }

		Properties prop = getProperties("spring/email.properties");

		if (email != null && !email.isEmpty()) {
			String message = null;
			String subject = null;

			if (loanObject != null && dataType.equals("amortizedLoan")) {
//				message = loanEmailGeneratorService.buildMessage(loanObject, payoffAmt, payoffOn);
				subject = prop.getProperty("email.subject") + loanObject.getLoanId();
				loanId = loanObject.getLoanId();
			}

			if (loan != null && dataType.equals("Loan")) {
//				message = loanEmailGeneratorService.buildMessage(loan);
				subject = prop.getProperty("email.subject") + loan.getLoanId();
				loanId = loan.getLoanId();
			}

			if (message != null && subject != null) {
				try {
//					loanEmailGeneratorService.sendMail(email, subject, message);
					redirectAttributes.addFlashAttribute("emailMsg", prop.getProperty("email.success"));
					response.addCookie(new Cookie("loanId", loanId.toString()));
					List<Preference> prefs = getPreferencesByEmailAddress(email);
					int loanidPrefId = -1;
					if (prefs != null) {
						for (Preference pref : prefs) {
							if (pref.getName().equals("LoanId")) {
								loanidPrefId = pref.getId();
								break;
							}
						}
						if (loanidPrefId == -1)
							loanidPrefId = prefs.size() + 1;
					}
					addPreference(new LoanIdPreference(), loanidPrefId, email, "LoanId", loanId.toString());
				} /*catch (EmailServiceException ex) {
					logger.error(ex.getMessage());
					redirectAttributes.addFlashAttribute("emailErr",
							"we couldn't send you the email. Please try later!");
				}*/ catch (PreferenceAccessException ex) {
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

	// ------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/quickview")
	public String quickView(Model model, @CookieValue(value = "loanId", defaultValue = "") String loanId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		if (loanId != null && !loanId.equals("")) {
			List<Serializable> loans = new ArrayList<>();
			java.util.Calendar calToday = java.util.Calendar.getInstance();
			String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
					+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
			try {
				loans = loanService.findLoan("select ln from Loan ln where ln.loanId = ?",
						new Object[] { new Long(loanId) });
				if (loans != null && loans.size() > 0) {
					Loan searchloan = (Loan) loans.get(0);
					AmortizedLoan amortizeLoan = AmortizedLoan.getAmortizedLoan(calTodayStr, searchloan);
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
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				if (prefs != null) {
					for (Preference preference : prefs) {
						if (preference.getType().equals(PLAN)) {
							plan = preference.getValue();
						}
					}
				}
				model.addAttribute(PLAN, plan);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Loan no longer available!");
			}
		}
		if (emailCookie != null)
			model.addAttribute(USER_EMAIL, emailCookie);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		if (prefs != null)
			addPlanToModel(model, plan, prefs);
		checkUserPrefernece(model, prefs);

		return "viewloans";
	}

	// -----------------------------------------------------------------
	@RequestMapping(value = "/loanviewask")
	public String loanviewask(@CookieValue(value = "loanId", defaultValue = "") String loanId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		model.addAttribute("message", "View Loans");
		model.addAttribute("loanId", loanId);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		if (emailCookie != null)
			model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);

		return "viewloans";
	}

	@RequestMapping(value = "/viewloanentries/{pageid}")
	public String viewloanentries(@PathVariable int pageid, Model model,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = "loanId", defaultValue = "") String loanId,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletRequest request,
			HttpServletResponse response) {
		int total = 12;
		pageid = pageid == 1 ? pageid : (pageid - 1) * total + 1;
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
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
				+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		model.addAttribute("payoffOn", calTodayStr);
		model.addAttribute("amortizeOn", calTodayStr);
		model.addAttribute("loanId", loanId);
		model.addAttribute("message", "View Loans");
		List<Preference> prefs1 = getPreferencesByEmailAddress(emailCookie);
		if (prefs1 != null)
			addPlanToModel(model, plan, prefs1);
		if (emailCookie != null)
			model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs1);

		return "amortizeloan";
	}

	@RequestMapping(value = "/viewloan/{pageid}")
	public String viewloan(@PathVariable int pageid, Model model,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = "loanId", defaultValue = "") String loanId,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletRequest request, @ModelAttribute("loans") Loan.Loans loansList) {
		java.util.Calendar calToday = java.util.Calendar.getInstance();
		String calTodayStr = (calToday.get(java.util.Calendar.MONTH) + 1) + "/"
				+ calToday.get(java.util.Calendar.DAY_OF_MONTH) + "/" + calToday.get(java.util.Calendar.YEAR);
		AmortizedLoan al;
		String amortizeOn = (String) model.asMap().get("amortizeOn");
		String payoffOn = (String) model.asMap().get("payoffOn");
		if (payoffOn == null || payoffOn.isEmpty()) {
			payoffOn = calTodayStr;
		}
		if (emailCookie != null) {
			model.addAttribute(USER_EMAIL, emailCookie);
			List<Preference> prefs1 = getPreferencesByEmailAddress(emailCookie);
			if (prefs1 != null) {
				addPlanToModel(model, plan, prefs1);
				checkUserPrefernece(model, prefs1);
			}
		}

		//		List loans = Arrays.asList(loans().toArray());
		//		List loans = (List)attributes.getFlashAttributes().get("loans");
		if (!loansList.isEmpty()) {
			getLoanInfo(pageid, model, loanId, loansList, amortizeOn, payoffOn);
		} else {
			model.addAttribute("message", "View Loan on Page#: " + pageid + " Not Found");
			return "searchloan";
		}

		model.addAttribute("message", "View Loans");

		return "searchloan";
	}

	private void getLoanInfo(int pageid, Model model, String loanId, Loan.Loans loans, String amortizeOn, String payoffOn) {
		AmortizedLoan al;
		if (loans == null || loans.isEmpty()) {
			return;
		}
		//		if (loans != null) {
		//			if (amortizeOn != null) {
		if ((pageid - 1) < loans.size()) {
			Loan loan = loans.get(pageid - 1);
			if(loan != null){
				al = new AmortizedLoan(amortizeOn, loan.getMonthly(), loan.getAmount(), loan.getTotal(), loan.getLender(),
					loan.getRegion(), loan.getState(), loan.getInterestRate(), loan.getAPR(), loan.getNumberOfYears(), 0,
					loan.getLoanId(), loan.getLoanType(), loan.getLoanDenomination(), loan.getEmail(), loan.getName(), null, null,
					null, null, null, null, null, null);

				model.addAttribute("amortizeloan", al);
				model.addAttribute("loanId", loanId);
				al.setLoanId(loan.getLoanId());
			}
		}
		//				model.addAttribute("payoffAmt",
		//						!payoffOn.isEmpty() ? ((al.getPayoffAmount(loan.getAmount(), payoffOn) != null)
		//								? al.getPayoffAmount(loan.getAmount(), payoffOn) : "-1.0") : "-1.0");
		//			}
		//		}
	}

	@RequestMapping(value = "/viewloanexcel/{loanid}")
	public String loanviewexcel(@PathVariable long loanid, RedirectAttributes redirectAttributes,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
		model.addAttribute(USER_EMAIL, emailCookie);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		if (prefs != null) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					model.addAttribute(PLAN, preference.getValue());
				}
			}
		}
		checkUserPrefernece(model, prefs);

		return "viewloan";
	}

	// ----------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/loanpreferenceviewask")
	public String loanpreferenceviewask(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		model.addAttribute("message", "Edit Preferences");
		model.addAttribute(REMINDER_FREQUENCY, reminderFrequency);

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		ArrayList<String> prefVal = null, prefAttr = null;
		String planRef = null;
		if (prefs != null) {
			prefVal = new ArrayList<String>(prefs.size());
			prefAttr = new ArrayList<String>(prefs.size());
			int prefIdx = 0;
			for (Preference pref : prefs) {
				prefAttr.add(pref.getName());
				prefVal.add(pref.getValue());
			}
			for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++)
				model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					planRef = preference.getValue();
				}
			}
		}
		model.addAttribute(PLAN, planRef != null ? planRef : plan);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);

		return "viewpreferences";
	}

	// ----------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/loanpreferenceviewasktoregister")
	public String loanpreferenceviewasktoregister(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@RequestParam(PLAN) String plan, Model model) {
		model.addAttribute("message", "Edit Preferences");
		model.addAttribute(REMINDER_FREQUENCY, reminderFrequency);

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		ArrayList<String> prefVal = null, prefAttr = null;

		if (prefs != null) {
			prefVal = new ArrayList<String>(prefs.size());
			prefAttr = new ArrayList<String>(prefs.size());
			int prefIdx = 0;
			for (Preference pref : prefs) {
				prefAttr.add(pref.getName());
				prefVal.add(pref.getValue());
			}
			for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++)
				model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
		}
		model.addAttribute(PLAN, plan);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);

		return "viewpreferences";
	}

	@RequestMapping(value = "/vieweditpreferences", method = RequestMethod.POST)
	public String vieweditpreferences(
/*			@RequestParam("airVal") String airVal, @RequestParam("lender") String lender,
			@RequestParam("loanAmt") String loanAmt, @RequestParam("state") String state,
			@RequestParam("numOfYears") String numOfYears,
			@RequestParam("locationPreference") String locationPreference,
			@RequestParam("webServicePreference") String webServicePreference,
			@RequestParam("riskTolerancePreference") String riskTolerancePreference,
			@RequestParam("timeHorizonPreference") String timeHorizonPreference,*/
			@RequestParam(USER_PREFERENCE) String userPreference, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("reminderfreq") String reminderFreq,
			@RequestParam(PLAN) String plan, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			HttpServletRequest request, HttpServletResponse response, Model model) {

			String passwordPreference = null, planPreference = null;
/*		if (loanAmt != null && !loanAmt.equals(""))
			amountPreference = loanAmt;
		if (airVal != null && !airVal.equals(""))
			airPreference = airVal;
		if (lender != null && !lender.equals(""))
			lenderPreference = lender;
		if (state != null && !state.equals(""))
			statePreference = state;
		if (numOfYears != null && !numOfYears.equals(""))
			numberOfYearsPreference = numOfYears;*/

		if (reminderFreq != null && !reminderFreq.equals("")) {
			model.addAttribute(REMINDER_FREQUENCY, reminderFreq);
			response.addCookie(new Cookie(REMINDER_FREQUENCY, reminderFreq));
		}

		if (password != null && !password.equals("")) {
			if (!checkPasswordStrength(password)) {
				model.addAttribute("message", INVALID_PASSWORD_MESSAGE);
				return "viewpreferences";
			}
			model.addAttribute("message", null);
			passwordPreference = password;
		}
		List<Preference> prefList = new ArrayList<Preference>();

		if (passwordPreference != null) {
			PasswordPreference lpwdPref = new PasswordPreference();
			lpwdPref.setId(12);
			lpwdPref.setName("Password");
			lpwdPref.setEmailAddress(email);
			SaltPreference saltPreference = getSaltPreference(email);

			// Hash a password for the first time
			String hashed = BCrypt.hashpw(passwordPreference, saltPreference.getValue());

			lpwdPref.setValue(hashed);
			lpwdPref.setFlag(true);
			lpwdPref.setActive("Y");
			prefList.add(lpwdPref);
			prefList.add(saltPreference);
		}

		if (plan != null && !plan.equals("")) {
			planPreference = plan;
			model.addAttribute(PLAN, plan);
			response.addCookie(new Cookie(PLAN, plan));
		}

		if (email != null && !email.equals("")) {
			model.addAttribute(USER_EMAIL, email);
			if (!emailCookie.isEmpty() && !emailCookie.equals(email)) {
				updatePreferenceEmailAddress(email, emailCookie);
			}
			response.addCookie(new Cookie(USER_EMAIL, email));
		}

/*		if (locationPreference != null && !locationPreference.equals("")) {
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
*/
		if (email != null && !email.isEmpty()) {
			EmailReminderPreference erPref = new EmailReminderPreference();
			erPref.setId(5);
			erPref.setEmailAddress(email);
			erPref.setName("EmailAddressReminder");
			erPref.setValue(email);
			erPref.setFlag(true);
			erPref.setActive("Y");
			prefList.add(erPref);
		}

		if (reminderFreq != null && !reminderFreq.isEmpty()) {
			ReminderFrequencyPreference rfPref = new ReminderFrequencyPreference();
			rfPref.setId(6);
			rfPref.setEmailAddress(email);
			rfPref.setName(REMINDER_FREQUENCY);
			rfPref.setValue(reminderFreq);
			rfPref.setFlag(true);
			rfPref.setActive("Y");
			prefList.add(rfPref);
		}

/*		if (amountPreference != null && !amountPreference.equals("")) {
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

		if (numberOfYearsPreference != null && !numberOfYearsPreference.equals("")) {
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

*/
		if (planPreference != null && !planPreference.isEmpty()) {
			PlanPreference planPref = new PlanPreference();
			planPref.setId(13);
			planPref.setEmailAddress(email);
			planPref.setName(PLAN);
			planPref.setValue(planPreference);
			planPref.setFlag(true);
			planPref.setActive("Y");
			prefList.add(planPref);
		}

		if (userPreference != null && !userPreference.equals("")) {
			UserPreference usPref = new UserPreference();
			usPref.setId(14);
			usPref.setEmailAddress(email);
			usPref.setName(USER_PREFERENCE);
			usPref.setValue(userPreference);
			usPref.setFlag(true);
			usPref.setActive("Y");
			prefList.add(usPref);
			model.addAttribute(USER_PREFERENCE, userPreference);
		}
		List<Preference> preferences = null;
		Preferences prefs = new Preferences();
		prefs.setPreferences(prefList);
		try {
			preferences = preferenceService.processPreferences(prefs, pref -> pref.getFlag() && pref.getActive().equals("Y"));
			if (preferences != null && preferences.size() > 0) {
				for (Preference p : preferences) {
					System.out.println("Email Address is " + p.getEmailAddress());
					preferenceService.createPreference(p);
				}
				if (userPreference != null && !userPreference.equals("")) {
					model.addAttribute(USER_PREFERENCE, userPreference);
				}
				model.addAttribute(PLAN_SELECTED, plan);
				model.addAttribute(PLAN, plan);
				response.addCookie(new Cookie(PLAN, plan));
				model.addAttribute("message", "Preference Service Successful! ");
			} else {
				model.addAttribute(PLAN_SELECTED, plan);
				model.addAttribute(PLAN, plan);
				response.addCookie(new Cookie(PLAN, plan));
				model.addAttribute("message", "Preference Service Failed!");
			}
		} catch (PreferenceAccessException | PreferenceProcessException pae) {
			pae.printStackTrace();
			model.addAttribute(PLAN_SELECTED, plan);
			model.addAttribute(PLAN, plan);
			model.addAttribute("message", "Preference Service Failed!");
			List<Preference> prefs1 = getPreferencesByEmailAddress(emailCookie);
			checkUserPrefernece(model, prefs1);
			return "viewpreferences";
		}

		List<Preference> prefs1 = getPreferencesByEmailAddress(emailCookie);
		if (prefs1 != null) {
			for (Preference preference : prefs1) {
				if (preference.getType().equals(PLAN)) {
					plan = preference.getValue();
				}
			}
			model.addAttribute(PLAN, plan);
		}
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs1);
		if (Double.valueOf(plan) > 0.0) {
			if (userPreference != null && userPreference.equals(""))
				return "payment";
			else
				return "viewpreferences";
		}

		return "viewpreferences";
	}

	private SaltPreference getSaltPreference(String email) {
		SaltPreference saltPreference = new SaltPreference();
		saltPreference.setEmailAddress(email);
		saltPreference.setValue(BCrypt.gensalt(15));
		saltPreference.setFlag(true);
		saltPreference.setActive("Y");

		return saltPreference;
	}

	// ---------------------------------------------------------------------------------------
	private Properties getProperties(String fileProp) {
		Properties prop = new Properties();
		try {
			prop.load(LoanCalculatorController.class.getClassLoader().getResourceAsStream(fileProp));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
		return prop;
	}

	private void addPreference(Preference pref, Integer id, String email, String name, String value)
			throws PreferenceAccessException {
		pref.setId(id);
		pref.setEmailAddress(email);
		pref.setName(name);
		pref.setValue(value);
		pref.setFlag(true);
		pref.setActive("Y");
		preferenceService.createPreference(pref);
	}

	private void modifyPreference(Preference pref, Integer id, String email, String name, String value)
			throws PreferenceAccessException {
		pref.setId(id);
		pref.setEmailAddress(email);
		pref.setName(name);
		pref.setValue(value);
		pref.setFlag(true);
		pref.setActive("Y");
		preferenceService.modifyPreference(pref);
		System.out.println("Plan Found is " + id + ":" + value);
	}

	private void updatePreferenceEmailAddress(String newEmail, String oldEmail) {
		List<Preference> preferences;
		try {
			preferences = preferenceService.findPreference("select p from Preference p where p.emailAddress = ?",
					new Object[] { oldEmail });
			if (preferences != null) {
				for (Preference p : preferences) {
					preferenceService.removePreference(p);
					p.setEmailAddress(newEmail);
					if (p instanceof EmailReminderPreference) {
						p.setValue(newEmail);
					}
					preferenceService.createPreference(p);
				}
			}
		} catch (PreferenceAccessException ex) {
			logger.error(ex.getMessage());
		}

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/aggregateloan", method = RequestMethod.POST)
	public String loanAgg(@RequestParam("loanId") String loanId, @RequestParam("loanAmt") String loanAmt,
			@RequestParam("lender") String lender, @RequestParam("state") String state,
			@RequestParam("numOfYears") String numOfYears, @RequestParam("airVal") String airVal,
			@RequestParam("email") String email, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {

		if (email != null && email.equals(""))
			email = emailCookie;

		List<Serializable> loans = searchLoanForAggregation(loanId, loanAmt, lender, state, numOfYears, airVal, email);

		if (loans != null && loans.size() > 0) {
			java.util.List<Serializable> loanRelationship = null;
			LoanAgg loanAgg = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			AggregationSummary aggregationSummary = new AggregationSummary();

			List<Loan> aggregatedLoans = new ArrayList<Loan>();

			List<Long> loanIdFromLoanRelationship = new ArrayList<Long>();
			Long loanIdtoCheck = null;
			Loan loanToCheck = null;
			boolean loannotfound;
			loanRelationship = searchLoanRelationship(loans);

			if (loanRelationship != null && loanRelationship.size() > 0) {
				loanAgg = ((LoanRelationship) loanRelationship.get(0)).getLoanAgg();

				for (int counter = 0; counter < loanRelationship.size(); counter++) {
					loannotfound = true;
					loanIdtoCheck = ((LoanRelationship) loanRelationship.get(counter)).getLoanId();
					loanIdFromLoanRelationship.add(loanIdtoCheck);

					for (int lnctr = 0; lnctr < loans.size(); lnctr++) {
						loanToCheck = (Loan) loans.get(lnctr);
						if (loanIdtoCheck.equals(loanToCheck.getLoanId())) {
							loannotfound = false;
							aggregatedLoans.add((Loan) loans.remove(lnctr));
							break;
						}
					}

					if (loannotfound && loanIdtoCheck != null) {
						List<Serializable> searchedLoan = searchLoanForAggregation(loanIdtoCheck.toString(), null, null, null, null,
								null, null);

						if (searchedLoan != null) {
							aggregatedLoans.add((Loan) searchedLoan.get(0));
						}
					} else if (loannotfound && email != null) {
						List<Serializable> searchedLoan = searchLoanForAggregation(null, null, null, null, null, null, email);

						if (searchedLoan != null) {
							aggregatedLoans.add((Loan) searchedLoan.get(0));
						}
					}

				}

				try {
					aggregationSummary = loanWebService.aggregationSummary(aggregatedLoans, loanAgg.getStartDate());

					if (aggregationSummary != null) {
						model.addAttribute("totalAmount", aggregationSummary.getTotalAmount());
						model.addAttribute("amountPaid", aggregationSummary.getAmountPaid());
						model.addAttribute("remainingAmount", aggregationSummary.getRemainingAmount());
						model.addAttribute("remainingPercent", aggregationSummary.getRemainingPercent());
						model.addAttribute("maximumNumOfYears", aggregationSummary.getMaximumNumOfYear());
						model.addAttribute("payoff", formatter.format(aggregationSummary.getPayoffDate().getTime()));
					}
				} catch (LoanAccessException lae) {
					lae.printStackTrace();
					model.addAttribute("message", "Calculate Summary Failed!");
					List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
					addPlanFromPreferencesToModel(model, prefs);
					checkUserPrefernece(model, prefs);
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
			if (loanAgg == null || loanAgg.getEmail() == null) {
				model.addAttribute("email", emailCookie);
			}
			model.addAttribute("message", "Loan Aggregation Created!");
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "No Record Found");
			return "aggregateloan";
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "aggregateloan";
	}

	@RequestMapping(value = "/aggregateloanask")
	public String aggregateloan(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Aggregate Loan");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		ArrayList<String> prefVal = null, prefAttr = null;

		if (prefs != null) {
			prefVal = new ArrayList<String>(prefs.size());
			prefAttr = new ArrayList<String>(prefs.size());
			int prefIdx = 0;
			for (Preference pref : prefs) {
				prefAttr.add(pref.getName());
				prefVal.add(pref.getValue());
			}
			for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++)
				model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
		}
		model.addAttribute(USER_EMAIL, emailCookie);

		List<Preference> prefs1 = getPreferencesByEmailAddress(emailCookie);
		checkUserPrefernece(model, prefs1);
		return "aggregateloan";
	}

	private java.util.List<Serializable> searchLoanRelationship(List<Serializable> loan) {
		StringBuffer querySB = new StringBuffer();
		java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
		Object[] queryVals = null;
		java.util.List<Serializable> loanRelationship = null;
		java.util.List<Serializable> loanRelationshipUsingLoanAgg = null;
		for (int i = 0; i < loan.size(); i++) {
			if (i == 0 && (i == loan.size() - 1)) {
				querySB.append("?)");
				queryValList.add(Long.valueOf(((Loan) loan.get(i)).getLoanId()));
				break;
			} else if (i < loan.size() - 1) {
				querySB.append("?,");
				queryValList.add(Long.valueOf(((Loan) loan.get(i)).getLoanId()));
			} else if (i != 0 && (i == loan.size() - 1)) {
				querySB.append("?)");
				queryValList.add(Long.valueOf(((Loan) loan.get(i)).getLoanId()));
			}
		}
		queryVals = new Object[queryValList.size()];
		queryVals = queryValList.toArray(queryVals);
		try {
			loanRelationship = loanRelationshipService.findLoanRelation(
					"select ls from LoanRelationship ls where loanId IN(" + querySB.toString(), queryVals);

			StringBuffer querySBForAgg = new StringBuffer();
			Object[] queryValsForAgg = null;
			java.util.List<Object> queryValListForAgg = new java.util.ArrayList<Object>();
			querySBForAgg.append("la.loanAgg=?");
			if (loanRelationship != null && loanRelationship.size() > 0) {
				queryValListForAgg.add(((LoanRelationship) loanRelationship.get(0)).getLoanAgg());
				queryValsForAgg = new Object[queryValListForAgg.size()];
				queryValsForAgg = queryValListForAgg.toArray(queryValsForAgg);
				loanRelationshipUsingLoanAgg = loanRelationshipService.findLoanRelation(
						"select la from LoanRelationship la where " + querySBForAgg.toString(), queryValsForAgg);
			}

		} catch (LoanAccessException lae) {
			lae.printStackTrace();
		}
		return loanRelationshipUsingLoanAgg;
	}

	private java.util.List<Serializable> searchLoanForAggregation(String loanId, String loanAmt, String lender,
			String state, String numOfYears, String airVal, String email) {
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
		if (email != null && !email.equals("")) {
			if (firstVal)
				querySB.append(" and ln.email=?");
			else {
				querySB.append(" ln.email=?");
				firstVal = true;
			}
			queryValList.add(email);
			loanObject.setEmail(email);
		}

		java.util.List<Serializable> loans = null;
		if (firstVal) {
			queryVals = new Object[queryValList.size()];
			queryVals = queryValList.toArray(queryVals);
			try {
				loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
			}
		}
		return loans;
	}

	@RequestMapping(value = "/updateaggregate", method = RequestMethod.POST)
	public String updateLoanAgg(@RequestParam("loanAggId") String loanAggId, @RequestParam("name") String name,
			@RequestParam("type") String type, @RequestParam("email") String email,
			@RequestParam("startDate") String startDate, @RequestParam("term") String term,
			@RequestParam("loanIds") String loanIds, @RequestParam("loansId") String loansId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
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
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
		}
		// System.out.println(loanIds);
		if (StringUtils.isNotEmpty(loanAggId)) {
			loanAgg = new LoanAgg();
			loanAgg.setLoanAggId(Long.valueOf(loanAggId));
			loanAgg.setName(name);
			loanAgg.setType(type);
			loanAgg.setEmail(email);
			loanAgg.setStartDate(cal);
			loanAgg.setTerm(term);
		}
		StringBuffer querySB = new StringBuffer();
		Object[] queryVals = null;
		Object[] queryValsForRemove = null;
		List<Object> queryValListForRemove = new ArrayList<Object>();
		List<Object> queryValList = new ArrayList<Object>();
		AggregationSummary aggregationSummary = new AggregationSummary();
		int loanAggRemovedCounter = 0;

		if (loanAggService != null && StringUtils.isEmpty(loanAggId) && !"[]".equals(loanIds)) {
			try {
				loanAgg = new LoanAgg();
				loanAgg.setName(name);
				loanAgg.setType(type);
				loanAgg.setEmail(email);
				loanAgg.setStartDate(cal);
				loanAgg.setTerm(term);
				LoanAgg loanAgg1 = loanAggService.createLoanAgg(loanAgg);
				loanAggId = loanAgg1.getLoanAggId() != null ? loanAgg1.getLoanAggId().toString() : "";
			} catch (LoanAccessException lae) {
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				lae.printStackTrace();
				return "aggregateloan";
			}
		}
		if (loanAggService != null && loanRelationshipService != null && StringUtils.isNotEmpty(loanAggId)) {
			try {
				querySB.append("la.loanAggId=?");
				queryValList.add(Long.valueOf(loanAggId));
				queryVals = new Object[queryValList.size()];
				queryVals = queryValList.toArray(queryVals);
				loanAggDetails = loanAggService.findLoanAgg("select la from LoanAgg la where " + querySB.toString(),
						queryVals);

				if (loanAggDetails != null && loanAggDetails.size() > 0) {
					Set<LoanRelationship> loanRelationshipSet = ((LoanAgg) loanAggDetails.get(0))
							.getLoanRelationshipSet();
					if (loanRelationshipSet != null) {
						Iterator<LoanRelationship> loanRelationshipsIter = loanRelationshipSet.iterator();
						while (loanRelationshipsIter.hasNext()) {
							loanRelationshipService.removeLoanRelation(loanRelationshipsIter.next());
						}
					}
				}
				if (loanRelationshipService != null && !"[]".equals(loanIds)) {
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
					for (int counter = 0; counter < loanId.length; counter++) {
						StringBuffer querySBForLoan = new StringBuffer();
						Object[] queryValsForLoan = null;
						List<Object> queryValListForLoan = new ArrayList<Object>();
						querySBForLoan.append("la.loanId=?");
						queryValListForLoan.add(Long.valueOf(Long.parseLong(loanId[counter])));
						queryValsForLoan = new Object[queryValListForLoan.size()];
						queryValsForLoan = queryValListForLoan.toArray(queryValsForLoan);
						loans = loanService.findLoan("select la from Loan la where " + querySBForLoan.toString(),
								queryValsForLoan);
						loans1.add((Loan) loans.get(0));
					}
					System.out.println(loans1);
					try {
						aggregationSummary = loanWebService.aggregationSummary(loans1, cal);
					} catch (LoanAccessException lae) {
						lae.printStackTrace();
						model.addAttribute("message", "Calculate Loan Failed!");
						List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
						if (prefs != null) {
							for (Preference preference : prefs) {
								if (preference.getType().equals(PLAN)) {
									plan = preference.getValue();
								}
							}
							model.addAttribute(PLAN, plan);
						}
						checkUserPrefernece(model, prefs);
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
		if (loanAggService != null && !"[]".equals(loansId)) {
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
					loansForAgg = loanAggService.findLoanAgg(
							"select la from Loan la where " + querySBForLoanAgg.toString(), queryValsForLoanAgg);
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
		if (loanAgg == null) {
			model.addAttribute("email", emailCookie);
		}
		if (aggregationSummary != null) {
			model.addAttribute("totalAmount", aggregationSummary.getTotalAmount());
			model.addAttribute("amountPaid", aggregationSummary.getAmountPaid());
			model.addAttribute("remainingAmount", aggregationSummary.getRemainingAmount());
			model.addAttribute("remainingPercent", aggregationSummary.getRemainingPercent());
			model.addAttribute("maximumNumOfYears", aggregationSummary.getMaximumNumOfYear());
			model.addAttribute("payoff", formatter.format(aggregationSummary.getPayoffDate().getTime()));
			model.addAttribute("startDateForSummary", startDate);
			model.addAttribute("NoOfLoansInRelation", loans1.size());
		}
		if (loanAggDetails == null && loanAggRemovedCounter == 0) {
			model.addAttribute("message", "Please select Loan For Aggregation!");
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "aggregateloan";
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model, SessionStatus status, HttpServletResponse response, HttpServletRequest request,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = "loginAttempt", defaultValue = "0") String loginAttempt,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute(USER_EMAIL, "");
		model.addAttribute(PLAN, "");
		model.addAttribute("message", "Logout");
		status.setComplete();

		Cookie userEmailCookie = new Cookie(USER_EMAIL, "");
		userEmailCookie.setMaxAge(0);

		Cookie loginStatusCookie = new Cookie(LOGIN_STATUS, "N");
		loginStatusCookie.setMaxAge(0);

		Cookie planCookie = new Cookie(PLAN, "");
		planCookie.setMaxAge(0);

		response.addCookie(userEmailCookie);
		response.addCookie(loginStatusCookie);
		response.addCookie(planCookie);
		request.getSession().invalidate();

		return "logout";
	}

	private String getPlan(String email) {
		List<Preference> prefs = getPreferencesByEmailAddress(email);
		String plan = null;
		if (prefs != null) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					plan = preference.getValue();
				}
			}
		}
		return plan;
	}

	@RequestMapping(value = "/login")
	public String login(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = "loginAttempt", defaultValue = "0") String loginAttempt,
			@CookieValue(value = REMINDER_FREQUENCY, defaultValue = "") String reminderFrequency,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (emailCookie == null) {
			List<Preference> prefs = getPreferencesByEmailAddress(email);
			model.addAttribute("message", "Register with preferences");
			model.addAttribute(REMINDER_FREQUENCY, reminderFrequency != null ? reminderFrequency : "");
			plan = getPlan(email);
			model.addAttribute(PLAN, plan != null ? plan : "");
			model.addAttribute(PLAN_SELECTED, plan != null ? plan : "");
			checkUserPrefernece(model, prefs);
			return "viewpreferences";
		}
		if (emailCookie != null && !emailCookie.equals("")) {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			plan = getPlan(emailCookie);
			model.addAttribute(PLAN, plan != null ? plan : "");
			ArrayList<String> prefVal = null, prefAttr = null;

			if (prefs != null) {
				prefVal = new ArrayList<String>(prefs.size());
				prefAttr = new ArrayList<String>(prefs.size());
				int prefIdx = 0;
				for (Preference pref : prefs) {
					prefAttr.add(pref.getName());
					prefVal.add(pref.getValue());
				}
				for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++) {
					model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
					if (prefAttr.get(prefIdx).equals(USER_PREFERENCE) && prefVal.get(prefIdx).equals("Admin"))
						model.addAttribute(USER_PREFERENCE, prefVal.get(prefIdx));
				}
			}
		}
		if (email != null && !email.equals("") && password != null && !password.equals("")) {
			List<Preference> prefs = getPreferencesByEmailAddress(email);
			if (prefs == null || prefs.isEmpty()) {
				model.addAttribute("message", "User with given username doesn't exist in the database!");
				return "login";
			}

			if (!hasSaltPreference(prefs)) {
				model.addAttribute("message", "Please reset your password!");
				return "resetpassword";
			}
			model.addAttribute("message", "Login Form");
			model.addAttribute(USER_EMAIL, email);
			plan = getPlan(email);
			//			if(!password.equals("ignore")){
			if (!isValidPassword(password, prefs)) {
				return "login";
			}

			if (prefs != null) {
				for (Preference preference : prefs) {
					if (preference.getType().equals(PLAN))
						plan = preference.getValue();

					if (preference.getType().equals(USER_PREFERENCE))
						request.getSession()
									 .setAttribute(USER_PREFERENCE, (preference.getValue() != null ? preference.getValue() : ""));

				}
				response.addCookie(new Cookie(USER_EMAIL, email != null && !email.equals("") ? email : emailCookie));
				response.addCookie(new Cookie(LOGIN_STATUS, "Y"));
				request.getSession().setAttribute(LOGIN_STATUS, "Y");
				request.getSession().setAttribute(PLAN, plan != null ? plan : "");
				request.getSession().setAttribute(PLAN_SELECTED, plan != null ? plan : "");
				model.addAttribute(PLAN_SELECTED, plan != null ? plan : "");
				model.addAttribute(PLAN, plan != null ? plan : "");
				model.addAttribute(USER_EMAIL, email);
				if (plan != null && !plan.equals("") && plan.equals(LoanCalculatorController.PREMIUM_PLAN)) {
					model.addAttribute("message", "Aggregate Loan Report");
					plan = getPlan(email);
					model.addAttribute(PLAN, plan);
					checkUserPrefernece(model, prefs);
					return "aggregateloanreport";

				} else if (plan != null && !plan.equals("") && plan.equals(LoanCalculatorController.LITE_PLAN)) {
					model.addAttribute("message", "Amortize Loan");
					plan = getPlan(email);
					model.addAttribute(PLAN, plan);
					checkUserPrefernece(model, prefs);
					return "amortizeloan";
				} else {
					searchLoanBasedOnEmail(email, plan, model);
					logger.debug("Model on Search Loan Based on Email" + model);
					return "bankoffersandnews";
				}
			} else {
				if (emailCookie != null) {
					plan = getPlan(emailCookie);
					model.addAttribute(USER_EMAIL, emailCookie);
					model.addAttribute(PLAN, plan != null ? plan : "");
					checkUserPrefernece(model, prefs);
					model.addAttribute("message", "Login Form");
					logger.info("Selected plan :" + plan);
				}
				return "login";
			}
		} else if (!loginAttempt.equals("0")) {
			List<Preference> prefs = getPreferencesByEmailAddress(email);
			Integer nextLoginAttempt = Integer.valueOf(loginAttempt);
			nextLoginAttempt++;
			response.addCookie(new Cookie("loginAttempt", Integer.toString(nextLoginAttempt)));
			model.addAttribute("message", "Login Form");
			plan = getPlan(emailCookie);
			model.addAttribute(PLAN, plan);
			checkUserPrefernece(model, prefs);
			return "loginwithrecaptcha";
		} else {
			if (emailCookie != null) {
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				plan = getPlan(emailCookie);
				model.addAttribute(USER_EMAIL, emailCookie);
				model.addAttribute(PLAN, plan != null ? plan : "");
				checkUserPrefernece(model, prefs);
				logger.info("Selected plan :" + plan);
			}

			model.addAttribute("message", "Login Form");
			return "login";
		}
	}

	private boolean hasSaltPreference(List<Preference> prefs) {
		return prefs.stream().anyMatch(preference -> preference.getType().equals(SALT_PREFERENCE_TYPE));
	}

	@RequestMapping(value = "/loginfromlaunch", method = RequestMethod.POST)
	public String loginfromlaunch(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String plan = null, userPref = null;
		List<Preference> prefs = preferenceService.findByUserEmail(email);
		if (prefs == null || prefs.isEmpty()) {
			return "index";
		}
		if (!hasSaltPreference(prefs)) {
			model.addAttribute("message", "Please reset your password!");
			return "resetpassword";
		}
		model.addAttribute("message", "Login Form");
		model.addAttribute(USER_EMAIL, email);
		if (!password.equals("ignore") && !isValidPassword(password, prefs)) {
			model.addAttribute(PLAN, "0.0");
			return "index";
		}

		if (prefs != null && !prefs.isEmpty()) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN))
					plan = preference.getValue();

				if (preference.getType().equals(USER_PREFERENCE))
					userPref = preference.getValue();
			}
		}
		response.addCookie(new Cookie(USER_EMAIL, email));
		response.addCookie(new Cookie(LOGIN_STATUS, "Y"));
		response.addCookie(new Cookie(PLAN, plan != null ? plan : "0.0"));
		response.addCookie(new Cookie(USER_PREFERENCE, userPref != null ? userPref : ""));
		request.getSession().setAttribute(LOGIN_STATUS, "Y");
		request.getSession().setAttribute(USER_EMAIL, email);
		request.getSession().setAttribute(PLAN, plan != null ? plan : "0.0");
		request.getSession().setAttribute(PLAN_SELECTED, plan != null ? plan : "0.0");
		request.getSession().setAttribute(USER_PREFERENCE, userPref != null ? userPref : "");
		model.addAttribute(PLAN_SELECTED, plan != null ? plan : "0.0");
		model.addAttribute(PLAN, plan != null ? plan : "0.0");
		model.addAttribute(USER_EMAIL, email);
		model.addAttribute("message", "Landing Page");
		searchLoanBasedOnEmail(email, plan, model);

		return "bankoffersandnews";
	}

	private void searchLoanBasedOnEmail(@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {
		StringBuffer querySB = new StringBuffer();
		List<Object> queryValList = new ArrayList<Object>();
		Object[] queryVals = null;
		List<Serializable> loans = null;
		if (emailCookie != null && !emailCookie.equals("")) {
			querySB.append("ln.email=?");
			queryValList.add(emailCookie);
		}
		try {
			queryVals = new Object[queryValList.size()];
			queryVals = queryValList.toArray(queryVals);
			loans = loanService.findLoan("select ln from Loan ln where " + querySB.toString(), queryVals);
			logger.debug("loans" + loans);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			logger.error(lae.getMessage());
			model.addAttribute("message", "Search Loan Failed!");
		}
		if (loans != null && loans.size() > 0) {
			Loan searchLoan = (Loan) loans.get(0);
			model.addAttribute("region", searchLoan.getRegion());
			model.addAttribute("loanType", searchLoan.getLoanType());
			searchSiteOffers(searchLoan.getRegion(), searchLoan.getLoanType(), null, null, emailCookie, plan, model);
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		checkUserPrefernece(model, prefs);
	}

	@RequestMapping(value = "/resetpasswordask")
	public String resetPassword(Model model) {
		model.addAttribute("message", "Reset Password");
		return "resetpassword";
	}

	@RequestMapping(value = "/resetpassword")
	public String resetPassword(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "oldpassword", defaultValue = "") String oldpassword,
			@RequestParam(value = "newpassword", defaultValue = "") String newpassword,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (email != null && !email.equals("") && oldpassword != null && !oldpassword.equals("") && newpassword != null
				&& !newpassword.equals("")) {
			model.addAttribute(USER_EMAIL, email);
			model.addAttribute("oldpassword", oldpassword);
			model.addAttribute("newpassword", newpassword);
			List<Preference> preferences = preferenceService.findByUserEmail(email);

			if (!isValidPassword(oldpassword, preferences)) {
				model.addAttribute("message", "Invalid Credentials! Please, try again!");
				return "resetpassword";
			}
			if (!checkPasswordStrength(newpassword)) {
				model.addAttribute("message", INVALID_PASSWORD_MESSAGE);
				return "resetpassword";
			}
			boolean updateSuccessful = updatePreferencePassword(email, newpassword);
			String message = updateSuccessful ? "Reset Password Successful!" : "Reset Password Failed!";
			model.addAttribute("message", message);
		}

		return "resetpassword";
	}

	private boolean updatePreferencePassword(String email, String newPassword) {
		List<Preference> preferences;
		try {
			preferences = preferenceService.findPreference("select p from Preference p where p.emailAddress = ?",
					new Object[] {email});
			if (preferences == null) {
				return false;
			}
			Map<String, Preference> preferencesKeyValues = getPreferencesMap(preferences);
			Preference databaseHashedPassword = preferencesKeyValues.get(PASSWORD_PREFERENCE_TYPE);
			Preference saltPreference = preferencesKeyValues.get(SALT_PREFERENCE_TYPE);
			saltPreference = saltPreference == null ? getSaltPreference(email) : saltPreference;

			String hashedPassword = BCrypt.hashpw(newPassword, saltPreference.getValue());
			databaseHashedPassword.setValue(hashedPassword);

			preferenceService.modifyPreference(databaseHashedPassword);
			preferenceService.modifyPreference(saltPreference);

			return true;

		} catch (PreferenceAccessException ex) {
			logger.error(ex.getMessage());
		}

		return false;
	}

	private Map<String, Preference> getPreferencesMap(List<Preference> preferences) {
		Map<String, Preference> preferencesKeyValues = new HashMap<>();
		preferences.forEach(preference -> preferencesKeyValues.put(preference.getType(), preference));
		return preferencesKeyValues;
	}

	@RequestMapping(value = "/forgetpasswordask")
	public String forgetPassword(Model model) {
		model.addAttribute("message", "Forget Password");
		return "forgetpassword";
	}

	@RequestMapping(value = "/forgetpassword")
	public String forgetPassword(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("message", "Forget Password Form");
		if (email != null && !email.equals("") && password != null && !password.equals("") && emailCookie != null
				&& !emailCookie.equals("") && email.equals(emailCookie)) {
			model.addAttribute(USER_EMAIL, email);
			model.addAttribute("password", password);

			if (!checkPasswordStrength(password)) {
				model.addAttribute("message", "New password is too weak!");
				return "forgetpassword";
			}

			boolean hasPasswordBeenUpdated = updatePreferencePassword(email, password);
			model.addAttribute("message", hasPasswordBeenUpdated ? "Change Password Successful!" : "Change Password Failed!");
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		checkUserPrefernece(model, prefs);
		return "forgetpassword";
	}

	private boolean isValidPassword(String password, List<Preference> preferences) {
		if (preferences == null || preferences.isEmpty()) {
			return false;
		}
		Map<String, Preference> preferencesKeyValues = getPreferencesMap(preferences);
		Preference databaseHashedPassword = preferencesKeyValues.get(PASSWORD_PREFERENCE_TYPE);
		Preference salt = preferencesKeyValues.get(SALT_PREFERENCE_TYPE);
		if (salt == null) {
			return BCrypt.checkpw(password, databaseHashedPassword.getValue());
		}
		String hashedPassword = BCrypt.hashpw(password, salt.getValue());

		return databaseHashedPassword.getValue().equals(hashedPassword);
	}

	private List<Preference> getPreferencesByEmailAddress(String email) {
		List<Preference> preferences = null;
		try {
			preferences = preferenceService.findPreference("select pref from Preference pref where pref.emailAddress = ?",
					new Object[] {email});
			if (preferences != null && !preferences.isEmpty()) {
				return preferences;
			}
		} catch (PreferenceAccessException ex) {
			logger.error(ex.getMessage());
		}
		return preferences;
	}

	@RequestMapping(value = "/aggregateloanreportask", method = RequestMethod.GET)
	public String aggregateloanReport(@CookieValue(value = USER_EMAIL, defaultValue = "") String userEmail,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		if (userEmail != null && !userEmail.equals("")) {
			List<Preference> prefs = getPreferencesByEmailAddress(userEmail);
			if (prefs != null) {
				for (Preference preference : prefs) {
					if (preference.getType().equals(PLAN)) {
						plan = preference.getValue();
					}
				}
				model.addAttribute(PLAN, plan);
			}
			ArrayList<String> prefVal = null, prefAttr = null;

			if (prefs != null) {
				prefVal = new ArrayList<String>(prefs.size());
				prefAttr = new ArrayList<String>(prefs.size());
				int prefIdx = 0;
				for (Preference pref : prefs) {
					prefAttr.add(pref.getName());
					prefVal.add(pref.getValue());
				}
				for (prefIdx = 0; prefIdx < prefAttr.size(); prefIdx++)
					model.addAttribute(prefAttr.get(prefIdx), prefVal.get(prefIdx));
			}
		}
		model.addAttribute("message", "Aggregate Loan Report");
		List<Preference> prefs = getPreferencesByEmailAddress(userEmail);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, userEmail);
		checkUserPrefernece(model, prefs);
		return "aggregateloanreport";
	}

	@RequestMapping(value = "/aggregateloanreport", method = RequestMethod.POST)
	public String aggregateloanreport(@RequestParam("loanId") String loanId, @RequestParam("loanAmt") String loanAmt,
			@RequestParam("lender") String lender, @RequestParam("state") String state,
			@RequestParam("numOfYears") String numOfYears, @RequestParam("APR") String airVal,
			@RequestParam("email") String email, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException, LoanAccessException {

		if (email != null && email.equals(""))
			email = emailCookie;

		List<Serializable> loans = searchLoanForAggregation(loanId, loanAmt, lender, state, numOfYears, airVal, email);
		if (loans != null && loans.size() > 0) {
			java.util.List<Serializable> loanRelationship = null;
			java.util.List<Serializable> loanRelationshipforCount = null;
			LoanAgg loanAgg = null;
			loanRelationship = searchLoanRelationship(loans);
			if (loanRelationship != null && loanRelationship.size() > 0) {
				loanAgg = ((LoanRelationship) loanRelationship.get(0)).getLoanAgg();
				System.out.println("loan agg Id " + loanAgg.getLoanAggId());
				StringBuffer querySBForAgg = new StringBuffer();
				Object[] queryValsForAgg = null;
				java.util.List<Object> queryValListForAgg = new java.util.ArrayList<Object>();
				querySBForAgg.append("la.loanAgg=?");
				queryValListForAgg.add(((LoanRelationship) loanRelationship.get(0)).getLoanAgg());
				queryValsForAgg = new Object[queryValListForAgg.size()];
				queryValsForAgg = queryValListForAgg.toArray(queryValsForAgg);
				try {
					loanRelationshipforCount = loanRelationshipService.findLoanRelation(
							"select la from LoanRelationship la where " + querySBForAgg.toString(), queryValsForAgg);
				} catch (LoanAccessException e) {
					e.printStackTrace();
				}

				// here could be a problem
				model.addAttribute("loanAggId", loanAgg.getLoanAggId());
				model.addAttribute("loanId", loanId);
				model.addAttribute("numberOfYears", numOfYears);
				model.addAttribute("loanAmt", loanAmt);
				model.addAttribute("lender", lender);
				model.addAttribute("state", state);
				model.addAttribute("APR", airVal);
				model.addAttribute("NoOfLoansInRelation", loanRelationshipforCount.size());
				model.addAttribute("message", "Aggregate Loan Report");

			} else {
				model.addAttribute("message", "No Record Found");
				model.addAttribute("loanId", loanId);
				model.addAttribute("numberOfYears", numOfYears);
				model.addAttribute("loanAmt", loanAmt);
				model.addAttribute("lender", lender);
				model.addAttribute("state", state);
				model.addAttribute("APR", airVal);
			}
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "No Record Found");
			return "aggregateloanreport";
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "aggregateloanreport";
	}

	@RequestMapping(value = "/generateReport", method = RequestMethod.GET)
	public void generateJasperReportPDF(@RequestParam("loanAggId") String loanAggId, HttpServletResponse response,
			HttpServletRequest request) {
		generateReport(loanAggId, null, response, request);
	}

	@RequestMapping(value = "/generateReportForEmail", method = RequestMethod.GET)
	public void generateJasperReportForEmail(@RequestParam(USER_EMAIL) String userEmail, HttpServletResponse response,
			HttpServletRequest request) {
		generateReport(null, userEmail, response, request);
	}

	private void generateReport(@RequestParam("loanAggId") String loanAggId, @RequestParam(USER_EMAIL) String userEmail,
			HttpServletResponse response, HttpServletRequest request) {
		JRPdfExporter exporter = new JRPdfExporter();
		Connection connection = null;
		HashMap jasperParameter = new HashMap();
		if (loanAggId != null && !loanAggId.equals("")) {
			jasperParameter.put("loanAggId", Double.valueOf(loanAggId));
			logger.debug("loanAggId" + loanAggId);
		} else if (userEmail != null && !userEmail.equals("")) {
			jasperParameter.put("email", userEmail);
			logger.debug("email" + userEmail);
		}

		try {

			Class.forName(dataSource.getDriverClassName());
			String postgresURL = dataSource.getUrl();
			connection = DriverManager.getConnection(postgresURL, dataSource.getUsername(), dataSource.getPassword());
			logger.debug("connection is null:" + connection == null);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} catch (ClassNotFoundException ex) {
			logger.error(ex.getMessage());
		}
		if (connection != null) {
			try {
				String path = null;
				if (loanAggId != null && !loanAggId.equals("")) {
					path = context.getRealPath("/WEB-INF/jasper/report1.jrxml");
				} else if (userEmail != null && !userEmail.equals("")) {
					path = context.getRealPath("/WEB-INF/jasper/report2.jrxml");
				}
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
			} finally {
				try {
					connection.close();
				} catch (SQLException ex) {
					logger.error("Error in closing connection!" + ex.getMessage());
				}
			}
		}
	}

	public static void renderHtml(JRExporter exporter, JasperPrint print, PrintWriter writer) throws JRException {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/servlets/image?image=");
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.exportReport();
	}

	@RequestMapping(value = "/siteoffersask")
	public String siteoffers(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Site offers");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "siteoffers";
	}

	@RequestMapping(value = "/updatesiteoffersask")
	public String updateSiteoffers(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Site offers");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "updatesiteofferandnews";
	}

	@RequestMapping(value = "/siteoffers", method = RequestMethod.POST)
	public String siteoffers(@RequestParam("bankName") String bankName, @RequestParam("linkUrl") String linkUrl,
			@RequestParam("newsType") String newsType, @RequestParam("loanType") String loanType,
			@RequestParam("region") String region, @RequestParam("offerStartDate") String offerStartDate,
			@RequestParam("offerEndDate") String offerEndDate, @RequestParam("offerAmount") String offerAmount,
			@RequestParam("offerRate") String offerRate, @RequestParam("newsTitle") String newsTitle,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		boolean allVal = false;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar startDate = null;
		Calendar endDate = null;
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		try {
			startDate.setTime(formatter.parse(offerStartDate));
			endDate.setTime(formatter.parse(offerEndDate));
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
		}
		if (newsType.equals("Bank Offer")) {
			Offer offer = new Offer();
			offer.setBankName(bankName);
			offer.setLinkUrl(linkUrl);
			offer.setNewsType(newsType);
			offer.setLoanType(loanType);
			offer.setRegion(region);
			offer.setOfferStartDate(startDate);
			offer.setOfferEndDate(endDate);
			if (offerAmount != null && !offerAmount.equals("")) {
				offer.setOfferAmount(Double.valueOf(offerAmount));
			}
			if (offerRate != null && !offerRate.equals("")) {
				offer.setOfferRate(Double.valueOf(offerRate));
			}
			try {
				siteOfferService.createNewsObject(offer);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Create Site Offer Failed!");
				return "siteoffers";
			}
			model.addAttribute("siteoffers", offer);
		} else if (newsType.equals("News Site")) {
			Site site = new Site();
			site.setBankName(bankName);
			site.setLinkUrl(linkUrl);
			site.setNewsType(newsType);
			site.setLoanType(loanType);
			site.setRegion(region);
			site.setOfferStartDate(startDate);
			site.setOfferEndDate(endDate);
			if (newsTitle != null && !newsTitle.equals("")) {
				site.setNewsTitle(newsTitle);
			}
			try {
				siteOfferService.createNewsObject(site);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Create Site Offer Failed!");
				return "siteoffers";
			}
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("message", "Create Site Offers");
		return "siteoffers";
	}

	@RequestMapping(value = "/updatesiteoffers", method = RequestMethod.GET)
	public String updatesiteoffers(@RequestParam("offerId") String offerId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		Offer newsObject = new Offer();
		int total = 0;
		StringBuffer querySB = new StringBuffer();
		java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
		Object[] queryVals = null;
		boolean firstVal = false;
		querySB.append("ln.offerId=?");
		queryValList.add(Long.valueOf(offerId));

		queryVals = new Object[queryValList.size()];
		queryVals = queryValList.toArray(queryVals);
		List<NewsObject> newsObjects = null;
		try {
			newsObjects = siteOfferService.findNewsObject("select ln from NewsObject ln where  " + querySB.toString(),
					queryVals);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search Site Offer Failed!");
		}
		if (newsObjects != null && newsObjects.size() != 0) {
			model.addAttribute("newsObject", newsObjects.get(0));
			SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
			model.addAttribute("offerStartDate", format1.format(newsObjects.get(0).getOfferStartDate().getTime()));
			model.addAttribute("offerEndDate", format1.format(newsObjects.get(0).getOfferEndDate().getTime()));
			model.addAttribute(USER_EMAIL, emailCookie);
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			checkUserPrefernece(model, prefs);
			return "searchsiteoffers";
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			model.addAttribute(USER_EMAIL, emailCookie);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Search Site OFfers: " + " Site Parameters Not Selected!");
			return "searchsiteoffers";
		}
	}

	@RequestMapping(value = "/updatesiteoffrevalues", method = RequestMethod.POST)
	public String updatesiteoffrevalues(@RequestParam("offerId") String offerId,
			@RequestParam("bankName") String bankName, @RequestParam("linkUrl") String linkUrl,
			@RequestParam("newsType") String newsType, @RequestParam("loanType") String loanType,
			@RequestParam("region") String region, @RequestParam("offerStartDate") String offerStartDate,
			@RequestParam("offerEndDate") String offerEndDate, @RequestParam("offerAmount") String offerAmount,
			@RequestParam("offerRate") String offerRate, @RequestParam("newsTitle") String newsTitle,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		NewsObject newsObject = null;
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		try {
			startDate.setTime(formatter.parse(offerStartDate));
			endDate.setTime(formatter.parse(offerEndDate));
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
		}
		if (newsType.equals("Bank Offer")) {
			Offer offer = new Offer();
			offer.setOfferId(Long.valueOf(offerId));
			offer.setBankName(bankName);
			offer.setLinkUrl(linkUrl);
			offer.setNewsType(newsType);
			offer.setLoanType(loanType);
			offer.setRegion(region);
			offer.setOfferStartDate(startDate);
			offer.setOfferEndDate(endDate);
			if (offerAmount != null && !offerAmount.equals("")) {
				offer.setOfferAmount(Double.valueOf(offerAmount));
			}
			if (offerRate != null && !offerRate.equals("")) {
				offer.setOfferRate(Double.valueOf(offerRate));
			}
			try {
				newsObject = siteOfferService.modifyNewsObject(offer);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Update Site Offer Failed!");
				return "searchsiteoffers";
			}
			model.addAttribute("siteoffers", offer);
		} else if (newsType.equals("News Site")) {
			Site site = new Site();
			site.setOfferId(Long.valueOf(offerId));
			site.setBankName(bankName);
			site.setLinkUrl(linkUrl);
			site.setNewsType(newsType);
			site.setLoanType(loanType);
			site.setRegion(region);
			site.setOfferStartDate(startDate);
			site.setOfferEndDate(endDate);
			if (newsTitle != null && !newsTitle.equals("")) {
				site.setNewsTitle(newsTitle);
			}
			try {
				newsObject = siteOfferService.modifyNewsObject(site);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				addPlanFromPreferencesToModel(model, prefs);
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Update Site Offer Failed!");
				return "searchsiteoffers";
			}
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("newsObject", newsObject);
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		model.addAttribute("offerStartDate", format1.format(newsObject.getOfferStartDate().getTime()));
		model.addAttribute("offerEndDate", format1.format(newsObject.getOfferEndDate().getTime()));
		model.addAttribute("message", "Update Site Offers");
		return "searchsiteoffers";
	}

	@RequestMapping(value = "/deletesiteoffer", method = RequestMethod.DELETE)
	public String deletesiteoffer(@RequestParam("offerId") String offerId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		List<NewsObject> newsObject = null;
		StringBuffer querySB = new StringBuffer();
		java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
		Object[] queryVals = null;
		querySB.append("ln.offerId=?");
		queryValList.add(Long.valueOf(offerId));
		queryVals = new Object[queryValList.size()];
		queryVals = queryValList.toArray(queryVals);

		try {
			newsObject = siteOfferService.findNewsObject("select ln from NewsObject ln where " + querySB.toString(),
					queryVals);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search Site Offer Failed!");
		}
		try {
			if (newsObject != null && newsObject.size() != 0) {
				siteOfferService.removeNewsObject(newsObject.get(0));
			}
		} catch (LoanAccessException e) {
			e.printStackTrace();
		}
		model.addAttribute(USER_EMAIL, emailCookie);
		model.addAttribute(PLAN, plan);
		return "searchsiteoffers";
	}

	@RequestMapping(value = "/siteoffers", method = RequestMethod.GET)
	public String siteoffers(@RequestParam("region") String region, @RequestParam("loanType") String loanType,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		searchSiteOffers(region, loanType, null, null, emailCookie, plan, model);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "bankoffersandnews";
	}

	private void searchSiteOffers(@RequestParam("region") String region, @RequestParam("loanType") String loanType,
			@RequestParam("offerStartDate") String offerStartDate, @RequestParam("offerEndDate") String offerEndDate,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model) {

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		checkUserPrefernece(model, prefs);
		List<NewsObject> newsObjects = new ArrayList<NewsObject>();
		List<NewsObject> newsarticle = new ArrayList<NewsObject>();
		List<NewsObject> siteoffers = new ArrayList<NewsObject>();
		StringBuffer querySB = new StringBuffer();
		Calendar today = Calendar.getInstance();
		List<Object> queryValList = new ArrayList<Object>();
		Object[] queryVals = null;
		boolean firstVal = false;
		if (region != null && !region.equals("")) {
			querySB.append("n.region=?");
			firstVal = true;
			queryValList.add(region);
		}

		if (loanType != null && !loanType.equals("")) {
			if (firstVal)
				querySB.append(" and n.loanType=?");
			else {
				querySB.append(" n.loanType=?");
				firstVal = true;
			}
			queryValList.add(loanType);
		}
		if (offerEndDate != null || offerStartDate != null) {
			if (offerStartDate != null && !offerStartDate.equals("")) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Calendar cal = null;
				cal = Calendar.getInstance();
				try {
					cal.setTime(formatter.parse(offerStartDate));
				} catch (ParseException parseEx) {
					parseEx.printStackTrace();
				}
				if (firstVal)
					querySB.append(" and n.offerStartDate > ?");
				else {
					querySB.append(" n.offerStartDate > ?");
					firstVal = true;
				}
				queryValList.add(cal);
			}

			if (offerEndDate != null && !offerEndDate.equals("")) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Calendar cal = null;
				cal = Calendar.getInstance();
				try {
					cal.setTime(formatter.parse(offerEndDate));
				} catch (ParseException parseEx) {
					parseEx.printStackTrace();
				}
				if (firstVal)
					querySB.append(" and n.offerEndDate < ?");
				else {
					querySB.append(" n.offerEndDate < ?");
					firstVal = true;
				}
				queryValList.add(cal);
			}
		}

		if (firstVal) {
			queryVals = new Object[queryValList.size()];
			queryVals = queryValList.toArray(queryVals);
			try {
				newsObjects = siteOfferService.findNewsObject("select n from NewsObject n where " + querySB.toString(),
						queryVals);

				if (newsObjects != null) {
					for (NewsObject n : newsObjects) {
						if (n.getNewsType().equals("Bank Offer")) {
							if (offerEndDate == null || offerStartDate == null) {
								if (n.getOfferEndDate().after(today) || n.getOfferEndDate().equals(today)) {
									siteoffers.add(n);
								}
							} else {
								siteoffers.add(n);
							}
						} else if (n.getNewsType().equals("News Site")) {
							if (offerEndDate == null || offerStartDate == null) {
								if (n.getOfferEndDate().after(today) || n.getOfferEndDate().equals(today)) {
									newsarticle.add(n);
								}
							} else {
								newsarticle.add(n);
							}
						}
					}
				}
				logger.debug("siteOffers" + siteoffers);
				logger.debug("newsArticles" + newsarticle);
			} catch (LoanAccessException ex) {
				ex.printStackTrace();
				logger.error(ex);
				model.addAttribute("message", "Search offers Failed!");
			}
			model.addAttribute("region", region);
			model.addAttribute("loanType", loanType);
			model.addAttribute("siteoffers", siteoffers);
			model.addAttribute("newsarticle", newsarticle);
			if (offerEndDate != null) {
				model.addAttribute("offerEndDate", offerEndDate);
			}

			if (offerStartDate != null) {
				model.addAttribute("offerStartDate", offerStartDate);
			}
		}
	}

	@RequestMapping(value = "/searchSiteoffersask")
	public String searchSiteoffers(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("message", "Site offers");
		return "siteofferandnews";
	}

	@RequestMapping(value = "/searchsiteoffers", method = RequestMethod.GET)
	public String searchSiteoffers(@RequestParam("region") String region, @RequestParam("loanType") String loanType,
			@RequestParam("offerStartDate") String offerStartDate, @RequestParam("offerEndDate") String offerEndDate,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		model.addAttribute(USER_EMAIL, emailCookie);
		model.addAttribute(PLAN, plan);
		searchSiteOffers(region, loanType, offerStartDate, offerEndDate, emailCookie, plan, model);
		return "siteofferandnews";
	}

	@RequestMapping(value = "/updatesearchsiteoffers", method = RequestMethod.GET)
	public String updatesearchsiteoffers(@RequestParam("region") String region, @RequestParam("loanType") String loanType,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		model.addAttribute(USER_EMAIL, emailCookie);
		model.addAttribute(PLAN, plan);

		searchSiteOffers(region, loanType, null, null, emailCookie, plan, model);
		return "updatesiteofferandnews";
	}

	@RequestMapping(value = "/payment")
	public String payment(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		return "payment";
	}

	@RequestMapping(value = "/vieweditpayment", method = RequestMethod.POST)
	public ModelAndView vieweditpayment(@RequestParam(name = "paymentType", defaultValue = "") String paymentType,
			@RequestParam(name = "paypalAcctNum", defaultValue = "") String paypalAcctNum,
			@RequestParam(name = "paypalEmailAddress", defaultValue = "") String paypalEmailAddress,
			@RequestParam(name = "paymentStartDate", defaultValue = "") String paymentStartDate,
			@RequestParam(name = "paymentEndDate", defaultValue = "") String paymentEndDate,
			@RequestParam(name = "paymentAmount", defaultValue = "") String paymentAmount,
			@RequestParam(name = "paymentFrequency", defaultValue = "") String paymentFrequency,
			@RequestParam(name = "balanceAmount", defaultValue = "") String balanceAmount,
			@RequestParam(name = "payPalAuthPersonName", defaultValue = "") String payPalAuthPersonName,
			@RequestParam(name = "payPalPassword", defaultValue = "") String payPalPassword, HttpServletRequest request,
			HttpServletResponse response, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan1, Model model) throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// try {
		// logger.info("Create Payment " + paymentType);
		//
		// if ("PayPal".equals(paymentType)) {
		//
		// // Configure Paypal environemnt
		// if (paypalApicontext == null)
		// paypalApicontext = new APIContext(clientId, clientSecret, "sandbox");
		//
		// // Build Plan object
		// Plan plan = new Plan();
		// plan.setName("Ayushi Loan calculator");
		// plan.setDescription("LoanInsight Online");
		// plan.setType("INFINITE");
		//
		// // Payment_definitions
		// PaymentDefinition paymentDefinition = new PaymentDefinition();
		// paymentDefinition.setName("Regular Payments");
		// paymentDefinition.setType("REGULAR");
		//
		// // Set the payment frequency based on user selection
		// if ("NoRemind".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("NONE");
		// paymentDefinition.setFrequencyInterval("1");
		// } else if ("Weekly".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("WEEK");
		// paymentDefinition.setFrequencyInterval("1");
		// } else if ("Monthly".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("MONTH");
		// paymentDefinition.setFrequencyInterval("1");
		// } else if ("Quarterly".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("MONTH");
		// // It specifies that payment will occur on ever 3 months
		// paymentDefinition.setFrequencyInterval("3");
		// } else if ("Semi-Annually".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("MONTH");
		// // It specifies that payment will occur on ever 6 months
		// paymentDefinition.setFrequencyInterval("6");
		// } else if ("Annually".equals(paymentFrequency)) {
		// paymentDefinition.setFrequency("YEAR");
		// paymentDefinition.setFrequencyInterval("1");
		// }
		//
		// /**
		// * which specifies the total number of billing cycles in the
		// * regular payment period. If you either do not specify a value
		// * or specify the value 0, the payments continue until PayPal
		// * (or the buyer) cancels or suspends the profile
		// */
		// paymentDefinition.setCycles("0");
		//
		// // Currency
		// Currency currency = new Currency();
		// currency.setCurrency("USD");
		// currency.setValue(paymentAmount);
		// paymentDefinition.setAmount(currency);
		//
		// // Charge_models
		// ChargeModels chargeModels = new
		// com.paypal.api.payments.ChargeModels();
		// chargeModels.setType("SHIPPING");
		// chargeModels.setAmount(currency);
		// List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
		// chargeModelsList.add(chargeModels);
		// paymentDefinition.setChargeModels(chargeModelsList);
		//
		// // Payment_definition
		// List<PaymentDefinition> paymentDefinitionList = new
		// ArrayList<PaymentDefinition>();
		// paymentDefinitionList.add(paymentDefinition);
		// plan.setPaymentDefinitions(paymentDefinitionList);
		//
		// // Merchant_preferences
		// MerchantPreferences merchantPreferences = new MerchantPreferences();
		// merchantPreferences.setSetupFee(currency);
		// merchantPreferences.setCancelUrl("http://ayushiloancalculatorapp.herokuapp.com/cancelPayPalPayment");
		// merchantPreferences.setReturnUrl("http://ayushiloancalculatorapp.herokuapp.com/confirmPayPalPayment");
		// merchantPreferences.setMaxFailAttempts("0");
		// merchantPreferences.setAutoBillAmount("YES");
		// merchantPreferences.setInitialFailAmountAction("CONTINUE");
		// plan.setMerchantPreferences(merchantPreferences);
		//
		// // Create payment
		// Plan createdPlan = plan.create(paypalApicontext);
		// System.out.println("Created plan with id = " + createdPlan.getId());
		// System.out.println("Plan state = " + createdPlan.getState());
		//
		// // Set up plan activate PATCH request
		// List<Patch> patchRequestList = new ArrayList<Patch>();
		// Map<String, String> value = new HashMap<String, String>();
		// value.put("state", "ACTIVE");
		//
		// // Create update object to activate plan
		// Patch patch = new Patch();
		// patch.setPath("/");
		// patch.setValue(value);
		// patch.setOp("replace");
		// patchRequestList.add(patch);
		//
		// // Activate plan
		// createdPlan.update(paypalApicontext, patchRequestList);
		// System.out.println("Plan ID = " + createdPlan.getId());
		//
		// // Create billing agreement
		//
		// // Create new agreement
		// Agreement agreement = new Agreement();
		// agreement.setName("Base Agreement");
		// agreement.setDescription("Basic Agreement");
		//
		// SimpleDateFormat payPalFormat = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		// Date startDate = sdf.parse(paymentStartDate);
		// startDate.setHours(1);
		// startDate.setMinutes(20);
		// startDate.setSeconds(30);
		// agreement.setStartDate(payPalFormat.format(startDate));
		//
		// // Set plan ID
		// Plan agreementPlan = new Plan();
		// agreementPlan.setId(createdPlan.getId());
		// agreement.setPlan(agreementPlan);
		//
		// // Add payer details
		// Payer payer = new Payer();
		// payer.setPaymentMethod("paypal");
		// agreement.setPayer(payer);
		//
		// // Set shipping address information
		// ShippingAddress shipping = new ShippingAddress();
		// shipping.setLine1("111 First Street");
		// shipping.setCity("Saratoga");
		// shipping.setState("CA");
		// shipping.setPostalCode("95070");
		// shipping.setCountryCode("US");
		// agreement.setShippingAddress(shipping);
		//
		// // Create agreement
		// try {
		// agreement = agreement.create(paypalApicontext);
		//
		// for (Links links : agreement.getLinks()) {
		// System.out.println(links.getHref());
		// if ("approval_url".equals(links.getRel())) {
		// URL url = new URL(links.getHref());
		//
		// // Before redirecting save details to the database
		// PayPalPayment payPalPayment = new PayPalPayment();
		// payPalPayment.setPaymentId(System.currentTimeMillis());
		// payPalPayment.setPayPalAccountNumber(paypalAcctNum);
		// payPalPayment.setPayPalAuthPersonName(payPalAuthPersonName);
		// payPalPayment.setPayPalEmailAddress(paypalEmailAddress);
		// payPalPayment.setBalanceAmount(Double.valueOf(balanceAmount));
		// payPalPayment.setPayPalPassword(payPalPassword);
		// payPalPayment.setPaymentAmount(Double.valueOf(paymentAmount));
		// Calendar pmtStartDate = Calendar.getInstance();
		// pmtStartDate.setTime(sdf.parse(paymentStartDate));
		// payPalPayment.setPaymentStartDate(pmtStartDate);
		// Calendar pmtEndDate = Calendar.getInstance();
		// pmtEndDate.setTime(sdf.parse(paymentStartDate));
		// payPalPayment.setPaymentEndDate(pmtEndDate);
		// payPalPayment.setPaymentFrequency(paymentFrequency);
		// payPalPayment.setPaymentType(paymentType);
		// // Save the PayPal plan id
		// payPalPayment.setPayPalPlanId(plan.getId());
		// paymentService.createPayment(payPalPayment);
		// System.out.println("Plan ID " + plan.getId());
		// // REDIRECT USER TO url
		// return new ModelAndView("redirect:" + links.getHref());
		//
		// // break;
		// }
		// }
		// } catch (PayPalRESTException e) {
		// System.err.println(e.getDetails());
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		//
		// model.addAttribute("message", "Create Payment Service Success!");
		//
		// } else {
		//
		// }
		//
		// } catch (PayPalRESTException | PaymentProcessException |
		// ParseException pae) {
		// pae.printStackTrace();
		// model.addAttribute("message", "Payment Service Failed!");
		// return new ModelAndView("payment");
		// }

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		if (prefs != null) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					plan1 = preference.getValue();
				}
			}
			model.addAttribute(PLAN, plan1);
		}
		model.addAttribute(USER_EMAIL, emailCookie);
		String papelPaymentUrl = paymentService.createPayment(emailCookie, Double.valueOf(plan1));
		if (!papelPaymentUrl.isEmpty()) {
			return new ModelAndView("redirect:" + papelPaymentUrl);
		}
		return new ModelAndView("payment");
	}

	@RequestMapping(value = "/confirmPaypalPayment", method = GET)
	public String confirmPaypalPayment(@RequestParam(name = "paymentId", defaultValue = "") String paymentId,
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "PayerID", defaultValue = "") String payerId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		System.out.println("paymentId = " + paymentId);
		System.out.println("payerId = " + payerId);
		System.out.println("token = " + token);

		// token obtained when creating the agreement (following redirect)
		// Agreement agreement = new Agreement();
		// agreement.setToken(token);
		//
		// try {
		// Agreement activeAgreement = agreement.execute(paypalApicontext,
		// agreement.getToken());
		// System.out.println("Plan ID " + activeAgreement.getPlan().getId());
		// System.out.println("Agreement created with ID " +
		// activeAgreement.getId());
		//
		// model.addAttribute("message", "Create Payment Service Success!");
		//
		// } catch (PayPalRESTException e) {
		// System.err.println(e.getDetails());
		// model.addAttribute("message", "Payment Service Failed!");
		// }
		System.out.println("PayPal Return Called");
		String message = paymentService.completePayment(new PayPalModel(paymentId, payerId));
		System.out.println(message);
		model.addAttribute("message", message);
		return "payment_message";

	}

	@RequestMapping(value = "/cancelPayPalPayment", method = GET)
	public String cancelPaypalPayment(@RequestParam(name = "paymentId", defaultValue = "") String paymentId,
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "PayerID", defaultValue = "") String payerId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, HttpServletResponse response, Model model) {
		System.out.println("paymentId = " + paymentId);
		System.out.println("payerId = " + payerId);
		System.out.println("token = " + token);
		String message =
				"Payment is Fail " + "paymentId = " + paymentId + "payerId = " + payerId + "token = " + token + " for " + emailCookie + " plan " + plan;

		System.out.println(message);
		model.addAttribute("message", message);
		List<Preference> prefs = null;
		prefs = getPreferencesByEmailAddress(emailCookie);

		if (prefs != null) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					Preference planPref = preference;
					try {
						Integer prefId = new Integer(planPref.getId());
						String freePlan = "0.0";
						modifyPreference(planPref, prefId, emailCookie, PLAN, freePlan);
						model.addAttribute(PLAN, freePlan);
						response.addCookie(new Cookie(PLAN, freePlan));
						model.addAttribute("message", message + " and Plan Changed to " + freePlan);
					} catch (PreferenceAccessException pae) {
						pae.printStackTrace();
						model.addAttribute("message", "Plan Not Changed!");
					}
				}
			}
		}
		return "payment_cancle";
	}

	@RequestMapping(value = "/externalLinksask")
	public String externalLink(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Equity External Calculator");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "externalLinks";
	}

	@RequestMapping(value = "/externalLinks", method = RequestMethod.POST)
	public String externalCalculator(@RequestParam("linkUrl") String linkUrl, @RequestParam("loanType") String loanType,
			@RequestParam("region") String region,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		EquityExternalCalculator equityExternalCalculator = new EquityExternalCalculator();
		equityExternalCalculator.setLinkUrl(linkUrl);
		equityExternalCalculator.setLoanType(loanType);
		equityExternalCalculator.setRegion(region);
		try {
			equityExternalCalculatorService.createEquityExternalCalculator(equityExternalCalculator);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Create External Link Failed!");
			return "externalLinks";
		}
		model.addAttribute("externalLinks", equityExternalCalculator);
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("message", "Create External Calculator ");
		return "externalLinks";
	}

	@RequestMapping(value = "/updateExternalLinksask")
	public String updateExternalLinksask(Model model,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Site offers");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "updateExternalLinks";
	}

	@RequestMapping(value = "/updateSearchExternalLinks", method = RequestMethod.GET)
	public String updateSearchExternalLinks(@RequestParam("region") String region,
			@RequestParam("loanType") String loanType, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		model.addAttribute(USER_EMAIL, emailCookie);
		model.addAttribute(PLAN, plan);
		List<EquityExternalCalculator> equityExternalCalculators = new ArrayList<EquityExternalCalculator>();
		StringBuffer querySB = new StringBuffer();
		List<Object> queryValList = new ArrayList<Object>();
		Object[] queryVals = null;
		boolean firstVal = false;
		if (region != null && !region.equals("")) {
			querySB.append("n.region=?");
			firstVal = true;
			queryValList.add(region);
		}
		if (loanType != null && !loanType.equals("")) {
			if (firstVal)
				querySB.append(" and n.loanType=?");
			else {
				querySB.append(" n.loanType=?");
				firstVal = true;
			}
			queryValList.add(loanType);
		}

		if (firstVal) {
			queryVals = new Object[queryValList.size()];
			queryVals = queryValList.toArray(queryVals);
			try {
				equityExternalCalculators = equityExternalCalculatorService.findEquityExternalCalculator(
						"select n from EquityExternalCalculator n where " + querySB.toString(), queryVals);
				if (equityExternalCalculators != null) {
					model.addAttribute("equityExternalCalculators", equityExternalCalculators);
				}
			} catch (LoanAccessException ex) {
				ex.printStackTrace();
				logger.error(ex);
				model.addAttribute("message", "Search equity external link Failed!");
			}

		}
		model.addAttribute("region", region);
		model.addAttribute("loanType", loanType);
		return "updateExternalLinks";
	}

	@RequestMapping(value = "/updateExternalLinks", method = RequestMethod.GET)
	public String updateExternalLinks(@RequestParam("externalCalculatorId") String externalCalculatorId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {

		StringBuffer querySB = new StringBuffer();
		java.util.List<Object> queryValList = new java.util.ArrayList<Object>();
		Object[] queryVals = null;
		boolean firstVal = false;
		querySB.append("ln.externalCalculatorId=?");
		queryValList.add(Long.valueOf(externalCalculatorId));

		queryVals = new Object[queryValList.size()];
		queryVals = queryValList.toArray(queryVals);
		List<EquityExternalCalculator> equityExternalCalculators = new ArrayList<>();
		try {
			equityExternalCalculators = equityExternalCalculatorService.findEquityExternalCalculator(
					"select ln from EquityExternalCalculator ln where  " + querySB.toString(), queryVals);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search Equity External Calculator Failed!");
		}
		if (equityExternalCalculators != null && equityExternalCalculators.size() != 0) {
			model.addAttribute("equityExternalCalculator", equityExternalCalculators.get(0));
			model.addAttribute("region", equityExternalCalculators.get(0).getRegion());
			model.addAttribute("loanType", equityExternalCalculators.get(0).getLoanType());
			model.addAttribute("linkUrl", equityExternalCalculators.get(0).getLinkUrl());
			model.addAttribute("externalCalculatorId", equityExternalCalculators.get(0).getExternalCalculatorId());
			model.addAttribute(USER_EMAIL, emailCookie);
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			checkUserPrefernece(model, prefs);
			return "searchExternalLinks";
		} else {
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			addPlanFromPreferencesToModel(model, prefs);
			model.addAttribute(USER_EMAIL, emailCookie);
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Search External Links");
			return "searchExternalLinks";
		}
	}

	@RequestMapping(value = "/updateExternalCalculatorValues", method = RequestMethod.POST)
	public String updateExternalCalculatorValues(@RequestParam("externalCalculatorId") String externalCalculatorId,
			@RequestParam("linkUrl") String linkUrl, @RequestParam("loanType") String loanType,
			@RequestParam("region") String region, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan, Model model, HttpServletRequest request)
			throws ParseException {
		EquityExternalCalculator equityExternalCalculator = new EquityExternalCalculator();

		equityExternalCalculator.setExternalCalculatorId(Long.valueOf(externalCalculatorId));
		equityExternalCalculator.setLinkUrl(linkUrl);
		equityExternalCalculator.setLoanType(loanType);
		equityExternalCalculator.setRegion(region);
		EquityExternalCalculator equityExternalCalculator1 = new EquityExternalCalculator();
		try {
			equityExternalCalculator1 = equityExternalCalculatorService.modifyEquityExternalCalculator(equityExternalCalculator);
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
			if (prefs != null) {
				for (Preference preference : prefs) {
					if (preference.getType().equals(PLAN)) {
						plan = preference.getValue();
					}
				}
				model.addAttribute(PLAN, plan);
			}
			checkUserPrefernece(model, prefs);
			model.addAttribute("message", "Update Equity External Calculator failed!");
		}
		model.addAttribute("equityExternalCalculator", equityExternalCalculator1);

		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanFromPreferencesToModel(model, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		model.addAttribute("message", "Update Equity External Calculator");
		return "searchExternalLinks";
	}

	@RequestMapping(value = "/calculateEquityask")
	public String calculateEquityask(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Equity Calculation");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "equityCalculation";
	}

	@RequestMapping(value = "/calculateEquity")
	public String calculateEquity(Model model, @RequestParam("loanId") String loanId,
			@RequestParam("loanStartDate") String loanStartDate, @RequestParam("valuationDate") String valuationDate,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) throws ParseException {
		model.addAttribute("message", "Equity Calculation");
		List<Serializable> loans = new ArrayList<>();
		model.addAttribute(PLAN, plan);
		try {
			loans = loanService.findLoan("select ln from Loan ln where ln.loanId = ?", new Object[] {new Long(loanId)});
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search loan for equity Failed!");
		}
		if (loans != null && loans.size() != 0) {
			Loan searchLoan = (Loan) loans.get(0);
			model.addAttribute("loanId", searchLoan.getLoanId());
			model.addAttribute("loanType", searchLoan.getLoanType());
			model.addAttribute("assetValue", searchLoan.getAmount());
			model.addAttribute("valuationDate", valuationDate);
			AmortizedLoan amortizeLoan = AmortizedLoan.getAmortizedLoan(loanStartDate, searchLoan);
			LoanApp loanApp = new LoanApp(amortizeLoan);
			amortizeLoan.setLoanApp(loanApp);
			String todaysDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			Calendar first = Calendar.getInstance();
			Calendar last = Calendar.getInstance();
			Calendar valuation = Calendar.getInstance();
			first.setTime(java.text.SimpleDateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.US).parse(loanStartDate));
			last.setTime(java.text.SimpleDateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.US).parse(todaysDate));
			valuation.setTime(java.text.SimpleDateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.US).parse(valuationDate));
			List<LoanEntry> loanEntries = amortizeLoan.getLoanEntries();
			int loanSize = loanEntries.size() - 1;
			model.addAttribute("loanBalanceAmount", loanEntries.get(loanSize).getLoanAmount());
			int diff = last.get(Calendar.YEAR) - first.get(Calendar.YEAR);
			if (first.get(Calendar.MONTH) > last.get(Calendar.MONTH) || (first.get(Calendar.MONTH) == last.get(Calendar.MONTH)
					&& first.get(Calendar.DATE) > last.get(Calendar.DATE))) {
				diff--;
			}
			model.addAttribute("remainingYear", searchLoan.getNumberOfYears() - diff);
			model.addAttribute("equityValue", searchLoan.getAmount() - loanEntries.get(loanSize).getLoanAmount());
			List<EquityExternalCalculator> equityExternalCalculators = new ArrayList<EquityExternalCalculator>();
			Equity equity = new Equity();

			equity.setAssetValue(searchLoan.getAmount());
			equity.setRemainingYear(searchLoan.getNumberOfYears() - diff);
			equity.setEmail(emailCookie);
			equity.setEquityValue(searchLoan.getAmount() - loanEntries.get(loanSize).getLoanAmount());
			equity.setLoanBalanceAmount(loanEntries.get(loanSize).getLoanAmount());
			equity.setLoanId(searchLoan.getLoanId());
			equity.setValuationDate(valuation);
			equity.setLoanType(searchLoan.getLoanType());

			Equity equity1 = new Equity();
			try {
				equity1 = equityService.createEquity(equity);
				sendEquityEmail(emailCookie, equity1, valuationDate);
			} catch (LoanAccessException lae) {
				lae.printStackTrace();
				List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
				if (prefs != null) {
					for (Preference preference : prefs) {
						if (preference.getType().equals(PLAN)) {
							plan = preference.getValue();
						}
					}
					model.addAttribute(PLAN, plan);
				}
				checkUserPrefernece(model, prefs);
				model.addAttribute("message", "Create Equity failed!");
			}
			StringBuffer querySB = new StringBuffer();
			List<Object> queryValList = new ArrayList<Object>();
			Object[] queryVals = null;
			boolean firstVal = false;
			if (searchLoan.getRegion() != null && !searchLoan.getRegion().equals("")) {
				querySB.append("n.region=?");
				firstVal = true;
				queryValList.add(searchLoan.getRegion());
			}
			if (searchLoan.getLoanType() != null && !searchLoan.getLoanType().equals("")) {
				if (firstVal)
					querySB.append(" and n.loanType=?");
				else {
					querySB.append(" n.loanType=?");
					firstVal = true;
				}
				queryValList.add(searchLoan.getLoanType());
			}

			if (firstVal) {
				queryVals = new Object[queryValList.size()];
				queryVals = queryValList.toArray(queryVals);
				try {
					equityExternalCalculators = equityExternalCalculatorService.findEquityExternalCalculator(
							"select n from EquityExternalCalculator n where " + querySB.toString(), queryVals);
					if (equityExternalCalculators != null && equityExternalCalculators.size() != 0) {
						model.addAttribute("linkUrl", equityExternalCalculators.get(0).getLinkUrl());
					}
				} catch (LoanAccessException ex) {
					ex.printStackTrace();
					logger.error(ex);
					model.addAttribute("message", "Search offers Failed!");
				}

			}
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "searchedEquity";
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	public int sendEquityEmail(String email, Equity equity, String valuationDate) {
		Properties prop = getProperties("spring/email.properties");

		if (email != null && !email.isEmpty()) {
			String message = null;
			String subject = null;

			if (equity != null) {
				if (valuationDate != null) {
//					message = loanEmailGeneratorService.buildEquityMessage(equity, valuationDate);
				} else {
					String todaysDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
//					message = loanEmailGeneratorService.buildEquityMessage(equity, todaysDate);
				}
				subject = prop.getProperty("email.subject") + equity.getLoanId();
			}

			if (message != null && subject != null) {
				try {
//          loanEmailGeneratorService.sendMail(email, subject, message);
					List<Preference> prefs = getPreferencesByEmailAddress(email);
					int loanidPrefId = -1;
					if (prefs != null) {
						for (Preference pref : prefs) {
							if (pref.getName().equals("LoanId")) {
								loanidPrefId = pref.getId();
								break;
							}
						}
						if (loanidPrefId == -1)
							loanidPrefId = prefs.size() + 1;
					}
					addPreference(new LoanIdPreference(), loanidPrefId, email, "LoanId", equity.getEmail());
				} /*catch (EmailServiceException ex) {
					logger.error(ex.getMessage());
				}*/ catch (PreferenceAccessException ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		return 1;
	}

	@RequestMapping(value = "/equityHistoryask")
	public String equityHistoryask(Model model, @CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) {
		model.addAttribute("message", "Equity Calculation");
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "equityHistory";
	}

	@RequestMapping(value = "/equityHistory")
	public String calculateEquity(Model model, @RequestParam("loanId") String loanId,
			@CookieValue(value = USER_EMAIL, defaultValue = "") String emailCookie,
			@CookieValue(value = PLAN, defaultValue = "") String plan) throws ParseException {
		model.addAttribute("message", "Equity History");
		List<Serializable> equity = new ArrayList<>();
		model.addAttribute(PLAN, plan);
		try {
			equity = equityService.findEquity("select eq from Equity eq where eq.loanId = ?",
					new Object[] {new Long(loanId)});
		} catch (LoanAccessException lae) {
			lae.printStackTrace();
			model.addAttribute("message", "Search loan for equity Failed!");
		}
		if (equity != null && equity.size() > 0) {
			List<Map<String, Object>> entries = new ArrayList<>();
			for (int i = 0; i < equity.size(); i++) {
				Equity equity1 = (Equity) equity.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				DecimalFormat df = new DecimalFormat("#.##");
				map.put("equityValue", Double.valueOf(df.format(equity1.getEquityValue())));
				SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
				String formatted = format1.format(equity1.getValuationDate().getTime());
				map.put("ValuationDate", formatted);
				Double percent = (equity1.getEquityValue() / equity1.getAssetValue()) * 100;
				map.put("equityPercent", Double.valueOf(df.format(percent)));
				entries.add(map);
			}
			model.addAttribute("loanId", loanId);
			model.addAttribute("equityHistory", entries);
		}
		List<Preference> prefs = getPreferencesByEmailAddress(emailCookie);
		addPlanToModel(model, plan, prefs);
		model.addAttribute(USER_EMAIL, emailCookie);
		checkUserPrefernece(model, prefs);
		return "equityHistory";
	}

	private void addPlanToModel(Model model, String plan, List<Preference> prefs) {
		if (prefs != null) {
			for (Preference preference : prefs) {
				if (preference.getType().equals(PLAN)) {
					plan = preference.getValue();
				}
			}
		}
		model.addAttribute(PLAN, plan != null && !plan.equals("") ? plan : FREE_PLAN);
	}

	private boolean checkPasswordStrength(String password) {
		Pattern textPattern = Pattern.compile(PASSWORD_VALIDATION_REGEX);
		return textPattern.matcher(password).matches();
	}

	@ModelAttribute("loans")
	public Loan.Loans loans() {
		return new Loan.Loans();
	}

	private void addPlanFromPreferencesToModel(Model model, List<Preference> preferences) {
		CollectionUtils.emptyIfNull(preferences)
									 .stream()
									 .filter(preference -> preference.getType().equals(PLAN))
									 .findFirst()
									 .ifPresent(preference -> model.addAttribute(PLAN, preference.getValue()));
	}

	public PreferenceService getPreferenceService() {
		return preferenceService;
	}

	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	public LoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}

	public LoanRelationshipService getLoanRelationshipService() {
		return loanRelationshipService;
	}

	public void setLoanRelationshipService(LoanRelationshipService loanRelationshipService) {
		this.loanRelationshipService = loanRelationshipService;
	}

	public LoanWebService getLoanWebService() {
		return loanWebService;
	}

	public void setLoanWebService(LoanWebService loanWebService) {
		this.loanWebService = loanWebService;
	}

	public LoanAggService getLoanAggService() {
		return loanAggService;
	}

	public void setLoanAggService(LoanAggService loanAggService) {
		this.loanAggService = loanAggService;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public EquityService getEquityService() {
		return equityService;
	}

	public void setEquityService(EquityService equityService) {
		this.equityService = equityService;
	}

	public EquityExternalCalculatorService getEquityExternalCalculatorService() {
		return equityExternalCalculatorService;
	}

	public void setEquityExternalCalculatorService(EquityExternalCalculatorService equityExternalCalculatorService) {
		this.equityExternalCalculatorService = equityExternalCalculatorService;
	}

	public SiteOfferService getSiteOfferService() {
		return siteOfferService;
	}

	public void setSiteOfferService(SiteOfferService siteOfferService) {
		this.siteOfferService = siteOfferService;
	}

}
