package dbAdapter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import appUtil.DBUtil;

import dataModel.EmailSubscriber;

public class DBEmail {

	// This inserts emailSubscriber into the database.
	public static void addEmailSubscriber(EmailSubscriber emailSubscriber) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(emailSubscriber);
			entityTrans.commit();
		} catch (Exception e) {
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// This retrieves emailSubscriber from the database.
	public static EmailSubscriber getSubscriber(String email) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT e FROM EmailSubscriber e WHERE e.email = :email";
		TypedQuery<EmailSubscriber> q = em.createQuery(query,
				EmailSubscriber.class);
		q.setParameter("email", email);
		EmailSubscriber emailSubscriber = null;

		try {
			emailSubscriber = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

		return emailSubscriber;
	}

	// This checks if an emailSubscriber already exists in the database.
	public static boolean emailExists(String email) {
		EmailSubscriber emailSubscriber = getSubscriber(email);
		return emailSubscriber != null;
	}

	// This retrieves emailSubscribers from the database.
	public static List<EmailSubscriber> getEmailSubscribers() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT e FROM EmailSubscriber e ORDER BY e.lastName";

		TypedQuery<EmailSubscriber> q = em.createQuery(query,
				EmailSubscriber.class);
		List<EmailSubscriber> emailSubscribers = null;

		try {
			emailSubscribers = q.getResultList();
			if (emailSubscribers == null || emailSubscribers.isEmpty())
				emailSubscribers = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return emailSubscribers;
	}
}
