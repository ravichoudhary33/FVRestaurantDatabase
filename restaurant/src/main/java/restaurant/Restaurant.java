package restaurant;

import java.util.Arrays;
import java.util.List;

import com.google.maps.model.OpeningHours;

/**
 * @author ravi
 *
 */

public class Restaurant {

	// fields for the restaurant

	// foursquare attributes
	private List<CategoryInfo> categories;
	private ContactInfo contact;
	private String description;
	private String foursquareId;
	private LocationInfo location;
	private MenuInfo menu;
	private String name;
	private StatsInfo stats;
	private String[] tags;
	private String timeZone;
	private Boolean verified;
	private HoursInfo hours;
	private PriceInfo price;
	private Integer rating;
	private Long beenHere;
	private Long likes;

	// google place attributes

	private List<PhotoInfo> photos;
	private OpeningHours openingHours;
	private Boolean permanentlyClosed;
	private String googlePlaceId;
	private String[] types;
	private String url;
	private String vicinity;

	/**
	 * @return the openingHours
	 */
	public OpeningHours getOpeningHours() {
		return openingHours;
	}

	/**
	 * @param openingHours
	 *            the openingHours to set
	 */
	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}

	/**
	 * @return the permanentlyClosed
	 */
	public Boolean isPermanentlyClosed() {
		return permanentlyClosed;
	}

	/**
	 * @param permanentlyClosed
	 *            the permanentlyClosed to set
	 */
	public void setPermanentlyClosed(Boolean permanentlyClosed) {
		this.permanentlyClosed = permanentlyClosed;
	}

	/**
	 * @return the googlePlaceId
	 */
	public String getGooglePlaceId() {
		return googlePlaceId;
	}

	/**
	 * @param googlePlaceId
	 *            the googlePlaceId to set
	 */
	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}

	/**
	 * @return the priceLevel
	 */
	public PriceInfo getPriceInfo() {
		return price;
	}

	/**
	 * @param priceLevel
	 *            the priceLevel to set
	 */
	public void setPriceInfo(PriceInfo price) {
		this.price = price;
	}

	/**
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	/**
	 * @return the types
	 */
	public String[] getTypes() {
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(String[] types) {
		this.types = types;
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
	 * @return the vicinity
	 */
	public String getVicinity() {
		return vicinity;
	}

	/**
	 * @param vicinity
	 *            the vicinity to set
	 */
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	/**
	 * @return the photos
	 */
	public List<PhotoInfo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *            the photos to set
	 */
	public void setPhotos(List<PhotoInfo> photos) {
		this.photos = photos;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stats
	 */
	public StatsInfo getStats() {
		return stats;
	}

	/**
	 * @param stats
	 *            the stats to set
	 */
	public void setStats(StatsInfo stats) {
		this.stats = stats;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *            the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the menu
	 */
	public MenuInfo getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(MenuInfo menu) {
		this.menu = menu;
	}

	/**
	 * @return the location
	 */
	public LocationInfo getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(LocationInfo location) {
		this.location = location;
	}

	/**
	 * @return the foursquareId
	 */
	public String getFoursquareId() {
		return foursquareId;
	}

	/**
	 * @param foursquareId
	 *            the foursquareId to set
	 */
	public void setFoursquareId(String foursquareId) {
		this.foursquareId = foursquareId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the contact
	 */
	public ContactInfo getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(ContactInfo contact) {
		this.contact = contact;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * @return the categories
	 */
	public List<CategoryInfo> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<CategoryInfo> categories) {
		this.categories = categories;
	}

	/**
	 * @return the hours
	 */
	public HoursInfo getHours() {
		return hours;
	}

	/**
	 * @param hours
	 *            the hours to set
	 */
	public void setHours(HoursInfo hours) {
		this.hours = hours;
	}

	/**
	 * @return the beenHere
	 */
	public Long getBeenHere() {
		return beenHere;
	}

	/**
	 * @param beenHere
	 *            the beenHere to set
	 */
	public void setBeenHere(Long beenHere) {
		this.beenHere = beenHere;
	}

	/**
	 * @return the likes
	 */
	public Long getLikes() {
		return likes;
	}

	/**
	 * @param likes
	 *            the likes to set
	 */
	public void setLikes(Long likes) {
		this.likes = likes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Restaurant [categories=" + categories + ", contact=" + contact + ", description=" + description
				+ ", foursquareId=" + foursquareId + ", location=" + location + ", menu=" + menu + ", name=" + name
				+ ", stats=" + stats + ", tags=" + Arrays.toString(tags) + ", timeZone=" + timeZone + ", verified="
				+ verified + ", hours=" + hours + ", price=" + price + ", rating=" + rating + ", beenHere=" + beenHere
				+ ", likes=" + likes + ", photos=" + photos + ", openingHours=" + openingHours + ", permanentlyClosed="
				+ permanentlyClosed + ", googlePlaceId=" + googlePlaceId + ", types=" + Arrays.toString(types)
				+ ", url=" + url + ", vicinity=" + vicinity + "]";
	}

}
