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
	public static LocalDate convertToDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
