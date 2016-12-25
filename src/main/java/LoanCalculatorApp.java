class LoanCalculatorApp {
	private String inputLeft, inputRight, calcEquals, inputOperator;
	
	public LoanCalculatorApp(String inpLft, String inpRgt, String calcEq, String inpOp){
		inputLeft = inpLft;
		inputRight = inpRgt;
		calcEquals = calcEq;
		inputOperator = inpOp;
	}

	public String calculate(){
		if(calcEquals != null && calcEquals.equals("+")){
			Float inpLeft = Float.valueOf(inputLeft);
			Float inpRight = Float.valueOf(inputRight);

			return String.valueOf(new Float(inpLeft + inpRight));
		}

		return "No Value";
	}

	public static void main(String[] args){
		LoanCalculatorApp lca = new LoanCalculatorApp("169125", "12467","+","=");
		System.out.println(lca.calculate());

	}
}
