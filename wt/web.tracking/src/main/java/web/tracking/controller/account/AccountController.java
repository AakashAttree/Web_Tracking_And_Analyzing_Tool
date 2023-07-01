package web.tracking.controller.account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.tracking.controller.request.AccountPojo;
import web.tracking.core.TrackingConstants;
import web.tracking.db.dao.AccountRepository;
import web.tracking.db.dto.AccountDTO;

@Controller
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @RequestMapping(value = "/company/{companyId}/account/post", method = RequestMethod.POST)
  @ResponseBody
  public String saveAccount(@PathVariable(name = "companyId",required = true) String companyId,
      @ModelAttribute() AccountPojo accountPojo) {

    if (accountPojo != null) {
      accountPojo.setCompId(companyId);
    }

    String operation = accountPojo.getOper();

    AccountDTO accountDTO = transform(accountPojo);
    if(!TrackingConstants.BCRYPT_PATTERN.matcher(accountDTO.getPassword()).matches()) {
      accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
    }
    
    LocalDateTime now = LocalDateTime.now();
    if ("add".equals(operation)) {
      accountDTO.setCreatedBy("");
      accountDTO.setCreatedTS(now);
      accountDTO.setModifiedBy("");
      accountDTO.setModifiedTS(now);
      accountDTO.setId(null);
      accountRepository.insert(accountDTO);
    } else if ("edit".equalsIgnoreCase(operation)) {
      accountDTO.setModifiedBy("");
      accountDTO.setModifiedTS(now);
      accountRepository.save(accountDTO);
    } else {
      accountRepository.delete(accountDTO);
    }
    return "refresh";
  }

  private AccountDTO transform(AccountPojo accountPojo) {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setCompId(accountPojo.getCompId());
    accountDTO.setEmail(accountPojo.getEmail());
    accountDTO.setFirstName(accountPojo.getFirstName());
    accountDTO.setId(accountPojo.getId());
    accountDTO.setLastName(accountPojo.getLastName());
    accountDTO.setMobile(accountPojo.getMobile());
    accountDTO.setPassword(accountPojo.getPassword());
    accountDTO.setUserName(accountPojo.getUserName());
    accountDTO.setCreatedBy(accountPojo.getCreatedBy());
    String createdTS = accountPojo.getCreatedTS();
    LocalDateTime now = LocalDateTime.now();
    if (StringUtils.isEmpty(createdTS)) {
      createdTS = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    accountDTO.setCreatedTS(LocalDateTime.parse(createdTS, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    accountDTO.setModifiedBy(accountPojo.getModifiedBy());
    String getModifiedTS = accountPojo.getCreatedTS();
    if (StringUtils.isEmpty(getModifiedTS)) {
      getModifiedTS = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    accountDTO
    .setModifiedTS(LocalDateTime.parse(getModifiedTS, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    List<String> roles = new ArrayList<>();

    if (accountPojo.getAdminrole() != null && "yes".equalsIgnoreCase(accountPojo.getAdminrole())) {
      roles.add(AccountRole.ADMIN.getRole());
    }

    if (accountPojo.getUserrole() != null && "yes".equalsIgnoreCase(accountPojo.getUserrole())) {
      roles.add(AccountRole.USER.getRole());
    }
    String[] arr = new String[roles.size()];
    accountDTO.setRoles(roles.toArray(arr));
    return accountDTO;
  }

  @RequestMapping(value = "/company/{companyId}/account/get", method = RequestMethod.GET)
  @ResponseBody
  public List<AccountPojo> getaccount(@PathVariable(name = "companyId") String companyId) {
    List<AccountPojo> accountPojos = new ArrayList<AccountPojo>();
    List<AccountDTO> accountDTOs = accountRepository.findByCompId(companyId);

    for (AccountDTO accountDTO : accountDTOs) {
      accountPojos.add(transform(accountDTO));
    }
    return accountPojos;
  }

  private AccountPojo transform(AccountDTO dto) {
    AccountPojo pojo = new AccountPojo();
    pojo.setCompId(dto.getCompId());
    pojo.setEmail(dto.getEmail());
    pojo.setFirstName(dto.getFirstName());
    pojo.setId(dto.getId());
    pojo.setLastName(dto.getLastName());
    pojo.setMobile(dto.getMobile());
    pojo.setPassword(dto.getPassword());
    pojo.setUserName(dto.getUserName());
    for (String role : dto.getRoles()) {

      if (AccountRole.ADMIN.getRole().equals(role)) {
        pojo.setAdminrole("Yes");
      }

      if (AccountRole.USER.getRole().equals(role)) {
        pojo.setUserrole("Yes");
      }
    }

    pojo.setCreatedBy(dto.getCreatedBy());
    LocalDateTime createdTS = dto.getCreatedTS();
    String strCreatedTS = "";
    if (createdTS != null) {
      strCreatedTS = createdTS.format(DateTimeFormatter.ofPattern("yyyy-MM-hh HH:mm:ss"));
    }
    pojo.setCreatedTS(strCreatedTS);
    pojo.setModifiedBy(dto.getModifiedBy());
    LocalDateTime modifiedTS = dto.getModifiedTS();
    String strModifiedTS = "";
    if (modifiedTS != null) {
      strModifiedTS = modifiedTS.format(DateTimeFormatter.ofPattern("yyyy-MM-hh HH:mm:ss"));
    }
    pojo.setModifiedTS(strModifiedTS);

    return pojo;
  }

}
