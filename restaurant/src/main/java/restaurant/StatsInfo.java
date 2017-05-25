package restaurant;

public class StatsInfo {
	private Integer checkinsCount; // total checkins ever here at restaurant
	private Integer usersCount; // total users who have ever checked in here
	private Integer tipCount; // number of tips here

	public StatsInfo(Integer checkinsCount, Integer usersCount, Integer tipCount) {
		this.checkinsCount = checkinsCount;
		this.usersCount = usersCount;
		this.tipCount = tipCount;
	}

	/**
	 * @return the checkinsCount
	 */
	public Integer getCheckinsCount() {
		return checkinsCount;
	}

	/**
	 * @param checkinsCount
	 *            the checkinsCount to set
	 */
	public void setCheckinsCount(Integer checkinsCount) {
		this.checkinsCount = checkinsCount;
	}

	/**
	 * @return the usersCount
	 */
	public Integer getUsersCount() {
		return usersCount;
	}

	/**
	 * @param usersCount
	 *            the usersCount to set
	 */
	public void setUsersCount(Integer usersCount) {
		this.usersCount = usersCount;
	}

	/**
	 * @return the tipCount
	 */
	public Integer getTipCount() {
		return tipCount;
	}

	/**
	 * @param tipCount
	 *            the tipCount to set
	 */
	public void setTipCount(Integer tipCount) {
		this.tipCount = tipCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StatsInfo [checkinsCount=" + checkinsCount + ", usersCount=" + usersCount + ", tipCount=" + tipCount
				+ "]";
	}

}
