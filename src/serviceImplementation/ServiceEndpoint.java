package serviceImplementation;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bid")
public class ServiceEndpoint {

	Bidding bidding = new Bidding();

	/**
	 * creates a new product for sale.
	 * @param product
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public String DBinsert(Bid bid) throws Exception{

		return bidding.addBid(bid);

	}

	//get the highest bidder if exists
	@GET
	@Path("/info/{productID}")
	@Produces(MediaType.APPLICATION_XML)
	public Bid findBid(@PathParam ("productID") String productID) throws Exception{

		return bidding.getHighestBidder(productID);

	}
	
	//get the highest bid till now for a product
		@GET
		@Path("/maxbid/{productID}")
		@Produces(MediaType.TEXT_PLAIN)
		public String findmaxBid(@PathParam ("productID") String productID) throws Exception{

			return bidding.findmaxBid(productID);

		}
	
	//your bids
	@GET
	@Path("/info/user/{custID}")
	@Produces(MediaType.APPLICATION_XML)
	public List<Bid> findYourBids(@PathParam ("custID") String custID) throws Exception{

		return bidding.getYourBids(custID);

	}
	
	//delete your bids
		@GET
		@Path("/delete/{custID}&{productID}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteYourBids(@PathParam ("custID") String custID,@PathParam ("productID") String productID) throws Exception{

			return bidding.deleteYourBids(custID,productID);

		}

}
