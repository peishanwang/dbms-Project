package review.model;

public class Products {
	protected String productId;
	protected String productName;
	protected String description;
	protected Brands brand;
	protected Double price;
	
	public Products(String productId, String productName, String description, Brands brand, Double price) {
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.brand = brand;
		this.price = price;
	}
	
	public Products(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Brands getBrand() {
		return brand;
	}

	public void setBrand(Brands brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
}
