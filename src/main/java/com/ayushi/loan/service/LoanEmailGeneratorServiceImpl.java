/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayushi.loan.service;

import com.ayushi.loan.AmortizedLoan;
import com.ayushi.loan.Loan;
import com.ayushi.loan.LoanEntry;
import com.ayushi.loan.exception.EmailServiceException;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author Daniel Gago
 */
public class LoanEmailGeneratorServiceImpl implements LoanEmailGeneratorService{
 
    private String emailFrom;
    private String headerTitle;
    private String headerSubTitle;
    private String footerTitle;
    private String footerSubTitle;
    private String colorDark;
    private String colorlight;
    private String secretApiKey;

    @Override
    public String buildMessage(AmortizedLoan amortizedLoan, Double payOffAmount, String payOffOn) {
        StringBuilder message = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DecimalFormat df = new DecimalFormat( "#,###,###,###.00" );
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

        message.append("<html>\n");

        //---------------------------------------   header   ----------------------------------------------
        message.append("<div style='background-color: #d7dde4; border-bottom-color: #78bd2e; border-bottom-width: 5px; border-bottom-style: solid; position: relative; width: 520px; margin-left: auto; margin-right: auto; padding-left: 30px; padding-top:1px '>\n");
        message.append("<h1 style='font: bold'>").append(headerTitle).append("</h1>\n");
        message.append("<p><font size='2'>").append(headerSubTitle).append("</font></p>\n");
        message.append("</div>\n");
        
        
        //-------------------------------------  Loan Detail table  ------------------------------------------------
        message.append("<div  style='width: 480px; margin-left: auto; margin-right: auto;padding-top: 15px'>\n");
        message.append("<font size='1'>");
        message.append("<table style='width: 480px; margin-left: auto; margin-right: auto; border-style: solid; border-width: 1px'>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Monthly Payment:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(amortizedLoan.getMonthly())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Interest Rate:(%)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(amortizedLoan.getInterestRate() * 100 *12)).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Interest:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(amortizedLoan.getInterest())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Principal:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(amortizedLoan.getPrincipal())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Loan Amount:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(amortizedLoan.getAmount())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Lender:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(amortizedLoan.getLender()).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>State:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(amortizedLoan.getState()).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Number of Years:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(amortizedLoan.getNumberOfYears()).append("</font></td>\n");
        message.append("</tr>\n");
        
        if(payOffAmount != null && payOffAmount > 0){
            message.append("<tr>\n");
            message.append("<td style='text-align:right'>Payoff Amount:($)</td>\n");
            message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(payOffAmount)).append("</font></td>\n");
            message.append("</tr>\n");

            message.append("<tr>\n");
            message.append("<td style='text-align:right'>As of Payoff Date on:</td>\n");
            message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(payOffOn).append("</font></td>\n");
            message.append("</tr>\n");
        }

        message.append("</table>\n");
        message.append("</font>");
        message.append("</div>\n");

        
         //------------------------------------- Loan Entry Table----------------------------------------------------------------
        
        message.append("<div  style='width: 480px; margin-left: auto; margin-right: auto;padding-top: 15px'>\n");
        message.append("<font size='2'>");
        message.append("<table cellpadding='0' cellspacing='0' style='width: 480px; margin-left: auto; margin-right: auto; margin-top: 10px; border:solid 0px ;text-align:center;border-collapse:collapse;'>\n");
        message.append("<thead style='background-color: #d7dde4'>\n");
        message.append("<tr>\n");
        message.append("<th style='width: 60; border: solid 1px #b0b0b0'>Date</th>\n");
        message.append("<th style='width: 60; border: solid 1px #b0b0b0'>Principal($)</th>\n");
        message.append("<th style='width: 60; border: solid 1px #b0b0b0'>Interest($)</th>\n");
        message.append("<th style='width: 60; border: solid 1px #b0b0b0'>Loan Amount($)</th>\n");
        message.append("<th style='width: 60; border: solid 1px #b0b0b0'>Monthly($)</th>\n");
        message.append("</tr>\n");
        message.append("</thead>\n");
        message.append("<tbody>\n");

        int lineNumber=0;
        String color;
        for(Integer key : amortizedLoan.getEntries().keySet()){
            LoanEntry le = amortizedLoan.getEntries().get(key);
	    if(le != null){
	
	        if(lineNumber%2 == 0) color=colorDark; else color=colorlight;

	        message.append("<tr>\n");
        	message.append("<td style='border: solid 1px #b0b0b0; background-color: ").append(color).append("'>").append(sdf.format(le.getDateEntry().getTime())).append("</td>\n");
            	message.append("<td style='border: solid 1px #b0b0b0; background-color: ").append(color).append("'>").append(df.format(le.getPrincipal())).append("</td>\n");
            	message.append("<td style='border: solid 1px #b0b0b0; background-color: ").append(color).append("'>").append(df.format(le.getInterest())).append("</td>\n");
            	message.append("<td style='border: solid 1px #b0b0b0; background-color: ").append(color).append("'>").append(df.format(le.getLoanAmount())).append("</td>\n");
            	message.append("<td style='border: solid 1px #b0b0b0; background-color: ").append(color).append("'>").append(df.format(le.getMonthly())).append("</td>\n");
            	message.append("</tr>\n");       

            	lineNumber++;
	   }
        }

        message.append("</tbody>\n");
        message.append("</table>\n");
        message.append("</font>");
        message.append("</div>\n");
            
       
        
        
        //------------------------------------------------Footer------------------------------------------------------
        message.append("<div style='background-color: #d7dde4; width: 580px; margin-left: auto; margin-right: auto; margin-top: 5px; padding: 2px 0 2px 30px'>\n");
        message.append("<bold><font size='4'>").append(footerTitle).append("</font></bold>\n");
        message.append("<p><font size='2'>").append(footerSubTitle).append("</font></p>");
        message.append("</div>\n");
        message.append("</html>");
      
        return message.toString();
    }
    
