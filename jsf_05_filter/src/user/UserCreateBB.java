package user;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.jsfcourse.login.ClientData;

import javax.faces.simplesecurity.PasswordHash;

import dao.UserDAO;
import dao.RoleDAO;
import dao.LogDAO;
import entities.Log;
import entities.Role;
import entities.User;

@Named
@RequestScoped
public class UserCreateBB {
	private static final String PAGE_STAY_AT_THE_SAME = "/pages/admin/users.xhtml";
	private User user = new User();
	private String roleOption;
	
	@Inject
	RoleDAO roleDAO;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	LogDAO logDAO;
	
	@Inject
	ClientData clientData;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRoleOption() {
		return roleOption;
	}
	public void setRoleOption(String roleOption) {
		this.roleOption = roleOption;
	}
	
	public boolean validateUser(User user) {
		List<User> duplicates = userDAO.searchForDuplicate(user.getUsername(), user.getEmail());
		if(duplicates.isEmpty() || duplicates == null) return true;
		else return false;
	}
	
	public void setUserRole() {
		//Set Role ID
		Role role = roleDAO.getRoleByName(this.roleOption);
		this.user.setRole(role);;
	}
	
	public void setHashedPassword() {
		//Hash Password
		String hashPassword = null;
		PasswordHash hash = new PasswordHash();
		hashPassword = hash.hashPassword(this.user.getPassword());
		this.user.setPassword(hashPassword);
	}
	
	public void createUser() throws IOException {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(clientData.getClient().getRole().getPermission().getCreateUser() != 1) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nie posiadasz uprawnień do tworzenia użytkowników", null));
		}
		
		
		if(validateUser(user)) {
			setUserRole();
			setHashedPassword();
			userDAO.create(user);
			
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Pomyślnie utworzono konto nowego uzytkownika", null));
			Log log = new Log("User created", "Utworzono nowego uzytkownika: " + user.getUsername());
			logDAO.create(log);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		}
		else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nie udało się utworzyć uzytkownika: login lub email występują w systemie", null));
		}
	}
	
}
