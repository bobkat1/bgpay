/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.voucher.enumerations;

import bgpay.voucher.Voucher;

public enum PayRates {
	
	UNION(Voucher.DEFAULT_UNION_RATE, 0), NONUNION(Voucher.NON_UNION_RATE, 1), SAE(Voucher.SAE_RATE, 1);
	
	private double rate;
	private int index;
	
	private PayRates(double rate, int index) {
		this.rate = rate;
		this.index = index;
	}
	
	public double getRate() {
		return rate;
	}
	
	public int getIndex() {
		return index;
	}

}
