/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.voucher.enumerations;

public enum Paid {
	
	YES(true), NO(false);
	
	boolean isPaid;
	
	private Paid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public boolean getIsPaid() {
		return isPaid;
	}

}
