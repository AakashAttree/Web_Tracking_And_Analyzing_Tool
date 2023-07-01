package web.tracking.application.test;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import web.tracking.configuration.LoadConfigurationInDB;
import web.tracking.db.dao.AccountRepository;
import web.tracking.db.dao.CompanyRepository;
import web.tracking.db.dao.WebTrackingDAO;
import web.tracking.db.dto.AccountDTO;
import web.tracking.db.dto.CompanyDTO;

public class SuperAdminDataCreator extends Thread {
	private ApplicationContext applicationContext;

	public SuperAdminDataCreator(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
	}

	@Override
	public void run() {
		configuredata(applicationContext);
	}

	private void configuredata(ApplicationContext applicationContext) {
		CompanyRepository companyRepository = applicationContext.getBean(CompanyRepository.class);

		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setName("Super Admin Company");
		WebTrackingDAO<CompanyDTO> dao = new WebTrackingDAO<CompanyDTO>();
		CompanyDTO exsits = dao.findRecord(companyRepository, companyDTO);
		if (exsits == null) {
			companyDTO.setName("Super Admin Company");
			companyDTO.setDomainName("http://localhost:9000");
			companyDTO = companyRepository.save(companyDTO);
			LoadConfigurationInDB configurationInDB = applicationContext.getBean(LoadConfigurationInDB.class);
			try {
				configurationInDB.loadConfiguration(companyDTO.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AccountRepository accountRepository = applicationContext.getBean(AccountRepository.class);
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setCompId(companyDTO.getId());
			accountDTO.setEmail("m06nov@gmail.com");
			accountDTO.setFirstName("Super");
			accountDTO.setLastName("Admin");
			accountDTO.setMobile("8744003819");
			String password = "$2a$12$ZU0Bcm9ZiXjhGG.11Y6pLu41unPhaxhRiiYGQUtc1xd24q1dA.UQi";
			System.out.println("------------------------------------------");
			System.out.println("Current password for SA : " + password);
			System.out.println("------------------------------------------");
			accountDTO.setPassword(password);
			accountDTO.setUserName("m06nov");
			accountDTO.setRoles(new String[] { "ROLE_SA" });
			accountRepository.insert(accountDTO);
		}

		/*
		 * AccountDTO accountDTO2 = new AccountDTO();
		 * accountDTO2.setCompId(companyDTO.getId());
		 * accountDTO2.setEmail("m06nov@gmail.com"); accountDTO2.setFirstName("Super");
		 * accountDTO2.setLastName("Admin"); accountDTO2.setMobile("8744003819"); String
		 * password2 = "test";
		 * 
		 * System.out.println("------------------------------------------");
		 * 
		 * System.out.println("Current password for CA : " + password2);
		 * System.out.println("------------------------------------------");
		 * 
		 * accountDTO2.setPassword(password2); accountDTO2.setUserName("m06nov2");
		 * accountDTO2.setRoles(new String[] {"ROLE_ADMIN"});
		 * accountRepository.insert(accountDTO2);
		 * 
		 * 
		 * 
		 * AccountDTO accountDTO3 = new AccountDTO();
		 * accountDTO3.setCompId(companyDTO.getId());
		 * accountDTO3.setEmail("m06nov@gmail.com"); accountDTO3.setFirstName("Super");
		 * accountDTO3.setLastName("Admin"); accountDTO3.setMobile("8744003819"); String
		 * password3 = "test";
		 * 
		 * System.out.println("------------------------------------------");
		 * 
		 * System.out.println("Current password for User : " + password2);
		 * System.out.println("------------------------------------------");
		 * 
		 * accountDTO3.setPassword(password2); accountDTO3.setUserName("m06nov3");
		 * accountDTO3.setRoles(new String[] {"ROLE_USER"});
		 * accountRepository.insert(accountDTO3);
		 * 
		 * CompanyVariableRepository companyVariableRepository =
		 * applicationContext.getBean(CompanyVariableRepository.class);
		 * 
		 * 
		 * CompanyVariableDTO companyVariable = new CompanyVariableDTO();
		 * companyVariable.setCompanyId(companyDTO.getId());
		 * companyVariable.setDefaultValue("test");
		 * companyVariable.setDescription("test");
		 * companyVariable.setIncludeInReport("Yes"); companyVariable.setName("test");
		 * companyVariable.setRequestParameter("test");
		 * 
		 * companyVariableRepository.insert(companyVariable);
		 * 
		 * 
		 * 
		 * CompanyVariableDTO companyVariable1 = new CompanyVariableDTO();
		 * companyVariable1.setCompanyId(companyDTO.getId());
		 * companyVariable1.setDefaultValue("test1");
		 * companyVariable1.setDescription("test1");
		 * companyVariable1.setIncludeInReport("Yes");
		 * companyVariable1.setName("test1");
		 * companyVariable1.setRequestParameter("test1");
		 * 
		 * companyVariableRepository.insert(companyVariable1);
		 * 
		 * 
		 * CompanyVariableDTO uniqueVariables = new CompanyVariableDTO();
		 * uniqueVariables.setCompanyId(companyDTO.getId());
		 * uniqueVariables.setDefaultValue(""); uniqueVariables.setDescription("Email");
		 * uniqueVariables.setIncludeInReport("No"); uniqueVariables.setName("email");
		 * uniqueVariables.setRequestParameter("email");
		 * uniqueVariables.setUniqueId("yes");
		 * companyVariableRepository.insert(uniqueVariables);
		 */

	}
}
