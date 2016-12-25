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

			return new String(new Float(inpLeft + inpRight));
		}
	}

	public static void main(String[] args){
		LoanCalculatorApp lca = new LoanCalculatorApp(args[0], args[1], args[2], args[3]);
		System.out.println(lca.calculate());

	}
}
