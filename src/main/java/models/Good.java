package models;

public class Good {
	
	private int id;
	private int price;
	private String title;
	private String info;
	private String img;
	
	public Good(int id, int price, String title, String info, String img) {
		this.id = id;
		this.price = price;
		this.title = title;
		this.info = info;
		this.img = img;
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
	
	public String getInfo() {
		return info;
	}
	
	public String getImg() {
		return img;
	}
	
	@Override
	public String toString() {
		return title + " стоит " + price;
	}	
}
