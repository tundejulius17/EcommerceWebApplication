package dbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import appUtil.DBUtil;
import dataModel.Payment;

public class DBPayment {

	// This inserts payment object into the database.
	public static void addPayment(Payment payment) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(payment);
			entityTrans.commit();
		} catch (Exception e) {
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// This retrieves payment objects from the database.
	public static List<Payment> getPayments(String startDate, String endDate) {
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
		} catch (ParseException e) {
			System.err.println(e.toString());
			return null;
		}

		try {
			payments = q.getResultList();
			if (payments == null || payments.isEmpty())
				payments = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return payments;
	}

	// This retrieves payment object from the database
	public static Payment getPaymentByOrderId(int id) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		String query = "SELECT p FROM Payment p WHERE p.customerOrder.id = :id";
		TypedQuery<Payment> q = em.createQuery(query, Payment.class);
		q.setParameter("id", id);
		Payment payment = null;

		try {
			payment = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

		return payment;
	}

}
