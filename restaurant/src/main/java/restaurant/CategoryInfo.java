package restaurant;

public class CategoryInfo {
	private String id; // refers to category id
	private String name; // refers to category name
	private String icon;// refers to category icon
	private Boolean primary;// refers to whether category is primary id

	// constructor
	public CategoryInfo(String id, String name, String icon, Boolean primary) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.primary = primary;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the primary
	 */
	public Boolean getPrimary() {
		return primary;
	}

	/**
	 * @param primary
	 *            the primary to set
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	@Override
	public String toString() {
		return "CategoryInfo [id=" + id + ", name=" + name + ", icon=" + icon + ", primary=" + primary + "]";
	}

}
