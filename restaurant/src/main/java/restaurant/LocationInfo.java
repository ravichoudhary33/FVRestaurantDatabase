package restaurant;

public class LocationInfo {

	private String address;
	private String crossStreet;
	private String cc; // cc stands for country code
	private Double lat; // latitude
	private Double lng; // longitude
	private Double distance;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String formattedAddress;

	public LocationInfo(String address, String crossStreet, String cc, Double lat, Double lng, Double distance,
			String city, String state, String postalCode, String country, String formattedAddress) {
		this.address = address;
		this.crossStreet = crossStreet;
		this.cc = cc;
		this.lat = lat;
		this.lng = lng;
		this.distance = distance;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.formattedAddress = formattedAddress;
	}

	/**
	 * @return the address
	 */
	public String getaddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setaddress(String address) {
		this.address = address;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public Double getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(Double lng) {
		this.lng = lng;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the formattedAddress
	 */
	public String getFormattedAddress() {
		return formattedAddress;
	}

	/**
	 * @param formattedAddress
	 *            the formattedAddress to set
	 */
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	/**
	 * @return the crossStreet
	 */
	public String getCrossStreet() {
		return crossStreet;
	}

	/**
	 * @param crossStreet
	 *            the crossStreet to set
	 */
	public void setCrossStreet(String crossStreet) {
		this.crossStreet = crossStreet;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocationInfo [address=" + address + ", crossStreet=" + crossStreet + ", cc=" + cc + ", lat=" + lat
				+ ", lng=" + lng + ", distance=" + distance + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", country=" + country + ", formattedAddress=" + formattedAddress + "]";
	}

}
