package bgpay.voucher;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import bgpay.ui.PayRates;

/**
 * @author Robert Miki A00990619
 * @version
 */

public class Voucher implements Serializable, Comparable<Voucher> {

	private static final long serialVersionUID = 9214918170106433931L;
	public static final double DEFAULT_UNION_RATE = 23.75;
	public static final double SAE_RATE = 31.45;
	public static final double NON_UNION_RATE = 11.75;

	private String productionName;
	private String productionCompany;
	private double rate;
	private PayRates rateEnum;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean isPaid;

	public Voucher() {

	}

	/**
	 * 
	 * @param productionName
	 * @param productionComapny
	 * @param rate
	 * @param startTime
	 * @param endTime
	 * @param isPaid
	 */
	public Voucher(String productionName, String productionCompany, double rate, LocalDate startDate, LocalDate endDate, LocalTime startTime,
			LocalTime endTime, boolean isPaid) {
		setProductionName(productionName);
		setProductionCompany(productionCompany);
		setRate(rate);
		setRateEnum(rate);
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isPaid = isPaid;
	}

	/**
	 * @return productionName
	 */
	public String getProductionName() {
		return productionName;
	}

	/**
	 * 
	 * @return productionComapny
	 */
	public String getProductionCompany() {
		return productionCompany;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	
	/**
	 * @return rateEnum
	 */
	public PayRates getPayRateEnum() {
		return rateEnum;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	public Date convertSDate() {
		return Date.valueOf(startDate);
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	public Date convertEDate() {
		return Date.valueOf(endDate);
	}

	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * @return isPaid
	 */
	public boolean getIsPaid() {
		return isPaid;
	}

	/**
	 * 
	 * @param productionName
	 */
	public void setProductionName(String productionName) {
		if (productionName != null && productionName.length() > 0) {
			this.productionName = productionName;
		}
	}

	/**
	 * 
	 * @param productionCompany
	 */
	public void setProductionCompany(String productionCompany) {
		if (productionCompany != null && productionCompany.length() > 0)
			this.productionCompany = productionCompany;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(double rate) {
		if (rate > 0)
			this.rate = rate;
	}
	
	/**
	 * Sets the class payRate field corresponding to the rate
	 * @param rate
	 */
	private void setRateEnum(double rate) {
		PayRates[] payRates = PayRates.values();
		for (PayRates payRate : payRates) {
			if (rate == payRate.getRate()) {
				rateEnum = payRate;
			}
		}
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isPaid == true)
			return startDate + " " + productionName + " " + productionCompany + " Yes";
		else
			return startDate + " " + productionName + " " + productionCompany + " No";
	}

	@Override
	public int compareTo(Voucher o) {
		return this.startDate.compareTo(o.getStartDate());
	}

}
