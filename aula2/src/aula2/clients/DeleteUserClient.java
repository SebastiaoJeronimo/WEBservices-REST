package aula2.clients;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import aula2.api.User;
import aula2.api.service.RestUsers;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class DeleteUserClient {

	public static void main(String[] args) throws IOException {
		
		if( args.length != 3) {
			System.err.println( "Use: java aula2.clients.DeleteUserClient url userId password");
			return;
		}
		
		String serverUrl = args[0];
		String userId = args[1];
		String password = args[2];
		
		System.out.println("Sending request to server.");
		
		//TODO complete this client code

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target( serverUrl ).path( RestUsers.PATH );

		Response r = target.path( userId ).queryParam(RestUsers.PASSWORD, password) //argumentos do path 
					.request() //inicia http request
					.accept(MediaType.APPLICATION_JSON) //especifica o que recebe
					.delete(); // especifica a request neste caso foi o delete ver na interface

		//esta parte nao esta concluida foi copiada do get user tenho que adaptar
		if( r.getStatus() == Status.OK.getStatusCode() && r.hasEntity()) {
			System.out.println("Success:");
			var u = r.readEntity(User.class);
			System.out.println( "User : " + u);
		} else
			System.out.println("Error, HTTP error status: " + r.getStatus());
	}
	
}
