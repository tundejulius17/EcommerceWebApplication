package servletControllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbAdapter.DBProduct;

@WebServlet("/imageDownload")
public class ProductImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductImageController() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		byte[] image = DBProduct.getProductImage(id);

		response.getOutputStream().write(image);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}