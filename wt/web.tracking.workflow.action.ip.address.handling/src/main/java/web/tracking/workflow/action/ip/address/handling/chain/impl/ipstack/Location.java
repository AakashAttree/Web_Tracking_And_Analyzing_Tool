package web.tracking.workflow.action.ip.address.handling.chain.impl.ipstack;

import java.util.ArrayList;
import java.util.List;

public class Location {
  private float geoname_id;
  private String capital;
  List<Object> languages = new ArrayList<Object>();
  private String country_flag;
  private String country_flag_emoji;
  private String country_flag_emoji_unicode;
  private String calling_code;
  private boolean is_eu;


 // Getter Methods 

  public float getGeoname_id() {
    return geoname_id;
  }

  public String getCapital() {
    return capital;
  }

  public String getCountry_flag() {
    return country_flag;
  }

  public String getCountry_flag_emoji() {
    return country_flag_emoji;
  }

  public String getCountry_flag_emoji_unicode() {
    return country_flag_emoji_unicode;
  }

  public String getCalling_code() {
    return calling_code;
  }

  public boolean getIs_eu() {
    return is_eu;
  }

 // Setter Methods 

  public void setGeoname_id( float geoname_id ) {
    this.geoname_id = geoname_id;
  }

  public void setCapital( String capital ) {
    this.capital = capital;
  }

  public void setCountry_flag( String country_flag ) {
    this.country_flag = country_flag;
  }

  public void setCountry_flag_emoji( String country_flag_emoji ) {
    this.country_flag_emoji = country_flag_emoji;
  }

  public void setCountry_flag_emoji_unicode( String country_flag_emoji_unicode ) {
    this.country_flag_emoji_unicode = country_flag_emoji_unicode;
  }

  public void setCalling_code( String calling_code ) {
    this.calling_code = calling_code;
  }

  public void setIs_eu( boolean is_eu ) {
    this.is_eu = is_eu;
  }
}