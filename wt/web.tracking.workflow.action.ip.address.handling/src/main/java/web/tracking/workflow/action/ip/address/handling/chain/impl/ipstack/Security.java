package web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack;

public class Security {

	  private boolean is_proxy;
	  private String proxy_type = null;
	  private boolean is_crawler;
	  private String crawler_name = null;
	  private String crawler_type = null;
	  private boolean is_tor;
	  private String threat_level;
	  private String threat_types = null;


	 // Getter Methods 

	  public boolean getIs_proxy() {
	    return is_proxy;
	  }

	  public String getProxy_type() {
	    return proxy_type;
	  }

	  public boolean getIs_crawler() {
	    return is_crawler;
	  }

	  public String getCrawler_name() {
	    return crawler_name;
	  }

	  public String getCrawler_type() {
	    return crawler_type;
	  }

	  public boolean getIs_tor() {
	    return is_tor;
	  }

	  public String getThreat_level() {
	    return threat_level;
	  }

	  public String getThreat_types() {
	    return threat_types;
	  }

	 // Setter Methods 

	  public void setIs_proxy( boolean is_proxy ) {
	    this.is_proxy = is_proxy;
	  }

	  public void setProxy_type( String proxy_type ) {
	    this.proxy_type = proxy_type;
	  }

	  public void setIs_crawler( boolean is_crawler ) {
	    this.is_crawler = is_crawler;
	  }

	  public void setCrawler_name( String crawler_name ) {
	    this.crawler_name = crawler_name;
	  }

	  public void setCrawler_type( String crawler_type ) {
	    this.crawler_type = crawler_type;
	  }

	  public void setIs_tor( boolean is_tor ) {
	    this.is_tor = is_tor;
	  }

	  public void setThreat_level( String threat_level ) {
	    this.threat_level = threat_level;
	  }

	  public void setThreat_types( String threat_types ) {
	    this.threat_types = threat_types;
	  }

}
