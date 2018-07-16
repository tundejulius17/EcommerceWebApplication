package appUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
	// An EntityManagerFactory object is declared.
	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("eCommercePU");

	// Static method to return EntityManagerFactory object.
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

}
