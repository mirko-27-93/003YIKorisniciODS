package studirnaje.yi.database.old.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;
import studiranje.ip.service.object.DatabaseListRequest;
import studiranje.ip.service.object.DatabaseListResponse;
import studiranje.ip.service.object.TableDescriptionRequest;
import studiranje.ip.service.object.TableDescriptionResponse;
import studiranje.ip.service.object.TableListRequest;
import studiranje.ip.service.object.TableListResponse;
import studiranje.ip.service.object.UserListRequest;
import studiranje.ip.service.object.UserListResponse;

/**
 * Jersey REST-full Service - Информације о старој релационој 
 * бази података на захтјев. 
 * @author mirko
 * @version 1.0
 */

@Path("/database")
public class DatabaseInfoService {
	@POST
	@Path("/root/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserListResponse getUsersViaPost(UserListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		UserListResponse response = new UserListResponse(); 
		try{response.setUsers(controller.getUsers());}
		catch(Exception ex) {
			DBUserData user = new DBUserData();
			user.setUserName(controller.getConnections().getDatabaseUser());
			user.setHostName(controller.getConnections().geDBtHostURL());
			user.setAuthenticationString("");
			response.setUsers(Arrays.asList(user));
		}
		return response; 
	}
	
	@GET
	@Path("/root/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserListResponse getUsersViaGet(UserListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		UserListResponse response = new UserListResponse(); 
		response.setUsers(controller.getUsers()); 
		try{response.setUsers(controller.getUsers());}
		catch(SQLException ex) {
			DBUserData user = new DBUserData();
			user.setUserName(controller.getConnections().getDatabaseUser());
			user.setHostName(controller.getConnections().geDBtHostURL());
			user.setAuthenticationString("");
			response.setUsers(Arrays.asList(user));
		}
		return response;
	}
	
	@POST
	@Path("/root/databases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DatabaseListResponse getDatabasesViaPost(DatabaseListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		DatabaseListResponse response = new DatabaseListResponse(); 
		try{response.setDatabases(controller.getDatabases());}
		catch(Exception ex) {
			response.setDatabases(Arrays.asList(controller.getConnections().getDatabaseUser()));
		}
		return response; 
	}
	
	@GET
	@Path("/root/databases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DatabaseListResponse getDatabasesViaGet(DatabaseListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		DatabaseListResponse response = new DatabaseListResponse(); 
		try{response.setDatabases(controller.getDatabases());}
		catch(Exception ex) {
			response.setDatabases(Arrays.asList(controller.getConnections().getDatabaseUser()));
		}
		return response; 
	}
	
	@POST
	@Path("/root/tables")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TableListResponse getTablesViaPost(TableListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		TableListResponse response = new TableListResponse(); 
		ArrayList<DBTableInfo> tables = new ArrayList<>();
		
		for(String table: controller.getTables(req.getDatabaseName())) {
			DBTableInfo tableInfo = controller.getTableInfo(req.getDatabaseName(), table);  
			tableInfo.setTableName(table);
			tables.add(tableInfo); 
		}
		
		response.setTableInfo(tables);
		return response; 
	}
	
	@GET
	@Path("/root/tables")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TableListResponse getTablesViaGet(TableListRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		TableListResponse response = new TableListResponse(); 
		ArrayList<DBTableInfo> tables = new ArrayList<>();
		
		for(String table: controller.getTables(req.getDatabaseName())) {
			DBTableInfo tableInfo = controller.getTableInfo(req.getDatabaseAPIAddress(), table); 
			tableInfo.setTableName(table);
			tables.add(tableInfo); 
		}
		
		response.setTableInfo(tables);
		return response; 
	}
	
	@POST
	@Path("/root/table")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TableDescriptionResponse getTableViaPost(TableDescriptionRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		TableDescriptionResponse response = new TableDescriptionResponse(); 
		ArrayList<DBRecordInfo> columns = new ArrayList<>();
		
		for(DBRecordInfo column: controller.getTableInfo(req.getDatabaseName(), req.getTableName()).getTableCoulumnsSchema().values()) 
			columns.add(column);
		
		response.setColumns(columns);
		return response; 
	}
	
	@GET
	@Path("/root/table")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TableDescriptionResponse getTableViaGet(TableDescriptionRequest req) throws SQLException {
		RootConnectionPool pool = RootConnectionPool.getConnectionPool(req.getDatabaseAPIAddress());
		DBRootDAO controller = new DBRootDAO(pool);
		TableDescriptionResponse response = new TableDescriptionResponse(); 
		ArrayList<DBRecordInfo> columns = new ArrayList<>();
		
		for(DBRecordInfo column: controller.getTableInfo(req.getDatabaseName(), req.getTableName()).getTableCoulumnsSchema().values()) 
			columns.add(column);
		
		response.setColumns(columns);
		return response; 
	}
}
