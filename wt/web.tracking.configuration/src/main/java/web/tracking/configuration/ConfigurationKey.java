package web.tracking.configuration;

public class ConfigurationKey {
  private String companyId;
  private String moduleName;
  private String name;

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
    result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ConfigurationKey other = (ConfigurationKey) obj;
    if (companyId == null) {
      if (other.companyId != null)
        return false;
    } else if (!companyId.equals(other.companyId))
      return false;
    if (moduleName == null) {
      if (other.moduleName != null)
        return false;
    } else if (!moduleName.equals(other.moduleName))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }



}
