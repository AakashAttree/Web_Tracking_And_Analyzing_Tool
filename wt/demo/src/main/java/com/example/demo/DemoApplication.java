package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    // System Property for Chrome Driver   
    System.setProperty("webdriver.chrome.driver", "D:\\software\\Newfolder\\chromedriver.exe");  

    // Instantiate a ChromeDriver class.     
    WebDriver driver=new ChromeDriver();  

    // Click on the Search button  

    ScheduledExecutorService callPythonModelsPoolExecutor = Executors.newScheduledThreadPool(1);
    callPythonModelsPoolExecutor.scheduleWithFixedDelay(()->{
      callURL(driver, "http://192.168.56.1:9000/jsp/testvisittracking.jsp");
    }, 5L, 5L, TimeUnit.SECONDS);
    
    ScheduledExecutorService callPythonModelsPoolExecutor2 = Executors.newScheduledThreadPool(1);
    callPythonModelsPoolExecutor2.scheduleWithFixedDelay(()->{
      callURL(driver, "http://192.168.56.1:9000/jsp/testopentracking.jsp");
    }, 1L, 1L, TimeUnit.SECONDS);
    
    ScheduledExecutorService callPythonModelsPoolExecutor3 = Executors.newScheduledThreadPool(1);
    callPythonModelsPoolExecutor3.scheduleWithFixedDelay(()->{
      callURL(driver, "http://192.168.56.1:9000/jsp/testclicktracking.jsp");
    }, 1L, 1L, TimeUnit.SECONDS);

  }
  private static void callURL(WebDriver driver, String url) {
    StringBuilder builder = new StringBuilder();
    try {
      // Launch Website  
      driver.navigate().to(url);  

      //Maximize the browser  
      driver.manage().window().maximize();  

      //Scroll down the webpage by 5000 pixels  
      JavascriptExecutor js = (JavascriptExecutor)driver;  
      js.executeScript("scrollBy(0, 5000)");   

    } catch (Exception e) {
      e.printStackTrace();
    }
    assert builder.toString().equalsIgnoreCase("success");
  }
}
