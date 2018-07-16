package appUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

	// Method used to initialise the application scope variable,
	// currentYear.
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		GregorianCalendar currentDate = new GregorianCalendar();
		int currentYear = currentDate.get(Calendar.YEAR);
		servletContext.setAttribute("currentYear", currentYear);

	}

	public void contextDestroyed(ServletContextEvent event) {
		// no cleanup necessary
	}

}
