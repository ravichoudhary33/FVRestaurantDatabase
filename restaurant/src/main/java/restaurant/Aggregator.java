package restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.similarity.CosineDistance;

import com.google.maps.model.PlaceDetails;

import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.venue.Timeframe;

/**
 * @author ravi
 *
 */
public class Aggregator {

	// database for the restaurant is an hashmap
	Map<String, Restaurant> db = new HashMap<>();

	// method to get place details form google
	public PlaceDetails getGooglePlaceDetails(String placeId) {
		// google place api call
		String apiKey = "AIzaSyCRX2fbu1FvF-_3XC3pn3Tt5PWRRdPACP0";
		GooglePlaceClient googleClient = new GooglePlaceClient(apiKey);
		return googleClient.getPlaceDetails(placeId);
	}

	// helper method to get list of restaurant id from google place by providing
	// latitude longitude
	public List<String> getGooglePlaceId(String latLng) {

		// google place api call
		String apiKey = "AIzaSyCRX2fbu1FvF-_3XC3pn3Tt5PWRRdPACP0";
		GooglePlaceClient googleClient = new GooglePlaceClient(apiKey);
		List<String> placesId = googleClient.getPlacesId(latLng);

		return placesId;
	}

	// method to get venue details from foursquare
	public CompleteVenue getFoursquareVenueDetails(String venueId) {
		// foursquare api call
		String clientId = "I1F5QCHNLGDK4TIIT44X3O2TKQAHZ0V5UNUKJTBCJLQ3L4KF";
		String clientSecret = "0RKXFCU2AOTEF1WX0KYRAYZEBEH1XDHFMDZTKOCM32D0W231";
		String redirectUrl = "https://api.foursquare.com/v2/";
		String categoryId = "4d4b7105d754a06374d81259";

		FoursquareClient fsClient = new FoursquareClient(clientId, clientSecret, redirectUrl, categoryId);

		return fsClient.getVenueDetails(venueId);

	}

	// helper method to get list of restaurant id from fousquare by providing
	// latitude longitude
	public List<String> getFoursquareId(String latLng) {
		// foursquare api call
		String clientId = "I1F5QCHNLGDK4TIIT44X3O2TKQAHZ0V5UNUKJTBCJLQ3L4KF";
		String clientSecret = "0RKXFCU2AOTEF1WX0KYRAYZEBEH1XDHFMDZTKOCM32D0W231";
		String redirectUrl = "https://api.foursquare.com/v2/";
		String categoryId = "4d4b7105d754a06374d81259";

		FoursquareClient fsClient = new FoursquareClient(clientId, clientSecret, redirectUrl, categoryId);

		List<String> venuesId = fsClient.getVenuesId(latLng);

		return venuesId;

	}

	// TODO: aggregte function to aggregate data of restaurant from google and
	// foursquare
	public void aggregate(String latLng) {

		// get restaurant id from both vendor
		List<String> foursquareVenuesId = getFoursquareId(latLng);
		// List<String> googlePlacesId = getGooglePlaceId(latLng);

		// first iterating over foursquare venue ids
		for (String id : foursquareVenuesId) {
			// TODO: create Restaurant Object and add it to db

			CompleteVenue cv = getFoursquareVenueDetails(id);

			Restaurant rest = setRestFromCompleteVenue(cv);
			// System.out.println(rest.getName());
			if (!db.containsKey(id))
				db.put(id, rest);
		}

		// now get surrounding places by google place and update if necessary
		List<String> googlePlacesId = getGooglePlaceId(latLng);
		for (String id : googlePlacesId) {
			PlaceDetails pd = getGooglePlaceDetails(id);
			// OpeningHours oh = placeDetails.openingHours;
			// now scan the db with the place id that was fetch by foursquare
			// api call
			// and insert if more information is available of same restaurant in
			// db
			double epsilon = 10; // epsilon refers to 5 meter
			for (String fid : foursquareVenuesId) {
				Restaurant rest1 = db.get(fid);
				// if distance between two place is less then 10 meter consider
				// it as the same restaurant
				CosineDistance cd = new CosineDistance();
				if (distance(pd.geometry.location.lat, rest1.getLocation().getLat(), pd.geometry.location.lng,
						rest1.getLocation().getLng()) < epsilon && cd.apply(pd.name, rest1.getName()) < 0.1) {
					rest1.setGooglePlaceId(pd.placeId);
					rest1.setVicinity(pd.vicinity);
					rest1.setPermanentlyClosed(pd.permanentlyClosed);
					rest1.setReviews(pd.reviews);

				}
			}

		}

	}

	// this function takes latitude and longitude of the two place and return
	// the distance between them in meter
	public double distance(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		// if it has elimination passed as argument then take height in
		// consideration
		// double height = el1 - el2;

		// distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance);
	}

	// method to display database
	public void displayDb() {
		System.out.println("db size: " + db.size());
		// iterate over the database and display line by line
		for (Restaurant rest : db.values()) {
			// Gson gson = new Gson();
			// String restStr = gson.toJson(rest);
			System.out.println(rest.toString());
			// System.out.println(rest.getName() + ", " + rest.getRating());
		}
	}

