package review.model;
import java.util.Date;

public class Reviews {
	
	protected int reviewId;
	protected Users user;
	protected Products product;
	protected Date created;
	protected String content;
	protected String summary;
	protected double rating;
	
	public Reviews(int reviewId, Users user, Products product, Date created, String content, String summary,
			double rating) {
		this.reviewId = reviewId;
		this.user = user;
		this.product = product;
		this.created = created;
		this.content = content;
		this.summary = summary;
		this.rating = rating;
	}
	
	public Reviews(int reviewId) {
		this.reviewId = reviewId;
	}

	public Reviews(Users user, Products product, String content, String summary, double rating) {
	        this.user = user;
	        this.product = product;
	        this.content = content;
	        this.summary = summary;
	        this.rating = rating;
	}
	
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}
