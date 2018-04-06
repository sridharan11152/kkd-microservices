package com.kkd.farmerviewproductservice;

public class FarmerProduct {

	private String productId;
	private String imageUrl;
	private String productName;
	private String description;
	private double price;
	private double bulkOrderPrice;
	private double quantity;
	public boolean availability;
	
	//Constructor with no fields
	public FarmerProduct() {
		
	}
	
	//Constructor with fields 
	public FarmerProduct(String productId, String imageUrl, String productName, String description, double price,
			double bulkOrderPrice, double quantity, boolean availability) {
		super();
		this.productId = productId;
		this.imageUrl = imageUrl;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.bulkOrderPrice = bulkOrderPrice;
		this.quantity = quantity;
		this.availability = availability;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getBulkOrderPrice() {
		return bulkOrderPrice;
	}

	public void setBulkOrderPrice(double bulkOrderPrice) {
		this.bulkOrderPrice = bulkOrderPrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	
}
