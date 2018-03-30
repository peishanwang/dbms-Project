package review.servlet;

import review.dal.FavoritesDao;
import review.model.Favorites;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/favoritedelete")
public class FavoriteDelete extends HttpServlet {
    
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Favorite");        
        req.getRequestDispatcher("/FavoriteDelete.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String favoriteId = req.getParameter("favoriteid");
        if (favoriteId == null || favoriteId.trim().isEmpty()) {
            messages.put("title", "Invalid FavoriteId");
            messages.put("disableSubmit", "true");
        } else {
            // Delete the BlogUser.
            Favorites favorite = new Favorites(Integer.parseInt(favoriteId));
            try {
                favorite = favoritesDao.delete(favorite);
                // Update the message.
                if (favorite == null) {
                    messages.put("title", "Successfully deleted " + favoriteId);
                    messages.put("disableSubmit", "true");
                } else {
                    messages.put("title", "Failed to delete " + favoriteId);
                    messages.put("disableSubmit", "false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/FavoriteDelete.jsp").forward(req, resp);
    }
}
