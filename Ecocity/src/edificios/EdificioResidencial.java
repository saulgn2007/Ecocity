package edificios;
import interfaz.GeneradorRecursos;
import motor.GestorRecursos;

public class EdificioResidencial extends Edificio implements GeneradorRecursos {

    private int habitantes;
    private double tasaImpuesto;
    private double consumoAgua;

    public EdificioResidencial(String nombre, double costo, int habitantes) {
        super(nombre, costo, 5.0 * (habitantes / 100.0));
        this.habitantes   = habitantes;
        this.tasaImpuesto = 12.0;
        this.consumoAgua  = habitantes * 0.5;
    }

    @Override
    public double producirRecurso() {
        double factorSalud = salud / 100.0;
        return habitantes * tasaImpuesto * factorSalud;
    }

    @Override
    public String getTipoRecurso() {
        return "Dinero (impuestos $)";
    }

    @Override
    public void aplicarEfectoMensual(GestorRecursos recursos) {
        double impuestos = producirRecurso();
        recursos.consumirAgua(consumoAgua);
        System.out.printf("%-20s → +%d hab. | +$%.0f impuestos | -%.0f L agua | -%.1f MW energía%n",
            nombre, habitantes, impuestos, consumoAgua, consumoEnergia);
        desgastar(3);
    }

    public int    getHabitantes()  { 
    	return habitantes; 
    	}
    public double getConsumoAgua() { 
    	return consumoAgua; 
    	}

    @Override
    public String toString() {
        return super.toString() + String.format(
            "%nResidencial | Habitantes: %d | Impuestos: $%.0f/mes | Agua: %.0f L/mes",
            habitantes, producirRecurso(), consumoAgua
        );
    }
}