package review.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.dal.*;
import review.model.*;

@WebServlet("/favoritecreate")
public class FavoriteCreate extends HttpServlet {

    protected FavoritesDao favoritesDao;
    
    @Override
    public void init() throws ServletException {
        favoritesDao = FavoritesDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/FavoriteCreate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String username = req.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
            // Create the Favorite.
            String productId = req.getParameter("productid");
            UsersDao usersDao = UsersDao.getInstance();
            ProductsDao productsDao = ProductsDao.getInstance();
            try {
                Users user = usersDao.getUserByUserName(username);
                Products product = productsDao.getProductById(productId);
                // Exercise: parse the input for StatusLevel.
                Favorites favorite = null;
                if (user == null || product == null) {
                    messages.put("success", "User or Product does not exist");
                } else {
                    favorite = new Favorites(user, product);
                    favorite = favoritesDao.create(favorite);
                    messages.put("success", "Successfully created FavoriteId " + favorite.getFavoriteId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/FavoriteCreate.jsp").forward(req, resp);
    }
}
