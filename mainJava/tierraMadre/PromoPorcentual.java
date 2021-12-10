package tierraMadre;

import java.util.List;

public class PromoPorcentual extends Promocion {
	
	
    public PromoPorcentual(String nombre, List<Atraccion> atraccionesPromo, double montoPromo, TipoPromo tipo) {
		super(nombre, atraccionesPromo, montoPromo, tipo);
	}

    public double calculaPorcentajeDescuento() {
		//calculamos el porcentaje que le descontamos con la promo para mostrarlo en pantalla
		int sumaCostos = 0;
		for(Atraccion x : atraccionesPromo) {
			sumaCostos += x.getCosto();
			}
		return (sumaCostos - montoPromo) * 100 / sumaCostos ;
	}
    
    @Override
    public void ofrecerPromo() {

    	System.out.println("Pack " + getNombre() + ": " + atraccionesPromo.get(0).getNombre() 
				+ " y " + atraccionesPromo.get(1).getNombre() + " con un " + calculaPorcentajeDescuento()  + "% de descuento.");
	}
    
}
