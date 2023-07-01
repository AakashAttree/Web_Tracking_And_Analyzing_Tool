package web.tracking.kafka.consumer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import web.tracking.core.StringUtils;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.CompanyActiviesRepository;
import web.tracking.db.dao.CompanyVariableRepository;
import web.tracking.db.dao.EndUserActiviesRepository;
import web.tracking.db.dao.RequestTrackingDataRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.CompanyActivies;
import web.tracking.db.dto.CompanyVariableDTO;
import web.tracking.db.dto.EndUserActivies;
import web.tracking.db.dto.RequestTrackingDataDTO;
import web.tracking.db.dto.SessionTrackingDataDTO;
import web.tracking.workflow.impl.StandardWorkflow;


@Component
public class WebTrackingStreamConsumer {

  private Logger logger = LoggerFactory.getLogger(WebTrackingStreamConsumer.class);

  private KafkaStreams webTrackingKafkaStream;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private CompanyActiviesRepository companyActiviesRepository;
  @Autowired
  private RequestTrackingDataRepository requestTrackingRepo;
  @Autowired
  private EndUserActiviesRepository userActiviesRepository;
  @Autowired
  private CompanyVariableRepository companyVariableRepository;

  @PostConstruct
  public void postContrust() {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "webtracking");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    StreamsBuilder webTrackingStreamBuilder = new StreamsBuilder();
    KStream<Object, String> webTrackingStream = webTrackingStreamBuilder.stream("tracking-data");
    webTrackingStream.foreach((key, value) -> {
      try {
        if(StringUtils.isNotBlank(value)) {
          String json = new String(value.substring(1, value.lastIndexOf("\"")).replace("\\", ""));
          Gson gson = new Gson();
          SessionTrackingDataDTO trackingData = gson.fromJson(json, SessionTrackingDataDTO.class);
          if (trackingData != null) {
            RequestTrackingDataDTO dataDTO = convert(trackingData );
            dataDTO = requestTrackingRepo.save(dataDTO);
            if (dataDTO != null) {
              String companyId = null;
              Map<String, Object> map = dataDTO.getData();
              if (map.get(TrackingConstants.REQUEST_OBJECT) != null) {
                Object obj = map.get(TrackingConstants.REQUEST_OBJECT);
                if (obj instanceof Map) {
                  Map<String, String> requestObj = (Map) obj;
                  String optype = (String) requestObj.get("optype");
                  companyId = trackingData.getCompanyId();
                  List<CompanyVariableDTO> variables = companyVariableRepository.findByCompanyId(companyId);
                  String uniqueIdName = null;
                  Map<String, String> variableValue = new HashMap<>();
                  for (CompanyVariableDTO variable : variables) {
                    if (variable.getUniqueId() != null && "yes".equalsIgnoreCase(variable.getUniqueId())) {
                      uniqueIdName = findVariable(map, variable.getName());
                    } else {
                      variableValue.put(variable.getName(), findVariable(map, variable.getName()));
                    }
                  }
                  String userId = uniqueIdName;
                  if (userId == null) {
                    userId = "unknown";
                  }

                  if (optype != null) {
                    

                    EndUserActivies endUserActivies = new EndUserActivies();
                    endUserActivies.setCompanyId(companyId);
                    endUserActivies.setEndUserId(userId);
                    endUserActivies.setActivityType(optype);
                    endUserActivies.setCreatedTS(LocalDateTime.now());
                    endUserActivies.setRequestId(dataDTO.getRequestId());
                    userActiviesRepository.save(endUserActivies);
                  }
                }
              }
            }
          }
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
    });
    Topology webTrackingTopology = webTrackingStreamBuilder.build();
    webTrackingKafkaStream = new KafkaStreams(webTrackingTopology, props);
    webTrackingKafkaStream.start();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        webTrackingKafkaStream.close();
      }
    });
  }
  private RequestTrackingDataDTO convert(SessionTrackingDataDTO trackingData) {
    RequestTrackingDataDTO dataDTO = new RequestTrackingDataDTO();
    dataDTO.setCompanyId(trackingData.getCompanyId());
    dataDTO.setPage(trackingData.getPage());
    dataDTO.setData(trackingData.getData());
    dataDTO.setIpProcessed(false);
    dataDTO.setReferrerProcessed(false);
    dataDTO.setSessionId(trackingData.getSessionId());
    dataDTO.setCreatedTS(LocalDateTime.now());
    return dataDTO;
  }


  private String findVariable(Map<String, Object> map, String uniqueIdName) {
    Map<String, String> requestObj = (Map) map.get(TrackingConstants.REQUEST_OBJECT);
    String uniqueValue = requestObj.get(uniqueIdName);
    if (StringUtils.isBlank(uniqueValue)) {
      Map<String, String> requestParam = (Map) map.get(TrackingConstants.REQUEST_PARAM);
      uniqueValue = requestParam.get(uniqueIdName);
      if (StringUtils.isBlank(uniqueValue)) {
        Map<String, String> requestHeader = (Map) map.get(TrackingConstants.REQUEST_HEADER);
        uniqueValue = requestHeader.get(uniqueIdName);
        if (StringUtils.isBlank(uniqueValue)) {
          Map<String, String> cookies = (Map) map.get(TrackingConstants.COOKIES);
          uniqueValue = cookies.get(uniqueIdName);
        }
      }
    }
    return uniqueValue;
  }



  @PreDestroy
  public void preDestory() {
    if(webTrackingKafkaStream != null) {
      webTrackingKafkaStream.close();
    }
  }
}
