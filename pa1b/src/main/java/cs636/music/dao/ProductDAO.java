
package cs636.music.dao;

import static cs636.music.dao.DBConstants.PRODUCT_TABLE;
import static cs636.music.dao.DBConstants.TRACK_TABLE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cs636.music.domain.Product;
import cs636.music.domain.Track;

public class ProductDAO {
	private Connection connection;

	
	public ProductDAO(DbDAO db) {
		connection = db.getConnection();
	}

	// Find Product by product id

	public Product findProductByPID(long prod_id) throws SQLException {
		Product products = null;
		Statement stmt = connection.createStatement();
		try {
			String sql_String =" select * from " + PRODUCT_TABLE + " p, " + TRACK_TABLE + " t "
					+ " where p.product_id = " + prod_id + " and p.product_id = t.product_id order by t.track_number";
			ResultSet set = stmt.executeQuery(sql_String);
			if (set.next()) { 
				products = new Product(set.getInt("product_id"), set.getString("product_code"),
						set.getString("product_description"), set.getBigDecimal("product_price"), null);
				Set<Track> tracks_data = new HashSet<Track>();
				Track track = new Track();
				track.setId(set.getInt("track_id"));
				track.setProduct(products );
				track.setSampleFilename(set.getString("sample_filename"));
				track.setTitle(set.getString("title"));
				track.setTrackNumber(set.getInt("track_number"));
				tracks_data.add(track);

				while (set.next()) {
					track = new Track();
					track.setId(set.getInt("track_id"));
					track.setProduct(products);
					track.setSampleFilename(set.getString("sample_filename"));
					track.setTitle(set.getString("title"));
					track.setTrackNumber(set.getInt("track_number"));
					tracks_data.add(track);
				}
				products.setTracks(tracks_data);
			}
			set.close();
		} finally {
			stmt.close();
		}

		return products ;
	}

	//Find Product by product Code
	
	public Product findProductByCode(String prod_Code) throws SQLException {
		Product products = null;
		Statement stmt = connection.createStatement();
		try {
			
			String sql_String =	" select * from " + PRODUCT_TABLE + " p, " + TRACK_TABLE + " t " + " where p.product_code = '"
							+ prod_Code + "'" + " and p.product_id = t.product_id  order by t.track_number";
			ResultSet set = stmt.executeQuery(sql_String);
			if (set.next()) { 
				
				products = new Product(set.getInt("product_id"), set.getString("product_code"),
						set.getString("product_description"), set.getBigDecimal("product_price"), null);
				Set<Track> tracks_data = new HashSet<Track>();
				Track track = new Track();
				track.setId(set.getInt("track_id"));
				track.setProduct(products);
				track.setSampleFilename(set.getString("sample_filename"));
				track.setTitle(set.getString("title"));
				track.setTrackNumber(set.getInt("track_number"));
				tracks_data.add(track);
				
				while (set.next()) {
					// the product has more than one tracks
					track = new Track();
					track.setId(set.getInt("track_id"));
					track.setProduct(products);
					track.setSampleFilename(set.getString("sample_filename"));
					track.setTitle(set.getString("title"));
					track.setTrackNumber(set.getInt("track_number"));
					tracks_data.add(track);
				}
				products.setTracks(tracks_data);
			}
			set.close();
		} finally {
			stmt.close();
		}

		return products;
	}

	// Find all available Products  
	
	public Set<Product> findAllProducts() throws SQLException {
		Set<Product> products = null;
		Statement stmt = connection.createStatement();
		try {
			products = new HashSet<Product>();
			String sql_String =" select * from " + PRODUCT_TABLE;
			ResultSet set = stmt.executeQuery(sql_String);
			while (set.next()) { 
				Product product = new Product(set.getInt("product_id"), set.getString("product_code"),
						set.getString("product_description"), set.getBigDecimal("product_price"), null);
				products.add(product);
			}
			set.close();
		} finally {
			stmt.close();
		}

		return products ;
	}

	//Find track by track id
	
	public Track findTrackByTID(int track_Id) throws SQLException {
		Product products = null ;
		Track tracks = null;
		Statement stmt = connection.createStatement();
		try {
			
			String sql_String =" select * from " + PRODUCT_TABLE + " p, " + TRACK_TABLE + " t "
					+ " where t.track_id = " + track_Id + " and p.product_id = t.product_id order by t.track_number";
			ResultSet set = stmt.executeQuery(sql_String);
			
			if (set.next()) { 
				
				products = this.findProductByPID(set.getInt("product_id"));
				if (products != null) {
					tracks = products.findTrackbyID(track_Id);
				}
			}
			set.close();
		} finally {
			stmt.close();
		}

		return tracks;
	}

}
