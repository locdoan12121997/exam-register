package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/assistants")
public class Assistant {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetAssistants(){
        try {
        	Main.establishConnection();
            String query = String.format("CALL GetAssistants();");
            ResultSet resultSet = Main.getResultSet(query);
	        JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
	        resultSet.close();
			return Response.ok().entity(jsonObject.toString()).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	@GET
	@Path("/{assistantId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetAssistantById(@PathParam("assistantId") int assistantId){
        try {
        	Main.establishConnection();
            String query = String.format("CALL GetAssistantById(%d);", assistantId);
            ResultSet resultSet = Main.getResultSet(query);
            JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
            resultSet.close();
            return Response.ok().entity(jsonObject.toString()).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response CreateAssistantAccount(@FormParam("username") String userName, @FormParam("password") String userPassword, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName){
        try {
        	Main.establishConnection();
            String query = String.format("CALL CreateAssistantAccount('%s', '%s', '%s', '%s');", userName, userPassword, firstName, lastName);
            Main.getResultSet(query).close();
            return Response.ok().status(201).build();
    }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @UPDATE
    @Path("/{assistantId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response UpdateAssistantAccount(@PathParam("assistantId") int assistantId, @FormParam("username") String userName, @FormParam("password") String userPassword, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName){
        try {
            Main.establishConnection();
            String query = String.format("CALL CreateAssistantAccount('%s', '%s', '%s', '%s');", userName, userPassword, firstName, lastName);
            Main.getResultSet(query).close();
            return Response.ok().build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	@DELETE
	@Path("/{assistantId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response DeleteAssistantAccount(@PathParam("assistantId") int assistantId){
        try {
        	Main.establishConnection();
            String query = String.format("CALL DeleteAssistantAccount(%d);", assistantId);
            Main.getResultSet(query).close();
            return Response.ok().status(204).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}