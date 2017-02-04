/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateAndTimeFormats {

	public static final DateTimeFormatter TIMEFORMATTER = DateTimeFormatter.ofPattern("HHmm");
	public static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
	public static final DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);

}
