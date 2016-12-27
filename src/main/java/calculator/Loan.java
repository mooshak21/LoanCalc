package calculator;

public class Loan {
	private double amount, total, monthly;
	private String lender;
	private String state;
	private double interestRate;
	private double apr;
	private int compoundingPeriods;

	public Loan(){
		amount = 0.0;
		total = 0.0;
		monthly = 0.0;
		lender = "Bank";
		state = "NJ";
		interestRate = 0.0;
		apr = 0.0;
		compoundingPeriods = 12;
	}

	public Loan(double mnthly, double amt, doubnle tot, String lndr, String st, double intRate, double Apr, int cmpPeriods){
		monthly = mnthly;
		amount = amt;
		total = tot;
		lender = lndr;
		state = st;
		interestRate = intRate;
		apr = Apr;
		compoundingPeriods = cmpPeriods;
	}
	public void setMonthly (double mthly){
		monthly = mthly;
	}
	public double getMonthly(){
		return monthly;
	}
	public void setAmount (double amt){
		amount = amt;
	}
	public double getAmount(){
		return amount;
	}
	public void setTotal (double tot){
		total = tot;
	}
	public double getTotal(){
		return total;
	}
	public void setLender(String lndr){
		lender = lndr;
	}
	public String getLender(){
		return lender;
	}
	public void setState(String st){
		state = st;
	}
	public String getState(){
		return state;
	}
	public void setInterestRate(double intRate){
		interestRate = intRate;
	}
	public double getInterestRate(){
		return interestRate;
	}
	public void setAPR(double Apr){
		apr = Apr;
	}
	public double getAPR(){
		return apr;
	}
	public void setCompoundingPeriods (int cp){
		compoundingPeriods = cp;
	}
	public int getCompoundingPeriods(){
		return compoundingPeriods;
	}


};
