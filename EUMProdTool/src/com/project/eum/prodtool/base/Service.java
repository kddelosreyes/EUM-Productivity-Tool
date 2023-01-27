package com.project.eum.prodtool.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

/**
 * @author khdelos
 *
 */
public abstract class Service {

	private static final String USERNAME = "eumprodtool";
    private static final String PASSWORD = "3uM_pr0duct1v1ty_tO0L";
    private static final String DATABASE = "eum_productivity_tool_test";
    private static final String SERVER = "localhost";
    private static final String PORT = "3306";
	
	private static final String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE;
	
	private static Connection connection = null;
	
	public abstract String getTableName() throws SQLException;
	
	public abstract Entity getResultSetEntity(ResultSet resultSet) throws SQLException;
	
	private static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
            	Class.forName("com.mysql.cj.jdbc.Driver"); 
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (CommunicationsException | ClassNotFoundException exc) {
            	exc.printStackTrace();
            }
        }

        return connection;
    }
	
	private static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	protected ResultSet executeQuery(String selectQuery) throws SQLException {
		if (connection == null) {
			connection = getConnection();
		}

		Statement statement = connection.createStatement();
		return statement.executeQuery(selectQuery);
	}
	
	protected int executeUpdate(String insertUpdateQuery) throws SQLException {
		if (connection == null) {
			connection = getConnection();
		}
		
		Statement statement = connection.createStatement();
		
		if (insertUpdateQuery.contains("INSERT")) {
			statement.executeUpdate(insertUpdateQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} else if (insertUpdateQuery.contains("UPDATE")){
			return statement.executeUpdate(insertUpdateQuery);
		}
		
		return -1;	
	}
	
	public Entity getEntityById(Integer id) throws SQLException {
		Entity entity = null;
		
		Query query = new Query("SELECT * FROM " + getTableName() + " WHERE id = ?1");
		query.params(id);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			entity = getResultSetEntity(resultSet);
		}
		
		return entity;
	}
	
	public List<Entity> getEntitiesByColumn(Column column, Object value) throws SQLException {
		List<Entity> entities = new ArrayList<Entity>();
		
		Query query = new Query("SELECT * FROM " + getTableName() + " WHERE " + column.getColumnName() + " = ?1");
		query.params(value);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		while (resultSet.next()) {
			entities.add(getResultSetEntity(resultSet));
		}
				
		return entities;
	}
	
	public Entity getEntityByColumn(Column column, Object value) throws SQLException {
		List<Entity> entities = getEntitiesByColumn(column, value);
		
		if (entities.size() == 1) {
			return entities.get(0);
		}
		
		return null;
	}
	
	public List<Entity> getAll() throws SQLException {
		List<Entity> entities = new ArrayList<>();
		
		Query query = new Query("SELECT * FROM " + getTableName());
		
		ResultSet resultSet = executeQuery(query.getQuery());
		while (resultSet.next()) {
			entities.add(getResultSetEntity(resultSet));
		}
		
		return entities;
	}
	
	public Integer count() throws SQLException {
		Query query = new Query("SELECT count(*) FROM " + getTableName());
		
		ResultSet resultSet = executeQuery(query.getQuery());
		resultSet.next();
		return resultSet.getInt(1);
	}
	
	public ResultSet getDataFromQuery(String strQuery, Object... params) throws SQLException {
		Query query = new Query(strQuery);
		query.params(params);
		
		ResultSet resultSet = executeQuery(query.getQuery());
		
		return resultSet;
	}
	
	public class Query {
		private String query;
		
		public Query(String query) {
			this.query = query;
		}
		
		public void params(Object... params) {
			int index = 1;
			for (Object param : params) {
				this.query = this.query.replace("?" + index, getParamValue(param));
				index++;
			}
		}
		
		public String getQuery() {
			System.out.println("[QUERY] " + query);
			return query;
		}
		
		private String getParamValue(Object param) {
			if (param instanceof String) {
				return getString(param);
			}
			return getInteger(param);
		}
		
		private String getString(Object param) {
			return "'" + String.valueOf(param) + "'";
		}
		
		private String getInteger(Object param) {
			return String.valueOf(param);
		}
	}
	
}
