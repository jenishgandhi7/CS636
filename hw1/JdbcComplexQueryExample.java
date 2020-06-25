// JdbcComplexQueryExample.java, available in $cs630/jdbc
// a simple modification of JdbcCheckup
// for Oracle, run with   
//   java -classpath ojdbc6.jar;. JdbcComplexQueryExample
//                                    (use : instead of ; on UNIX)
// Each student gets an account in the one big database
// from cs.umb.edu hosts:
//   host for Oracle: dbs3.cs.umb.edu
//   port: 1521
//   sid: dbs3
// from off-site, with tunnel in place for Oracle on dbs2 using local port 1521:
//   host for Oracle: localhost
//   port: 1521
//   sid: dbs3

// for MySql, run with   
//   java -classpath mysql-connector-java-5.1.36-bin.jar;. JdbcComplexQueryExample
//                                    (use : instead of ; on UNIX)
//   Each student gets an individual database: username+db, such as joedb for user joe
// from cs.umb.edu hosts:
//   host for MySql: topcat.cs.umb.edu
//   port: 3306
// from off-site, with tunnel in place for mysql on topcat.cs.umb.edu using local port 3333:
//   host for mysql: localhost
//   port: 3333

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

class JdbcComplexQueryExample {
	public static void main(String args[]) {
		String dbSys = null;
		Scanner in = null;
		try {
			in = new Scanner(System.in);
			System.out
					.println("Please enter information to test connection to the database");
			dbSys = readEntry(in, "Using Oracle (o), MySql (m) or HSQLDB (h)? ");

		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(1);
		}
		// Prompt the user for connect information
		String user = null;
		String password = null;
		String connStr = null;
		String yesNo;
		try {
			if (dbSys.equals("o")) {
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				yesNo = readEntry(in,
						"use canned Oracle connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 1521): ");
					String sid = readEntry(in, "sid (site id): ");
					connStr = "jdbc:oracle:thin:@" + host + ":" + port + ":"
							+ sid;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			} else if (dbSys.equals("m")) {// MySQL--
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				yesNo = readEntry(in,
						"use canned MySql connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 3306): ");
					String db = user + "db";
					connStr = "jdbc:mysql://" + host + ":" + port + "/" + db;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			} else if (dbSys.equals("h")) { // HSQLDB (Hypersonic) db
				yesNo = readEntry(in,
						"use canned HSQLDB connection string (y/n): ");
				if (yesNo.equals("y")) {
					String db = readEntry(in, "db or <CR>: ");
					connStr = "jdbc:hsqldb:hsql://localhost/" + db;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
				user = "sa";
				password = "";
			} else {
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				connStr = readEntry(in, "connection string: ");
			}
		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(3);
		}
		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");
		System.out.flush();
		Connection conn = null;
		// Connect to the database
		// Use finally clause to close connection
		try {
			conn = DriverManager.getConnection(connStr, user, password);
			System.out.println("connected.");
			tryComplexQuery(conn);
		} catch (SQLException e) {
			System.out.println("Problem with JDBC Connection\n");
			printSQLException(e);
			System.exit(4);
		} finally {
			// Close the connection, if it was obtained, no matter what happens
			// above or within called methods
			if (conn != null) {
				try {
					conn.close(); // this also closes the Statement and
									// ResultSet, if any
				} catch (SQLException e) {
					System.out
							.println("Problem with closing JDBC Connection\n");
					printSQLException(e);
					System.exit(5);
				}
			}
		}
	}
	/*
	-- Homework 3 problem 1c. Find employees (eid and salary) certified for ALL aircraft of cruising
	-- range under 2000.

	-- find aircraft with cruising range under 2000
	-- select *
	-- from aircraft
	-- where cruisingrange < 2000
	-- order by aid;

	-- now view the eid's of certified employees associated with ANY of these
	-- aircraft

	-- select *
	-- from certified c
	-- where c.aid in (select a.aid
	-- from aircraft a
	-- where cruisingrange < 2000)
	-- order by c.eid, c.aid;

	-- it can be seen that no rows will be selcted because there are not any
	-- employees capaable of flying aid 16
	-- so we modify the question to eliminate aid 16

	SELECT e.eid, e.salary
	FROM employees e
	WHERE NOT EXISTS
	(
	(SELECT a.aid FROM aircraft a
	  WHERE a.cruisingrange < 2000 and a.aid <> 16)
	MINUS
	(SELECT c.aid FROM certified c
	  WHERE c.eid = e.eid)
	);
*/
	// show how cursors can be used with complex queries
	// output: 269734834,289950
	// but could have multiple rows
	static void tryComplexQuery(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rset = null;
		try {
			String q = "SELECT e.eid, e.salary FROM employees1 e" + " WHERE NOT EXISTS ("
					+ "(SELECT a.aid FROM aircraft1 a WHERE a.cruisingrange < 2000 and a.aid <> 16)" + " MINUS "
					+ "(SELECT c.aid FROM certified1 c WHERE c.eid = e.eid)" + ")";
			System.out.println(q);
			rset = stmt.executeQuery(q);
			while (rset.next()) {
				System.out.println(rset.getInt("eid") + "," + rset.getInt("salary"));
			}
			System.out.println("Done");
		} finally { // Note: try without catch: let the caller handle
					// any exceptions of the "normal" db actions.
			stmt.close(); // clean up statement resources, incl. rset
		}
	}

	// print out all exceptions connected to e by nextException or getCause
	static void printSQLException(SQLException e) {
		// SQLExceptions can be delivered in lists (e.getNextException)
		// Each such exception can have a cause (e.getCause, from Throwable)
		while (e != null) {
			System.out.println("SQLException Message:" + e.getMessage());
			Throwable t = e.getCause();
			while (t != null) {
				System.out.println("SQLException Cause:" + t);
				t = t.getCause();
			}
			e = e.getNextException();
		}
	}

	// super-simple prompted input from user
	public static String readEntry(Scanner in, String prompt)
			throws IOException {
		System.out.print(prompt);
		return in.nextLine().trim();
	}
}
