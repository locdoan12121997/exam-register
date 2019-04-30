package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/lecturer")
public class Lecturer {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLecturerList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetLecturers();";	
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
	@Path("/{lecturerId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulebyLecturerID(@PathParam("lecturerId") int lecturerId) {
		try {
            String query = String.format("CALL GetModulesByLecturerId(%d);", lecturerId);
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
