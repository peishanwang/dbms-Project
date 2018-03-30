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


@WebServlet("/brandupdate")
public class BrandUpdate extends HttpServlet {
    
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

        // Retrieve user and validate.
        String brandName = req.getParameter("brandname");
        if (brandName == null || brandName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid BrandName.");
        } else {
            try {
                Brands brand = brandsDao.getBrandByBrandName(brandName);
                if(brand == null) {
                    messages.put("success", "BrandName does not exist.");
                }
                req.setAttribute("brand", brand);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/BrandUpdate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String brandName = req.getParameter("brandname");
        if (brandName == null || brandName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid BrandName.");
        } else {
            try {
                Brands brand = brandsDao.getBrandByBrandName(brandName);
                if(brand == null) {
                    messages.put("success", "BrandName does not exist. No update to perform.");
                } else {
                    String newDescription = req.getParameter("description");
                    if (newDescription == null || newDescription.trim().isEmpty()) {
                        messages.put("success", "Please enter a valid Description.");
                    } else {
                        brand = brandsDao.updateDescription(brand, newDescription);
                        messages.put("success", "Successfully updated " + brandName);
                    }
                }
                req.setAttribute("brand", brand);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/BrandUpdate.jsp").forward(req, resp);
    }
}
