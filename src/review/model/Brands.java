package review.model;

public class Brands {

    protected String brandName;
    protected String description;
    
    public Brands(String brandName, String description) {
        this.brandName = brandName;
        this.description = description;
    }
    
    public Brands(String brandName) {
        this.brandName = brandName;
    }
    
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
