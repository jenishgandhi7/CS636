package cs636.music.presentation;

import java.sql.*;

public class Register {
	private static Connection conn;

	public Register(String dbUrl, String usr, String passwd) throws Exception {
		
		System.out.println("running SystemTest, calling config with: " + dbUrl + " " + usr + " " + passwd);
		if (dbUrl == null) {
			System.out.println(" Replacing null dbUrl with " + "jdbc:h2:~/test-music");
			dbUrl = "jdbc:h2:~/test-music"; // default to H2, an embedded DB
			usr = "test";
			passwd = "";
		} else {
			System.out.println("Register called with " + dbUrl);
		}

		try {
			String dbDriverName;
			if (dbUrl.contains("mysql")) {
				dbDriverName = "com.mysql.cj.jdbc.Driver";

			} else if (dbUrl.contains("oracle"))
				dbDriverName = "oracle.jdbc.OracleDriver";
			else if (dbUrl.contains("h2"))
				dbDriverName = "org.h2.Driver";
			else
				throw new SQLException("Unknown DB URL " + dbUrl);

			Class.forName(dbDriverName);
		} catch (Exception e) {
			System.out.println(e);
		}

		conn = DriverManager.getConnection(dbUrl, usr, passwd);

	}


	// try use a "Site User" table for Register New User, or throw if this fails
	void reg(Connection conn) throws SQLException {
		// Create a statement
		Statement stmt = conn.createStatement();
		ResultSet rset = null;
		try {
			// We treat this drop table specially to allow it to fail
			// as it will the very first time we run this program

			try {
				
				stmt.execute("delete from site_user where site_user.user_id= 10");
			} catch (SQLException e) {
				// assume not there yet, so OK to continue
			}
			// The following database actions are handled normally,
			// i.e., if they fail they will throw a SQLException
			// and terminate the execution of this method
			// with execution of the finally clause
			String email = "jenish.gandhi7@gmail.com";
			long id = 10;
			String firstname = "Jenish";
			String lastname = "Gandhi";
			/*String company_name = "UMB";
			String add1 = "xyz";
			String add2 = "abc";
			String city = "Boston";
			String state = "MA";
			String zip = "02125";
			String country = "USA";
			String CC_type = null;
			String CC_Number = null;
			String CC_Exp_date = null;*/
			
			//			stmt.execute(
//					"Insert into site_user(USER_ID,FIRSTNAME,LASTNAME,EMAIL_ADDRESS,COMPANY_NAME,ADDRESS1,ADDRESS2,CITY,STATE,ZIP,COUNTRY,CREDITCARD_TYPE,CREDITCARD_NUMBER,CREDITCARD_EXPIRATIONDATE) "
//					+ "values('" + id + "','" + firstname + "','" + lastname + "','" + email + "','" + company_name + "','" + add1 + "','" + add2 + "','" + city + "','" + state + "','" + zip + "','" + country + "','" + CC_type + "','" + CC_Number +  "','" + CC_Exp_date+"')");

			
			stmt.execute(
					"Insert into site_user(USER_ID,FIRSTNAME,LASTNAME,EMAIL_ADDRESS) "
					+ "values('" + id + "','" + firstname + "','" + lastname + "','" + email + "')");

			rset = stmt.executeQuery("select * from site_user where site_user.user_id= 10");

			while (rset.next()) {
				System.out.println("*******************************************************");
				System.out.println("New User is Successfully registered. As details as below");
				System.out.println("*******************************************************");
				System.out.println("User id: " + rset.getString(1));
				System.out.println("First Name: " + rset.getString(2));
				System.out.println("Last Name: " + rset.getString(3));
				System.out.println("Email id: " + rset.getString(4));
				System.out.println("*******************************************************");
			}
			System.out.println("\n");
			
		} finally { // Note: try without catch: let the caller handle
					// any exceptions of the "normal" db actions.
			stmt.close(); // clean up statement resources, incl. rset
		}
	}

	
	public static void main(String[] args) {
		// String inFile = null;
		String dbUrl = null;
		String usr = null;
		String pw = null;
		
		if (args.length == 0) { // no args: run on H2 with test.dat
			dbUrl = null;
			usr = null;
			pw = null;
		} else if (args.length == 3) {
			dbUrl = args[0];
			usr = args[1];
			pw = args[2];
		} else {
			System.out.println("usage:java [<inputFile>] <dbURL> <user> <passwd> ");
			return;
		}
		try {
			Register test = new Register(dbUrl, usr, pw);
			test.reg(conn);
			System.out.println("Run complete, exiting");
		} catch (Exception e) {
			System.out.println("Error in run of SystemTest: ");
			
		}
	}


}