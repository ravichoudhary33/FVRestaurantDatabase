package restaurant;

import java.util.Arrays;

public class TimeframeInfo {

	private String days;
	private Boolean includesToday;
	private String[] open;
	private String[] segments;

	public TimeframeInfo(String days, Boolean includesToday, String[] open, String[] segments) {
		this.days = days;
		this.includesToday = includesToday;
		this.setOpen(open);
		this.setSegments(segments);
	}

	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}

	/**
	 * @return the includesToday
	 */
	public Boolean getIncludesToday() {
		return includesToday;
	}

	/**
	 * @param includesToday
	 *            the includesToday to set
	 */
	public void setIncludesToday(Boolean includesToday) {
		this.includesToday = includesToday;
	}

	/**
	 * @return the open
	 */
	public String[] getOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(String[] open) {
		this.open = open;
	}

	/**
	 * @return the segments
	 */
	public String[] getSegments() {
		return segments;
	}

	/**
	 * @param segments
	 *            the segments to set
	 */
	public void setSegments(String[] segments) {
		this.segments = segments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeframeInfo [days=" + days + ", includesToday=" + includesToday + ", open=" + Arrays.toString(open)
				+ ", segments=" + Arrays.toString(segments) + "]";
	}
}
