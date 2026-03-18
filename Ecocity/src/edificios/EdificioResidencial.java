package edificios;

import interfaces.GeneradorRecursos;

public class EdificioResidencial extends Edificio implements GeneradorRecursos {

	    private int    habitantes;
	    private double tasaImpuesto;    // $ por habitante por mes
	    private double consumoAgua;     // litros por mes

	    public EdificioResidencial(String nombre, double costo, int habitantes) {
	        super(nombre, costo, 5.0 * (habitantes / 100.0));  // 5 MW por cada 100 hab.
	        this.habitantes   = habitantes;
	        this.tasaImpuesto = 12.0;
	        this.consumoAgua  = habitantes * 0.5;
	    }

	    // GeneradorRecursos 

	    @Override
	    public double producirRecurso() {
	        // Los impuestos dependen de la salud del edificio (eficiencia)
	        double factorSalud = salud / 100.0;
	        return habitantes * tasaImpuesto * factorSalud;
	    }

	    @Override
	    public String getTipoRecurso() {
	        return "Dinero (impuestos $)";
	    }

	    // Efecto mensual 

	    @Override
	    public void aplicarEfectoMensual() {
	        double impuestos = producirRecurso();
	        System.out.printf(
	            "  🏠 %-20s → +%d hab. | +$%.0f impuestos | -%.0f L agua | -%.1f MW energía%n",
	            nombre, habitantes, impuestos, consumoAgua, consumoEnergia
	        );
	        desgastar(3);  // Desgaste mensual estándar
	    }

	    // Getters / Setters 

	    public int    getHabitantes()   { return habitantes; }
	    public double getConsumoAgua()  { return consumoAgua; }

	    @Override
	    public String toString() {
	        return super.toString() + String.format(
	            "%n    └─ 🏠 Residencial | Habitantes: %d | Impuestos: $%.0f/mes | Agua: %.0f L/mes",
	            habitantes, producirRecurso(), consumoAgua
	        );
	    }
	}
