package models;

public class Order {
	
	private int idOrder;
	private int price;
	private String title;
	private int orderStatus;
	private int count;
	private int sum;
	private String username;

	public Order(int idOrder, int price, String title, int orderStatus, int sum, int count, String username) {
		this.idOrder = idOrder;
		this.price = price;
		this.title = title;
		this.orderStatus = orderStatus;
		this.count = count;
		this.sum = sum;
		this.username=username;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public int getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public int getCount() {
		return count;
	}

	public int getSum() {
		return sum;
	}
	public String getUsername() {
		return username;
	}
}
