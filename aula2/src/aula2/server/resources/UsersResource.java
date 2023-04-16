package aula2.server.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import aula2.api.User;
import aula2.api.service.RestUsers;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@Singleton
public class UsersResource implements RestUsers {

	private final Map<String,User> users = new HashMap<>();

	private static Logger Log = Logger.getLogger(UsersResource.class.getName());
	
	public UsersResource() {
	}
		
	@Override
	public String createUser(User user) {
		Log.info("createUser : " + user);
		
		// Check if user data is valid
		if(user.getUserId() == null || user.getPassword() == null || user.getFullName() == null || 
				user.getEmail() == null) {
			Log.info("User object invalid.");
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		
		// Insert new user, checking if userId already exists
		if( users.putIfAbsent( user.getUserId(), user) != null ) {
			Log.info("User already exists.");
			throw new WebApplicationException( Status.CONFLICT );
		}
		return user.getUserId();
	}


	@Override
	public User getUser(String userId, String password) {
		Log.info("getUser : user = " + userId + "; pwd = " + password);
		
		// Check if user is valid
		if(userId == null || password == null) {
			Log.info("UserId or password null.");
			throw new WebApplicationException( Status.BAD_REQUEST );
		}
		
		var user = users.get(userId);
		
		// Check if user exists 
		if( user == null ) {
			Log.info("User does not exist.");
			throw new WebApplicationException( Status.NOT_FOUND );
		}
		
		//Check if the password is correct
		if(!user.getPassword().equals(password)) {
			Log.info("Password is incorrect.");
			throw new WebApplicationException( Status.FORBIDDEN );
		}
		
		return user;
	}

	/**
	 * 
	 * @param user user instance
	 * @returns true if the user is null or has invalid parameters
	 */
	private boolean checkIfUserNotViable(User user){
		return (user == null || user.getUserId() == null || user.getPassword() == null
		|| user.getFullName() == null || user.getEmail() == null);
	}

	@Override
	public User updateUser(String userId, String password, User user) {
		Log.info("updateUser : user = " + userId + "; pwd = " + password + " ; user = " + user);
		
		// TODO Complete method
		//throw new WebApplicationException(Status.NOT_IMPLEMENTED);

		if (userId == null || password == null || checkIfUserNotViable(user)){
			Log.info("UserId or password null or updated User is null or has null parameters.");
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		
		//user that was in the server and its goint to be updated
		var userServer = users.get(userId);
		
		// Check if user exists 
		if( userServer == null ) {
			Log.info("User does not exist.");
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		//the password given was not the specified user password
		if (!password.equals(userServer.getPassword())){ 	//Compare strings with equals~
			Log.info("Wrong User password."); 
			throw new WebApplicationException(Status.FORBIDDEN);
		}

		return users.put(userId, user);
	}


	@Override
	public User deleteUser(String userId, String password) {
		Log.info("deleteUser : user = " + userId + "; pwd = " + password);
		// TODO Complete method
		throw new WebApplicationException( Status.NOT_IMPLEMENTED );
	}


	@Override
	public List<User> searchUsers(String pattern) {
		Log.info("searchUsers : pattern = " + pattern);
		// TODO Complete method
		throw new WebApplicationException( Status.NOT_IMPLEMENTED );
	}

}
