package EC_Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	Properties pro;

	public ReadConfig()
	{
		try {

			FileInputStream fis=new FileInputStream("G:\\Ecplise_workspace\\ECommerce_Pojo\\Configurations\\config.properties");
			pro=new Properties();
			pro.load(fis);		

		}catch(Exception e)
		{
          System.out.println("Exception is "+e.getMessage());
		}
	}

	public String getApplicationUrl()
	{
		return pro.getProperty("baseUrl");
		
	}
	public String getUserName()
	{
		return pro.getProperty("userName");
		
	}
	public String getPassword()
	{
		return pro.getProperty("password");
		
	}
	public void setAccessToken(String token)
	{
	 pro.setProperty("token", token);
	 
		
	}
	public String getAccessToken()
	{
		return pro.getProperty("token");
		
	}

}
