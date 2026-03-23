package edificios;
import interfaz.GeneradorRecursos;
import motor.GestorRecursos;

public class EdificioComercial extends Edificio implements GeneradorRecursos {

    private int bonusFelicidad;
    private double ingresoBase;

    public EdificioComercial(String nombre, double costo, int bonusFelicidad, double ingresoBase) {
        super(nombre, costo, 3.5);
        this.bonusFelicidad = bonusFelicidad;
        this.ingresoBase    = ingresoBase;
    }

    @Override
    public double producirRecurso() {
        return ingresoBase * (salud / 100.0);
    }

    @Override
    public String getTipoRecurso() {
        return "Dinero (comercio €)";
    }

    @Override
    public void aplicarEfectoMensual(GestorRecursos recursos) {
        double ingresos = producirRecurso();
        int felicidad = (int)(bonusFelicidad * (salud / 100.0));
        recursos.consumirAgua(200);
        System.out.printf("%-20s → +%d felicidad | +$%.0f ingresos | -200 L agua | -%.1f MW energía%n",
            nombre, felicidad, ingresos, consumoEnergia);
        desgastar(2);
    }

    public int    getBonusFelicidad() { 
    	return bonusFelicidad; 
    	}
    public double getIngresoBase() { 
    	return ingresoBase; 
    	}

    @Override
    public String toString() {
        return super.toString() + String.format("%nComercial | Felicidad: +%d pts/mes | Ingresos: $%.0f/mes",bonusFelicidad, producirRecurso());
    }
}