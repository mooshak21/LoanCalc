import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.lang.Math;
//Imports all necessary classes to run program

public class LoanCalculator extends Application {
	private Button calculateLoan;
	private TextField AIR;
	private TextField numOfYears;
	private TextField loanAmount;
	private TextField monthlyPayment;
	private TextField totalPayment;
	protected Stage loanCalc;
	//Creates a class that extends Application class and its private and protected data members

	
public void start( Stage loanCalc )
{
	this.loanCalc = loanCalc;
	loanCalc.setTitle( "Loan Calculator" );
	GridPane calc = new GridPane();
	calc.setPadding( new Insets( 10 ) );
	calc.setHgap( 5 );
	calc.setVgap( 5 );
	calc.setAlignment( Pos.CENTER );
	//Creates title of GUI, GridPane, and sets visual alignment and gaps
	
	Scene myScene = new Scene( calc, 300, 200 );
	//Creates a scene
	
	AIR = new TextField ();
	calc.add( new Label( "Annual Interest Rate: " ), 0, 0 );
	calc.add(AIR, 1, 0);
	
	numOfYears = new TextField ();
	calc.add(new Label ( "Number of Years: " ), 0, 1 );
	calc.add(numOfYears, 1, 1 );
	
	loanAmount = new TextField ();
	calc.add(new Label( "Loan Amount: " ), 0, 2 );
	calc.add(loanAmount, 1, 2 );
	
	monthlyPayment = new TextField ();
	calc.add(new Label( "Monthly Payment: " ), 0, 3 );
	calc.add(monthlyPayment, 1, 3 );
	
	totalPayment = new TextField ();
	calc.add(new Label( "Total Payment: " ), 0, 4 );
	calc.add(totalPayment, 1, 4 );
	//Creates labels and text fields for each given category
	
	calculateLoan = new Button( "Calculate");
	calc.add(calculateLoan, 1, 5 );
	calculateLoan.setOnAction( new LoanCalcButtonHandler());
	//Creates and positions the button

	calc.setHalignment(calculateLoan, HPos.RIGHT);
	//Aligns the button to bottom-right location
	
	loanCalc.setScene( myScene );
	loanCalc.show();
	//Displays entire GUI
	}

	class LoanCalcButtonHandler implements EventHandler<ActionEvent>
	//Creates a ButtonHandler where monthly and total payments are calculated
	{
		public void handle( ActionEvent e ) {
			
			double periodicInterestRate = Double.valueOf(AIR.getText()) / 12;
			double addOne = (1 + periodicInterestRate);
			double loanAmt = Double.valueOf(loanAmount.getText());
			double compoundingPeriods = Double.valueOf(numOfYears.getText()) * 12;
			
			double monthly = 0;
			double total = 0;
			
			monthly = (periodicInterestRate * loanAmt) / (1-(Math.pow(addOne, -compoundingPeriods)));
			total = (compoundingPeriods) * monthly;
			
			monthlyPayment.setText(String.valueOf(monthly));
			totalPayment.setText(String.valueOf(total));
			
			calculateLoan.setText("Calculated!");
		}
	}

	public static void main(String[] args) {
		launch(args);
		//Launches start method
	}

}