package web.tracking.controller.report;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.tracking.controller.request.SessionClusterRequest;
import web.tracking.controller.response.SessionClusterResponse;
import web.tracking.db.dao.EndUserActiviesRepository;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.SessionClusterRepository;
import web.tracking.db.dao.SessionTrackingDataRepository;
import web.tracking.db.dto.EndUserActivies;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SessionClusterDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;

@RestController
public class SessionClusterReportController {

  @Autowired
  private SessionClusterRepository sessionClusterRepository;

  @Autowired
  private SessionTrackingDataRepository sessionTrackingDataRepository;

  @Autowired
  private RequestTrackingDataRepository requestTrackingRepo;

  @Autowired
  private EndUserActiviesRepository userActiviesRepository;

  @GetMapping("/report/session-cluster/{companyId}")
  public List<SessionClusterResponse> getResponse(@PathVariable(name = "companyId") String companyId)
  {
    List<SessionTrackingDataDTO> sessions = sessionTrackingDataRepository.findByCompanyIdAndClosed(companyId, true);
    Function<SessionTrackingDataDTO, List<SessionClusterResponse>> mapper 
    = session -> transform(sessionClusterRepository.findBySessionId(session.getSessionId()));
    return sessions.stream().map(mapper).flatMap(List::stream).collect(Collectors.toList());
  }

  @PostMapping("/report/session-cluster/{companyId}")
  public String updateClusterInfoamtion(@PathVariable(name = "companyId") String companyId, @ModelAttribute SessionClusterRequest requestData) {
    List<SessionClusterDTO> clusterData = sessionClusterRepository.findBySessionId(requestData.getSessionId());
    clusterData.forEach(session -> {
      session.setCustomer(requestData.getCustomer());
      sessionClusterRepository.save(session);
    });
    return null;
  }

  @PostMapping("/prediction/customer")
  public String predictCustomer(@RequestBody SessionClusterRequest requestData) throws ClientProtocolException, IOException {

    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost("http://localhost:5000/predict");

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("numberOfRequests", String.valueOf(requestData.getNumberOfRequests())));
    params.add(new BasicNameValuePair("averageRequestTime", String.valueOf(requestData.getAverageRequestTime())));
    params.add(new BasicNameValuePair("totalDurationOfSession", String.valueOf(requestData.getTotalDurationOfSession())));
    params.add(new BasicNameValuePair("numberOfDistinctPageVisit", String.valueOf(requestData.getNumberOfDistinctPageVisit())));
    params.add(new BasicNameValuePair("numberOfDistinctActivities", String.valueOf(requestData.getNumberOfDistinctActivities())));
    params.add(new BasicNameValuePair("knownContact", String.valueOf(requestData.getKnownContact()?1:0)));
    params.add(new BasicNameValuePair("hasSocialMediaId", String.valueOf(requestData.getHasSocialMediaId()?1:0)));
    //      params.add(new BasicNameValuePair("cluster", requestData.getCluster()));
    httpPost.setEntity(new UrlEncodedFormEntity(params));

    CloseableHttpResponse response = client.execute(httpPost);
    InputStream content = response.getEntity().getContent();
    BufferedInputStream bufferedInputStream = new BufferedInputStream(content);
    BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
    StringBuilder result = new StringBuilder();
    String line;
    while((line = reader.readLine()) != null) {
      result.append(line);
    }
    client.close();
    return result.toString().replace("[", "").replace("]", "").toLowerCase();
  }

  private List<SessionClusterResponse> transform(List<SessionClusterDTO> clusterList) {
    List<SessionClusterResponse> clusterResponses = new ArrayList<>();
    for(SessionClusterDTO dto: clusterList) {
      clusterResponses.add(transform(dto));
    }
    return clusterResponses;
  }

  private SessionClusterResponse transform(SessionClusterDTO sessionClusterDTO) {
    SessionClusterResponse response = new SessionClusterResponse();
    response.setId(sessionClusterDTO.getId());
    response.setAverageRequestTime(sessionClusterDTO.getAverageRequestTime());
    response.setCluster(sessionClusterDTO.getCluster());
    response.setHasSocialMediaId(sessionClusterDTO.getHasSocialMediaId());
    response.setKnownContact(sessionClusterDTO.getKnownContact());
    response.setNumberOfDistinctActivities(sessionClusterDTO.getNumberOfDistinctActivities());
    response.setNumberOfRequests(sessionClusterDTO.getNumberOfRequests());
    response.setSessionId(sessionClusterDTO.getSessionId());
    response.setTotalDurationOfSession(sessionClusterDTO.getTotalDurationOfSession());
    response.setNumberOfDistinctPageVisit(sessionClusterDTO.getNumberOfDistinctPageVisit());
    response.setCustomer(sessionClusterDTO.getCustomer());
    if(Boolean.TRUE.equals(sessionClusterDTO.getKnownContact())) {
      Set<String> contacts = new HashSet<>();
      List<RequestTrackingDataDTO> requestsInSession = requestTrackingRepo.findBySessionId(sessionClusterDTO.getSessionId());
      for(RequestTrackingDataDTO requestDTO : requestsInSession) {
        EndUserActivies endUserActivity = userActiviesRepository.findByRequestId(requestDTO.getRequestId());
        if(!"unknown".equalsIgnoreCase(endUserActivity.getEndUserId())) {
          contacts.add(endUserActivity.getEndUserId());
        }
      }
      String contactsInSession = String.join(",", contacts);
      response.setContactsInSession(contactsInSession);
    }
    else {
      response.setContactsInSession("");
    }
    return response;
  }


}
