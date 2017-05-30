package restaurant;

import java.util.ArrayList;
import java.util.List;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareClient {

	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private String categoryId;

	// constructor
	public FoursquareClient(String clientId, String clientSecret, String redirectUrl, String categoryId) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		this.categoryId = categoryId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret
	 *            the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl
	 *            the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	// method to get venues id of category food by providing location latitude
	// and longitude ll = "1.32,103,45" for example
	public List<String> getVenuesId(String latLng) {
		// get the foursquareApi object first
		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, redirectUrl);
		// set the version of the foursquare api
		foursquareApi.setVersion("20170524");

		List<String> venuesId = new ArrayList<String>();

		try {
			// get the venues search result around 2,000m around user position
			Result<VenuesSearchResult> result = foursquareApi.venuesSearch(latLng, null, null, null, null, 50, null,
					categoryId, null, null, null, 2000, null);

			// if request is successfull get the id's else just return null at
			// the end
			if (result.getMeta().getCode() == 200) {
				// iterate over array of compact venue to get venues id
				for (CompactVenue cv : result.getResult().getVenues()) {
					venuesId.add(cv.getId());
				}

			}

		} catch (FoursquareApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return venuesId;
	}

	// method to get venue details
	public CompleteVenue getVenueDetails(String venueId) {

		// get the foursquareApi object first
		FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, redirectUrl);
		try {
			Result<CompleteVenue> result = foursquareApi.venue(venueId);
			// if status code is 200 , means request successfull
			if (result.getMeta().getCode() == 200) {
				CompleteVenue cv = result.getResult();
				return cv;
			}

		} catch (FoursquareApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
