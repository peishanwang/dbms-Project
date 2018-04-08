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

import review.model.*;
import review.dal.*;

@WebServlet("/product")
public class Product extends HttpServlet {

    protected ProductsDao productsDao;
    
    @Override
    public void init() throws ServletException {
        productsDao = ProductsDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Retrieve and validate product id.
        String productId = req.getParameter("productid");
        
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("title", "Invalid product id.");
            messages.put("title2", "");
        } else {
            messages.put("title", "Product Details");
            messages.put("title2", "Product ID: " + productId);
        }
        
        Products product;
        try {
        	product = productsDao.getProductById(productId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("product", product);
        req.getRequestDispatcher("/Product.jsp").forward(req, resp);
    }
}
