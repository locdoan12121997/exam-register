package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/assistants")
public class Assistant {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetAssistants() throws Exception{
		String query = String.format("CALL GetAssistants();");
        JSONArray jsonArray = Main.getQueryArray(query);
        return Response.ok().entity(jsonArray.toString()).build();
    }
	
	@GET
	@Path("/{assistantId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetAssistantById(@PathParam("assistantId") int assistantId) throws Exception{
		String query = String.format("CALL GetAssistantById(%d);", assistantId);
		JSONArray jsonArray = Main.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response CreateAssistantAccount(com.model.Assistant assistant) throws Exception{
		String query = String.format("CALL CreateAssistantAccount('%s', '%s', '%s', '%s');", assistant.getUserName(), assistant.getPassword(), assistant.getFirstName(), assistant.getLastName());
        Main.executeQuery(query);
        return Response.ok().status(201).build();
    }
	
	@PUT
	@Path("/{assistantId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAssistant(@PathParam("assistantId") int assistantId, com.model.Assistant assistant) throws Exception{
		String query = String.format("CALL UpdateAssistant(%d, '%s', '%s', '%s', '%s');", assistantId, assistant.getUserName(), assistant.getPassword(), assistant.getFirstName(), assistant.getLastName());
		Main.executeQuery(query);
		return Response.ok().status(204).build();
    }
	
	@DELETE
	@Path("/{assistantId}")
    public Response DeleteAssistantAccount(@PathParam("assistantId") int assistantId) throws Exception{
		String query = String.format("CALL DeleteAssistantAccount(%d);", assistantId);
		Main.executeQuery(query);
		return Response.ok().status(204).build();
    }
}