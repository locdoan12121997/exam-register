package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	@Path("/createassistant")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void CreateAssistantAccount(@FormParam("username") String userName, @FormParam("password") String userPassword, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName){
        try {
        	Main.establishConnection();
            String query = String.format("CALL CreateAssistantAccount('%s', '%s', '%s', '%s');", userName, userPassword, firstName, lastName);
            Main.getResultSet(query).close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
	
	@POST
	@Path("/deleteassistant")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public static void DeleteAssistantAccount(@FormParam("assistantId") int assistantId){
        try {
        	Main.establishConnection();
            String query = String.format("CALL DeleteAssistantAccount(%d);", assistantId);
            Main.getResultSet(query).close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}