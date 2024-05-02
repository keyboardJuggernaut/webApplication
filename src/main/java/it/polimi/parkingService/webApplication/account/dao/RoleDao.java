package it.polimi.parkingService.webApplication.account.dao;


import it.polimi.parkingService.webApplication.account.models.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
