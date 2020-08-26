package studiranje.ip.bean;

public class DatabaseInfoBean {
	private String jdbcURL = "jdbc:mysql://localhost:3306/yi?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
    private String username = "root";
    private String password = "root";
    private String database = "yi"; 
    private String table = "userinfo"; 
    private String driver = "com.mysql.cj.jdbc.Driver";
	public String getJdbcURL() {
		return jdbcURL;
	}
	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
}
