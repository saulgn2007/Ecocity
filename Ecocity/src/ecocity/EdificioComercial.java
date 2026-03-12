package ecocity;
public class EdificioComercial extends Edificio {
	private int bonusFelicidad;
	private double ingresosMensuales;

	public EdificioComercial(String nombre, int bonusFelicidad, double ingresosMensuales) {
		super(nombre, 15_000+ ingresosMensuales * 10, 5.0);
		this.bonusFelicidad= bonusFelicidad;
		this.ingresosMensuales= ingresosMensuales;
	}

	@Override
	public void aplicarEfectoMensual() {
		if (estaDestruido()) {
			System.out.println("Está cerrado.", nombre);
			return;
		}
		EstadoCiudad estado= EstadoCiudad.getInstance();
		estado.agregarFelicidad(bonusFelicidad);
		estado.agregarDinero(ingresosMensuales);
		desgastar(2);
		System.out.println("Felicidad, Ingresos%n", nombre, bonusFelicidad, ingresosMensuales);
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Felicidad", bonusFelicidad);
	}
}