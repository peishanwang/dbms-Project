package review.model;

import java.util.Date;

import review.model.Questions;
import review.model.Users;

public class Answers {
	protected int answerId;
	protected String content;
	protected Date created;
	protected Questions question;
	protected Users user;
	
	public Answers(int answerId, String content, Date created, Questions question, Users user) {
		this.answerId = answerId;
		this.content = content;
		this.created = created;
		this.question = question;
		this.user = user;
	}
	
	public Answers(String content, Date created, Questions question, Users user) {
		this.content = content;
		this.created = created;
		this.question = question;
		this.user = user;
	}
	

	public int getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
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
	
	public Questions getQuestion() {
		return this.question;
	}
	
	public Users getUser() {
		return this.user;
	}
}