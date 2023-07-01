package web.tracking.processes.starter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "web.tracking" })
@EnableMongoRepositories(basePackages = { "web.tracking.db", "web.tracking.activities" })
public class WebTrackingProcessStarter {

  public static void main(String[] args) {
    ConfigurableApplicationContext appContext = SpringApplication.run(WebTrackingProcessStarter.class, args);
    WebTrackingWorkflowStarter workflowStarter = new WebTrackingWorkflowStarter(appContext);
    ScheduledExecutorService workflowExecutor = Executors.newScheduledThreadPool(2);
    workflowExecutor.scheduleWithFixedDelay(workflowStarter, 30L, 30L, TimeUnit.SECONDS);
    
    ScheduledExecutorService callPythonModelsPoolExecutor = Executors.newScheduledThreadPool(1);
    callPythonModelsPoolExecutor.scheduleWithFixedDelay(()->{
      System.out.println("Calling python api.............");
      callURL("http://localhost:5000/createCustomerPredictTemplate");
      callURL("http://localhost:5000/clusterSession");
    }, 5L, 5L, TimeUnit.MINUTES);
    
  }

  private static void callURL(String url) {
    StringBuilder builder = new StringBuilder();
    try(CloseableHttpClient client = HttpClients.createDefault()) {
      HttpGet httpPost = new HttpGet(url);
      CloseableHttpResponse response = client.execute(httpPost);
      InputStream content = response.getEntity().getContent();
      BufferedInputStream bufferedInputStream = new BufferedInputStream(content);
      byte[] data = new byte[1024]; 
      while(bufferedInputStream.read(data)!=-1){    
        builder.append(new String(data)) ;
      }   
    } catch (UnsupportedOperationException | IOException e) {
      e.printStackTrace();
    }
     assert builder.toString().equalsIgnoreCase("success");
  }

}
