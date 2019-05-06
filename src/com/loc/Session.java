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

@Path("/sessions")
public class Session {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionList() throws Exception{
		String query = "CALL GetModuleSessions();";	
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{sessionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionById(@PathParam("sessionId") int sessionId) throws Exception{
		String query = String.format("CALL GetModuleSessionById(%d);", sessionId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@PUT
	@Path("/{sessionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateSession(com.model.Session  session, @PathParam("sessionId") int sessionId) throws Exception{
		String query = String.format("CALL UpdateModuleSession(%d, '%s', '%s', '%s');", sessionId, session.getSessionDate(), session.getFromTime(), session.getToTime());
		Util.executeQuery(query);
		return Response.ok().build();	
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSession(com.model.Session session) throws Exception{
		String query = String.format("CALL CreateModuleSession('%s', '%s', '%s', %d);", session.getSessionDate(), session.getFromTime(), session.getToTime(),  session.getModuleId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
	
	@DELETE
	@Path("/{sessionId}")
	public Response deleteSession(@PathParam("sessionId") int sessionId) throws Exception{
		String query = String.format("CALL DeleteModuleSession(%d);", sessionId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}
