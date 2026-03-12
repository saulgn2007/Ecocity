package ecocity;

public abstract class Edificio { 

	    // Atributos 
	    protected String nombre;
	    protected double costo;
	    protected double consumoEnergia;
	    protected int salud; // Rango: 0 a 100

	    // Constructor 
	    public Edificio(String nombre, double costo, double consumoEnergia) {
	        this.nombre = nombre;
	        this.costo = costo;
	        this.consumoEnergia = consumoEnergia;
	        this.salud = 100; // Todo edificio empieza con salud completa
	    }

	    // Método abstracto (cada subclase define su propio efecto mensual)
	    public abstract void aplicarEfectoMensual();

	    // Getters
	    public String getNombre() { return nombre; }
	    public double getCosto() { return costo; }
	    public double getConsumoEnergia() { return consumoEnergia; }
	    public int getSalud() { return salud; }

	    // Setter con validación para salud
	    public void setSalud(int salud) {
	        // La salud nunca puede salir del rango 0-100
	        this.salud = Math.max(0, Math.min(100, salud));
	    }

	    // toString para mostrar info en la consola
	    @Override
	    public String toString() {
	        return String.format(
	            "[%s] | Salud: %d%% | Consumo energía: %.1f kWh | Costo: %.0f€",
	            nombre, salud, consumoEnergia, costo
	        );
	    }
	}

