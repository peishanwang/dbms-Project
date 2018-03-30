package review.servlet;

import review.dal.BrandsDao;
import review.model.Brands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/branddelete")
public class BrandDelete extends HttpServlet {
    
    protected BrandsDao brandsDao;
    
    @Override
    public void init() throws ServletException {
        brandsDao = BrandsDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Brand");        
        req.getRequestDispatcher("/BrandDelete.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String brandName = req.getParameter("brandname");
        if (brandName == null || brandName.trim().isEmpty()) {
            messages.put("title", "Invalid BrandName");
            messages.put("disableSubmit", "true");
        } else {
            // Delete the BlogUser.
            Brands brand = new Brands(brandName);
            try {
                brand = brandsDao.delete(brand);
                // Update the message.
                if (brand == null) {
                    messages.put("title", "Successfully deleted " + brandName);
                    messages.put("disableSubmit", "true");
                } else {
                    messages.put("title", "Failed to delete " + brandName);
                    messages.put("disableSubmit", "false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/BrandDelete.jsp").forward(req, resp);
    }
}
