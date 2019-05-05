package com.loc;

<<<<<<< HEAD
import javax.ws.rs.GET;
=======
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
>>>>>>> Thang
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

<<<<<<< HEAD
@Path("/lecturers")
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
	@Path("/{lecturerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLecturerById(@PathParam("lecturerId") int lecturerId) {
		try {
			String query = String.format("CALL GetLecturerById(%d);", lecturerId);
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
	@Path("/{lecturerId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulebyLecturerID(@PathParam("lecturerId") int lecturerId) {
		try {
=======

@Path("/lecturers")
public class Lecturer {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetLecturers(){
        try {
        	Main.establishConnection();
            String query = String.format("CALL GetLecturers();");
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
	@Path("/{lecturerId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetModulesByLecturerId(@PathParam("lecturerId") int lecturerId){
        try {
        	Main.establishConnection();
>>>>>>> Thang
            String query = String.format("CALL GetModulesByLecturerId(%d);", lecturerId);
            ResultSet resultSet = Main.getResultSet(query);
            JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
            resultSet.close();
<<<<<<< HEAD
			return Response.ok().entity(jsonObject.toString()).build();
=======
            return Response.ok().entity(jsonObject.toString()).build();
>>>>>>> Thang
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
<<<<<<< HEAD
	}
}

=======
    }

	@GET
	@Path("/{lecturerId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetLecturerById(@PathParam("lecturerId") int lecturerId){
        try {
        	Main.establishConnection();
            String query = String.format("CALL GetLecturerById(%d);", lecturerId);
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
	@Path("/createlecturer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void CreateLecturerAccount(@FormParam("username") String userName, @FormParam("password") String userPassword, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName){
        try {
        	Main.establishConnection();
            String query = String.format("CALL CreateLecturerAccount('%s', '%s', '%s', '%s');", userName, userPassword, firstName, lastName);
            Main.getResultSet(query).close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
	
	@POST
	@Path("/deletelecturer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public static void DeleteLecturerAccount(@FormParam("lecturerId") int lecturerId){
        try {
        	Main.establishConnection();
            String query = String.format("CALL DeleteLecturerAccount(%d);", lecturerId);
            Main.getResultSet(query).close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
	
}
>>>>>>> Thang
