package dbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import appUtil.DBUtil;
import dataModel.CustomerOrder;
import dataModel.EmailSubscriber;
import dataModel.Payment;

public class DBReportDownload {

	// This creates an excel workbook of all email subscribers.
	public static Workbook getEmailSubscribersReport() {
		List<EmailSubscriber> emailSubscribers = DBEmail.getEmailSubscribers();

		// create a workbook, sheet and its title

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Email Subscribers Report");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("The Email Subscribers Report");

		// create row headers
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("LastName");
		row.createCell(1).setCellValue("FirstName");
		row.createCell(2).setCellValue("Email");

		// create data rows

		int i = 3;
		for (EmailSubscriber e : emailSubscribers) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(e.getLastName());
			row.createCell(1).setCellValue(e.getFirstName());
			row.createCell(2).setCellValue(e.getEmail());
			i++;
		}

		return workbook;
	}

	// This creates an excel workbook of all orders.
	public static Workbook getOrderReport(String startDate, String endDate) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT co from CustomerOrder co "
				+ "WHERE co.orderDate >= :startDate AND "
				+ "co.orderDate <= :endDate ORDER BY co.orderDate DESC";
		TypedQuery<CustomerOrder> q = em
				.createQuery(query, CustomerOrder.class);
		List<CustomerOrder> orders = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			q.setParameter("startDate", sdf.parse(startDate));
			q.setParameter("endDate", sdf.parse(endDate));
			orders = q.getResultList();
		} catch (ParseException e) {
			System.err.println(e.toString());
			return null;
		} finally {
			em.close();
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Orders Report");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("The Orders Report");

		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Start Date: " + startDate);
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("End Date: " + endDate);

		row = sheet.createRow(5);
		row.createCell(0).setCellValue("OrderID");
		row.createCell(1).setCellValue("OrderDate");
		row.createCell(2).setCellValue("OrderStatus");
		row.createCell(3).setCellValue("OrderAmount");
		row.createCell(4).setCellValue("CustomerID");

		int total = 0;
		int i = 6;
		for (CustomerOrder order : orders) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(order.getId().toString());
			row.createCell(1).setCellValue(order.getOrderDate().toString());
			row.createCell(2).setCellValue(order.getStatus());
			row.createCell(3).setCellValue(order.getTotalPrice());
			row.createCell(4).setCellValue(
					order.getCustomer().getId().toString());
			total++;
			i++;
		}
		row = sheet.createRow(i + 1);
		row.createCell(0).setCellValue("Total number of orders: " + total);
		return workbook;
	}

	// This creates an excel workbook of all payments.
	public static Workbook getPaymentReport(String startDate, String endDate) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT p from Payment p "
				+ "WHERE p.paymentDate >= :startDate AND "
				+ "p.paymentDate <= :endDate ORDER BY p.paymentDate DESC";
		TypedQuery<Payment> q = em.createQuery(query, Payment.class);
		List<Payment> payments = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			q.setParameter("startDate", sdf.parse(startDate));
			q.setParameter("endDate", sdf.parse(endDate));
			payments = q.getResultList();
		} catch (ParseException e) {
			System.err.println(e.toString());
			return null;
		} finally {
			em.close();
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Payment Report");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("The Payment Report");

		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Start Date: " + startDate);
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("End Date: " + endDate);

		row = sheet.createRow(5);
		row.createCell(0).setCellValue("paymentID");
		row.createCell(1).setCellValue("Amount");
		row.createCell(2).setCellValue("PaymentDate");
		row.createCell(3).setCellValue("PaymentStatus");
		row.createCell(4).setCellValue("TransactionID");
		row.createCell(5).setCellValue("CustomerID");
		row.createCell(6).setCellValue("OrderID");

		int total = 0;
		int i = 6;
		for (Payment p : payments) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(p.getId().toString());
			row.createCell(1).setCellValue(p.getAmount());
			row.createCell(2).setCellValue(p.getPaymentDate().toString());
			row.createCell(3).setCellValue(p.getPaymentStatus());
			row.createCell(4).setCellValue(p.getTransactionId());
			row.createCell(5).setCellValue(p.getCustomer().getId().toString());
			row.createCell(6).setCellValue(
					p.getCustomerOrder().getId().toString());

			total++;
			i++;
		}
		row = sheet.createRow(i + 1);
		row.createCell(0).setCellValue("Total number of payments: " + total);
		return workbook;
	}
}
