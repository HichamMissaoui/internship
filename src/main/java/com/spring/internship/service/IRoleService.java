package com.spring.internship.service;

import java.util.Optional;

import com.spring.internship.model.Role;
import com.spring.internship.model.RoleName;

public interface IRoleService extends IGenericService<Role> {

	public Optional<Role> findByName(RoleName roleName);
}
