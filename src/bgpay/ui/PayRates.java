/**
 * @author Robert Miki A00990619
 * @version 
 */
package bgpay.ui;

import bgpay.voucher.Voucher;

public enum PayRates {
	
	UNION(Voucher.DEFAULT_UNION_RATE), NONUNION(Voucher.NON_UNION_RATE), SAE(Voucher.SAE_RATE);
	
	private double rate;
	
	private PayRates(double rate) {
		this.rate = rate;
	}
	
	public double getRate() {
		return rate;
	}

}
