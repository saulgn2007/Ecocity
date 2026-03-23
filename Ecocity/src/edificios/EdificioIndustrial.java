package edificios;
import interfaz.*;

public abstract class EdificioIndustrial extends Edificio implements GeneradorRecursos {

    protected double capacidadProduccion;   
    protected double costoMantenimiento;    

    public EdificioIndustrial(String nombre, double costo, double consumoEnergia, double capacidadProduccion, double costoMantenimiento) {
        super(nombre, costo, consumoEnergia);
        this.capacidadProduccion = capacidadProduccion;
        this.costoMantenimiento = costoMantenimiento;
    }
    
    //  Métodos concretos compartidos por toda industria

    @Override
    public void reparar() {
        int reparacion = 40;
        salud = Math.min(100, salud + reparacion);
        System.out.printf("[INDUSTRIAL] %s reparado (costo de mantenimiento: $%.0f). Salud: %d%%%n",nombre, costoMantenimiento, salud);
    }

    // a menor salud, menor producción efectiva.
    protected double factorEficiencia() {
        return salud / 100.0;
    }

    // Métodos abstractos que deben implementar las subclases (qué producen y cuánto producen)

    public double getCapacidadProduccion() { 
    	return capacidadProduccion; 
    	}
    public double getCostoMantenimiento()  { 
    	return costoMantenimiento; 
    	}

    @Override
    public String toString() {
        return super.toString() + String.format(
            "%n Industrial | Producción máx: %.1f | Mant.: $%.0f/mes | Eficiencia: %.0f%%",
            capacidadProduccion, costoMantenimiento, factorEficiencia() * 100
        );
    }
}
