package restaurant;

public class MenuInfo {
	private String type;
	private String label;
	private String anchor;
	private String url;
	private String mobileUrl;

	public MenuInfo(String type, String label, String anchor, String url, String mobileUrl) {
		this.type = type;
		this.label = label;
		this.anchor = anchor;
		this.url = url;
		this.mobileUrl = mobileUrl;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the anchor
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * @param anchor
	 *            the anchor to set
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the mobileUrl
	 */
	public String getMobileUrl() {
		return mobileUrl;
	}

	/**
	 * @param mobileUrl
	 *            the mobileUrl to set
	 */
	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuInfo [type=" + type + ", label=" + label + ", anchor=" + anchor + ", url=" + url + ", mobileUrl="
				+ mobileUrl + "]";
	}

}
