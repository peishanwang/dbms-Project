package review.tools;

import review.dal.*;
import review.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Inserter {

    public static void main(String[] args) throws SQLException {
        
        // DAO instances.
        UsersDao usersDao = UsersDao.getInstance();
        BrandsDao brandsDao = BrandsDao.getInstance();
        FollowDao followDao = FollowDao.getInstance();
        ReviewsDao reviewsDao = ReviewsDao.getInstance();
        ProductsDao productsDao = ProductsDao.getInstance();
        ReviewCommentsDao reviewCommentsDao = ReviewCommentsDao.getInstance();
        
        // CREATE
        Users user = new Users("a","Alice","Nv");
        user = usersDao.create(user);
        Users user2 = new Users("b","Brian","Lee");
        user2 = usersDao.create(user2);
        Users user3 = new Users("c","Cathy","Stone");
        user3 = usersDao.create(user3);
        Users user4 = new Users("d","Dan","Tyler");
        user4 = usersDao.create(user4);
        Users user5 = new Users("e","Emmy","Kim");
        user5 = usersDao.create(user5);
        Users user6 = new Users("f","Frank","Lam");
        user6 = usersDao.create(user6);
        Users user7 = new Users("g","Gilly","Song");
        user7 = usersDao.create(user7);
        Users user8 = new Users("h","Henry","Leigh");
        user8 = usersDao.create(user8);
        
        Brands brand = new Brands("brand1", "about1");
        brand = brandsDao.create(brand);
        Brands brand2 = new Brands("brand2", "about2");
        brand2 = brandsDao.create(brand2);
        Brands brand3 = new Brands("brand3", "about3");
        brand3 = brandsDao.create(brand3);
        
        Follow follow = new Follow(user2, user);
        follow = followDao.create(follow);
        Follow follow2 = new Follow(user, user2);
        follow2 = followDao.create(follow2);
        Follow follow3 = new Follow(user3, user2);
        follow3 = followDao.create(follow3);
        Follow follow4 = new Follow(user4, user);
        follow4 = followDao.create(follow4);
        Follow follow5 = new Follow(user5, user2);
        follow5 = followDao.create(follow5);
        Follow follow6 = new Follow(user2, user3);
        follow6 = followDao.create(follow6);
        Follow follow7 = new Follow(user, user3);
        follow7 = followDao.create(follow7);
        
        
        Products product = new Products("p1", "p1name", "p1discription", brand, 1.11);
        product = productsDao.create(product);
        Products product2 = new Products("p2", "p2name", "p2discription", brand2, 2.22);
        product2 = productsDao.create(product2);
        Products product3 = new Products("p3", "p3name", "p3discription", brand3, 3.33);
        product3 = productsDao.create(product3);
        Products product4 = new Products("p4", "p4name", "p4discription", brand, 4.44);
        product4 = productsDao.create(product4);
        Products product5 = new Products("p5", "p1name", "p5discription", brand2, 5.55);
        product5 = productsDao.create(product5);
        Products product6 = new Products("p6", "p6name", "p6discription", brand3, 6.66);
        product6 = productsDao.create(product6);
        
        Date date = new Date();
        Reviews review = new Reviews(1, user, product, date, "content1", "summary1", 1.1);
        review = reviewsDao.create(review);
        Reviews review2 = new Reviews(2, user, product2, date, "content2", "summary2", 2.2);
        review2 = reviewsDao.create(review2);
        Reviews review3 = new Reviews(3, user3, product3, date, "content3", "summary3", 3.3);
        review3 = reviewsDao.create(review3);
        Reviews review4 = new Reviews(4, user4, product3, date, "content4", "summary4", 1.1);
        review4 = reviewsDao.create(review4);
        Reviews review5 = new Reviews(5, user, product3, date, "content5", "summary5", 4.1);
        review5 = reviewsDao.create(review5);
        Reviews review6 = new Reviews(6, user5, product3, date, "content6", "summary6", 4.1);
        review6 = reviewsDao.create(review6);
        Reviews review7 = new Reviews(7, user5, product5, date, "content7", "summary7", 4.1);
        review7 = reviewsDao.create(review7);
        Reviews review8 = new Reviews(8, user7, product6, date, "content8", "summary8", 4.1);
        review8 = reviewsDao.create(review8);
        Reviews review9 = new Reviews(9, user8, product6, date, "content9", "summary9", 4.1);
        review9 = reviewsDao.create(review9);
        
        ReviewComments reviewComment = new ReviewComments(1, user, review, true);
        reviewComment = reviewCommentsDao.create(reviewComment);
        ReviewComments reviewComment2 = new ReviewComments(2, user2, review2, true);
        reviewComment2 = reviewCommentsDao.create(reviewComment2);
        ReviewComments reviewComment3 = new ReviewComments(3, user, review3, false);
        reviewComment3 = reviewCommentsDao.create(reviewComment3);
        ReviewComments reviewComment4 = new ReviewComments(4, user4, review, true);
        reviewComment4 = reviewCommentsDao.create(reviewComment4);
        ReviewComments reviewComment5 = new ReviewComments(5, user, review, true);
        reviewComment5 = reviewCommentsDao.create(reviewComment5);
        
        
        // READ
        Users u1 = usersDao.getUserByUserName("a");
        System.out.format("Reading user: u:%s f:%s l:%s \n",
            u1.getUserName(), u1.getFirstName(), u1.getLastName());
        
        System.out.println();
        Brands b1 = brandsDao.getBrandByBrandName("brand1");
        System.out.format("Reading brand: b:%s d:%s \n",
                b1.getBrandName(), b1.getDescription());
        
        System.out.println();
        int followId = 1;
        Follow f1 = followDao.getFollowById(followId);
        System.out.format("Reading follow: id:%d follower:%s followee:%s \n",
                followId, f1.getFollower().getUserName(), f1.getFollowee().getUserName());
        
        System.out.println();
        List<Follow> fList1 = followDao.getFollowByFollowerName("a");
        for (Follow f : fList1) {
            System.out.format("Looping follows: follower:%s followee:%s \n",
                    f.getFollower().getUserName(), f.getFollowee().getUserName());
        }
        
        System.out.println();
        List<Follow> fList2 = followDao.getFollowByFolloweeName("a");
        for (Follow f : fList2) {
            System.out.format("Looping follows: follower:%s followee:%s \n",
                    f.getFollower().getUserName(), f.getFollowee().getUserName());
        }
        
        System.out.println();
        Products p1 = productsDao.getProductById("p1");
        System.out.format("Reading product1: productId:%s productName:%s description:%s brand:%s price:%.2f \n",
                p1.getProductId(), p1.getProductName(), p1.getDescription(), p1.getBrand().getBrandName(), p1.getPrice());
        
        System.out.println();
        List<Products> pList = productsDao.getProductByProductName("p1name");
        for (Products p11 : pList) {
        	System.out.format("Looping products: productId:%s productName:%s description:%s brand:%s price:%.2f \n",
                p11.getProductId(), p11.getProductName(), p11.getDescription(), p11.getBrand().getBrandName(), p11.getPrice());
        }
        
        System.out.println();
        Reviews r1 = reviewsDao.getReviewById(1);
        System.out.format("Reading review1: reviewId:%d userName:%s product:%s content:%s summary:%s\n",
                r1.getReviewId(), r1.getUser().getUserName(), r1.getProduct().getProductName(), r1.getContent(), r1.getSummary());
        
        System.out.println();
        List<Reviews> rList1 = reviewsDao.getReviewForProduct(product3);
        for (Reviews rr : rList1) {
            System.out.format("Looping reviews: reviewId:%d userName:%s product:%s content:%s summary:%s\n",
                    rr.getReviewId(), rr.getUser().getUserName(), rr.getProduct().getProductName(), rr.getContent(), rr.getSummary());
        }
        
        System.out.println();
        List<Reviews> rList2 = reviewsDao.getReviewForUser(user);
        for (Reviews r : rList2) {
            System.out.format("Looping reviews: reviewId:%d userName:%s product:%s content:%s summary:%s\n",
                    r.getReviewId(), r.getUser().getUserName(), r.getProduct().getProductName(), r.getContent(), r.getSummary());
        }
        
        System.out.println();
        ReviewComments rc = reviewCommentsDao.getCommentById(1);
        System.out.format("Reading comment1: commentId:%d userName:%s review:%s helpful:%b \n",
                rc.getCommentId(), rc.getUser().getUserName(), rc.getReview().getReviewId(), rc.isHelpful());
        
        System.out.println();
        List<ReviewComments> rcList = reviewCommentsDao.getReviewCommentsForReview(review);
        for (ReviewComments rrc : rcList) {
        	System.out.format("Looping comments: commentId:%d userName:%s review:%s helpful:%b \n",
                rrc.getCommentId(), rrc.getUser().getUserName(), rrc.getReview().getReviewId(), rrc.isHelpful());
        }
        
        
        
        // UPDATE
        System.out.println();
        Brands updatedBrand = brandsDao.updateDescription(brand, "about1 -- updated");
        System.out.format("Update brand: name:%s des:%s \n", updatedBrand.getBrandName(), updatedBrand.getDescription());
        
        System.out.println();
        Users updatedUser = usersDao.update(user, "Amy", "Wang");
        System.out.format("Update user: u:%s f:%s l:%s \n", updatedUser.getUserName(), updatedUser.getFirstName(), updatedUser.getLastName());
        
        System.out.println();
        Products updateProduct = productsDao.updateDescription(product, "newDescription");
        updateProduct = productsDao.updatePrice(product, 9.99);
        updateProduct = productsDao.updateProductName(product, "newName");
        System.out.format("Update product1: productId:%s productName:%s description:%s brand:%s price:%.2f \n",
        		updateProduct.getProductId(), updateProduct.getProductName(), updateProduct.getDescription(), updateProduct.getBrand().getBrandName(), updateProduct.getPrice());

        System.out.println();
        Reviews updateReview = reviewsDao.updateContent(review, "newContent");
        updateReview = reviewsDao.updateSummary(review, "newSummary");
        System.out.format("Update review1: reviewId:%d userName:%s product:%s content:%s summary:%s\n",
        		updateReview.getReviewId(), updateReview.getUser().getUserName(), updateReview.getProduct().getProductName(), updateReview.getContent(), updateReview.getSummary());
      
        
        
        // DELETE
        System.out.println();
        usersDao.delete(user2);
        Users u2 = usersDao.getUserByUserName("b");
        if (u2 == null) {
            System.out.println("delete user: success");
        } else {
            System.out.println("delete user: failed");
        }
        
        System.out.println();
        brandsDao.delete(brand);
        Brands b = brandsDao.getBrandByBrandName("brand1");
        if (b == null) {
            System.out.println("delete brand: success");
        } else {
            System.out.println("delete brand: failed");
        }
        
        System.out.println();
        productsDao.delete(product);
        Products p = productsDao.getProductById("p1");
        if (p == null) {
            System.out.println("delete product: success");
        } else {
            System.out.println("delete product: failed");
        }
        
        System.out.println();
        reviewsDao.delete(review9);
        Reviews r = reviewsDao.getReviewById(9);
        if (r == null) {
            System.out.println("delete review: success");
        } else {
            System.out.println("delete review: failed");
        }
        
        System.out.println();
        reviewCommentsDao.delete(reviewComment5);
        ReviewComments rrcc = reviewCommentsDao.getCommentById(5);
        if (rrcc == null) {
            System.out.println("delete reviewComment: success");
        } else {
            System.out.println("delete reviewComment: failed");
        }
    }
}