/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayushi.loan.service;

import com.ayushi.loan.AmortizedLoan;
import com.ayushi.loan.LoanEntry;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;

/**
 *
 * @author Daniel Gago
 */
public class LoanEmailGeneratorServiceImpl implements LoanEmailGeneratorService{
 
    private String emailFrom;
    private String subject;

   

    @Override
    public String buildMessage(AmortizedLoan amortizedLoan) {
        StringBuilder message = new StringBuilder();
        

        message.append("<html>\n");
        message.append("<head>\n");
        message.append("<style>\n");
        message.append("html, body { width: 100%;height: 100%}\n"); 
        message.append("th{text-align: center}\n"); 
        message.append("</style>\n");
        
        //Header
        message.append("</head>\n");
        message.append("</html>\n");
        message.append("<div style='background-color: #d7dde4; border-bottom-color: #78bd2e; border-bottom-width: 5px; border-bottom-style: solid; position: relative; width: 75%; margin-left: auto; margin-right: auto; height:10%;padding-left: 30px; display: flex; align-items: center;'>\n");
        message.append("<h1 style='font: bold'>Loan Calculator</h1>\n");
        message.append("</div>\n");
        
        
        // table Loan Detail
        message.append("<div  style='width: 75%; margin-left: auto; margin-right: auto; padding-top: 15px; background-color: #f7f7f9'>\n");
        message.append("<table style='width: 75%; margin-left: auto; margin-right: auto;'>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Monthly Payment:($)</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getMonthly()).append("</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Interest Rate:(%)</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getInterestRate()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Interest:($)</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getInterest()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Last Principal:($)</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getPrincipal()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Loan Amount:($)</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getAmount()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Lender:</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getLender()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>State:</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getState()).append("%</td>\n");
        message.append("</tr>\n");
        
        message.append("<tr>\n");
        message.append("<td style='text-align:right'>Number of Years:</td>\n");
        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getNumberOfYears()).append("%</td>\n");
        message.append("</tr>\n");
        
//        message.append("<tr>\n");
//        message.append("<td style='text-align:right'>Payoff Amount:($)</td>\n");
//        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.get).append("%</td>\n");
//        message.append("</tr>\n");
//        
//        message.append("<tr>\n");
//        message.append("<td style='text-align:right'>As of Payoff Date on:</td>\n");
//        message.append("<td style='padding-left: 5px'>").append(amortizedLoan.getpayo).append("%</td>\n");
//        message.append("</tr>\n");
//        
        
        message.append("</table>\n");
        
        message.append("<table style='width: 80%; margin-left: auto; margin-right: auto; margin-top: 10px; border-style: solid; border-width: 1px'>\n");
        message.append("<thead style='background-color: #d7dde4'>\n");
        message.append("<tr>\n");
        message.append("<th>Date</th>\n");
        message.append("<th>Principal($)</th>\n");
        message.append("<th>Interest($)</th>\n");
        message.append("<th>Loan Amount($)</th>\n");
        message.append("<th>Monthly($)</th>\n");
        message.append("</tr>\n");
        message.append("</thead>\n");
        message.append("<tbody>\n");
        message.append("<tr>\n");
        for(Integer key : amortizedLoan.getEntries().keySet()){
            LoanEntry le = amortizedLoan.getEntries().get(key);
            message.append("<td>").append(le.getDateEntry()).append("</td>\n");//format date
            message.append("<td>").append(le.getPrincipal()).append("</td>\n");
            message.append("<td>").append(le.getInterest()).append("</td>\n");
            message.append("<td>").append(le.getLoanAmount()).append("</td>\n");
            message.append("<td>").append(le.getMonthly()).append("</td>\n");
        }
        message.append("</tr>\n");       
        message.append("</tbody>\n");
        message.append("</table>\n");
        message.append("</div>\n");
        
        
        
        return message.toString();
    }

    @Override
    public boolean sendMail(String emailTo, String message) {
        boolean status = false;
        Email from = new Email(emailFrom);
        Email to = new Email(emailTo);
        Content content = new Content("text/html", message);
        Mail mail = new Mail(new Email(emailFrom), subject, new Email(emailTo), content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
          request.method = Method.POST;
          request.endpoint = "mail/send";
          request.body = mail.build();
          Response response = sg.api(request);
          if(response.statusCode == 1){
              status = true;
                      
          }
          System.out.println("--------------------------------->response code: "+response.statusCode);
          System.out.println("---------------------------------->response body: "+response.body);
          System.out.println("---------------------------------->response header: "+response.headers);
        } catch (IOException ex) {
      
    }
    return status;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
    
    
}