    @Override
    public String buildMessage(Loan loan){
        StringBuilder message = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DecimalFormat df = new DecimalFormat( "#,###,###,###.00" );
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

        message.append("<html>\n");
        //---------------------------------------   header   ----------------------------------------------
        message.append("<div style='background-color: #d7dde4; border-bottom-color: #78bd2e; border-bottom-width: 5px; border-bottom-style: solid; position: relative; width: 520px; margin-left: auto; margin-right: auto; padding-left: 30px; padding-top:1px '>\n");
        message.append("<h1 style='font: bold'>").append(headerTitle).append("</h1>\n");
        message.append("<p><font size='2'>").append(headerSubTitle).append("</font></p>\n");
        message.append("</div>\n");
       
        //-------------------------------------  Loan Detail table  ------------------------------------------------
        message.append("<div  style='width: 480px; margin-left: auto; margin-right: auto;padding-top: 15px'>\n");
        message.append("<font size='1'>");
        message.append("<table style='width: 480px; margin-left: auto; margin-right: auto; border-style: solid; border-width: 1px'>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Loan Id: </td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(loan.getLoanId()).append("</font></td>\n");
        message.append("</tr>\n");
        
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Monthly Payment:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getMonthly())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Interest Rate:(%)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getInterestRate() * 100 *12)).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Interest:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getInterest())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Principal:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getPrincipal())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Loan Amount:($)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getAmount())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Lender:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(loan.getLender()).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>State:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(loan.getState()).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>APR:(%)</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(df.format(loan.getAPR())).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Number of Years:</td>\n");
        message.append("<td style='padding-left: 5px'><font color='#78bd2e'>").append(loan.getNumberOfYears()).append("</font></td>\n");
        message.append("</tr>\n");
        
        message.append("</table>\n");
        message.append("</font>");
        message.append("</div>\n");

         //------------------------------------------------Footer------------------------------------------------------
        message.append("<div style='background-color: #d7dde4; width: 580px; margin-left: auto; margin-right: auto; margin-top: 5px; padding: 2px 0 2px 30px'>\n");
        message.append("<bold><font size='4'>").append(footerTitle).append("</font></bold>\n");
        message.append("<p><font size='2'>").append(footerSubTitle).append("</font></p>");
        message.append("</div>\n");
        message.append("</html>");
      
        return message.toString();
       
    }

    @Override
    public void sendMail(String emailTo, String subject, String message) throws EmailServiceException{
       
        Content content = new Content("text/html", message);
        Mail mail = new Mail(new Email(emailFrom), subject, new Email(emailTo), content);
        SendGrid sg = new SendGrid(secretApiKey);
        Request request = new Request();

        try {
          request.method = Method.POST;
          request.endpoint = "mail/send";
          request.body = mail.build();
          Response response = sg.api(request);
          if(response.statusCode != 202){
              throw new EmailServiceException("There was an error attempting to send email, HTTP response code: "+response.statusCode);
          }
          
        } catch (IOException ex) {
            throw new EmailServiceException(ex.getMessage());
        }
    
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getSecretApiKey() {
        return secretApiKey;
    }

    public void setSecretApiKey(String secretApiKey) {
        this.secretApiKey = secretApiKey;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderSubTitle() {
        return headerSubTitle;
    }

    public void setHeaderSubTitle(String headerSubTitle) {
        this.headerSubTitle = headerSubTitle;
    }

    public String getFooterTitle() {
        return footerTitle;
    }

    public void setFooterTitle(String footerTitle) {
        this.footerTitle = footerTitle;
    }

    public String getFooterSubTitle() {
        return footerSubTitle;
    }

    public void setFooterSubTitle(String footerSubTitle) {
        this.footerSubTitle = footerSubTitle;
    }

    public String getColorDark() {
        return colorDark;
    }

    public void setColorDark(String colorDark) {
        this.colorDark = colorDark;
    }

    public String getColorlight() {
        return colorlight;
    }

    public void setColorlight(String colorlight) {
        this.colorlight = colorlight;
    }
    
    
    
    
}
