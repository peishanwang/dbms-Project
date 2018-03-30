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

@WebServlet("/findproducts")
public class FindProduct extends HttpServlet {

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

        List<Products> products = new ArrayList<>();
        
        // Retrieve and validate name.
        // product name is retrieved from the URL query string.
        String productName = req.getParameter("productname");
        if (productName == null || productName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
            // Retrieve Products, and store as a message.
            try {
                products = productsDao.getProductByProductName(productName);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            messages.put("success", "Displaying results for " + productName);
            // Save the previous search term, so it can be used as the default
            // in the input box when rendering FindUsers.jsp.
            messages.put("previousProductName", productName);
        }
        req.setAttribute("products", products);
        
        req.getRequestDispatcher("/FindProducts.jsp").forward(req, resp);
    }
    
//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        // Map for storing messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        req.setAttribute("messages", messages);
//
//        List<Products> products = new ArrayList<>();
//        
//        // Retrieve and validate name.
//        // product name is retrieved from the form POST submission. By default, it
//        // is populated by the URL query string (in FindProducts.jsp).
//        String productName = req.getParameter("productname");
//        if (productName == null || productName.trim().isEmpty()) {
//            messages.put("success", "Please enter a valid name.");
//        } else {
//            // Retrieve Products, and store as a message.
//            try {
//                products = productsDao.getProductByProductName(productName);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new IOException(e);
//            }
//            messages.put("success", "Displaying results for " + productName);
//        }
//        req.setAttribute("products", products);
//        
//        req.getRequestDispatcher("/FindProducts.jsp").forward(req, resp);
//    }
}
    