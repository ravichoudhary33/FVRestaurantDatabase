package restaurant;

import java.util.HashMap;
import java.util.Map;

public class Database {

	private Map<String, Restaurant> db;

	public Database() {
		db = new HashMap<>();
	}

	public void addToDb(String id, Restaurant rest) {
		db.put(id, rest);
	}

	public void removeToDb(String id) {
		db.remove(id);
	}

	public Restaurant getRestFromDb(String id) {
		return db.get(id);
	}

	public Map<String, Restaurant> getDb() {
		return db;
	}

}
