package it.polimi.parkingService.webApplication.account.dao;

import it.polimi.parkingService.webApplication.account.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * The {@code RoleDao} implements role dao
 */
@Repository
public class RoleDao implements IRoleDao {

	private final EntityManager entityManager;

	/**
	 * Constructs the dao
	 * @param entityManager the entity low level manager
	 */
	public RoleDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Role findRoleByName(String roleName) {

		TypedQuery<Role> query = entityManager.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", roleName);
		
		Role role;
		
		try {
			role = query.getSingleResult();
		} catch (Exception e) {
			role = null;
		}
		return role;
	}
}
