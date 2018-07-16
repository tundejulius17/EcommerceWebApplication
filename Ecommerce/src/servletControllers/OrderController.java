package servletControllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dataModel.Cart;
import dataModel.Product;
import dbAdapter.DBProduct;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderController() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		if (requestURI.endsWith("/clearCart")) {
			url = clearCart(request, response);
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		if (requestURI.endsWith("/addCartItem")) {
			url = addCartItem(request, response);
		} else if (requestURI.endsWith("/updateCartItem")) {
			url = updateCartItem(request, response);
		}else if (requestURI.endsWith("/removeCartItem")) {
			url = removeCartItem(request, response);
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);

	}

	// Add product to the shopping cart.
	private String addCartItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
		String code = request.getParameter("code");

		if (!code.isEmpty()) {
			Product product = DBProduct.getProductByCode(code);
			cart.addCartItem(product);
		}
		session.setAttribute("cart", cart);
		return "/index.jsp";

	}

	// Update cart item quantity in the shopping cart.
	private String updateCartItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String code = request.getParameter("code");
		String qtyString = request.getParameter("quantity");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		int quantity;
		Product product = DBProduct.getProductByCode(code);

		try {
			quantity = Integer.parseInt(qtyString);
			if (quantity < 0 || quantity == 0) {
				quantity = 1;
			}

			if (quantity > product.getQuantity()) {
				quantity = 1;
				request.setAttribute("insuffientQuantity",
						"insufficient quantity");
			}
		} catch (NumberFormatException ex) {
			quantity = 1;
		}

		if (quantity < product.getQuantity()
				|| quantity == product.getQuantity()) {
			cart.updateCartItem(product, quantity);
		}
		return "/cart/cart.jsp";

	}

	// Remove cart item from the shopping cart
	private String removeCartItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		Product product = DBProduct.getProductByCode(code);
		if (product != null && cart != null) {
			cart.removeCartItem(product);
		}
		return "/cart/cart.jsp";
	}

	// Remove all items in the cart.
	private String clearCart(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			cart.clearCart();
		}
		return "/cart/cart.jsp";
	}

}
