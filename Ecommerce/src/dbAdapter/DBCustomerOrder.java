package dbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import appUtil.DBUtil;
import dataModel.CustomerOrder;

public class DBCustomerOrder {

	// This inserts customerOrder object into the database.
	public static void addCustomerOrder(CustomerOrder customerOrder) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(customerOrder);
			entityTrans.commit();
		} catch (Exception e) {
			entityTrans.rollback();
			e.printStackTrace();

		} finally {
			em.close();
		}
	}

	// This updates customerOrder object in the database.
	public static void updateCustomerOrder(CustomerOrder customerOrder) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.merge(customerOrder);
			entityTrans.commit();
		} catch (Exception e) {
			System.out.println(e);
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// This retrieves customerOrder objects from the database.
	public static List<CustomerOrder> getOrders(String startDate, String endDate) {
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
		} catch (ParseException e) {
			System.err.println(e.toString());
			return null;
		}

		try {
			orders = q.getResultList();
			if (orders == null || orders.isEmpty())
				orders = null;
		}catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		
		return orders;
	}

	// This retrieves open customer orders from the database.
	public static List<CustomerOrder> getOpenOrders() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT co FROM CustomerOrder co"
				+ "WHERE oc.status = 'open' ORDER BY co.orderDate DESC";
		TypedQuery<CustomerOrder> q = em
				.createQuery(query, CustomerOrder.class);
		List<CustomerOrder> openOrders = null;

		try {
			openOrders = q.getResultList();
			if (openOrders == null || openOrders.isEmpty())
				openOrders = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return openOrders;
	}

	// This retrieves open customer orders from the database.
	public static List<CustomerOrder> getClosedOrders() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT co FROM CustomerOrder co"
				+ "WHERE oc.status = 'closed' ORDER BY co.orderDate DESC";
		TypedQuery<CustomerOrder> q = em
				.createQuery(query, CustomerOrder.class);
		List<CustomerOrder> closedOrders = null;

		try {
			closedOrders = q.getResultList();
			if (closedOrders == null || closedOrders.isEmpty())
				closedOrders = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return closedOrders;
	}

}
