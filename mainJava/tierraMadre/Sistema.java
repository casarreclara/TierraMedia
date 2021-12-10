package tierraMadre;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

import tierraMedia.dao.AtraccionesDAO;
import tierraMedia.dao.ItinerarioDAO;
import tierraMedia.dao.PromocionesDAO;
import tierraMedia.dao.UsuariosDAO;

public class Sistema {
	public static void main(String[] args) throws IOException, SQLException {
		
		// agrega usuario y atracciones a lista usuarioList y atraccionesList
		List<Usuario> turistaList = UsuariosDAO.getUsuarios(); 
		List<Atraccion> atraccionesList = AtraccionesDAO.getAtracciones();
		
		// cargo las promociones a una promocionesList
		List<Promocion> promociones = PromocionesDAO.getPromociones(atraccionesList);
		
		/*
		 * para cada turista, recorro la lista de promociones que estan disponibles para
		 * el usuario, si la puede adquirir se la ofrezco, y si no que siga
		 */
		
		for (Usuario turista : turistaList) {
			
			double costoFinal = 0;
			Itinerario itinerario = new Itinerario();
			Scanner scanner = new Scanner(new InputStreamReader(System.in));
			
			System.out.println("Hola " + turista.getNombre() + ", tenemos las siguientes atracciones para ofrecerle, "
					+ "si desea adquirirlas escriba 'SI', de lo contrario escriba 'NO'. \n");

			// nombro una nueva lista que ser� la que tiene solo las promociones del tipo preferido del usuario
			List<Promocion> promosPreferidas = Promocion.filtraPromosPreferidas(turista.getAtraccion(), promociones);
			
			for (Promocion promocion : promosPreferidas) {
               
				boolean contienePromo = itinerario.yaTienePromo(promocion.getAtraccionesPromo());
				
				if (promocion.puedoOfrecer(turista) && !contienePromo) {
					// ofrezco promo con el mensaje que corresponde a cada tipo de promo

					promocion.ofrecerPromo();

					String valorEntrada = scanner.nextLine();

					if (valorEntrada.equals("si") || valorEntrada.equals("no")) {

						if (valorEntrada.equals("si")) {

							// agregar la promo al itinerario

							itinerario.agregarPromo(promocion);

							// cambiarle tiempo disponible y presupuesto al turista seg�n la promo que
							// acept�

							turista.aceptarPromo(promocion.getMontoPromo(), promocion.getTiempoDeRecorrido());

							// cambiarle el cupo a las atracciones
							promocion.aceptarPromocion();

							// le sumo al contador de plata el monto de la promo
							costoFinal += promocion.getMontoPromo();
						}
						// si no es "si", es "no" entonces quiero que siga

					} else
						System.out.println(valorEntrada + " no es una opci�n valida ");
				}
				PromocionesDAO.updateAtraccionPromo(promocion);

			}
			
			/* Ahora ofrezco las atracciones solas. 
			 * Primero las que son del tipo preferido del usuario, ordenadas por costo
			 * luego las que no son del mismo tipo, ordenadas tambi�n por costo.
			 */

			List<Atraccion> atraccionesPreferidas = Atraccion.listaAtraccionesOrdenadas(turista, atraccionesList);
			
			for (Atraccion a : atraccionesPreferidas) {

				boolean contieneAtraccion = itinerario.yaTieneAtraccion(a);
				
				if (a.puedoOfrecer(turista) && !contieneAtraccion) {

					System.out.println("Atracci�n " + a.getNombre() + " con un costo de $" + a.getCosto());
				
					String valorEntrada = scanner.nextLine();

					if (valorEntrada.equals("si") || valorEntrada.equals("no")) {

						if (valorEntrada.equals("si")) {

							// agregar la promo al itinerario

							itinerario.agregarAtraccion(a);

							// cambiarle tiempo disponible y presupuesto al turista seg�n la promo que //
							// acept�

							turista.aceptarPromo(a.getCosto(), a.getTiempoRecorrido());

							// cambiarle el cupo a las atracciones
							a.elegirAtraccion();
							
							//le descuento a la atracci�n el cupo en la base de datos
							
							AtraccionesDAO.update(a);
							// le sumo al contador de plata el monto de la promo
							costoFinal += a.getCosto();
						}
						// si no es "si", es "no" entonces quiero que siga


					} else
						System.out.println(valorEntrada + " no es una opci�n valida ");
					    
				}

			}
			System.out.println("Usted ya no tiene tiempo o dinero para seguir eligiendo atracciones, a continuaci�n crearemos "
					+ "su itinerario. \n\n" );
			//Escribo itinerarios
			//itinerario.escribirItinerario(costoFinal, turista.getNombre() + " itinerario" + ".txt");
			//Cambiar en base de datos 
			UsuariosDAO.update(turista);
			// Guardar itinerarios en la base de datos
            ItinerarioDAO itinerarioDAO = new ItinerarioDAO();
            itinerarioDAO.insert(turista.getNombre() , itinerario.getAtracciones(),costoFinal, itinerario.getTiempoTotal());
        } 
			
		}

	}
