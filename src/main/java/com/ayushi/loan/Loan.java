package com.ayushi.loan;

import java.io.Serializable;
import java.util.ArrayList;

public class Loan implements Serializable, Comparable<Loan> {
	private static final long serialVersionUID = 1L;
	private Double amount, total, monthly;
	private String lender;
	private String region;
	private String state;
	private Double interestRate;
	private Double apr;
	private Integer numberOfYears;
	private Long loanId;
	private String loanType;
	private String loanDenomination;
	private LoanApp loanApp;
	private Double principal, interest;
	private String email;
	private String name;
	private String vehicleModel;
	private String vehicleMake;
	private String vehicleYear;
	private String vin;
	private String address;
	private String city;
	private String country;
	private String zipcode;

	public Loan(){
		amount = 0.0;
		total = 0.0;
		monthly = 0.0;
		lender = "Bank";
		region = "Brunswicks";
		state = "NJ";
		interestRate = 0.0;
		apr = 0.0;
		numberOfYears = 1;
		loanId = System.currentTimeMillis();
		loanApp = null;
		principal = 0.0;
		interest = 0.0;
		loanType = "Home Loan";
		loanDenomination = "USD";
		email = "contact@loaninsight.online";
		name = "ayushi";
		vehicleModel = null;
		vehicleMake = null;
		vehicleYear = null;
		vin = null;
		address = null;
		city = null;
		country = null;
		zipcode = null;
	}

	public Loan(double mnthly, double amt, double tot, String lndr, String rn, String st, double intRate, double Apr, int numYears, double interestPayment, Long loanIdNo, String loantype, String loanDenom, String email,
				String name, String vehicleModel, String vehicleMake,String vehicleYear,String vin, String address, String city, String country, String zipcode){
		monthly = new Double(mnthly);
		amount = new Double(amt);
		total = new Double(tot);
		lender = lndr;
		region = rn;
		state = st;
		interestRate = new Double(intRate);
		apr = new Double(Apr);
		numberOfYears = new Integer(numYears);
		if(loanIdNo != null){
			loanId = loanIdNo;
			loanApp = new LoanApp(mnthly, amt, tot, lndr, st, intRate, Apr, numYears,loanIdNo);
		}else{
			loanId = System.currentTimeMillis();
			loanApp = new LoanApp(mnthly, amt, tot, lndr, st, intRate, Apr, numYears,null);
		}
		interest = new Double(interestPayment);
		principal = new Double(monthly - interest);
		loanType = loantype;
		loanDenomination = loanDenom;
		this.email = email;
		this.name = name;
		this.vehicleModel = vehicleModel;
		this.vehicleMake =  vehicleMake;
		this.vehicleYear = vehicleYear;
		this.vin = vin;
		this.address = address;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
	}
	public void setLoanApp(LoanApp lnApp){
		loanApp = lnApp;
		loanApp.setLoan(this);
	}

	public LoanApp getLoanApp(){
		return loanApp;
	}
	public void setMonthly (Double mthly){
		monthly = mthly;
	}
	public Double getMonthly(){
		return monthly;
	}
	public void setAmount (Double amt){
		amount = amt;
	}
	public Double getAmount(){
		return amount;
	}
	public void setTotal (Double tot){
		total = tot;
	}
	public Double getTotal(){
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
	public void setInterestRate(Double intRate){
		interestRate = intRate;
	}
	public Double getInterestRate(){
		return interestRate;
	}
	public void setAPR(Double Apr){
		apr = Apr;
	}
	public Double getAPR(){
		return apr;
	}
	public void setNumberOfYears (Integer num){
		numberOfYears = num;
	}
	public Integer getNumberOfYears(){
		return numberOfYears;
	}
	public Long getLoanId(){
		return loanId;
	}
	public void setLoanId(Long lnId){
		loanId = lnId;
	}
	public void calculatePrincipalAndInterest(double loanAmount, int numOfYears, double interestRate, double air){
		                        double periodicInterestRate = Double.valueOf(air)/(12*100);
					                        double addOne = (1 + periodicInterestRate);
								                        double loanAmt = Double.valueOf(loanAmount);
											                        double compoundingPeriods = Double.valueOf(numOfYears)*12;

														                        double monthly = 0;
																	                        double total = 0;

																				                        monthly = loanAmt * (((periodicInterestRate * Math.pow(addOne, compoundingPeriods))/(Math.pow(addOne,compoundingPeriods) - 1)));
			principal = loanAmt / compoundingPeriods;
			interest = monthly - principal;
	}

	public void setPrincipal(Double princ){
		principal = princ;
	}
	public Double getPrincipal(){
		return principal;
	}
	public void setInterest(Double intAmt){
		interest = intAmt;
	}
	public Double getInterest(){
		return interest;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanDenomination() {
		return loanDenomination;
	}

	public void setLoanDenomination(String loanDenomination) {
		this.loanDenomination = loanDenomination;
	}

	public String toString(){
		return "Loan with id " + this.getLoanId() + " and amount $" + this.getAmount() + " has a monthly payment of $" + this.getMonthly() + " with principal of $ " + this.getPrincipal() + " and interest of $ " + this.getInterest() + " for " + this.getNumberOfYears() + " years and is from " + this.getLender() + " in state " + this.getState() + " at interest rate of " + this.getInterestRate() + "% and APR of " + this.getAPR() + "%" + " and Loan App of " + (this.getLoanApp() != null ? this.getLoanApp().toString() : "");
	}

    @Override
    public int compareTo(Loan o) {
        if (this.loanId > o.getLoanId()){
            return 1;
        }else {
            return -1;
        }
    }

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleMake() {
		return vehicleMake;
	}

	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	public String getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public static class Loans extends ArrayList<Loan> {
	}
}
