package it.polimi.parkingService.webApplication.account.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code UserDao} implements user dao
 */
@Repository
public class UserDao implements IUserDao {

	private final EntityManager entityManager;

	/**
	 * Constructs the dao
	 * @param entityManager the entity low level manager
	 */
	public UserDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findByUserName(String username) {

		TypedQuery<User> query = entityManager.createQuery("from User where userName=:uName and enabled=true", User.class);
		query.setParameter("uName", username);

		User user;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			user = null;
		}

		return user;
	}

	@Override
	public User findById(Long id) {
		return entityManager.find(User.class, id);

	}

	@Override
	@Transactional
	public void save(User user) {

		entityManager.merge(user);
	}


}
