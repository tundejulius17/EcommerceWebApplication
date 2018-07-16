package paypal;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import appUtil.EmailUtil;
import dataModel.Cart;
import dataModel.CartItem;
import dataModel.Customer;
import dataModel.CustomerOrder;
import dataModel.Payment;
import dataModel.Product;
import dbAdapter.DBCustomer;
import dbAdapter.DBCustomerOrder;
import dbAdapter.DBPayment;
import dbAdapter.DBProduct;

@WebServlet("/Return")
public class ReturnController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReturnController() {
		super();

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String email = "";
		/*
		 * String payerId = ""; String payerStatus = "";
		 */
		String firstName = "";
		String lastName = "";
		String phone = "";
		String shipToName = "";
		String shipToStreet = "";
		String shipToCity = "";
		/*
		 * String shipToState = ""; String shipToCntryCode = "";
		 */
		String shipToCountry = "";
		String shipToZip = "";
		// String addressStatus = "";
		String totalAmt = "";
		// String currencyCode = "";
		String transactionId = "";
		String transactionStatus = "";

		if (isSet(request.getParameter("PayerID"))) {
			session.setAttribute("payer_id", request.getParameter("PayerID"));
		}

		String token = "";

		if (isSet(request.getParameter("token"))) {
			session.setAttribute("TOKEN", request.getParameter("token"));
			token = request.getParameter("token");
		} else {
			token = (String) session.getAttribute("TOKEN");
		}

		// Check to see if the Request object contains a variable named 'token'
		PayPal payPal = new PayPal();
		Map<String, String> result = new HashMap<String, String>();

		// If the Request object contains the variable 'token' then it means
		// that the user is coming from PayPal site.
		if (isSet(token)) {
			// Call the GetExpressCheckoutDetails API call
			Map<String, String> results = payPal.getShippingDetails(token);
			String strAck = results.get("ACK").toString();

			if (strAck != null
					&& (strAck.equalsIgnoreCase("SUCCESS") || strAck
							.equalsIgnoreCase("SUCCESSWITHWARNING"))) {
				session.setAttribute("payer_id", results.get("PAYERID"));
				result.putAll(results);

			} else {
				// Display a user friendly Error on the page using any of the
				// following error information returned by PayPal
				String errorCode = results.get("L_ERRORCODE0").toString();
				String errorShortMsg = results.get("L_SHORTMESSAGE0")
						.toString();
				String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
				String errorSeverityCode = results.get("L_SEVERITYCODE0")
						.toString();

				String errorString = "SetExpressCheckout API call failed. " +

				"<br>Detailed Error Message: " + errorLongMsg
						+ "<br>Short Error Message: " + errorShortMsg
						+ "<br>Error Code: " + errorCode
						+ "<br>Error Severity Code: " + errorSeverityCode;
				request.setAttribute("error", errorString);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("error.jsp");
				session.invalidate();
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
				return;
			}

		}

		Map<String, String> checkoutDetails = new HashMap<String, String>();
		checkoutDetails.putAll((Map<String, String>) session
				.getAttribute("checkoutDetails"));

		for (String key : checkoutDetails.keySet()) {
			System.out.println(key + " " + checkoutDetails.get(key));
		}

		checkoutDetails.putAll(setRequestParams(request));
		checkoutDetails.put("TOKEN", token);
		checkoutDetails.put("payer_id",
				(String) session.getAttribute("payer_id"));

