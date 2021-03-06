package services;

import java.util.HashMap;
import java.util.Map;

import model.Promocion;
import model.Recommender;
import model.Usuario;
import persistence.impl.PromocionesDAOImpl;
import persistence.impl.UsuariosDAOImpl;

public class ComprarPromocionService {
	
	PromocionesDAOImpl promocionesDAO = new PromocionesDAOImpl();
	UsuariosDAOImpl usuariosDAO = new UsuariosDAOImpl();

	public Map<String, String> comprar(Integer usuarioId, Integer promoId) {
		//Preparo un map de errores por si falla.
		Map<String, String> errors = new HashMap<String, String>();

		Usuario turista = usuariosDAO.find(usuarioId);
		Promocion promocion = promocionesDAO.find(promoId);
		
		//Si la atraccion ya no tiene cupo
		if (!promocion.tienenCupo()) {
			errors.put("promocion", "Lo sentimos! No hay cupos disponible");
		}
		//Si el turista no puede comprar
		if (!promocion.puedePagar(turista)) {
			errors.put("user", "No tienes dinero suficiente");
		}
		// Y/o si el turista no tiene mas tiempo, lanza alguno de los mensajes
		if (!promocion.puedeRecorrer(turista)) {
			errors.put("user", "No tienes tiempo suficiente");
		}

		/*Si no, es decir si el mapa esta vacio por ende no hubo errores, 
		 * entonces DESCONTAMOS del credito/tiempo del usuario
		 *  y restamos el cupo a la atraccion.
		*/
		if (errors.isEmpty()) {
			Recommender.agregarYPagarPromo(turista, promocion);
		}
		//Siempre retorno la lista de errores, segun haya o no, si la lista esta vacia (VER Servlet)
		return errors;

	}
	
	

}
