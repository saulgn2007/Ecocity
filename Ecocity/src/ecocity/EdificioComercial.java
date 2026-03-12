package ecocity;

public class EdificioComercial extends Edificio {
	private int bonusFelicidad;
	private double ingresosMensuales;

	public EdificioComercial(String nombre, int bonusFelicidad, double ingresosMensuales) {
		super(nombre, 15000+ ingresosMensuales * 10, 5.0);
		this.bonusFelicidad= bonusFelicidad;
		this.ingresosMensuales= ingresosMensuales;
	}

	@Override
	public void aplicarEfectoMensual() {
		if (this.getSalud()<= 0) {
			System.out.println(nombre + " Cerrado por mal estado.");
			return;
		}

		EstadoCiudad estado =EstadoCiudad.getInstance();
		estado.agregarFelicidad(bonusFelicidad);
		estado.agregarDinero(ingresosMensuales);

		// Bajamos salud usando el método de la clase padre
		setSalud(getSalud()- 2);
		System.out.printf("Generó de felicidad y de ingresos.", nombre, bonusFelicidad, ingresosMensuales);
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Bonus Felicidad:", bonusFelicidad);
	}
}