package restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

public class GooglePlaceClient {

	// apiKey to store the api key for google place api
	private String apiKey;
	// private GeoApiContext context;

	// constructor
	public GooglePlaceClient(String apiKey) {
		this.setApiKey(apiKey);
		// GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	// method to collect place id for the restaurant
	public List<String> getPlacesId(String latLng) {

		// first get the geoapi context
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		// split the string by ',' and store them in array of string called loc
		String[] loc = latLng.split(",");
		// create the latLng object
		LatLng location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
		// now request nearby search query around location within 2km of type
		// restaurant
		NearbySearchRequest request = PlacesApi.nearbySearchQuery(context, location).radius(2000)
				.type(PlaceType.RESTAURANT);

		// to store the next page token
		String nextPageToken = null;
		// list of string to store the places id that will be returned
		List<String> placesId = new ArrayList<>();

		// apply do while loop to get the data until next page token is not null
		do {

			try {
				PlacesSearchResponse response = request.await();
				nextPageToken = response.nextPageToken;
				// get the result from response
				PlacesSearchResult[] result = response.results;
				// iterate over result to get id's
				for (PlacesSearchResult res : result) {
					placesId.add(res.placeId);
				}

			} catch (ApiException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request = PlacesApi.nearbySearchNextPage(context, nextPageToken);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (nextPageToken != null);

		return placesId;

	}

	// method to get place details
	public PlaceDetails getPlaceDetails(String placeId) {

		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		PlaceDetailsRequest request = PlacesApi.placeDetails(context, placeId);

		try {
			// calling get request to the api by using await method
			PlaceDetails placeDetails = request.await();
			return placeDetails;

		} catch (ApiException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
