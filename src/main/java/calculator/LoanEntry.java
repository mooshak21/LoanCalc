package calculator;

public class LoanEntry implements Serializable{
	public static final long serialVersionUId = 1L;
	private double principal, interest;

	public LoanEntry(double princ, double intrst){
		principal = princ;
		interest = intrst;
	}
	public void setPrincipal(double princ){
		principal = princ;
	}
	public double getPrincipal(){
		return principal;
	}
	public void setInterest(double intrst){
		interest = intrst;
	}
	public double getInterest(){
		return interest;
	}


	
}