		// Call the DoExpressCheckoutPayment API call.
		String page = "return.jsp";
		if (isSet(request.getParameter("page"))
				&& request.getParameter("page").equals("return")) {
			// FIXME - The method 'request.getServerName()' must be sanitized
			// before being used.
			HashMap<String, String> results = payPal.confirmPayment(
					checkoutDetails, request.getServerName());
			request.setAttribute("payment_method", "");
			String strAck = results.get("ACK").toString().toUpperCase();
			request.setAttribute("ack", strAck);
			if (strAck != null
					&& (strAck.equalsIgnoreCase("Success") || strAck
							.equalsIgnoreCase("SuccessWithWarning"))) {
				result.putAll(results);
				result.putAll(checkoutDetails);

				email = result.get("EMAIL");
				/*
				 * payerId = result.get("PAYERID"); payerStatus =
				 * result.get("PAYERSTATUS")
				 */;
				firstName = result.get("FIRSTNAME");
				lastName = result.get("LASTNAME");
				phone = result.get("PHONENUM");
				shipToName = result.get("PAYMENTREQUEST_0_SHIPTONAME");
				shipToStreet = result.get("PAYMENTREQUEST_0_SHIPTOSTREET");
				shipToCity = result.get("PAYMENTREQUEST_0_SHIPTOCITY");
				/*
				 * shipToState = result.get("PAYMENTREQUEST_0_SHIPTOSTATE");
				 * shipToCntryCode = result
				 * .get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE");
				 */
				shipToCountry = result.get("SHIPTOCOUNTRYNAME");
				shipToZip = result.get("PAYMENTREQUEST_0_SHIPTOZIP");
				// addressStatus = result.get("ADDRESSSTATUS");
				totalAmt = result.get("PAYMENTINFO_0_AMT");
				// currencyCode = result.get("CURRENCYCODE");
				transactionStatus = result.get("PAYMENTINFO_0_PAYMENTSTATUS");
				transactionId = result.get("PAYMENTINFO_0_TRANSACTIONID");

				System.out.println(request.getAttribute("ack"));

				// If a customer already exists in the database,
				// we update its details
				if (DBCustomer.customerEmailExists(email)) {
					Customer customer = DBCustomer.getCustomer(email);
					customer.setStreet(shipToStreet);
					customer.setCity(shipToCity);
					customer.setEmail(email);
					customer.setFirstName(firstName);
					customer.setLastName(lastName);
					customer.setPhone(phone);
					customer.setPostalCode(shipToZip);
					DBCustomer.updateCustomer(customer);
					session.setAttribute("customer", customer);
				} else {
					Customer customer = new Customer();
					customer.setStreet(shipToStreet);
					customer.setCity(shipToCity);
					customer.setEmail(email);
					customer.setFirstName(firstName);
					customer.setLastName(lastName);
					customer.setPhone(phone);
					customer.setPostalCode(shipToZip);
					DBCustomer.addCustomer(customer);
					session.setAttribute("customer", customer);
				}

				// Retrieve the cart obj from the session obj.
				Cart cart = (Cart) session.getAttribute("cart");
				CustomerOrder customerOrder = new CustomerOrder();

				Date date = new Date();
				customerOrder.setOrderDate(date);
				customerOrder.setCustomer((Customer) session
						.getAttribute("customer"));
				customerOrder.setStatus("open");
				customerOrder.setLineItems(cart.getCartItems());
				customerOrder.setTotalPrice(Double.parseDouble(totalAmt));
				DBCustomerOrder.addCustomerOrder(customerOrder);

				// Update product quantity in the database.
				for (CartItem item : cart.getCartItems()) {
					Product product = item.getProduct();
					product.setQuantity(product.getQuantity()
							- item.getQuantity());
					DBProduct.updateProduct(product);
				}

				Payment payment = new Payment();
				payment.setAmount(Double.parseDouble(totalAmt));
				payment.setCustomer((Customer) session.getAttribute("customer"));
				payment.setCustomerOrder(customerOrder);
				payment.setPaymentStatus(transactionStatus);
				payment.setPaymentDate(date);
				payment.setTransactionId(transactionId);

				DBPayment.addPayment(payment);

				// Prepare order confirmation email.
				String recipient = email;
				String sender = this.getServletContext().getInitParameter("custServEmailAddress");
				String subject = "Order Confirmation Email";
				String body = "<p>Hello " + firstName + ",</p>\n\n"
						+ "<p>Thanks for your order!"
						+ " Your order will be processed shortly.</p>\n\n"
						+ "<p><b>Your delivery address</b></p>" + "<p>"
						+ shipToName + "</p><p>" + shipToStreet + "</p>"
						+ "<p>" + shipToZip + " " + shipToCity + "</p>" + "<p>"
						+ shipToCountry + "</p>\n\n"
						+ "<p><b>Your order</b></p>";

				body += "<table><tr><th>Description</th><th></th>"
						+ "<th>Quantity</th><th></th><th>Price</th></tr>";

				for (CartItem item : cart.getCartItems()) {
					body += "<tr><td>" + item.getProduct().getDescription()
							+ "</td><td></td>" + "<td>" + item.getQuantity()
							+ "</td><td></td>" + "<td>"
							+ String.format("%.02f", item.getTotal())
							+ "</td></tr>";
				}

				body += "<tr></tr><tr><td></td><td></td><td><b>Total:</b></td><td></td><td>"
						+ totalAmt
						+ "</td></tr></table>"
						+ "<p><b>Transaction ID:</b> "
						+ transactionId
						+ "</p>"
						+ "<p>Sincerely, </p>\n" + "<p>T&T Team</p>\n\n";
				boolean bodyIsHTML = true;

				// Send order confirmation to customer after
				// order and payment completion.
				try {
					EmailUtil.sendEmail(recipient, sender, subject, body,
							bodyIsHTML);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				System.out.println("phone: " + phone);

				session.invalidate();
			} else {
				// Display a user friendly Error on the page using any of the
				// following error information returned by PayPal
				String errorCode = results.get("L_ERRORCODE0").toString();
				String errorShortMsg = results.get("L_SHORTMESSAGE0")
						.toString();
				String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
				String errorSeverityCode = results.get("L_SEVERITYCODE0")
						.toString();
				String errorString = "SetExpressCheckout API call failed. "
						+ "<br>Detailed Error Message: " + errorLongMsg
						+ "<br>Short Error Message: " + errorShortMsg
						+ "<br>Error Code: " + errorCode
						+ "<br>Error Severity Code: " + errorSeverityCode;
				request.setAttribute("error", errorString);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("error.jsp");
				session.invalidate();
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
				return;
			}

			page = "return.jsp";

		} else {
			page = "review.jsp";
		}
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);

		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

	// Retrieve all the request parameters .
	private Map<String, String> setRequestParams(HttpServletRequest request) {
		Map<String, String> requestMap = new HashMap<String, String>();
		for (String key : request.getParameterMap().keySet()) {

			requestMap.put(key, request.getParameterMap().get(key)[0]);
		}

		return requestMap;

	}

	// This checks for null or empty string.
	private boolean isSet(Object value) {
		return (value != null && value.toString().length() != 0);
	}

}
