package servletControllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import appUtil.ValidatorUtil;
import dataModel.Category;
import dataModel.CustomerOrder;
import dataModel.EmailSubscriber;
import dataModel.Payment;
import dataModel.Product;
import dbAdapter.DBCategory;
import dbAdapter.DBCustomerOrder;
import dbAdapter.DBEmail;
import dbAdapter.DBPayment;
import dbAdapter.DBProduct;
import dbAdapter.DBReportDownload;

@WebServlet("/adminController/*")
@MultipartConfig
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get the request url from the request obj.
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		String url = "/admin";

		if (requestURI.endsWith("/getCategories")) {
			url = getCategories(request, response);
		} else if (requestURI.endsWith("/getProductsByCategoryId")) {
			url = getProductsByCategoryId(request, response);
		} else if (requestURI.endsWith("/getProductData")) {
			url = getProductData(request, response);
		} else if (requestURI.endsWith("/getProducts")) {
			url = getProducts(request, response);
		} else if (requestURI.endsWith("/getEmailSubscribers")) {
			url = getEmailSubscribers(request, response);
		} else if (requestURI.endsWith("/getEmailSubscribersReport")) {
			getEmailSubscribersReport(request, response);
		} else if (requestURI.endsWith("/deleteCategory")) {
			url = deleteCategory(request, response);
		} else if (requestURI.endsWith("/getCategoryName")) {
			url = getCategoryName(request, response);
		} else if (requestURI.endsWith("/deleteProduct")) {
			url = deleteProduct(request, response);
		} 

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get the request url from the request obj.
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		String url = "/admin";

		if (requestURI.endsWith("/addCategory")) {
			url = addCategory(request, response);

		} else if (requestURI.endsWith("/editCategory")) {
			url = editCategory(request, response);

		} else if (requestURI.endsWith("/deleteCategory")) {
			url = deleteCategory(request, response);

		} else if (requestURI.endsWith("/addProduct")) {
			url = addProduct(request, response);

		} else if (requestURI.endsWith("/editProduct")) {
			url = editProduct(request, response);
		} else if (requestURI.endsWith("/displayOrder")) {
			url = displayOrder(request, response);
		} else if (requestURI.endsWith("/updateCustomerOrder")) {
			url = updateCustomerOrder(request, response);
		} else if (requestURI.endsWith("/getReport")) {
			url = getReport(request, response);
		} else if (requestURI.endsWith("/getOrders")) {
			url = getOrders(request, response);
		} else if (requestURI.endsWith("/getPayments")) {
			url = getPayments(request, response);
		}

		getServletContext().getRequestDispatcher(url)
				.forward(request, response);
	}

	// Insert Category obj into the database.
	private String addCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = (request.getParameter("name")).trim();
		Date date = new Date();

		Category addCategory = new Category();
		addCategory.setName(name);
		addCategory.setLastUpdate(date);
		request.setAttribute("category", addCategory);
		String message = "";
		String url = "";

		if (!ValidatorUtil.checkParam(name)) {
			message = "Field cannot be empty.";
			request.setAttribute("message", message);
			url = "/admin/addCategory.jsp";
		} else {
			// No two categories can have the same name in the database.
			if (DBCategory.categoryNameExists(name)) {
				message = "This name already exists for another category. "
						+ "<br> Kindly provide another name.";
				request.setAttribute("message", message);
				url = "/admin/addCategory.jsp";
			} else {
				DBCategory.addCategory(addCategory);
				url = getCategories(request, response);
			}
		}

		return url;
	}

	// Retrieve Category name from the database.
	public String getCategoryName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Category category = DBCategory.getCategoryById(id);
		if (category == null) {
			request.setAttribute("message",
					"Selected category no longer exist.");
			return getCategories(request, response);
		} else {
			String categoryName = category.getName();
			HttpSession session = request.getSession();
			session.setAttribute("categoryName", categoryName);
			// request.setAttribute("categoryName", categoryName);
			return "/admin/editCategory.jsp";
		}

	}

	// Update Category obj in the database.
	private String editCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String oldName = request.getParameter("oldName");
		String newName = request.getParameter("newName").trim();
		Category category = DBCategory.getCategoryByName(oldName);
		category.setName(newName);
		category.setLastUpdate(new Date());
		String message = "";
		String url = "";

		if (!ValidatorUtil.checkParam(newName)) {
			message = "Field cannot be empty.";
			request.setAttribute("message", message);
			url = "/admin/editCategory.jsp";
		} else {
			if (DBCategory.categoryNameExists(newName)) {
				message = "This name already exists for a category. "
						+ "<br> Kindly provide another name.";
				request.setAttribute("message", message);
				request.setAttribute("categoryName", newName);
				url = "/admin/editCategory.jsp";
			} else {
				DBCategory.updateCategory(category);
				url = getCategories(request, response);
			}
		}

		return url;
	}

	// Delete a Category obj from the database.
	private String deleteCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Category category = DBCategory.getCategoryById(id);
		DBCategory.deleteCategory(category);
		String url = getCategories(request, response);

		return url;
	}

	// Get category objects from the database.
	private String getCategories(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		List<Category> categories = DBCategory.getCategories();
		HttpSession session = request.getSession();
		session.setAttribute("categories", categories);
		return "/admin/categoryList.jsp";
	}

	// Insert Product obj into the database.
	private String addProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String code = request.getParameter("code").trim();
		String description = request.getParameter("description").trim();
		double price = Double.parseDouble(request.getParameter("price"));
		byte[] image = uploadFile(request, response);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String categoryName = request.getParameter("categoryName");

		Category category = DBCategory.getCategoryByName(categoryName);
		Product product = new Product();
		product.setCode(code);
		product.setDescription(description);
		product.setPrice(price);
		product.setImage(image);
		product.setQuantity(quantity);
		product.setCategory(category);
		product.setLastUpdate(new Date());

		request.setAttribute("product", product);

		String message = "";
		String url = "";

		if (!ValidatorUtil.checkParam(code)
				|| !ValidatorUtil.checkParam(description)) {
			message = "No field can be empty";
			request.setAttribute("message", message);
			url = "/admin/addProduct.jsp";
		} else {
			// No two product can have the same code.
			if (DBProduct.productCodeExists(code)) {
				message = "This code already exist for another product. "
						+ "<br> Kindly provide another code.";
				request.setAttribute("message", message);
				url = "/admin/addProduct.jsp";
			}// A product must belong to only one existing category in the
				// database.
			else if (category == null) {
				message = "The selected category no longer exist. "
						+ "<br> Kindly select another category.";
				request.setAttribute("message", message);
				url = "/admin/addProduct.jsp";
			} else {
				DBProduct.addProduct(product);
				url = getProducts(request, response);
			}

		}
		return url;

	}

	// Delete Product obj from the database.
	private String deleteProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = DBProduct.getProductById(id);
		DBProduct.deleteProduct(product);
		return getProducts(request, response);
	}

	// Retrieve product image from the database.
	private byte[] uploadFile(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Part filePart = request.getPart("image");

			InputStream fileContent = filePart.getInputStream();
			byte[] content = IOUtils.toByteArray(fileContent);
			return content;
		} catch (IllegalStateException | IOException | ServletException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retrieve Product objects from the database.
	private String getProducts(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Product> products = DBProduct.getProducts();
		HttpSession session = request.getSession();
		session.setAttribute("products", products);
		return "/admin/productList.jsp";
	}

	// Retrieve all products belonging to a particular category.
	private String getProductsByCategoryId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		int id = Integer.parseInt(request.getParameter("id"));

		List<Category> categories = DBCategory.getCategories();

		Category category = null;
		for (Category c : categories) {
			category = c;
			if (category.getId() == id) {
				break;
			}
		}

		session.setAttribute("category", category);
		return "/admin/displayProductsByCategoryId.jsp";
	}

	// Retrieve Product obj from the database.
	private String getProductData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = DBProduct.getProductById(id);

		if (product == null) {
			request.setAttribute("message", "selected product no longer exist.");
			return getProducts(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("editProduct", product);
			return "/admin/editProduct.jsp";
		}

	}

	// Update product data in the database.
	private String editProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String code = request.getParameter("code");
		String description = request.getParameter("description").trim();
		double price = Double.parseDouble(request.getParameter("price"));
		byte[] image = uploadFile(request, response);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String categoryName = request.getParameter("categoryName");

		Category category = DBCategory.getCategoryByName(categoryName);
		Product product = DBProduct.getProductById(id);
		product.setCode(code);
		product.setDescription(description);
		product.setPrice(price);
		product.setImage(image);
		product.setQuantity(quantity);
		product.setCategory(category);
		product.setLastUpdate(new Date());

		request.setAttribute("product", product);

		String message = "";
		String url = "";

		if (!ValidatorUtil.checkParam(description)) {
			message = "No field can be empty.";
			request.setAttribute("message", message);
			url = "/admin/editProduct.jsp";
		} else if (!ValidatorUtil.checkParam(categoryName)) {
			message = "The selected category no longer exist. "
					+ "<br> Kindly select another category.";
			request.setAttribute("message", message);
			url = "/admin/editProduct.jsp";
		} else {
			if (category == null) {
				message = "The selected category no longer exist. "
						+ "<br> Kindly select another category.";
				request.setAttribute("message", message);
				url = "/admin/editProduct.jsp";
			} else {
				DBProduct.updateProduct(product);
				url = getProducts(request, response);
			}
		}

		return url;

	}

	// Retrieve orders form the database.
	private String getOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String startDate = request.getParameter("startDate").trim();
		String endDate = request.getParameter("endDate").trim();
		String message = "";
		String url = "";
		List<CustomerOrder> orders = null;

		if (!ValidatorUtil.checkDate(startDate)
				|| !ValidatorUtil.checkDate(endDate)
				|| !ValidatorUtil.checkParam(startDate)
				|| !ValidatorUtil.checkParam(endDate)) {
			message = "enter date in the right format";
			request.setAttribute("message", message);
			url = "/admin/orderParameters.jsp";
		} else {
			orders = DBCustomerOrder.getOrders(startDate, endDate);
			HttpSession session = request.getSession();
			session.setAttribute("orders", orders);
			url = "/admin/orders.jsp";
		}

		return url;
	}

	// Retrieve an Order obj from the database.
	@SuppressWarnings("unchecked")
	private String displayOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		List<CustomerOrder> orders = (List<CustomerOrder>) session
				.getAttribute("orders");
		CustomerOrder order = null;
		Payment payment = null;

		for (CustomerOrder customerOrder : orders) {
			order = customerOrder;
			if (order.getId() == id) {
				payment = DBPayment.getPaymentByOrderId(id);
				break;
			}
		}
		session.setAttribute("order", order);
		session.setAttribute("payment", payment);
		return "/admin/order.jsp";
	}

	// Update an order in the database.
	@SuppressWarnings("unchecked")
	private String updateCustomerOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		List<CustomerOrder> orders = (List<CustomerOrder>) session
				.getAttribute("orders");
		CustomerOrder order = null;

		for (CustomerOrder customerOrder : orders) {
			order = customerOrder;
			if (order.getId() == id)
				break;
		}

		order.setStatus("closed");
		DBCustomerOrder.updateCustomerOrder(order);
		return "/admin/order.jsp";
	}

	// Retrieve all Payment objects from the database.
	private String getPayments(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String startDate = request.getParameter("startDate").trim();
		String endDate = request.getParameter("endDate").trim();
		String message = "";
		String url = "";
		List<Payment> payments = null;

		if (!ValidatorUtil.checkDate(startDate)
				|| !ValidatorUtil.checkDate(endDate)
				|| !ValidatorUtil.checkParam(startDate)
				|| !ValidatorUtil.checkParam(endDate)) {
			message = "enter date in the right format";
			request.setAttribute("message", message);
			url = "/admin/paymentParameters.jsp";
		} else {
			payments = DBPayment.getPayments(startDate, endDate);
			HttpSession session = request.getSession();
			session.setAttribute("payments", payments);
			url = "/admin/payments.jsp";
		}

		return url;
	}

	// Retrieve all emailSubscriber objects from the database.
	private String getEmailSubscribers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<EmailSubscriber> emailSubscribers = DBEmail.getEmailSubscribers();

		HttpSession session = request.getSession();
		session.setAttribute("emailSubscribers", emailSubscribers);

		return "/admin/emailList.jsp";
	}

	// Generate an excel document of email subscribers
	private void getEmailSubscribersReport(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// Workbook workbook = DBReportDownload.getEmailSubscribersReport();
		XSSFWorkbook workbook = (XSSFWorkbook) DBReportDownload
				.getEmailSubscribersReport();
		response.setHeader("content-disposition",
				"attachment; filename = emailSubscribers.xlsx");
		response.setHeader("cache-control", "no-cache");

		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.close();
	}

	// Generate excel document.
	private String getReport(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String reportName = request.getParameter("reportName");
		String startDate = request.getParameter("startDate").trim();
		String endDate = request.getParameter("endDate").trim();
		String message = "";
		String url = "";
		if (!ValidatorUtil.checkDate(startDate)
				|| !ValidatorUtil.checkDate(endDate)
				|| !ValidatorUtil.checkParam(startDate)
				|| !ValidatorUtil.checkParam(endDate)) {
			message = "enter date in the right format";
			request.setAttribute("message", message);
			url = "/admin/downloadParameters.jsp";
		} else {
			XSSFWorkbook workbook;
			if (reportName.equalsIgnoreCase("Orders report")) {
				workbook = (XSSFWorkbook) DBReportDownload.getOrderReport(
						startDate, endDate);
			} else if (reportName.equalsIgnoreCase("Payments report")) {
				workbook = (XSSFWorkbook) DBReportDownload.getPaymentReport(
						startDate, endDate);
			} else {
				workbook = new XSSFWorkbook();
			}

			response.setHeader("content-disposition", "attachment; filename = "
					+ reportName + ".xlsx");
			response.setHeader("cache-control", "no-cache");

			OutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			outputStream.close();
			url = "/admin/downloadParameters.jsp";
		}
		return url;

	}

}
