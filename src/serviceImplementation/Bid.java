package serviceImplementation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * this class initializes users.
 * @author Leila
 *
 */
@XmlRootElement
public class Bid {

	private String custID, productID, biddingDate;
	private int price;

	public Bid() {

	}

	public Bid(String custID, String productID, String biddingDate, int price) {
		super();
		this.custID = custID;
		this.productID = productID;
		this.biddingDate = biddingDate;
		this.price = price;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getBiddingDate() {
		return biddingDate;
	}

	public void setBiddingDate(String biddingDate) {
		this.biddingDate = biddingDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
