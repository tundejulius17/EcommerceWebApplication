package dbAdapter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import appUtil.DBUtil;
import dataModel.Customer;

public class DBCustomer {

	// Method to insert customer obj into the database.
	public static void addCustomer(Customer customer) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(customer);
			entityTrans.commit();
		} catch (Exception e) {
			entityTrans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	// Method to retrieve customer objects from the database.
	public static List<Customer> getCustomers() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT c FROM Customer c ORDER BY c.firstName";
		TypedQuery<Customer> q = em.createQuery(query, Customer.class);
		List<Customer> customers = null;

		try {
			customers = q.getResultList();
			if (customers == null || customers.isEmpty())
				customers = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return customers;
	}

	// Method to update a customer object in the database.
	public static void updateCustomer(Customer customer) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.merge(customer);
			entityTrans.commit();
		} catch (Exception e) {
			System.out.println(e);
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// Method to retrieve customer obj from the database.
	public static Customer getCustomer(String email) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT c FROM Customer c WHERE c.email = :email";
		TypedQuery<Customer> q = em.createQuery(query, Customer.class);
		q.setParameter("email", email);
		Customer customer = null;

		try {
			customer = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

		return customer;
	}

	// This checks if a customer already exist in the database.
	public static boolean customerEmailExists(String email) {
		Customer customer = getCustomer(email);
		return customer != null;
	}
}
