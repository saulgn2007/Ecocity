package edificios;

public class ParqueEolico extends EdificioIndustrial {

	    private int    numeroDeTurbinas;
	    private double produccionPorTurbina;   // MW por turbina

	    public ParqueEolico(String nombre, double costo, int numeroDeTurbinas) {
	        super(nombre, costo, 0.0, 500.0);
	        this.numeroDeTurbinas = numeroDeTurbinas;
	        this.produccionPorTurbina = 5.0;
	    }

	    // GeneradorRecursos 

	    @Override
	    public double producirRecurso() {
	        // La producción eólica depende de la salud (turbinas operativas)
	        return capacidadProduccion * factorEficiencia();
	    }

	    @Override
	    public String getTipoRecurso() {
	        return "Energía Eólica (MW)";
	    }

	    // Efecto mensual 

	    @Override
	    public void aplicarEfectoMensual() {
	        double energia = producirRecurso();
	        int turbinasActivas = (int)(numeroDeTurbinas * factorEficiencia());
	        System.out.printf("💨 %-20s → +%.1f MW energía | Turbinas activas: %d/%d | Salud: %d%%%n", nombre, energia, turbinasActivas, numeroDeTurbinas, salud);
	        desgastar(2);  // Muy poco desgaste mensual
	    }

	    // Getters 

	    public int getNumeroDeTurbinas() { return numeroDeTurbinas; }

	    @Override
	    public String toString() {
	        return super.toString() + String.format(
	            "%n    └─ 💨 Eólico | Turbinas: %d | %.0f MW/turbina | Producción actual: %.1f MW",
	            numeroDeTurbinas, produccionPorTurbina, producirRecurso()
	        );
	    }
	}
