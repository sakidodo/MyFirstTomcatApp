package net.mybluemix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//STEP 1. Import required packages
import java.sql.*;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 // JDBC driver name and database URL (Database credentials)
  	static final String JDBC_URL = "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_c044be4d4a601c9?user=beb0c072c5678f&password=879adaa7";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	StringBuilder msgPage = new StringBuilder();
 		msgPage.append("<br>STEP 1. Import required packages");
 		
 		Connection conn = null;
 		Statement stmt = null;
 		String sql = "";
 		String table = "User" + System.currentTimeMillis();
 		try {
 			msgPage.append("<br>STEP 2: Register JDBC driver");
 			Class.forName("com.mysql.jdbc.Driver");

 			msgPage.append("<br>STEP 3: Open a connection");
 			conn = DriverManager.getConnection(JDBC_URL);
 			stmt = conn.createStatement();

 			msgPage.append("<br>STEP 4: Create a new table USER");
 			sql = "CREATE TABLE " + table + " (name VARCHAR(20), age INTEGER)";
 			stmt.execute(sql);
 			
 			msgPage.append("<br>STEP 5: Filling the fields records");
 			sql = "INSERT INTO " + table + " VALUES (\'Biao Wu\', 30)";
 			stmt.executeUpdate(sql);
 			
 			msgPage.append("<br>STEP 6: Executing a query");
 			sql = "SELECT name, age FROM " + table;
 			ResultSet rs = stmt.executeQuery(sql);

 			msgPage.append("<br>STEP 7: Extract data from result set");
 			while (rs.next()) {
 				// Retrieve by column name
 				msgPage.append("<br> Name: "+rs.getString("name"));
 				msgPage.append("<br> Age: "+rs.getInt("age"));
 			}
 			rs.close();
 			
 			msgPage.append("<br>STEP 8: Deleting records");
 			sql = "DELETE FROM " + table + " WHERE name=\'Raphael Hespanhol\'";
 			stmt.executeUpdate(sql);

 			msgPage.append("<br>STEP 9: Removing table from database");
 			sql = "DROP TABLE " + table;
 			stmt.execute(sql);
 			
 			msgPage.append("<br>STEP 10: Clean-up environment");
 			stmt.close();
 			conn.close();
 		} catch (SQLException se) {
 			// Handle errors for JDBC
 			se.printStackTrace();
 		} catch (Exception e) {
 			// Handle errors for Class.forName
 			e.printStackTrace();
 		} finally {
 			// finally block used to close resources
 			try {
 				if (stmt != null)
 					stmt.close();
 			} catch (SQLException se2) {
 			} // nothing we can do
 			try {
 				if (conn != null)
 					conn.close();
 			} catch (SQLException se) {
 				se.printStackTrace();
 			} // end finally try
 		} // end try
 		
 		msgPage.append("<br>STEP 11: Done...!");
 		response.setContentType("text/html");
        response.getWriter().print(msgPage);
    }

}
