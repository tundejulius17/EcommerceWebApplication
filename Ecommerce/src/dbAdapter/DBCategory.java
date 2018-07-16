package dbAdapter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import javax.persistence.TypedQuery;

import appUtil.DBUtil;
import dataModel.Category;

public class DBCategory {

	// Method to add a Category object to the database.
	public static void addCategory(Category category) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.persist(category);
			entityTrans.commit();
			// return "";
		} catch (Exception e) {
			entityTrans.rollback();
			e.printStackTrace();

		} finally {
			em.close();
		}
	}

	// Method to delete a Category object from the database.
	public static void deleteCategory(Category category) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.remove(em.merge(category));
			entityTrans.commit();

		} catch (Exception e) {
			entityTrans.rollback();

		} finally {
			em.close();
		}
	}

	// Method to update category
	public static void updateCategory(Category category) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction entityTrans = em.getTransaction();
		entityTrans.begin();

		try {
			em.merge(category);
			entityTrans.commit();
		} catch (Exception e) {
			System.out.println(e);
			entityTrans.rollback();
		} finally {
			em.close();
		}
	}

	// Method to retrieve a Category object by id from the database.
	public static Category getCategoryById(long id) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();

		try {
			return em.find(Category.class, id);
		} finally {
			em.close();
		}

	}

	// Method to retrieve Category objects from the database.
	public static List<Category> getCategories() {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT c FROM Category c ORDER BY c.lastUpdate";
		TypedQuery<Category> q = em.createQuery(query, Category.class);
		List<Category> categories = null;

		try {
			categories = q.getResultList();
			if (categories == null || categories.isEmpty())
				categories = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return categories;
	}
	
	// Method to retrieve categories from the database 
	public static List<Category> getCustCategories(){
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT c FROM Category c ORDER BY c.name";
		TypedQuery<Category> q = em.createQuery(query, Category.class);
		List<Category> categories = null;

		try {
			categories = q.getResultList();
			if (categories == null || categories.isEmpty())
				categories = null;
		} catch (NoResultException n) {
			n.printStackTrace();
		} finally {
			em.close();
		}
		return categories;
	}

	// Method to get category obj by its name from the database.
	public static Category getCategoryByName(String name) {
		EntityManager em = DBUtil.getEntityManagerFactory()
				.createEntityManager();
		String query = "SELECT c FROM Category c WHERE c.name = :name";
		TypedQuery<Category> q = em.createQuery(query, Category.class);
		q.setParameter("name", name);
		Category category = null;

		try {
			category = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

		return category;
	}

	// Method to check if the name of a category already
	// exists in the database.
	public static boolean categoryNameExists(String name) {
		Category category = getCategoryByName(name);

		/*
		 * if(category==null) return false; else return true;
		 */
		return category != null;
	}

}
