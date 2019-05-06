package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONArray;

@Path("/register")
public class Register {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionList() throws Exception{
		String query = "CALL GetModuleSessions();";	
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRegister(com.model.Register register) throws Exception{
		String query = String.format("CALL CreateRegister(%d, %d);", register.getStudentId(), register.getExamId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
	
	@DELETE
	@Path("/{registerId}")
	public Response deleteRegister(@PathParam("registerId") int registerId) throws Exception{
		String query = String.format("CALL DeleteRegister(%d);", registerId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}
