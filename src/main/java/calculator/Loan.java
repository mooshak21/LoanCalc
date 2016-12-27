package calculator;

public class Loan {
	private float amount, total, monthly;
	private String lender;
	private String state;
	private float interestRate;
	private float apr;
	private int compoundingPeriods;

	public Loan(){
		amount = 0.0F;
		total = 0.0F;
		monthly = 0.0F;
		lender = "Bank";
		state = "NJ";
		interestRate = 0.0F;
		apr = 0.0F;
		compoundingPeriods = 12;
	}

	public Loan(float mnthly, float amt, float tot, String lndr, String st, float intRate, float Apr, int cmpPeriods){
		monthly = mnthly;
		amount = amt;
		total = tot;
		lender = lndr;
		state = st;
		interestRate = intRate;
		apr = Apr;
		compoundingPeriods = cmpPeriods;
	}
	public void setMonthly (float mthly){
		monthly = mthly;
	}
	public float getMonthly(){
		return monthly;
	}
	public void setAmount (float amt){
		amount = amt;
	}
	public float getAmount(){
		return amount;
	}
	public void setTotal (float tot){
		total = tot;
	}
	public float getTotal(){
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
	public void setInterestRate(float intRate){
		interestRate = intRate;
	}
	public float getInterestRate(){
		return interestRate;
	}
	public void setAPR(float Apr){
		apr = Apr;
	}
	public float getAPR(){
		return apr;
	}
	public void setCompoundingPeriods (int cp){
		compoundingPeriods = cp;
	}
	public int getCompoundingPeriods(){
		return compoundingPeriods;
	}


};
