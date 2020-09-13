
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="row justify-content-center">
	<div class="card col-10 col-md-8 cardBody">
		<div class="card-header">
			<h5>${message}</h5>
		</div>
		<div class="card-block">
			<form name="paymentForm" id="paymentform" action="/vieweditpayment"
				method="POST">
				<%-- 				<div class="form-group">
					<label for="paymentType">Payment Type: </label>
					<select class="form-control resetMe" id="paymentType"
						name="paymentType">
						<option value="PayPal" selected>PayPal</option>
						<option value="Stripe">Stripe</option>

					</select>
				</div>

				<div class="form-group">
					<label for="paypalAcctNum">PayPal Account Number:</label>
					<input class="form-control resetMe" name="paypalAcctNum"
						value="${payPalAccountNumber}" id="paypalAcctNum">
				</div>

				<div class="form-group">
					<label for="paypalEmailAddress">PayPal Email Address:</label>
					<input class="form-control resetMe" type="text"
						name="paypalEmailAddress" value="${payPalEmailAddress}"
						id="paypalEmailAddress">
				</div>

				<div class="form-group">
					<label for="paymentStartDate">Payment Start Date: </label>
					<input class="form-control resetMe" type="date"
						placeholder="dd-MM-yyyy" name="paymentStartDate"
						value="${paymentStartDate}" id="paymentStartDate">
				</div>

				<div class="form-group">
					<label for="paymentEndDate">Payment End Date: </label>
					<input class="form-control resetMe" type="date"
						placeholder="dd-MM-yyyy" name="paymentEndDate"
						value="${paymentEndDate}" id="paymentEndDate">
				</div>

				<div class="form-group">
					<label for="paymentAmount">Payment Amount: </label>
					<input class="form-control resetMe" type="number"
						name="paymentAmount" value="${paymentAmount}" id="paymentAmount">
				</div>


				<div class="form-group">
					<label for="paymentFrequency">Payment Frequency: </label>
					<select class="form-control resetMe" id="paymentFrequency"
						name="paymentFrequency">
						<option value="NoRemind" selected>No Remind</option>
						<option value="Weekly">Weekly</option>
						<option value="Monthly">Monthly</option>
						<option value="Quarterly">Quarterly</option>
						<option value="Semi-Annually">Semi-Annually</option>
						<option value="Annually">Annually</option>
					</select>
				</div>
				<div class="form-group">
					<label for="balanceAmount">Balance Amount: </label>
					<input class="form-control resetMe" type="number"
						name="balanceAmount" value="${balanceAmount}" id="balanceAmount">
				</div>

				<div class="form-group">
					<label for="payPalAuthPersonName">PayPal Authorization
						Person Name :</label>
					<input class="form-control resetMe" type="text"
						name="payPalAuthPersonName" value="${payPalAuthPersonName}"
						id="payPalAuthPersonName">
				</div>

				<div class="form-group">
					<label for="payPalAuthPersonName">PayPal Password:</label>
					<input class="form-control resetMe" type="password"
						name="payPalPassword" value="${payPalPassword}"
						id="payPalPassword">
				</div>
 --%>

				<div class="form-group">
					<label for="Plan">Plan Amount:</label>
					<input class="form-control resetMe" type="text" name="Plan"
						value="${Plan}" id="plan" disabled="disabled">
				</div>

				<div class="card-block">
					<p>Please proceed to payment of the subscription plan</p>
					<p>as chosen above by clicking submit button!</p></div>
				<input type="submit" class="btn btn-default float-left"
					value="Submit" />
				</div>
				<div class="card-header">
					<p>Privacy Policy:</p>
					<textarea id="privacy" rows="4" cols="50">
