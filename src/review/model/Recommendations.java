package review.model;

import review.model.Products;
import review.model.Users;

public class Recommendations {
	protected int recommendationId;
	protected Users user;
	protected Products product;
	
	public Recommendations(int recommendationId, Users user, Products product) {
		this.recommendationId = recommendationId;
		this.user = user;
		this.product = product;;
	}
	
	public Recommendations(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	
	public Recommendations(Users user, Products product) {
		this.user = user;
		this.product = product;
	}

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Products getProduct() {
		return this.product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
}

