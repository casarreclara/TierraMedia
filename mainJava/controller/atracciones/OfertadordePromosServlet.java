package controller.atracciones;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Promocion;
import services.AtraccionService;
import services.PromotionService;

@WebServlet("/promociones/index.do")
public class OfertadordePromosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7741411249743724516L;
	private PromotionService promotionService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.promotionService = new PromotionService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		List<Promocion> promociones = promotionService.list();

		req.setAttribute("promociones", promociones);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/promociones/index.jsp");
		dispatcher.forward(req, resp);

	}
	

}
