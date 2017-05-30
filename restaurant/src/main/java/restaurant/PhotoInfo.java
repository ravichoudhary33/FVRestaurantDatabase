package restaurant;

public class PhotoInfo {
	private String id;
	private String url; // photo original url
	private Integer height; // height of the photo
	private Integer width; // width of the photo
	private String visibility; // visibility of the photo
	private String prefix; // prefix of the photo
	private String suffix; // suffix of the photo
	private SourceInfo source; // source of the photo
	private Boolean isBestPhoto;

	public PhotoInfo(String id, String url, Integer height, Integer width, String visibility, String prefix,
			String suffix, SourceInfo source, Boolean isBestPhoto) {
		this.id = id;
		this.url = url;
		this.height = height;
		this.width = width;
		this.visibility = visibility;
		this.prefix = prefix;
		this.suffix = suffix;
		this.source = source;
		this.isBestPhoto = isBestPhoto;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility
	 *            the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the source
	 */
	public SourceInfo getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(SourceInfo source) {
		this.source = source;
	}

	/**
	 * @return the isBestPhoto
	 */
	public boolean isBestPhoto() {
		return isBestPhoto;
	}

	/**
	 * @param isBestPhoto
	 *            the isBestPhoto to set
	 */
	public void setBestPhoto(boolean isBestPhoto) {
		this.isBestPhoto = isBestPhoto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhotoInfo [id=" + id + ", url=" + url + ", height=" + height + ", width=" + width + ", visibility="
				+ visibility + ", prefix=" + prefix + ", suffix=" + suffix + ", source=" + source + ", isBestPhoto="
				+ isBestPhoto + "]";
	}

}
