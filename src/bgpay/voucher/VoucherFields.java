/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.voucher;

public enum VoucherFields {
	
	PRODUCTION_NAME("productionName"), PRODUCTION_COMPANY("productionCompany"), PAY_RATE("payRate"), START_DATE_TIME("startDateTime"),
	END_DATE_TIME("endDateTime"), PAID("paid");
	
	private String fieldTitle;
	
	private VoucherFields(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	
	public String getFieldTitle() {
		return fieldTitle;
	}

}
