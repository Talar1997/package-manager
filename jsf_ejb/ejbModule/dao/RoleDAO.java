package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Role;

@Stateless
public class RoleDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public Role find(Object id) {
		return em.find(Role.class, id);
	}
	
	public void create(Role role) {
		em.persist(role);
	}
	
	public void remove(Role role) {
		em.remove(em.merge(role));
	}
	
	public Role merge(Role role) {
		return em.merge(role);
	}
	
	public List<Role> getAllRoles(){
		return em.createNamedQuery("Role.findAll").getResultList();
	}
	
	public Role getRoleByName(String roleName) {
		Query query = em.createQuery("SELECT r FROM Role r WHERE r.name LIKE :name");
		query.setParameter("name", roleName);
		try {
			return (Role) query.getResultList().get(0);
		}catch(Exception e) {}
		return null;
	}
	
	public Role getRoleById(int id) {
		Query query = em.createQuery("SELECT r FROM Role r WHERE r.idRole = :id");
		query.setParameter("id", id);
		return (Role) query.getResultList().get(0);
	}

}
