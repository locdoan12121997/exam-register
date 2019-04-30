package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/module")
public class Module {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuleList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetAllModulesDetails();";	
	        ResultSet resultSet = Main.getResultSet(query);
	        System.out.println(resultSet);
	        JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
	        resultSet.close();
			return Response.ok().entity(jsonObject.toString()).build();
		}catch (Exception e) {
            e.printStackTrace();
            return null;
		}
	}
	
	@GET
	@Path("/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModules(@PathParam("moduleId") int moduleId) {
		try {
            String query = String.format("CALL GetModuleById(%d);", moduleId);
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
	
	
}
