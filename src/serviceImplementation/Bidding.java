package serviceImplementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.utils.SystemProperty;

/**
 * this class is responsible for registering new products.
 * 
 * @author Leila
 *
 */
public class Bidding {
	
	
	public String currentTimestamp(){
		Date date = new Date();
        return (new Timestamp(date.getTime()).toString());
	}
	
	/**
	 * adds a new product to database
	 * 
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	public String addBid(Bid bid) throws Exception {

		Connection connection = connect();

		if (isUpdate(connection,bid)){

			PreparedStatement statement = connection
					.prepareStatement("update bids set biddingPrice = "+ bid.getPrice() + ", biddingDate = '"+currentTimestamp()+
							"' where custID = " + bid.getCustID() + " and productID = " + bid.getProductID());
			statement.executeUpdate();
			return "your previous bid is updated! :) ";
		}
		else {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO `bids` (`custID`, `productID`, `biddingPrice`, `biddingDate`)"
							+ "VALUES ('" + bid.getCustID() + "','" + bid.getProductID() + "','" + bid.getPrice() + "','"+currentTimestamp()+"')");
			statement.executeUpdate();

			return "your bid is saved! :) ";}
	}

	public Connection connect() throws Exception {

		String url;
		Connection connect;

		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			// Load the class that provides the new "jdbc:google:mysql://"
			// prefix.
			Class.forName("com.mysql.jdbc.GoogleDriver");
			url = "jdbc:google:mysql://biddingservice-150217:biddingservice/biddingservice?user=myuser&password=leila";
			connect = DriverManager.getConnection(url);
		} else {
			// Local MySQL instance to use during development.
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://127.0.0.1:3306/biddingservice?user=root";
			connect = DriverManager.getConnection(url);
		}
		return connect;
	}

	public boolean isUpdate(Connection connect, Bid bid) throws Exception{

		PreparedStatement statement = connect.prepareStatement("select * from bids where custID="+ bid.getCustID() +" and productID="+bid.getProductID());
		ResultSet result = statement.executeQuery();
		String update="no";

		while(result.next()){
			if(result.getString(1).equals(bid.getCustID()))
				update = "yes";
		}

		if(update.equals("yes"))
			return true;
		else
			return false;

	}

	public Bid getHighestBidder(String productID) throws Exception{

		Connection connection = connect();

		//select from DB
		PreparedStatement statement = connection.prepareStatement("select * from (select * from bids where biddingPrice = ( select max(biddingPrice) from bids as f where f.productID = bids.productID) and productID="+productID+") as x where x.biddingDate = (select min(biddingDate) from bids where biddingPrice = ( select max(biddingPrice) from bids as f where f.productID = bids.productID) and productID="+productID+");");
			
		ResultSet result = statement.executeQuery();
		Bid mybid = new Bid();
				
		if (!result.isBeforeFirst() ) {    
		    mybid = new Bid("-1",productID,"-1",0);
		} 
		while(result.next()){
					
			mybid = new Bid(result.getString(1),productID,result.getString(4),result.getInt(3));
			
		}

		return mybid;
	}

	public List<Bid> getYourBids(String custID) throws Exception {
		Connection connection = connect();

		//select from DB
		PreparedStatement statement = connection.prepareStatement("select * from bids where custID =" + custID);
		ResultSet result = statement.executeQuery();

		List<Bid> bidlist = new ArrayList<>();

		while(result.next()){

			Bid mybid = new Bid(result.getString(1),result.getString(2),result.getString(4),result.getInt(3));
			bidlist.add(mybid);

		}

		return bidlist;
	}

	public String deleteYourBids(String custID, String productID) throws Exception{

		Connection connection = connect();
		
		PreparedStatement statement = connection
				.prepareStatement("delete from bids where custID = '"+custID+"' and productID = '"+productID+"';");
		statement.executeUpdate();

		return "deleted!";
	}

	public String findmaxBid(String productID) throws Exception{
		Connection connection = connect();
		String myresponse = "0";
		//select from DB
		PreparedStatement statement = connection.prepareStatement("select max(biddingPrice) from bids where biddingPrice = ( select max(biddingPrice) from bids as f where f.productID = bids.productID) and productID=" + productID);
		ResultSet result = statement.executeQuery();

		while(result.next()){

			myresponse = Integer.toString(result.getInt(1));

		}

		return myresponse;
	}


}
