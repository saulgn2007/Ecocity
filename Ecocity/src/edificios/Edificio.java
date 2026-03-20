package edificios;
import interfaces.Mantenible;

public abstract class Edificio implements Mantenible {

    // Atributos 
    protected String nombre;
    protected double costo;
    protected double consumoEnergia;   // MW consumidos por mes
    protected int    salud;            // 0 - 100

    // Constructor 
    public Edificio(String nombre, double costo, double consumoEnergia) {
        this.nombre         = nombre;
        this.costo          = costo;
        this.consumoEnergia = consumoEnergia;
        this.salud          = 100;    // Todo edificio nace con salud perfecta
    }

    // Método abstracto

    public abstract void aplicarEfectoMensual();


    @Override
    public void reparar() {
        int reparacion = 30;
        salud = Math.min(100, salud + reparacion);
        System.out.printf("  🔧 %s reparado. Salud restaurada a %d%%%n", nombre, salud);
    }

    @Override
    public void desgastar(int cantidad) {
        salud = Math.max(0, salud - cantidad);
    }

    @Override
    public boolean necesitaMantenimiento() {
        return salud < 30;
    }

    // Getters y Setters

    public String getNombre() { 
    	return nombre; 
    }
  
    public double getCosto() { 
    	return costo; 
    }
   
    public double getConsumoEnergia()  { 
    	return consumoEnergia; 
    }
    
    public int    getSalud() {
    	return salud; 
    }

    public void setSalud(int salud) {
        this.salud = Math.max(0, Math.min(100, salud));
    }


    @Override
    public String toString() {
        String estadoSalud = salud >= 70 ? "✅" : (salud >= 30 ? "⚠️ " : "🔴");
        return String.format(
            "%-22s | Costo: $%8.0f | Energía consumida: %5.1f MW | Salud: %s %3d%%",
            nombre, costo, consumoEnergia, estadoSalud, salud
        );
    }
}
