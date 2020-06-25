package cs636.music.presentation.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.UserData;

@Controller
public class CatalogController {
	@Autowired
	private UserService userService;

	// String constants for URL's : please use these!
	private static final String WELCOME_URL = "/welcome.html";
	private static final String WELCOME_VIEW = "/welcome";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart";
	private static final String PRODUCT_URL = "/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product";
	private static final String LISTEN_URL = "/listen.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound";
	private static final String DOWNLOAD_URL = "/download.html";
	private static final String REGISTER_FORM_VIEW = "/WEB-INF/jsp/registerForm";

	@RequestMapping(WELCOME_URL)
	public String handleWelcome() {
		return WELCOME_VIEW;
	}

	@RequestMapping("/welcome")
	public String welcome2(Model model) {
		return WELCOME_VIEW;
	}

	@RequestMapping("/")
	public String welcome3(Model model) {
		return WELCOME_VIEW;
	}

	@RequestMapping(CATALOG_URL)
	public String catalogCotroller(Model model, HttpServletRequest request) throws ServletException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		List<Product> catalogs = new ArrayList<Product>();
		Set<Product> catalog = null;
		try {
			catalog = userService.getProductList();
			catalogs = new ArrayList<Product>(catalog);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}
		model.addAttribute("catalogs", catalogs);
		return CATALOG_VIEW;
	}

	@RequestMapping(PRODUCT_URL)
	public String product_detail_Controller(Model model, @RequestParam() String productCode, HttpServletRequest request)
			throws ServletException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");

		Product product = null;
		try {
			product = userService.getProduct(productCode);
			long prod_Id = product.getId();
			userBean.setProductId(prod_Id);
		} catch (ServiceException e) {
			throw new ServletException(e);
		}
		model.addAttribute("product", product);
		return PRODUCT_VIEW;
	}

	// Listing of sound tracks on product detail
	@RequestMapping(LISTEN_URL)
	public String sound_Controller(Model model, HttpServletRequest request) throws ServletException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		long product_Id = userBean.getProductId();
		Product product = null;
		try {
			product = userService.getProduct(product_Id);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}
		UserData user = userBean.getUser();
		model.addAttribute("product", product);
		model.addAttribute("track", product.getTracks());
		return user != null ? SOUND_VIEW : REGISTER_FORM_VIEW;
	}

	// Download Tracks
	@RequestMapping(value = DOWNLOAD_URL, method = RequestMethod.GET)
	@ResponseBody
	public void download_Controller(@RequestParam() int id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, ServiceException, IOException {

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		UserData user = userBean.getUser();
		long productId = userBean.getProductId();
		Product product = null;
		Track track = null;
		try {
			product = userService.getProduct(productId);
			track = product.findTrackbyID(id);
			long user_id = user.getId();
			userService.addDownload(user_id, track);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}

		String downloadFolder = request.getServletContext().getRealPath("/sound/" + product.getCode() + "/");
		Path file = Paths.get(downloadFolder, track.getSampleFilename());
		// Check if file exists
		if (Files.exists(file)) {
			response.setContentType("audio/mpeg");
			response.addHeader("Content-Disposition", "attachment; filename=" + track.getSampleFilename());
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

	}

	// Create Cart & add Product into cart
	@RequestMapping(CART_URL)
	public String showcart(Model model, HttpServletRequest request, @RequestParam(required = false) String code)
			throws ServletException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Cart userCart_info = userBean.getCart();
		long product_id = -1;
		Set<CartItemData> cart_Detail = null;

		try {

			if (userCart_info == null) {
				userCart_info = userService.createCart();
				userBean.setCart(userCart_info);
			}
			if (code != null) {
				product_id = userBean.getProductId();
			}
			if (code != null && product_id > 0) {
				userService.addItemtoCart(product_id, userCart_info, 1);
			}

			cart_Detail = userService.getCartInfo(userCart_info);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}

		model.addAttribute("cart_Detail", cart_Detail);
		return CART_VIEW;

	}

	@RequestMapping("/cart_remove.html")
	public String remove_item_cart(Model model, @RequestParam() long removeId, HttpServletRequest request)
			throws ServletException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");

		Cart userCart_info = userBean.getCart();

		userService.del_item_Cart(removeId, userCart_info);
		return "forward:" + CART_URL;
	}

	@RequestMapping("/cart_update.html")
	public String update_cart(Model model, @RequestParam() long id, int quantity, HttpServletRequest request)
			throws ServletException, ServiceException {
		if (!SalesController.checkUser(request))
			return "forward:" + USER_WELCOME_URL;
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Product product = null;

		product = userService.getProduct(id);
		Cart userCart_info = userBean.getCart();

		userService.changeCart(product, userCart_info, quantity);
		return "forward:" + CART_URL;
	}
}
