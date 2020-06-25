package cs636.music.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.ServiceException;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

public class UserService {

	private DownloadDAO ddao;
	private InvoiceDAO idao;
	private ProductDAO pdao;
	private UserDAO userDb;

	/**
	 * construct a user service provider
	 * 
	 * @param productDao
	 * @param userDao
	 * @param downloadDao
	 * @param lineItemDao
	 * @param invoiceDao
	 */
	public UserService(ProductDAO productDao, UserDAO userDao, DownloadDAO downloadDao, InvoiceDAO invoiceDao) {
		ddao = downloadDao;
		idao = invoiceDao;
		pdao = productDao;
		userDb = userDao;
		
	}

	/* Get list of all products */
	public Set<Product> getListofProduct() throws ServiceException {
		Set<Product> prodcut_list = null;
		try {
			prodcut_list = pdao.findAllProducts();
		} catch (SQLException e) {
			throw new ServiceException("unable to find product list in db: ", e);
		}
		return prodcut_list;
	}

	/* Find user info by given email address */

	public UserData getUserDetails(String email_id) throws ServiceException {

		UserData user_data = null;
		try {

			user_data = new UserData(userDb.findUserByEmail(email_id));

		} catch (SQLException e) {
			throw new ServiceException("Error while getting user info by email: " + email_id, e);
		}
		return user_data;
	}

	/* Get product info by given product code */

	public Product getProductByPro_Code(String prod_Code) throws ServiceException {
		
		Product prod_Info = null;
		try {
			prod_Info = pdao.findProductByCode(prod_Code);
		} catch (SQLException e) {
			throw new ServiceException("Error while getting product info by product code: ", e);
		}
		return prod_Info;
	}

	/* Register new user */
	public void NewUser_register(String fname, String lname, String email_id) throws ServiceException {
		User user = null;

		try {
			user = userDb.findUserByEmail(email_id);
			if (user == null) {
				user = new User();
				user.setFirstname(fname);
				user.setLastname(lname);
				user.setEmailAddress(email_id);
				userDb.insertUser(user);
			}
		else {
				   System.out.println("Alredy User available Found");
			    	 
			    	
			     }
			
			
		} catch (SQLException e) {
			throw new ServiceException("Error while register new user in database: ", e);
		}
	}

	/*** Add download history, record the user****/
	public void Download_Record(long user_Id, Track track) throws ServiceException {
		Download download =null;
		try {
			download = new Download();
			User user = userDb.findUserByID(user_Id);
			download.setUser(user);
			download.setTrack(track);
			ddao.insertDownload(download); 
		} catch (SQLException e) {
			throw new ServiceException("Error while add download record: ", e);
		}
	}

	/****Create a new cart******/
	
	public Cart createCart() {
		return new Cart();
	}

	/*** Get cart info ***/
	public Set<CartItemData> getCartDetail(Cart cart) throws ServiceException {
		
		Set<CartItemData> items =null;

		try {
			items = new HashSet<CartItemData>();
			for (CartItem item : cart.getItems()) {

				long Prod_Id = item.getProductId();
				int Item_Qty = item.getQuantity();

				Product product = pdao.findProductByPID(Prod_Id);

				String prod_code = product.getCode();
				String prod_Desc = product.getCode();
				BigDecimal prod_price = product.getPrice();

				CartItemData itemData = new CartItemData(Prod_Id, Item_Qty, prod_code, prod_Desc, prod_price);

				items.add(itemData);
			}
		} catch (Exception e) {
			throw new ServiceException("Error while getting cart info: ", e);
		}
		return items;

	}

	/*
	 * Check out the cart
	 */
	public InvoiceData checkout(Cart cart, long user_Id) throws ServiceException {
		InvoiceData invoice_list=null;
		Invoice invoice = null;
		try {
			User user = userDb.findUserByID(user_Id);

			invoice = new Invoice(-1, user, new Date(), false, null, null);

			Set<LineItem> lineItems = new HashSet<LineItem>();
			for (CartItem item : cart.getItems()) {

				long Prod_Id = item.getProductId();
				int Item_Qty = item.getQuantity();

				Product product_id = pdao.findProductByPID(Prod_Id);
				LineItem lineItem = new LineItem(product_id, invoice, Item_Qty);
				lineItems.add(lineItem);
			}

			invoice.setLineItems(lineItems);
			invoice.setTotalAmount(invoice.calculateTotal());
			idao.insertInvoice(invoice);

		} catch (SQLException e) {
			throw new ServiceException("Error while check out: ", e);
		}
		cart.clear();
		invoice_list=new InvoiceData(invoice);
		return invoice_list;
	}

	/*
	 * Add a product to the cart.
	 */
	public void addItemIntoCart(Product product, Cart cart, int quantity) {

		long prod_Id = product.getId();
		CartItem item = cart.findItem(prod_Id);

		// if item is already in the cart and qty not available
		if (item != null) {
			
			int count = item.getQuantity();
			item.setQuantity(count + quantity);
		} 
		// if item not in the cart, so add new items & qty
		else {
			item = new CartItem(prod_Id, quantity);
			cart.getItems().add(item);
		}
	}

	/*
	 * Update Cart with Qty
	 */
	public void updateCart(Product product, Cart cart, int quantity) {
		long prod_Id = product.getId();
		
		CartItem item = cart.findItem(prod_Id);
		if (item != null) {
			if (quantity > 0) {
				item.setQuantity(quantity);

			} else { 
				cart.removeItem(prod_Id);
			}
		}
	}

	/*
	 * Remove a product item from the cart
	 */
	public void DeleteCartItem(Product product, Cart cart) {
		long prod_Id = product.getId();

		CartItem item = cart.findItem(prod_Id);
		if (item != null) {
			cart.removeItem(prod_Id);
		}
	}

}
