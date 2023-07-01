package web.tracking.controller.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.tracking.controller.request.RolePOJO;
import web.tracking.db.dao.RoleRepository;
import web.tracking.db.dto.RoleDTO;

@RestController
public class RolesController {
	@Autowired
	RoleRepository roleRepository;

	@RequestMapping(value = "/company/{compId}/roles/get", method = RequestMethod.GET)
	public List<RolePOJO> get(@PathVariable(name = "compId") String compId) {
		System.out.println(compId);
		return transform(roleRepository.findByCompId(compId));
	}

	private List<RolePOJO> transform(List<RoleDTO> list) {
		List<RolePOJO> pojos = new ArrayList<>();
		for (RoleDTO dto : list) {
			RolePOJO pojo = new RolePOJO();
			pojo.setRoleId(dto.getRoleId());
			pojo.setCompId(dto.getCompId());
			pojo.setRoleName(dto.getRoleName());
			pojo.setDescription(dto.getDescription());
			pojos.add(pojo);
		}
		return pojos;
	}

	@RequestMapping(value = "/company/{compId}/roles/post", method = RequestMethod.POST)
	public String post(@ModelAttribute RolePOJO rolePOJO) {
		roleRepository.save(transform(rolePOJO));
		return "refresh";
	}

	private RoleDTO transform(RolePOJO rolePOJO) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleId(rolePOJO.getRoleId());
		roleDTO.setCompId(rolePOJO.getCompId());
		roleDTO.setDescription(rolePOJO.getDescription());
		roleDTO.setRoleName(rolePOJO.getRoleName());
		return roleDTO;
	}
}