	// method to set Restaurant data from complete venue
	public Restaurant setRestFromCompleteVenue(CompleteVenue cv) {
		Restaurant rest = new Restaurant();
		// TODO: incomplete
		// capture foursquare restaurant id
		rest.setFoursquareId(cv.getId());
		// capture name of the restaurant
		rest.setName(cv.getName());
		// capture contact information of the restaurant
		rest.setContact(new ContactInfo(cv.getContact().getEmail(), cv.getContact().getFacebook(),
				cv.getContact().getTwitter(), cv.getContact().getPhone()));
		// capture location information here
		rest.setLocation(new LocationInfo(cv.getLocation().getAddress(), cv.getLocation().getCrossStreet(),
				cv.getLocation().getCc(), cv.getLocation().getLat(), cv.getLocation().getLng(),
				cv.getLocation().getDistance(), cv.getLocation().getCity(), cv.getLocation().getState(),
				cv.getLocation().getPostalCode(), cv.getLocation().getCountry(), null));
		// capture categories information
		List<CategoryInfo> categories = new ArrayList<>();
		for (fi.foyt.foursquare.api.entities.Category cat : cv.getCategories()) {
			categories.add(new CategoryInfo(cat.getId(), cat.getName(), cat.getIcon(), cat.getPrimary()));
		}
		rest.setCategories(categories);
		// capture verified rest or not
		rest.setVerified(cv.getVerified());
		// capture stats from
		rest.setStats(new StatsInfo(cv.getStats().getCheckinsCount(), cv.getStats().getUsersCount(),
				cv.getStats().getTipCount()));
		// capture url from foursquare
		if (cv.getUrl() != null)
			rest.setUrl(cv.getUrl());
		// capture hours of the venue during the week that
		// the venue is open along with any named hours segments in a
		// human-readable format
		if (cv.getHours() != null) {
			List<TimeframeInfo> timeframesInfo = new ArrayList<>();
			for (Timeframe tf : cv.getHours().getTimeframes()) {
				timeframesInfo
						.add(new TimeframeInfo(tf.getDays(), tf.getIncludesToday(), tf.getOpen(), tf.getSegments()));
			}

			HoursInfo hoursInfo = new HoursInfo(cv.getHours().getStatus(), cv.getHours().getIsOpen(), timeframesInfo);
			rest.setHours(hoursInfo);
		}
		// capture menu
		if (cv.getMenu() != null) {
			MenuInfo menuInfo = new MenuInfo(cv.getMenu().getType(), cv.getMenu().getLabel(), cv.getMenu().getAnchor(),
					cv.getMenu().getUrl(), cv.getMenu().getMobileUrl());
			rest.setMenu(menuInfo);
		}
		// capture price
		if (cv.getPrice() != null) {
			rest.setPriceInfo(new PriceInfo(cv.getPrice().getTier(), cv.getPrice().getMessage()));
		}
		// capture rating
		if (cv.getRating() != null) {
			rest.setRating(cv.getRating());
		}
		// capture description
		if (cv.getDescription() != null) {
			rest.setDescription(cv.getDescription());
		}
		// capture the tags
		rest.setTags(cv.getTags());
		// capture canonical url of the venue
		rest.setCanonicalUrl(cv.getCanonicalUrl());
		// capture photos
		// rest.setPhotos(new PhotoInfo());
		// definig list of photo
		List<PhotoInfo> mPhotos = new ArrayList<>();
		// first collect photos from foursquare
		PhotoGroup[] photoGroups = cv.getPhotos().getGroups();
		for (PhotoGroup photoGroup : photoGroups) {
			if (photoGroup.getType() == "venue") {
				Photo[] photos = photoGroup.getItems();
				for (Photo photo : photos) {
					// create photoinfo object and add it to the photo list
					mPhotos.add(new PhotoInfo(photo.getId(), photo.getUrl(), photo.getHeight(), photo.getWidth(),
							photo.getVisibility(), photo.getPrefix(), photo.getSuffix(),
							new SourceInfo(photo.getSource().getName(), photo.getSource().getUrl())));

				}
			}

		}
		// now set the mPhotos to restaurant photos list
		rest.setPhotos(mPhotos);

		return rest;
	}

	// main method here to test the function
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String latLng = "1.3521,103.8198";

		Aggregator agg = new Aggregator();
		// agg.getFoursquareId(latLng);
		// agg.getGooglePlaceId(latLng);
		// int i = 0;
		List<String> gids = agg.getGooglePlaceId(latLng);
		for (String id : gids) {
			PlaceDetails d = agg.getGooglePlaceDetails(id);
			System.out.println(d.name);
		}

		// System.out.println("================Before===================");

		// latLng = "1.2838,103.8486";
		// agg.aggregate(latLng);
		// agg.displayDb();
		// System.out.println("================After===================");
		// latLng = "12.9716,77.5946";
		//
		// agg.aggregate(latLng);
		// agg.displayDb();

		/////////// for testing only
		// get the foursquareApi object first
		// String clientId = "I1F5QCHNLGDK4TIIT44X3O2TKQAHZ0V5UNUKJTBCJLQ3L4KF";
		// String clientSecret =
		// "0RKXFCU2AOTEF1WX0KYRAYZEBEH1XDHFMDZTKOCM32D0W231";
		// String redirectUrl = "https://api.foursquare.com/v2/";
		// FoursquareApi foursquareApi = new FoursquareApi(clientId,
		// clientSecret, redirectUrl);
		//
		// try {
		// Result<CompleteVenue> result =
		// foursquareApi.venue("4b476a40f964a5205e3126e3");
		// CompleteVenue cv = result.getResult();
		// Photos ph = cv.getPhotos();
		// PhotoGroup[] pgs = ph.getGroups();
		// for (PhotoGroup pg : pgs) {
		// Photo[] ps = pg.getItems();
		// for (Photo p : ps) {
		// System.out.println(cv.getName() + "," + p.getPrefix() + " & " +
		// p.getSuffix());
		// }
		// }
		// } catch (FoursquareApiException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
