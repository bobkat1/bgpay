/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.voucher;

public enum VoucherFields {
	
	PRODUCTION_NAME("productionName"), PRODUCTION_COMPANY("productionCompany"), RATE("Rate"), START_DATE("startDate"),
	END_DATE("endDate"), START_TIME("startTime"), END_TIME("endTime"), PAID("paid");
	
	private String fieldTitle;
	
	private VoucherFields(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	
	public String getFieldTitle() {
		return fieldTitle;
	}

}
