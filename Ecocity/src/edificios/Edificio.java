package edificios;
import interfaz.Mantenible;
import motor.GestorRecursos;

public abstract class Edificio implements Mantenible {

    protected String nombre;
    protected double costo;
    protected double consumoEnergia;
    protected int salud;

    public Edificio(String nombre, double costo, double consumoEnergia) {
        this.nombre = nombre;
        this.costo = costo;
        this.consumoEnergia = consumoEnergia;
        this.salud = 100;
    }

    public abstract void aplicarEfectoMensual(GestorRecursos recursos);

    @Override
    public void reparar() {
        int reparacion = 30;
        salud = Math.min(100, salud + reparacion);
        System.out.printf("%s reparado. Salud restaurada a %d%%%n", nombre, salud);
    }

    @Override
    public void desgastar(int cantidad) {
        salud = Math.max(0, salud - cantidad);
    }

    @Override
    public boolean necesitaMantenimiento() {
        return salud < 30;
    }

    public String getNombre(){ 
    	return nombre; 
    	}
    public double getCosto() { 
    	return costo; 
    	}
    public double getConsumoEnergia() { 
    	return consumoEnergia; 
    	}
    public int getSalud(){ 
    	return salud; 
    	}
    public void setSalud(int salud) {
    	this.salud = Math.max(0, Math.min(100, salud)); 
    	}

	@Override
	public String toString() {
		return "Edificio [nombre=" + nombre + ", costo=" + costo + ", consumoEnergia=" + consumoEnergia + ", salud="+ salud + "]";
	}
}