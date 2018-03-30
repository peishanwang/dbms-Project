package review.model;

public class Follow {

    protected int followId;
    protected Users follower;
    protected Users followee;
 
    public Follow(int followId, Users follower, Users followee) {
        this.followId = followId;
        this.followee = followee;
        this.follower = follower;
    }
    
    public Follow(int followId) {
        this.followId = followId;
    }
    
    public Follow(Users follower, Users followee) {
        this.followee = followee;
        this.follower = follower;
    }
    
    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }
    
    public Users getFollower() {
        return follower;
    }

    public void setFollower(Users follower) {
        this.follower = follower;
    }

    public Users getFollowee() {
        return followee;
    }

    public void setFollowee(Users followee) {
        this.followee = followee;
    }
    
}
