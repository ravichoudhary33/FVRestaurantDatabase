package restaurant;

public class PriceInfo {

	private Integer tier;
	private String message;

	public PriceInfo(Integer tier, String message) {
		this.tier = tier;
		this.message = message;
	}

	/**
	 * @return the tier
	 */
	public Integer getTier() {
		return tier;
	}

	/**
	 * @param tier
	 *            the tier to set
	 */
	public void setTier(Integer tier) {
		this.tier = tier;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PriceInfo [tier=" + tier + ", message=" + message + "]";
	}

}
