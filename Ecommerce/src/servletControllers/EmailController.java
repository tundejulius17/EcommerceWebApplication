package servletControllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appUtil.EmailUtil;
import appUtil.ValidatorUtil;
import dataModel.EmailSubscriber;
import dbAdapter.DBEmail;

@WebServlet("/email/*")
public class EmailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmailController() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get the requested url from the Request object
		String requestURI = request.getRequestURI();
		String url = "";
		if (requestURI.endsWith("/subscribeToEmail")) {
			url = subscribeToEmail(request, response);
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}

	// Insert EmailSubscriber obj into the database.
	private String subscribeToEmail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String email = request.getParameter("email").trim();
		String message = "";
		String url = "";

		EmailSubscriber emailSubscriber = new EmailSubscriber();
		emailSubscriber.setFirstName(firstName);
		emailSubscriber.setLastName(lastName);
		emailSubscriber.setEmail(email);
		request.setAttribute("emailSubscriber", emailSubscriber);

		if (!ValidatorUtil.checkParam(firstName)
				|| !ValidatorUtil.checkParam(lastName)
				|| !ValidatorUtil.checkParam(email)) {
			message = "No field can be empty";
			request.setAttribute("message", message);
			url = "/emailSubscription/index.jsp";
		} else if (!ValidatorUtil.checkEmail(email)) {
			request.setAttribute("emailError", "email not in the right format");
			url = "/emailSubscription/index.jsp";
		} else {// No two email addresses can be the same in the database.
			if (DBEmail.emailExists(email)) {
				String emailMessage = "This email address already exist. "
						+ "<br> Kindly provide another email address.";
				request.setAttribute("emailMessage", emailMessage);
				url = "/emailSubscription/index.jsp";
			} else {
				DBEmail.addEmailSubscriber(emailSubscriber);
				// Prepare the confirmation email message
				String recipient = email;
				String sender = this.getServletContext().getInitParameter("custServEmailAddress");
				String subject = "Email Subscription Confirmation";
				String body = "<p>Hello "
						+ emailSubscriber.getFirstName()
						+ ",</p>\n\n"
						+ "<p>Thank you for subscribing to our email list."
						+ " We will send you news about our new products and special offers.</p>\n\n"
						+ "<p>Sincerely, </p>\n" + "<p>T&T Team</p>\n\n";

				boolean bodyIsHTML = true;

				try {
					// Send email message.
					EmailUtil.sendEmail(recipient, sender, subject, body,
							bodyIsHTML);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				url = "/emailSubscription/confirmation.jsp";
			}
		}

		return url;
	}

}
