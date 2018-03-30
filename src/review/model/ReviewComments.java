package review.model;

public class ReviewComments {
	protected int commentId;
	protected Users user;
	protected Reviews review;
	protected boolean helpful;
	
	public ReviewComments(int commentId, Users user, Reviews review, boolean helpful) {
		this.commentId = commentId;
		this.user = user;
		this.review = review;
		this.helpful = helpful;
	}
	public ReviewComments(int commentId) {
	    this.commentId = commentId;
	}
	public ReviewComments(Users user, Reviews review, boolean helpful) {
	        this.user = user;
	        this.review = review;
	        this.helpful = helpful;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Reviews getReview() {
		return review;
	}
	public void setReview(Reviews review) {
		this.review = review;
	}
	public boolean isHelpful() {
		return helpful;
	}
	public void setHelpful(boolean helpful) {
		this.helpful = helpful;
	}
	
	
	
}
