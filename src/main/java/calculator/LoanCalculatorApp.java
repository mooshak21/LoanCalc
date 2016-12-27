package calculator;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class LoanCalculatorApp {
	private static String inputLeft, inputRight, calcEquals, inputOperator;
	@RequestMapping("/")
    	@ResponseBody
    	String home() {
		return (LoanCalculatorApp.calculate());
    	}

	@RequestMapping("/loan")
    	@ResponseBody
    	Loan loan() {
		return new Loan(Integer.valueOf(inputLeft), "PNC BANK", "NJ", 3.5F, 3.75F);
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

	public static void main(String[] args) throws Exception {
		inputLeft = args[0];
		inputRight = args[1];
		calcEquals = args[2];
		inputOperator = args[3];
	        SpringApplication.run(LoanCalculatorApp.class, args);


		/*LoanCalculatorApp lcaSalaryIncrement = new LoanCalculatorApp("169125", "2.5", "%", "=");
		System.out.println(lcaSalaryIncrement.calculate());

		LoanCalculatorApp lcaSalaryLoss = new LoanCalculatorApp("169125", "19125", "-", "=");
		System.out.println(lcaSalaryLoss.calculate());

		LoanCalculatorApp lcaSalaryPayCheck = new LoanCalculatorApp("169125", "26", "/", "=");
		System.out.println(lcaSalaryPayCheck.calculate());

		LoanCalculatorApp lcaSalaryPayIncrease = new LoanCalculatorApp("6667", "26", "*", "=");
		System.out.println(lcaSalaryPayIncrease.calculate());*/
	}
}
