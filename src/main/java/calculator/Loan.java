package calculator;

public class Loan {
	private int amount;
	private String lender;
	private String state;
	private float interestRate;
	private float apr;

	public Loan(){
		amount = 0;
		lender = "Bank";
		state = "NJ";
		interestRate = 0.0F;
		apr = 0.0F;
	}

	public Loan(int amt, String lndr, String st, float intRate, float Apr){
		amount = amt;
		lender = lndr;
		state = st;
		interestRate = intRate;
		apr = Apr;
	}
	public void setAmount (int amt){
		amount = amt;
	}
	public int getAmount(){
		return amount;
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
};
