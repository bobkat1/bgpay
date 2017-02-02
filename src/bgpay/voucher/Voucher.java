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


	@Override
	public int compareTo(Voucher o) {
		if (this.equals(null) || o.equals(null))
			return 0;
		return this.startDate.compareTo(o.getStartDate());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((productionCompany == null) ? 0 : productionCompany.hashCode());
		result = prime * result + ((productionName == null) ? 0 : productionName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rateEnum == null) ? 0 : rateEnum.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Voucher)) {
			return false;
		}
		Voucher other = (Voucher) obj;
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!endTime.equals(other.endTime)) {
			return false;
		}
		if (isPaid != other.isPaid) {
			return false;
		}
		if (productionCompany == null) {
			if (other.productionCompany != null) {
				return false;
			}
		} else if (!productionCompany.equals(other.productionCompany)) {
			return false;
		}
		if (productionName == null) {
			if (other.productionName != null) {
				return false;
			}
		} else if (!productionName.equals(other.productionName)) {
			return false;
		}
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate)) {
			return false;
		}
		if (rateEnum != other.rateEnum) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isPaid == true)
			return startDate + " " + productionName + " " + productionCompany + " Paid";
		else
			return endDate + " " + productionName + " " + productionCompany + " Not Paid";
	}


}
