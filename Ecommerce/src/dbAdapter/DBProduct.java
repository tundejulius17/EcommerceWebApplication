package dbAdapter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import javax.persistence.TypedQuery;

import appUtil.DBUtil;

import dataModel.Product;

public class DBProduct {

	// This inserts product obj into the database.
	public static void addProduct(Product product) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(product);
			entityTrans.commit();
		} catch (Exception e) {
			entityTrans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	// This deletes product obj from the database.
	public static void deleteProduct(Product product) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.remove(em.merge(product));
			entityTrans.commit();

		} catch (Exception e) {
			entityTrans.rollback();

		} finally {
			em.close();
		}
	}

	// This updates product obj in the database.
	public static void updateProduct(Product product) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.merge(product);
			entityTrans.commit();
		} catch (Exception e) {
			// System.out.println(e);
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// This retrieves product obj from the database by its id.
	public static Product getProductById(long id) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		try {
			return em.find(Product.class, id);
		} finally {
			em.close();
		}
	}

	// This retrieves product obj from the database by its code.
	public static Product getProductByCode(String code) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT p FROM Product p WHERE p.code = :code";
		TypedQuery<Product> q = em.createQuery(query, Product.class);
		q.setParameter("code", code);
		Product product = null;

		try {
			product = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

		return product;
	}

	// This retrieves product objects from the database.
	public static List<Product> getProducts() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT p FROM Product p ORDER BY p.lastUpdate";
		TypedQuery<Product> q = em.createQuery(query, Product.class);
		List<Product> products;

		try {
			products = q.getResultList();
			if (products == null || products.isEmpty())
				products = null;
		} finally {
			em.close();
		}
		return products;
	}

	// This retrieves product obj image from the database.
	public static byte[] getProductImage(long id) {

		Product product = getProductById(id);
		return product.getImage();
	}

	// This checks if a product obj already exists in the database.
	public static boolean productCodeExists(String code) {
		Product product = getProductByCode(code);
		return product != null;
	}

}
