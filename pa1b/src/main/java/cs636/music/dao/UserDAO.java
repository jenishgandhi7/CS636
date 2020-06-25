
package cs636.music.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs636.music.dao.DbDAO;
import cs636.music.domain.User;
import static cs636.music.dao.DBConstants.USER_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

public class UserDAO {

	private Connection connection;

	public UserDAO(DbDAO db) {
		connection = db.getConnection();
	}

	// Insert New User
	public void insertUser(User new_usr) throws SQLException {
		Statement stmt = connection.createStatement();
		int userId = FindNextUser_Id();
		new_usr.setId(userId);
		try {
			String sql_String = "insert into " + USER_TABLE + " (user_id, firstname, lastname, email_address) values ("
					+ new_usr.getId() + ", '" + new_usr.getFirstname() + "', '" + new_usr.getLastname() + "', '"
					+ new_usr.getEmailAddress() + "') ";
			stmt.execute(sql_String);
			
		} finally {
			stmt.close();
		}
	}

	// add or update New User Id
	private void addUserID() throws SQLException {
		Statement stmt = connection.createStatement();
		try {
			String sql_String = "update " + SYS_TABLE + " set user_id = user_id + 1";
			stmt.executeUpdate(sql_String);
		} finally {
			stmt.close();
		}
	}

	// Find next user id, the id has been taken, so set +1 for next one
	public int FindNextUser_Id() throws SQLException {
		int next_uid;
		Statement stmt = connection.createStatement();
		try {
			String sql_String = " select user_id from " + SYS_TABLE;

			ResultSet set = stmt.executeQuery(sql_String);

			set.next();
			next_uid = set.getInt("user_id");
		} finally {
			stmt.close();
		}
		addUserID(); 
		return next_uid;
	}

	// find user by id
	public User findUserByID(long user_Id) throws SQLException {
		User usr_info = null;
		Statement stmt = connection.createStatement();
		try {
			String sql_String = " select * from " + USER_TABLE + " where user_id = " + user_Id;

			ResultSet set = stmt.executeQuery(sql_String);
			if (set.next()) {

				usr_info = new User();
				usr_info.setId(set.getInt("user_id"));
				usr_info.setFirstname(set.getString("firstname"));
				usr_info.setLastname(set.getString("lastname"));
				usr_info.setEmailAddress(set.getString("email_address"));
				set.close();
			}
		} finally {
			stmt.close();
		}
		return usr_info;
	}

	// Find User info by Email
	public User findUserByEmail(String email_id) throws SQLException {
		User usr_info = null;
		Statement stmt = connection.createStatement();
		try {
			String sql_String = " select * from " + USER_TABLE + " where email_address = '" + email_id + "'";

			ResultSet set = stmt.executeQuery(sql_String);
			if (set.next()) {
				// for  result is not empty
				usr_info = new User();
				usr_info.setId(set.getInt("user_id"));
				usr_info.setFirstname(set.getString("firstname"));
				usr_info.setLastname(set.getString("lastname"));
				usr_info.setEmailAddress(set.getString("email_address"));
				set.close();
			}
		} finally {
			stmt.close();
		}
		return usr_info;
	}

}
