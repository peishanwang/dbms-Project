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

import review.model.Brands;
import review.dal.BrandsDao;

@WebServlet("/brand")
public class ProductBrand extends HttpServlet {

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
        
        // Retrieve and validate brand name.
        String brandName = req.getParameter("brandname");
        if (brandName == null || brandName.trim().isEmpty()) {
            messages.put("title", "Invalid brand name.");
        } else {
            messages.put("title", "Brand for " + brandName);
        }
        
        Brands brand;
        try {
            brand = brandsDao.getBrandByBrandName(brandName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("brand", brand);
        req.getRequestDispatcher("/ProductBrand.jsp").forward(req, resp);
    }
}
