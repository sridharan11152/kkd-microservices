package com.kkd.productmanagementservice.model;

public class FarmerProductBean {
	private String productId;
	private String kkdFarmId;
	private String imageUrl;
	private String productName;
	private String description;
	private double price;
	private double bulkOrderPrice;
	private double quantity;
	public boolean availability;

	// Constructor with no fields
	public FarmerProductBean() {
	}

	// Constructor with fields
	public FarmerProductBean(String productId, String kkdFarmId, String imageUrl, String productName,
		String description, double price, double bulkOrderPrice, double quantity, boolean availability) {
		super();
		this.productId = productId;
		this.kkdFarmId = kkdFarmId;
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
	
	public String getKkdFarmId() {
		return kkdFarmId;
	}

	public void setKkdFarmId(String kkdFarmId) {
		this.kkdFarmId = kkdFarmId;
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
