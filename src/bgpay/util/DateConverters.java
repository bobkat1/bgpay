/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverters {

	/**
	 * Converts a java.util.Date instance to a java.time.LocalDate instance
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate convertToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date convertToDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

}
