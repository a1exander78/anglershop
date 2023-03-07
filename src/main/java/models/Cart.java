package models;

public class Cart {
	
	private int id;
	private int price;
	private String title;
	private int count;
	private int idBasket;
	
	public Cart(int id, int price, String title, int count, int idBasket) {
		this.id = id;
		this.price = price;
		this.title = title;
		this.count = count;
		this.idBasket=idBasket;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getCount() {
		return count;
	}
	
	public int idBasket() {
		return idBasket;
	}	
}	

