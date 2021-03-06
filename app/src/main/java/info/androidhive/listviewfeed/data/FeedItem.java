package info.androidhive.listviewfeed.data;

import java.io.Serializable;

public class FeedItem implements Serializable{
	private int id, getCountLiked;
	private String name, status, image, profilePic, timeStamp, url;
	private boolean isLiked;
	public FeedItem() {
	}

	public FeedItem(int id, String name, String image, String status,
			String profilePic, String timeStamp, String url, int getCountLiked, boolean isLiked) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
		this.getCountLiked = getCountLiked;
		this.isLiked = isLiked;
	}

	public int getId() {
		return id;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getGetCountLiked() {
		return getCountLiked;
	}

	public void setGetCountLiked(int getCountLiked) {
		this.getCountLiked = getCountLiked;
	}
}
