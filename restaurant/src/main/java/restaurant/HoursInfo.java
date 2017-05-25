package restaurant;

import java.util.List;

public class HoursInfo {
	private String status;
	private Boolean isOpen;
	private List<TimeframeInfo> timeframes;

	public HoursInfo(String status, Boolean isOpen, List<TimeframeInfo> timeframes) {
		this.status = status;
		this.isOpen = isOpen;
		this.timeframes = timeframes;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the isOpen
	 */
	public Boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen
	 *            the isOpen to set
	 */
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the timeframes
	 */
	public List<TimeframeInfo> getTimeframes() {
		return timeframes;
	}

	/**
	 * @param timeframes
	 *            the timeframes to set
	 */
	public void setTimeframes(List<TimeframeInfo> timeframes) {
		this.timeframes = timeframes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HoursInfo [status=" + status + ", isOpen=" + isOpen + ", timeframes=" + timeframes + "]";
	}

}
