package restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.text.similarity.CosineDistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.maps.model.PlaceDetails;

import fi.foyt.foursquare.api.entities.Category;
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

	Database DB = new Database();
	// Map<String, Restaurant> db = new HashMap<>();

	// method to get place details form google
	public PlaceDetails getGooglePlaceDetails(String placeId) {
		// google place api call
		// String apiKey = "AIzaSyCRX2fbu1FvF-_3XC3pn3Tt5PWRRdPACP0";
		String apiKey = "AIzaSyBol99P_bdbqO-kyLytW7qmThKOYPutfCE";
		GooglePlaceClient googleClient = new GooglePlaceClient(apiKey);
		return googleClient.getPlaceDetails(placeId);
	}

	// helper method to get list of restaurant id from google place by providing
	// latitude longitude
	public List<String> getGooglePlaceId(String latLng) {

		// google place api call
		String apiKey = "AIzaSyBol99P_bdbqO-kyLytW7qmThKOYPutfCE";
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
	// if aggregate call is successfull it will return 1 else -1
	public int aggregate(String latLng) {

		// get restaurant id from both vendor
		List<String> foursquareVenuesId = getFoursquareId(latLng);
		// List<String> googlePlacesId = getGooglePlaceId(latLng);
		if (foursquareVenuesId == null) {
			return -1;
		}

		// first iterating over foursquare venue ids
		for (String id : foursquareVenuesId) {
			// TODO: create Restaurant Object and add it to db

			CompleteVenue cv = getFoursquareVenueDetails(id);
			// check if complete venue is null if it is then simply return
			if (cv == null) {
				return -1;
			}

			Restaurant rest = setRestFromCompleteVenue(cv);
			// System.out.println(rest.getName());
			if (!DB.getDb().containsKey(id))
				DB.addToDb(id, rest);
		}

		// now get surrounding places by google place and update if necessary
		List<String> googlePlacesId = getGooglePlaceId(latLng);
		if (googlePlacesId == null) {
			return -1;
		}
		for (String id : googlePlacesId) {
			PlaceDetails pd = getGooglePlaceDetails(id);
			if (pd == null) {
				return -1;
			}
			// OpeningHours oh = placeDetails.openingHours;
			// now scan the db with the place id that was fetch by foursquare
			// api call
			// and insert if more information is available of same restaurant in
			// db
			double epsilon = 10; // epsilon refers to 10 meter
			for (String fid : foursquareVenuesId) {
				Restaurant rest1 = DB.getRestFromDb(fid);
				// if distance between two place is less then 10 meter consider
				// it as the same restaurant
				CosineDistance cd = new CosineDistance();
				// JaccardSimilarity cd = new JaccardSimilarity();
				if (distance(pd.geometry.location.lat, rest1.getLocation().getLat(), pd.geometry.location.lng,
						rest1.getLocation().getLng()) < epsilon && cd.apply(pd.name, rest1.getName()) <= 0.5) {
					System.out.println("place name: " + pd.name + ", rest name: " + rest1.getName());
					rest1.setGooglePlaceId(pd.placeId);
					rest1.setVicinity(pd.vicinity);
					rest1.setPermanentlyClosed(pd.permanentlyClosed);
					rest1.setOpeningHours(pd.openingHours);
					rest1.setTypes(pd.types);
					// rest1.setHours(null);
					// finally put it into the db
					DB.addToDb(fid, rest1);
				}

			}

		}
		return 1;

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
		System.out.println("db size: " + DB.getDb().size());
		// iterate over the database and display line by line
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// JSONObject jo = new JSONObject(DB.getDb());
		// JsonArray ja = new JsonArray();
		List<Restaurant> rl = new ArrayList<Restaurant>();
		for (Restaurant rest : DB.getDb().values()) {

			rl.add(rest);
			// System.out.println(restStr);
			// System.out.println(rest.getName() + ", " + rest.getRating());
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement element = gson.toJsonTree(rl, new TypeToken<List<Restaurant>>() {
		}.getType());

		if (!element.isJsonArray()) {
			// fail appropriately
			System.out.println("ERROR!");

		}

		JsonArray jsonArray = element.getAsJsonArray();
		System.out.println(jsonArray);
		// below code is used to convert java object to json
		// Gson gson = new Gson();
		// String resStr = gson.toJson(DB);
		// System.out.println(resStr);
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
		for (Category cat : cv.getCategories()) {
			categories.add(new CategoryInfo(cat.getId(), cat.getName(), cat.getIcon(), cat.getPrimary()));
		}
		rest.setCategories(categories);
		// capture verified rest or not
		rest.setVerified(cv.getVerified());
		// capture stats i.e, checkins count, users count and tip count
		// information
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
					cv.getMenu().getMobileUrl());
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
		// set the count of people been here
		if (cv.getBeenHere() != null) {
			rest.setBeenHere(cv.getBeenHere().getCount());
		}

		// capture photos
		// rest.setPhotos(new PhotoInfo());
		// definig list of photo
		List<PhotoInfo> mPhotos = new ArrayList<>();
		// first collect photos from foursquare
		// frist get the best photo from foursquare
		if (cv.getBestPhoto() != null) {
			mPhotos.add(new PhotoInfo(cv.getBestPhoto().getId(), cv.getBestPhoto().getUrl(),
					cv.getBestPhoto().getHeight(), cv.getBestPhoto().getWidth(), cv.getBestPhoto().getVisibility(),
					cv.getBestPhoto().getPrefix(), cv.getBestPhoto().getSuffix(),
					new SourceInfo(cv.getBestPhoto().getSource().getName(), cv.getBestPhoto().getSource().getUrl()),
					true));
		}
		// and then add additional photos
		PhotoGroup[] photoGroups = cv.getPhotos().getGroups();
		for (PhotoGroup photoGroup : photoGroups) {
			Photo[] photos = photoGroup.getItems();
			for (Photo photo : photos) {
				// create photoinfo object and add it to the photo list
				mPhotos.add(new PhotoInfo(photo.getId(), photo.getUrl(), photo.getHeight(), photo.getWidth(),
						photo.getVisibility(), photo.getPrefix(), photo.getSuffix(),
						new SourceInfo(photo.getSource().getName(), photo.getSource().getUrl()), false));

			}

		}
		// now set the mPhotos to restaurant photos list
		rest.setPhotos(mPhotos);
		// record the total likes for this venue
		rest.setLikes(cv.getLikes().getCount());
		// get the tips from users
		// TipGroup[] tgs = cv.getTips().getGroups();
		// for (TipGroup tg : tgs) {
		// CompleteTip[] cts = tg.getItems();
		// for (CompleteTip ct : cts) {
		// System.out.println(ct.getText());
		// }
		// }
		return rest;
	}

	// main method here to test the function
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// String latLng = "1.3521,103.8198";

		List<String> loc = new ArrayList<String>(Arrays.asList("1.496833242363765,103.81725910656685",
				"1.307212,103.852451", "1.3009381446954327,103.83986256000243", "1.285494913138075,103.84732428146437",
				"1.304640020402789,103.85593011398319", "1.2775696527818308,103.84428406169364",
				"1.3071315241227512,103.82962176049816", "1.3046603364105918,103.90313031325793",
				"1.297664799074627,103.7873986774237", "1.3001024818530966,103.84978141754544",
				"1.3217984314097737,103.86946841667962", "1.3863240860391841,103.87354545901044",
				"1.2496923919561107,103.82456108715199", "1.2998773827464343,103.85144029260049",
				"1.2776460603830841,103.84757339001082", "1.2768121879231749,103.84581831679742",
				"1.305819528845283,103.78782876558577", "1.3812740298072703,103.75221533221686",
				"1.2849554136769443,103.8324238924659", "1.311893258843157,103.83942604064941", "1.336225,103.885928",
				"1.3028143534022798,103.83463733461771", "1.356574837426768,103.98630107786609",
				"1.2768515354970018,103.84351977133836", "1.3627337027533937,103.98851700645882",
				"1.3455030267401922,103.85473608970642", "1.290688845422199,103.85989350947817",
				"1.3076291873468713,103.85651895401136", "1.301332299524974,103.86086739003811",
				"1.2748891529232678,103.843440511844", "1.3527376206128572,103.83448004722595",
				"1.2844160346576114,103.843599030879", "1.311859,103.79447", "1.3049900531768799,103.83257293701172",
				"1.2946530107505183,103.85797407924292", "1.305255,103.862268", "1.3061655555870866,103.79083514213562",
				"1.3009208404947923,103.83942664509959", "1.2957137659427311,103.85265190696873",
				"1.2956781888025235,103.80427500778393", "1.364951463859359,103.8662804810491",
				"1.3159780307381224,103.88306469478862", "1.3014046030826758,103.83653379077134",
				"1.3154168955071115,103.87241859761768", "1.3243768958417927,103.94240920872102", "1.290739,103.840497",
				"1.2908370608844313,103.85631512448239", "1.2846193043025744,103.85136102848543",
				"1.264933689784453,103.82183270413141", "1.470540727136759,103.76341777804049",
				"1.3633746854839284,103.7646965004924", "1.302088650206512,103.90516932151702", "1.3115,103.856294",
				"1.3095345659369058,103.86390231832932", "1.3304706627677958,103.79511594772337",
				"1.323185408026334,103.85481472207111", "1.3617533477425776,103.87476293278982",
				"1.2797128927521804,103.85089110646682", "1.3336382311609647,103.9285747770594",
				"1.303268537390398,103.83515814978605", "1.535801974555982,103.62858132809959",
				"1.2634987858520716,103.82102325667347", "1.310145,103.795244", "1.3135665179962277,103.90225410461426",
				"1.291337801472736,103.84982671079347", "1.3008422089813834,103.84009467110634",
				"1.3884230360642789,103.77357999679643", "1.2838916811160543,103.8445501489749",
				"1.5153872395524575,103.74839653041536", "1.2986549,103.83889", "1.3472826968078513,103.68034398428341",
				"1.3039023583767895,103.90106320381165", "1.3062046587545229,103.83249182365692",
				"1.3476761978943002,103.7579805145646", "1.2805196386025555,103.84460676335733",
				"1.311306117700316,103.81500066676445", "1.2982592345215138,103.85621320983245",
				"1.3156708745301013,103.85622453367867", "1.290017,103.857747", "1.335124618888614,103.74583375110488",
				"1.287572,103.834187", "1.282336,103.844007", "1.4571248566383075,103.76391002816266",
				"1.367961548963746,103.83693572965973", "1.3575856981416732,103.76063346862793",
				"1.4732521471393771,103.67626713914912", "1.2783102118634984,103.84100613562232",
				"1.2787687566996229,103.81310451209129", "1.474144501605804,103.89655471852727", "1.258872,103.816143",
				"1.3003046160557188,103.85645101080111", "1.2964606125724263,103.8485188743321",
				"1.467191318283804,103.75015599447237", "1.311817082353561,103.7951461336763",
				"1.2783726153757433,103.8419402515695"));

		// Aggregator object to aggregate the data
		Aggregator agg = new Aggregator();

		int i = 0;
		for (String latLng : loc) {

			int s = agg.aggregate(latLng);
			// if status is -1 break
			if (s == -1) {
				break;
			}

		}

		agg.displayDb();

		// agg.aggregate(latLng);
		// // agg.displayDb();
		//
		// latLng = "1.4022,103.7881";
		// agg.aggregate(latLng);
		// agg.displayDb();

		// agg.getFoursquareId(latLng);
		// System.out.println("================Google Place
		// Details===================");
		//
		// List<PlaceDetails> pds = new ArrayList<>();
		// List<String> gids = agg.getGooglePlaceId(latLng);
		// for (String id : gids) {
		// PlaceDetails d = agg.getGooglePlaceDetails(id);
		// pds.add(d);
		// }
		//
		// for (PlaceDetails d : pds) {
		// System.out.println(d.name + ", " + d.geometry.location.lat + "," +
		// d.geometry.location.lng);
		// }
		//
		// System.out.println("================Foursquare Place
		// Details===================");
		//
		// List<String> ids = agg.getFoursquareId(latLng);
		// for (String id : ids) {
		// CompleteVenue cv = agg.getFoursquareVenueDetails(id);
		// System.out.println(cv.getName() + ", " + cv.getLocation().getLat() +
		// "," + cv.getLocation().getLng());
		// }

		//
		// System.out.println("----------------------------------------------");
		//
		// List<String> gids = agg.getGooglePlaceId(latLng);
		// for (String gid : gids) {
		// PlaceDetails pd = agg.getGooglePlaceDetails(gid);
		// System.out.println(pd.name + " [" + pd.geometry.location.lat + "," +
		// pd.geometry.location.lng);
		// }
		// agg.displayDb();
		// System.out.println("================After===================");
		// latLng = "1.2838,103.8486";
		//
		// agg.aggregate(latLng);
		// agg.displayDb();

	}

}
