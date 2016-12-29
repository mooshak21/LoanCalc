package calculator;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
@RestController
public class LoanCalculatorApp {
	private static String inputLeft, inputRight, calcEquals, inputOperator;
	private static String air, numOfYears, loanAmount, lender, state;
	@RequestMapping("/")
    	String home() {
		return "home";
    	}

	@RequestMapping(path="/calculateloan", produces="application/json")
    	Loan calculateloan(@RequestParam("airVal") String airVal,
		                @RequestParam("lender") String lndr,
				                @RequestParam("loanAmt") String loanAmtVal,
						                @RequestParam("state") String st,
								                @RequestParam("numOfYears") String numYrs
    	) {
			air = airVal;
			numOfYears = numYrs;
			loanAmount = loanAmtVal;
			lender = lndr;
			state = st;

			double periodicInterestRate = Double.valueOf(air)/(12*100);
			double addOne = (1 + periodicInterestRate);
			double loanAmt = Double.valueOf(loanAmount);
			double compoundingPeriods = Double.valueOf(numOfYears)*12;
			
			double monthly = 0;
			double total = 0;
			
			monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, compoundingPeriods))/(Math.pow(addOne,compoundingPeriods) - 1)));
			total = (compoundingPeriods) * monthly;

		return new Loan(monthly, loanAmt, total, lender, state, periodicInterestRate, Double.valueOf(air), Integer.valueOf(numOfYears));    	
	}

	public LoanCalculatorApp(){
	}
	
	public LoanCalculatorApp(String inpLft, String inpRgt, String calcEq, String inpOp){
		inputLeft = inpLft;
		inputRight = inpRgt;
		calcEquals = calcEq;
		inputOperator = inpOp;
	}

	public static String calculate(){
		Float inpLeft = Float.valueOf(inputLeft);
		Float inpRight = Float.valueOf(inputRight);
		if(calcEquals != null && calcEquals.equals("+")){
			return String.valueOf(new Float(inpLeft + inpRight));
		}else if(calcEquals != null && calcEquals.equals("-")){
			return String.valueOf(new Float(inpLeft - inpRight));
		}else if(calcEquals != null && calcEquals.equals("*")){
			return String.valueOf(new Float(inpLeft * inpRight));
		}else if(calcEquals != null && calcEquals.equals("/")){
			return String.valueOf(new Float(inpLeft / inpRight));
		}else if(calcEquals != null && calcEquals.equals("%")){
			return String.valueOf(new Float(inpLeft * inpRight / 100));
		}

		return "No Value";
	}

}
