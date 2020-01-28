package user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import dao.UserDAO;
import dao.RoleDAO;
import entities.User;

@Named
@RequestScoped
public class UsersBB {
	private List<User> allUsers;
	private List<User> lastRegisteredUsers;
	private User selectedUser;
	private User editedUser;

	@Inject
	UserDAO userDAO;
	
	@PostConstruct
    public void init() {
        this.allUsers = userDAO.getAllUsers();
        this.lastRegisteredUsers = userDAO.getLastRegisteredUsers();
    }

	public int amountOfUsers() {
		return userDAO.countUsers();
	}
	
	public List<User> getLastRegisteredUsers(){
		return this.lastRegisteredUsers;
	}
	
	public List<User> getAllUsers(){
		return this.allUsers;
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public User getEditedUser() {
		return editedUser;
	}

	public void setEditedUser(User editedUser) {
		this.editedUser = editedUser;
	}

	private String temp_role;


	public String getTemp_role() {
		return temp_role;
	}

	public void setTemp_role(String temp_role) {
		this.temp_role = temp_role;
	}
	
	public String getRoleName(User user) {
		return "chuj";
	}
	
}
