package review.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.dal.*;
import review.model.*;

@WebServlet("/favorites")
public class UserFavorites extends HttpServlet {
    
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
        
        // Retrieve and validate UserName.
        String username = req.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
            messages.put("title", "Favorites of " + username);
        }
        
        // Retrieve Reviews, and store in the request.
        List<Favorites> favorites = new ArrayList<>();
        try {
            favorites = favoritesDao.getFavoritesByUserName(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("favorites", favorites);
        req.getRequestDispatcher("/UserFavorites.jsp").forward(req, resp);
    }
}
