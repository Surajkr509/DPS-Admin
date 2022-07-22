package com.dps_admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dps_admin.model.Role;
import com.dps_admin.repository.RoleRepository;
import com.dps_admin.service.RoleService;


@Controller
@RequestMapping("/admin")
public class RoleController {
	
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	RoleService roleService;
	
	@GetMapping("/addRole")
	public ModelAndView addRole() {
		ModelAndView modelAndView = new ModelAndView();
			String rolename="";
		if (rolename.equals("")) {
			Role role = new Role();
			modelAndView.addObject("role", role);
			modelAndView.setViewName("roles/addRole");
			return modelAndView;
		} else {
			modelAndView.setViewName("admin/403");
			return modelAndView;
		}
	}
	
	@PostMapping("/addRole")
	public ModelAndView addRole(@Valid Role role,BindingResult bindingResult) {
		System.err.println(":::Admin.addRole::::::");
		ModelAndView modelAndView =new ModelAndView();
		if(role.getRoleName()!=null) {
			Role data=roleRepo.findByRoleName(role.getRoleName());
			System.out.println("Role::::::"+data);
			if(data!=null) {
				bindingResult.rejectValue("roleName","error.roleName","*Role Already Exists");
			}
			if(bindingResult.hasErrors()) {
				modelAndView.setViewName("/roles/addRole");
		} else {
					roleService.addRole(role);
			modelAndView.addObject("allRole", roleRepo.findAllRole());
			modelAndView.setViewName("roles/roleList");
			}
			return modelAndView;
	}else {
		modelAndView.setViewName("admin/403");
		return modelAndView;
	}
	}

	@GetMapping("/viewRoles")
	public String showRoles(Model model, HttpServletRequest request) {
		System.err.println("::: RoleController.showRoles :::");
	List<Role> roledata=roleRepo.findAllRole();
	System.out.println("Roles:::::"+roledata);
		if (!roledata.isEmpty()) {
			model.addAttribute("allRole",roledata);
			return "/roles/roleList";
		} else {
			return "index";
		}
	}
	@GetMapping(value = "/updateRoleStatus/{id}")
	@ResponseBody
	public String updateRoleStatus(@PathVariable("id") Long id) {
		System.err.println("::: RoleController.updateRoleStatus :::");
		Role role=roleRepo.findById(id).orElse(null);
		if(role!=null) {
			if(role.isActive()) 
				role.setActive(false);
			 else 
				role.setActive(true);
				roleRepo.save(role);
	}
		return "Role Status Updated";
	}
}
