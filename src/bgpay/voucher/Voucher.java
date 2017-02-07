package bgpay.voucher;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

import bgpay.util.DateAndTimeFormats;
import bgpay.voucher.enumerations.PayRates;

/**
 * @author Robert Miki A00990619
 * @version
 */

public class Voucher extends Observable implements Serializable, Comparable<Voucher>{

	private static final long serialVersionUID = 9214918170106433931L;
	public static final double DEFAULT_UNION_RATE = 23.75;
	public static final double SAE_RATE = 31.45;
	public static final double NON_UNION_RATE = 11.75;

	public static class Builder {

		// Fields
		private String productionName;
		private LocalDateTime startDateTime;
		private LocalDateTime endDateTime;
		private double payRate;

		private String productionCompany;
		private PayRates rateEnum;
		private boolean isPaid;

		public Builder(String productionName, LocalDateTime startDateTime, LocalDateTime endDateTime, double payRate) {
			setProductionName(productionName);
			setStartDateTime(startDateTime);
			setEndDateTime(endDateTime);
			setPayRate(payRate);
			setPayRateEnum(payRate);
		}

		// Builders

		/**
		 * 
		 * @param productionCompany
		 * @return
		 */
		public Builder productionCompany(String productionCompany) {
			setProductionCompany(productionCompany);
			return this;
		}

		public Builder isPaid(boolean isPaid) {
			setIsPaid(isPaid);
			return this;
		}

		/**
		 * Builds a Voucher from the builder pattern
		 * 
		 * @return
		 */
		public Voucher build() {
			return new Voucher(this);
		}

		// Inner Class setter methods

		/**
		 * Sets productionName if Valid
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
		 * @param startDateTime
		 */
		public void setStartDateTime(LocalDateTime startDateTime) {
			this.startDateTime = startDateTime;
		}

		/**
		 * 
		 * @param endDateTime
		 */
		public void setEndDateTime(LocalDateTime endDateTime) {
			this.endDateTime = endDateTime;
		}

		/**
		 * 
		 * @param payRate
		 */
		public void setPayRate(double payRate) {
			if (payRate > 0)
				this.payRate = payRate;
		}

		/**
		 * 
		 * @param productionCompany
		 */
		public void setProductionCompany(String productionCompany) {
			if (productionCompany != null && productionCompany.length() > 0) {
				this.productionCompany = productionCompany;
			}
		}

		/**
		 * Sets the class payRate field corresponding to the rate
		 * 
		 * @param rate
		 */
		public void setPayRateEnum(double payRate) {
			PayRates[] payRates = PayRates.values();
			for (PayRates rate : payRates) {
				if (payRate == rate.getRate()) {
					rateEnum = rate;
				}
			}
		}

		public void setIsPaid(boolean isPaid) {
			this.isPaid = isPaid;
		}

		/**
		 * @param rateEnum
		 *            the rateEnum to set
		 */
		public void setRateEnum(PayRates rateEnum) {
			this.rateEnum = rateEnum;
		}

		/**
		 * @param isPaid
		 *            the isPaid to set
		 */
		public void setPaid(boolean isPaid) {
			this.isPaid = isPaid;
		}

	}

	private String productionName;
	private String productionCompany;
	private double payRate;
	private PayRates rateEnum;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private boolean isPaid;

	public Voucher() {

	}

	/**
	 * Constructor uses a Builder pattern to create an instance of itself
	 * 
	 * @param builder
	 */
	public Voucher(Builder builder) {
		this.productionName = builder.productionName;
		this.productionCompany = builder.productionCompany;
		this.payRate = builder.payRate;
		this.rateEnum = builder.rateEnum;
		this.startDateTime = builder.startDateTime;
		this.endDateTime = builder.endDateTime;
		this.isPaid = builder.isPaid;
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
		return payRate;
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
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * 
	 * @return LocalDate portion of startDateTime
	 */
	public LocalDate getStartDate() {
		return startDateTime.toLocalDate();
	}

	/**
	 * 
	 * @return LocalTime portion of startDateTime
	 */
	public LocalTime getStartTime() {
		return startDateTime.toLocalTime();
	}

	/**
	 * @return the endDateTime
	 */
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	/**
	 * 
	 * @return LocalDate portion of endDateTime
	 */
	public LocalDate getEndDate() {
		return endDateTime.toLocalDate();
	}

	/**
	 * 
	 * @return LocalTimePortion of endDateTime
	 */
	public LocalTime getEndTime() {
		return endDateTime.toLocalTime();
	}

	/**
	 * @return isPaid
	 */
	public boolean getIsPaid() {
		return isPaid;
	}
	
	/**
	 * 
	 * @return the hour and minute difference between the startDateTime and endDateTime
	 */
	public String getHoursWorked() {
		
		long hours = ChronoUnit.HOURS.between(startDateTime, endDateTime);
		long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime) - (hours * 60);
		int decimalMin; 
		if (minutes == 0) {
			decimalMin = 0;
		} else 
			decimalMin = (int) (60 / minutes);
		
		return hours + "." + decimalMin + " hours";
	}

	/**
	 * 
	 * @param productionName
	 */
	public void setProductionName(String productionName) {
		if (productionName != null && productionName.length() > 0) {
			this.productionName = productionName;
			setChanged();
		}
	}

	/**
	 * 
	 * @param productionCompany
	 */
	public void setProductionCompany(String productionCompany) {
		if (productionCompany != null && productionCompany.length() > 0) {
			this.productionCompany = productionCompany;
			setChanged();
		}
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(double payRate) {
		if (payRate > 0) {
			this.payRate = payRate;
			setRateEnum(payRate);
			setChanged();
		}
	}

	/**
	 * Sets the class payRate field corresponding to the rate
	 * 
	 * @param rate
	 */
	public void setRateEnum(double payRate) {
		PayRates[] payRates = PayRates.values();
		for (PayRates rate : payRates) {
			if (payRate == rate.getRate()) {
				rateEnum = rate;
			}
		}
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
		
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
		
	}

	/**
	 * 
	 * @param isPaid
	 */
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
		setChanged();
	}

	@Override
	public int compareTo(Voucher o) {
		if (this.equals(null) || o.equals(null))
			return 0;
		return this.startDateTime.compareTo(o.getStartDateTime());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((productionCompany == null) ? 0 : productionCompany.hashCode());
		result = prime * result + ((productionName == null) ? 0 : productionName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(payRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rateEnum == null) ? 0 : rateEnum.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
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
		if (endDateTime == null) {
			if (other.endDateTime != null) {
				return false;
			}
		} else if (!endDateTime.equals(other.endDateTime)) {
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
		if (Double.doubleToLongBits(payRate) != Double.doubleToLongBits(other.payRate)) {
			return false;
		}
		if (rateEnum != other.rateEnum) {
			return false;
		}
		if (startDateTime == null) {
			if (other.startDateTime != null) {
				return false;
			}
		} else if (!startDateTime.equals(other.startDateTime)) {
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
			return startDateTime.format(DateAndTimeFormats.DATETIMEFORMAT) + "|" + productionName + "|" + productionCompany +  "|"  + rateEnum.toString() + "|" + getHoursWorked() + "|Paid";
		else
			return startDateTime.format(DateAndTimeFormats.DATETIMEFORMAT) + "|" + productionName + "|" + productionCompany + "|" + rateEnum.toString()  + "|" + getHoursWorked() + "|Unpaid";
	}

}
