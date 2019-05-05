package com.loc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/students")
public class Student {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetStudents();";	
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
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("studentId") int studentId) {
		try {
            String query = String.format("CALL GetStudentById(%d);", studentId);
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
	@Path("/{studentId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulebyStudentID(@PathParam("studentId") int studentId) {
		try {
            String query = String.format("CALL GetModulesByStudentId(%d);", studentId);
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

	@PUT
	@Path("/{studentId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateStudentCode(@PathParam("studentId") int studentId, @FormParam("studentCode") String studentCode){
		try {
			Main.establishConnection();
			String query = String.format("CALL UpdateStudentCode('%d', '%s');", studentId, studentCode);
			Main.getResultSet(query).close();
			
			return Response.ok().build();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
