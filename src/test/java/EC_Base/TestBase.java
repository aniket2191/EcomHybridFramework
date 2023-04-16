package EC_Base;

import java.util.HashMap;
import java.util.Map;
import EC_Utilities.ReadConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

public class TestBase {
	
	public ReadConfig getConfig()
	{
          return new ReadConfig();
	}

	public Map<String, String> setFormData(String productName,String productAddedBy,String productCategory,String productSubCategory,String productPrice,String productDescription,String productFor)
	{
		Map<String, String> fMap=new HashMap<String, String>();
		
		fMap.put("productName", productName);
		fMap.put("productAddedBy",productAddedBy );
		fMap.put("productCategory", productCategory);
		fMap.put("productSubCategory",productSubCategory );
		fMap.put("productPrice", productPrice);
		fMap.put("productDescription",productDescription );
		fMap.put("productFor", productFor); //hii
		
		return fMap;	
		
	}
	
	public RequestSpecification requestSpec(String ContentType)
	{
		return
		new RequestSpecBuilder()
		.setBaseUri(getConfig().getApplicationUrl())
		.setContentType(ContentType)
		.build();
		
	}
	
	public ResponseSpecification resSpec(int statusCode)
	{	
		return
		new  ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectContentType(ContentType.JSON)
		.build();
	}
	
	
}
