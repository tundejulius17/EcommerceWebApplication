package paypal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;

import dataModel.Cart;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CheckoutController() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PayPal paypal = new PayPal();
		Cart cart = (Cart) session.getAttribute("cart");
		
		String logoPath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/images/logo3.png";

		// The returnURL is the location where buyers return to when a payment
		// has been successfully authorised.
		String returnURL = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/Return?page=review";
		// This checks if the merchant's review page is to be skipped.
		if (paypal.getUserActionFlag().equals("true"))
			returnURL = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ "/Return?page=return";

		// The cancelURL is the location buyers are sent to when they hit the
		// cancel button during authorization of payment during the PayPal flow

		String cancelURL = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/cancel.jsp";
		Map<String, String> checkoutDetails = new HashMap<String, String>();
		// Gets all the request parameters.
		checkoutDetails = setRequestParams(request);
		checkoutDetails.put("LOGOIMG", logoPath);

		if (!isSet(request.getParameter("Confirm"))
				&& isSet(request.getParameter("checkout"))) {
			session.setAttribute("checkoutDetails", checkoutDetails);

			if (isSet(request.getParameter("checkout"))
					|| isSet(session.getAttribute("checkout"))) {
				session.setAttribute("checkout", StringEscapeUtils
						.escapeHtml4(request.getParameter("checkout")));
			}

			// Assign the Return and Cancel to the Session object for
			// ExpressCheckout Mark
			session.setAttribute("EXPRESS_MARK", "ECMark");

			request.setAttribute("PAYMENTREQUEST_0_AMT", StringEscapeUtils
					.escapeHtml4(request.getParameter("PAYMENTREQUEST_0_AMT")));
			// redirect to check out page
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("checkout.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Map<String, String> nvp = null;

			if (isSet(session.getAttribute("EXPRESS_MARK"))
					&& session.getAttribute("EXPRESS_MARK").equals("ECMARK")) {
				checkoutDetails.putAll((Map<String, String>) session
						.getAttribute("checkoutDetails"));
				checkoutDetails.putAll(setRequestParams(request));

				/*
				 * if (isSet(checkoutDetails.get("shipping_method"))) {
				 * BigDecimal new_shipping = new BigDecimal(
				 * checkoutDetails.get("shipping_method")); BigDecimal
				 * shippingamt = new BigDecimal(
				 * checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
				 * BigDecimal paymentAmount = new BigDecimal(
				 * checkoutDetails.get("PAYMENTREQUEST_0_AMT")); if
				 * (shippingamt.compareTo(new BigDecimal(0)) > 0) {
				 * paymentAmount = paymentAmount.add(new_shipping)
				 * .subtract(shippingamt); } String val =
				 * checkoutDetails.put("PAYMENTREQUEST_0_AMT",
				 * paymentAmount.toString()); // .replace(".00", "")
				 * 
				 * checkoutDetails.put("PAYMENTREQUEST_0_SHIPPINGAMT",
				 * new_shipping.toString()); checkoutDetails.put("shippingAmt",
				 * new_shipping.toString());
				 * 
				 * }
				 */

				nvp = paypal.callMarkExpressCheckout(checkoutDetails,
						returnURL, cancelURL);
				session.setAttribute("checkoutDetails", checkoutDetails);

			} else {
				session.invalidate();
				session = request.getSession();
				nvp = paypal.callShortcutExpressCheckout(checkoutDetails,
						returnURL, cancelURL);
				session.setAttribute("checkoutDetails", checkoutDetails);
				session.setAttribute("cart", cart);

			}

			String strAck = nvp.get("ACK").toString().toUpperCase();
			if (strAck != null
					&& (strAck.equals("SUCCESS") || strAck
							.equals("SUCCESSWITHWARNING"))) {
				session.setAttribute("TOKEN", nvp.get("TOKEN").toString());
				// Redirect to paypal.com
				paypal.redirectURL(
						response,
						nvp.get("TOKEN").toString(),
						(isSet(session.getAttribute("EXPRESS_MARK"))
								&& session.getAttribute("EXPRESS_MARK").equals(
										"ECMark") || (paypal
								.getUserActionFlag().equalsIgnoreCase("true"))));
			} else {
				// Display a user friendly Error on the page using any of the
				// following error information returned by PayPal
				String ErrorCode = nvp.get("L_ERRORCODE0").toString();
				String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
				String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
				String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0")
						.toString();

				String errorString = "SetExpressCheckout API call failed. "
						+ "<br>Detailed Error Message: " + ErrorLongMsg
						+ "<br>Short Error Message: " + ErrorShortMsg
						+ "<br>Error Code: " + ErrorCode
						+ "<br>Error Severity Code: " + ErrorSeverityCode;
				request.setAttribute("error", errorString);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("error.jsp");
				session.invalidate();
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}

			}

		}

	}

	// This retrieves all the request parameters from the express checkout
	// button.
	private HashMap<String, String> setRequestParams(HttpServletRequest request) {
		HashMap<String, String> requestMap = new HashMap<String, String>();

		for (String key : request.getParameterMap().keySet()) {
			requestMap.put(key, StringEscapeUtils.escapeHtml4(request
					.getParameterMap().get(key)[0]));
		}

		return requestMap;
	}

	// This checks for null or empty string.
	private boolean isSet(Object value) {
		return (value != null && value.toString().length() != 0);
	}

}
