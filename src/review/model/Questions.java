package review.model;

import java.util.Date;

import review.model.Products;
import review.model.Users;

public class Questions {
	protected int questionId;
	protected String content;
	protected Date created;
	protected Products product;
	protected Users user;
	
	public Questions(int questionId, String content, Date created, Products product, Users user) {
		this.questionId = questionId;
		this.content = content;
		this.created = created;
		this.product = product;
		this.user = user;
	}
	
	public Questions(int questionId) {
		this.questionId = questionId;
	}
	
	public Questions(String content, Date created, Products product, Users user) {
		this.content = content;
		this.created = created;
		this.product = product;
		this.user = user;
	}
	

	public int getQuestionId() {
		return this.questionId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getCreated() {
		return this.created;
	}
	
	public Products getProduct() {
		return this.product;
	}
	
	public Users getUser() {
		return this.user;
	}
}