package edificios;
import motor.GestorRecursos;

public class ParqueEolico extends EdificioIndustrial {

    private int numeroDeTurbinas;
    private double produccionPorTurbina;

    public ParqueEolico(String nombre, double costo, int numeroDeTurbinas) {
        super(nombre, costo, 0.0, 500.0, costo);
        this.numeroDeTurbinas    = numeroDeTurbinas;
        this.produccionPorTurbina = 5.0;
    }

    @Override
    public double producirRecurso() {
        return capacidadProduccion * factorEficiencia();
    }

    @Override
    public String getTipoRecurso() {
        return "Energía Eólica (MW)";
    }

    @Override
    public void aplicarEfectoMensual(GestorRecursos recursos) {
        double energia = producirRecurso();
        int turbinasActivas  = (int)(numeroDeTurbinas * factorEficiencia());
        recursos.consumirAgua(50);
        System.out.printf("%-20s → +%.1f MW energía | Turbinas activas: %d/%d | -50 L agua | Salud: %d%%%n",
            nombre, energia, turbinasActivas, numeroDeTurbinas, salud);
        desgastar(2);
    }

    public int getNumeroDeTurbinas() { 
    	return numeroDeTurbinas; 
    	}

    @Override
    public String toString() {
        return super.toString() + String.format(
            "%n Eólico | Turbinas: %d | %.0f MW/turbina | Producción actual: %.1f MW",
            numeroDeTurbinas, produccionPorTurbina, producirRecurso()
        );
    }
}