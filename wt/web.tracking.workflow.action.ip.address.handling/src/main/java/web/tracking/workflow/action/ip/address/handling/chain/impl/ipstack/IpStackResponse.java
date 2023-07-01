package web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack;

public class IpStackResponse {

	  private String ip;
	  private String hostname;
	  private String type;
	  private String continent_code;
	  private String continent_name;
	  private String country_code;
	  private String country_name;
	  private String region_code;
	  private String region_name;
	  private String city;
	  private String zip;
	  private float latitude;
	  private float longitude;
	  Location location;
	  Time_zone time_zone;
	  Currency currency;
	  Connection connection;
	  Security security;


	 // Getter Methods 

	  public String getIp() {
	    return ip;
	  }

	  public String getHostname() {
	    return hostname;
	  }

	  public String getType() {
	    return type;
	  }

	  public String getContinent_code() {
	    return continent_code;
	  }

	  public String getContinent_name() {
	    return continent_name;
	  }

	  public String getCountry_code() {
	    return country_code;
	  }

	  public String getCountry_name() {
	    return country_name;
	  }

	  public String getRegion_code() {
	    return region_code;
	  }

	  public String getRegion_name() {
	    return region_name;
	  }

	  public String getCity() {
	    return city;
	  }

	  public String getZip() {
	    return zip;
	  }

	  public float getLatitude() {
	    return latitude;
	  }

	  public float getLongitude() {
	    return longitude;
	  }

	  public Location getLocation() {
	    return location;
	  }

	  public Time_zone getTime_zone() {
	    return time_zone;
	  }

	  public Currency getCurrency() {
	    return currency;
	  }

	  public Connection getConnection() {
	    return connection;
	  }

	  public Security getSecurity() {
	    return security;
	  }

	 // Setter Methods 

	  public void setIp( String ip ) {
	    this.ip = ip;
	  }

	  public void setHostname( String hostname ) {
	    this.hostname = hostname;
	  }

	  public void setType( String type ) {
	    this.type = type;
	  }

	  public void setContinent_code( String continent_code ) {
	    this.continent_code = continent_code;
	  }

	  public void setContinent_name( String continent_name ) {
	    this.continent_name = continent_name;
	  }

	  public void setCountry_code( String country_code ) {
	    this.country_code = country_code;
	  }

	  public void setCountry_name( String country_name ) {
	    this.country_name = country_name;
	  }

	  public void setRegion_code( String region_code ) {
	    this.region_code = region_code;
	  }

	  public void setRegion_name( String region_name ) {
	    this.region_name = region_name;
	  }

	  public void setCity( String city ) {
	    this.city = city;
	  }

	  public void setZip( String zip ) {
	    this.zip = zip;
	  }

	  public void setLatitude( float latitude ) {
	    this.latitude = latitude;
	  }

	  public void setLongitude( float longitude ) {
	    this.longitude = longitude;
	  }

	  public void setLocation( Location locationObject ) {
	    this.location = locationObject;
	  }

	  public void setTime_zone( Time_zone time_zoneObject ) {
	    this.time_zone = time_zoneObject;
	  }

	  public void setCurrency( Currency currencyObject ) {
	    this.currency = currencyObject;
	  }

	  public void setConnection( Connection connectionObject ) {
	    this.connection = connectionObject;
	  }

	  public void setSecurity( Security securityObject ) {
	    this.security = securityObject;
	  }
}