Please take a minute to read the following policy so you understand how we use the personal information we ask you to submit. If you use any of Ayushi Software Services Group's services, you agree to these terms.
Our commitment to privacy
Your privacy is important to us. To better protect your privacy, we provide this notice explaining our online information practices and the choices you can make about the way your information is collected and used. To make this notice easy to find, we make it available from our footer navigation that is found on every page of our website. When you register at Ayushi Software Services Group, we ask you for personal information. We use this information to process your orders, conduct account verifications, and to communicate with you about the status of your orders. We also use your email address to send you newsletters you are subscribed to during registration, as well as messages about Ayushi Software Services Group special offers, promotional announcements and consumer surveys. Occasionally, we send our customers special offers and promotional information via postal mail, using the shipping address provided. If you no longer want to receive the newsletters or other announcements simply unsubscribe by checking the unsubscribe links.
The information we collect
This notice applies to all information collected or submitted on the Ayushi Software Services Group website. On some pages, you can order memberships, order products, make requests, and register to receive materials. The types of personal information collected at these pages are:
�	Email address
�	Name
�	Home address
�	Auto model
�	Auto make
�	Email
How we use information
The primary use of your personal information, other than directly in connection with a purchase, is to efficiently provide you with personalized, relevant information.
We also use registration information to let you know about new features or other offers of interest from us and to address customer service needs and requests.
�	Cookies
Contrary to popular myth, cookies do not extract private or personal information from your computer's memory, but rather, record only information you volunteer to us upon registering or visiting the site. Cookies are tiny files placed onto the hard drive of your computer when you register at our Web site that enable our server to recognize or �remember� who you are each time you return. Cookies can be removed by following Internet browser help file directions.
�	Log files
Like most standard website servers, we use log files. This includes Internet protocol (IP) addresses, browser type, Internet service provider (ISP), referring/exit pages, platform type, date/time stamp, and number of clicks to analyze trends, administer the site, track user's movement in the aggregate, and gather broad demographic information for aggregate use. IP addresses, etc. are not linked to personally identifiable information.
Communications from Ayushi Software Services Group
�	Special offers and updates
We send all new members a welcoming email. Established members will occasionally receive information on products, services, special deals, and a newsletter. Out of respect for the privacy of our users we present the option to not receive these types of communications. We also send all registrants special offers and updates regarding our services. If you are a former customer, you will continue to receive special offers and updates regarding our services unless you choose to unsubscribe from such communications.
�	Newsletter
If a user wishes to subscribe to our newsletter, we ask for contact information such as name and email address. Out of respect for our user�s privacy, we provide a way to opt-out of these communications.
�	Service announcements
On rare occasions it is necessary to send out a strictly service-related announcement. For instance, if our service is temporarily suspended for maintenance, we might send users an email. Generally, users may not opt-out of these communications, though they can deactivate their account. However, these communications are not promotional in nature.
�	Customer service
We communicate with users on a regular basis to provide requested services and in regard to issues relating to their account we reply via email or phone, in accordance with the user's wishes.
�	Other
During the registration process, we collect phone information so that we may call you for account management purposes. We also will call you during the first several weeks of your trial or service to provide answers to any questions, as well as to offer additional promotions only available via phone. After the first several weeks of your trial or service, we, or our authorized marketing partners, may periodically contact you for the purposes of improving our services by asking you to answer questions or complete surveys or by offering you other services that we feel you may benefit from.
Sharing�Third parties
Ayushi Software Services Group will not disclose your personal information provided in connection with membership registration or purchases, except with your knowledge and consent as described in this Privacy Policy or as may be required by law, or to protect the rights or property of Ayushi Software Services Group.
Information submitted by you online (such as information about products you purchase from us, your name, address, email address) may be shared with companies with which we have a commercial relationship, including companies through which you reached Ayushi Software Services Group. For example, if you make a purchase with us because of a special offer made through another site acting as a distributor of our products, in that circumstance you will be considered their customer as well.
We will not, except as may be required by law, share with any other party your password or payment information. We may also share information about you (including name, email address, phone number, and postal address) to our marketing partners or other companies with whom we have a commercial relationship so they may contact you about information and services that we feel you would benefit from.
We also reserve the right to disclose aggregated user statistics, such as �45% of our users are female� in order to describe our services to prospective partners, advertisers, and other third parties. Under protection of confidentiality agreements, Ayushi Software Services Group may match common user information, such as name, address and phone number with third party data to avoid duplication and prevent errors.
Our commitment to data security
Ayushi Software Services Group takes every precaution to protect our users' information. When users submit sensitive information via the website, their information is protected both online and off-line.
When our registration/order form asks users to enter sensitive information (such as credit card number), that information is encrypted and is protected with the best encryption software in the industry - SSL. While on a secure page, such as our order form, the lock icon on the bottom of Web browsers such as Netscape Navigator and Microsoft Internet Explorer becomes locked, as opposed to un-locked, or open, when users are just �surfing.�
While we use SSL encryption to protect sensitive information online, we also do everything in our power to protect user-information off-line. All of our users' information, not just the sensitive information mentioned above, is restricted in our offices. Only employees who need the information to perform a specific job (for example, our billing clerk or a customer service representative) are granted access to personally identifiable information. Furthermore, ALL employees are kept up-to-date on our security and privacy practices. Finally, the servers that store personally identifiable information are in a secure environment, behind a locked cage in a locked facility.
Ayushi Software Services Group reserves the right to make any amendments, modifications or changes to this Privacy Policy at any time. If any material change is made to this Privacy Policy, we will notify our users about those changes via email. If you do not agree to such changes, you can modify your notification settings to opt out of certain notices from Ayushi Software Services Group or its marketing partners. If users have any questions about the security at our website, please contact Customer Support.
Our commitment to children's privacy
Protecting the privacy of the very young is especially important. For that reason, we never collect or maintain information at our website from those we actually know are under 13, and no part of our website is structured to attract anyone under 13. In order to subscribe to Ayushi Software Services Group's services, the member must be 18 with a valid payment method.
How you can access or correct your information
You can access your personally identifiable information collected by Ayushi Software Services Group by logging into your account and visiting the �Settings� section of the website. You can correct factual errors in your personally identifiable information by sending us a request that credibly shows error. To protect your privacy and security, we will also take reasonable steps to verify your identity before granting access or making corrections.
How to contact us
Should you have other questions or concerns about these privacy policies, please contact Customer Support.
				</textarea>
<%--				<input type="button" class="btn btn-default float-right"
					value="Reset" onclick="resetForm()" /> --%>

			</form>
		</div>
	</div>
</div>

<style media="screen">
	#privacy{
		width: 100%;
		height: 200px;
	}
	textarea{
		font-size: 14px!important;
	}

	#privacy::-webkit-scrollbar {
	  width: 6px;
	  height: 3px; }

	#privacy::-webkit-scrollbar-thumb {
	  background: black;
	  border-radius: 2px; }

		.card-header{
			font-weight: normal!important;
			text-transform: none;
		}
		.cardBody{
			white-space: normal!important;
		}

</style>
<script>
	function resetForm() {
		$(document).ready(function() {
			$(".resetMe").val("");
		});

	};
</script>
