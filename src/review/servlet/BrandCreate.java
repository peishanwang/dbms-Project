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

import review.dal.BrandsDao;
import review.model.Brands;

@WebServlet("/brandcreate")
public class BrandCreate extends HttpServlet {

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
        //Just render the JSP.   
        req.getRequestDispatcher("/BrandCreate.jsp").forward(req, resp);
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
            messages.put("success", "Invalid BrandName");
        } else {
            // Create the BlogUser.
            String description = req.getParameter("description");
            try {
                // Exercise: parse the input for StatusLevel.
                Brands brand = new Brands(brandName, description);
                brand = brandsDao.create(brand);
                messages.put("success", "Successfully created " + brandName);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/BrandCreate.jsp").forward(req, resp);
    }
}
