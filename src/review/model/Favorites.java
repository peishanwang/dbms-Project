package review.model;

import review.model.Products;
import review.model.Users;

public class Favorites {
	protected int favoriteId;
	protected Users user;
	protected Products product;
	
	public Favorites(int favoriteId, Products product, Users user) {
		this.favoriteId = favoriteId;
		this.user = user;
		this.product = product;;
	}
	
	public Favorites(int favoriteId) {
		this.favoriteId = favoriteId;
	}
	
	public Favorites(Users user, Products product) {
		this.user = user;
		this.product = product;
	}

	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
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
