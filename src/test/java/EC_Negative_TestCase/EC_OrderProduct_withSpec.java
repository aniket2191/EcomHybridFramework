package EC_Negative_TestCase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.testng.annotations.Test;
import EC_Base.TestBase;
import EC_Pojo.LoginCredentials;
import EC_Pojo.LoginResponse;
import EC_Pojo.Order;
import EC_Pojo.PlacedOrderDetails;
import EC_Pojo.AllOrdersToPlace;
import EC_Pojo.DeleteOrderDetails;
import EC_Pojo.FetchedAllOrderDetails;
import EC_Pojo.AddedProductDetails;
import io.restassured.RestAssured;

public class EC_OrderProduct_withSpec extends TestBase{

	LoginCredentials lC;
	LoginResponse lR;
	AddedProductDetails pD;
	AllOrdersToPlace ods;
	Order od;
	PlacedOrderDetails pOD;
	FetchedAllOrderDetails fAOD;
	DeleteOrderDetails doD;

	@Test(priority = 0)
	public void getAccessToken() throws InterruptedException
	{
		lC=new LoginCredentials();	
		//lC.setUserEmail("manali.kulkarni@cogniwize.com");
		//lC.setUserPassword("Manali@123");
		lC.setUserEmail(getConfig().getUserName());
		lC.setUserPassword(getConfig().getPassword());

		lR=	
				given()
				.spec(requestSpec("application/json"))
				.body(lC)
				.when()
				.post("api/ecom/auth/login")
				.then()
				.log().all()
				.spec(resSpec(201))
				.extract().response().as(LoginResponse.class);	

		assertEquals("Login Successfully", lR.getMessage());
	}

	@Test(priority = 1)
	public void addProduct() throws InterruptedException
	{

		Map<String, String> fData =setFormData("qwerty", lR.getUserId(), "fashion", "shirts", "11500","Addias Originals","women");

		pD=
				given()
				.spec(requestSpec("multipart/form-data"))	
				.header("Authorization", lR.getToken())
				.formParams(fData)
				.multiPart("productImage", new File("G:\\Resume\\Cogniwize\\Training\\photo.jpg"))
				.when()
				.post("api/ecom/product/add-product")
				.then()
				.log().all()
				.spec(resSpec(201))
				.extract().response().as(AddedProductDetails.class);

		assertEquals("Product Added Successfully", pD.getMessage());

	}

	@Test(priority = 2)
	public void placeOrder() throws InterruptedException
	{

		od=new Order();
		od.setCountry("India");
		od.setProductOrderedId(pD.getProductId());

		ArrayList<Order> odsList=new ArrayList<>();
		odsList.add(od);
		ods=new AllOrdersToPlace();
		ods.setOrders(odsList);

		pOD=	
				given()
				.log().all()
				.spec(requestSpec("application/json"))
				.header("Authorization",lR.getToken())
				.body(ods)
				.when()
				.post("api/ecom/order/create-order")
				.then()
				.log().all()
				.spec(resSpec(201))
				.extract().response().as(PlacedOrderDetails.class);

		assertEquals("Order Placed Successfully", pOD.getMessage());

		System.out.println("orderId :"+pOD.getOrders().get(0));
		System.out.println("OrderProductId :"+pOD.getProductOrderId().get(0));
		System.out.println("SuccessFully..");


	}

	@Test(priority = 3)
	public void viewOrderDetails() throws InterruptedException
	{
		RestAssured.baseURI=getConfig().getApplicationUrl();

		fAOD=
				given()
				.header("Authorization",lR.getToken())
				.queryParam("id", pOD.getOrders().get(0))
				.when()
				.get("api/ecom/order/get-orders-details")
				.then()
				.log().all()
				.spec(resSpec(200))
				.extract().response().as(FetchedAllOrderDetails.class);

		assertEquals("Orders fetched for customer Successfully", fAOD.getMessage());

	}

	@Test(priority = 4)
	public void deleteProduct() throws InterruptedException
	{
		RestAssured.baseURI=getConfig().getApplicationUrl();

		doD=
				given()
				.header("Authorization",lR.getToken())		
				.when()
				.delete("api/ecom/product/delete-product/{pdoductId}",fAOD.getData().getProductOrderedId())
				.then()
				.log().all()
				.spec(resSpec(200))
				.extract().response().as(DeleteOrderDetails.class);

		assertEquals("Product Deleted Successfully", doD.getMessage());

	}


}
