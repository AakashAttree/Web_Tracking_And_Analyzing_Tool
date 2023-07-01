package web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack;

public class Time_zone {

	  private String id;
	  private String current_time;
	  private float gmt_offset;
	  private String code;
	  private boolean is_daylight_saving;


	 // Getter Methods 

	  public String getId() {
	    return id;
	  }

	  public String getCurrent_time() {
	    return current_time;
	  }

	  public float getGmt_offset() {
	    return gmt_offset;
	  }

	  public String getCode() {
	    return code;
	  }

	  public boolean getIs_daylight_saving() {
	    return is_daylight_saving;
	  }

	 // Setter Methods 

	  public void setId( String id ) {
	    this.id = id;
	  }

	  public void setCurrent_time( String current_time ) {
	    this.current_time = current_time;
	  }

	  public void setGmt_offset( float gmt_offset ) {
	    this.gmt_offset = gmt_offset;
	  }

	  public void setCode( String code ) {
	    this.code = code;
	  }

	  public void setIs_daylight_saving( boolean is_daylight_saving ) {
	    this.is_daylight_saving = is_daylight_saving;
	  }

}
