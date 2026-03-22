package motor;
import java.util.ArrayList;

public class GestorRecursos {

    // Inventario con ArrayList 
    private final ArrayList<String> historialEventos;
    private final ArrayList<String> registroMensual;

    // Recursos principales 
    private double energia;       // MW disponibles
    private double agua;          // Litros disponibles
    private double dinero;        // $ en el presupuesto
    private int    felicidad;     // 0-100 puntos

    // Umbrales críticos 
    private static final double ENERGIA_MINIMA  = 0.0;
    private static final int    FELICIDAD_MIN   = 0;
    private static final int    FELICIDAD_MAX   = 100;

    public GestorRecursos(double dineroInicial) {
        this.dinero           = dineroInicial;
        this.energia          = 100.0;
        this.agua             = 10_000.0;
        this.felicidad        = 60;
        this.historialEventos = new ArrayList<>();
        this.registroMensual  = new ArrayList<>();
    }

    // Modificadores de recursos 

    public void agregarEnergia(double cantidad) {
        energia += cantidad;
    }

    public void consumirEnergia(double cantidad) {
        energia -= cantidad;
        if (energia < ENERGIA_MINIMA) {
            registrarEvento(" Déficit energético: energía en negativo (" + String.format("%.1f", energia) + " MW)");
        }
    }

    public void agregarDinero(double cantidad) {
        dinero += cantidad;
    }

    public boolean gastarDinero(double cantidad) {
        if (dinero >= cantidad) {
            dinero -= cantidad;
            return true;
        }
        return false;
    }

    public void consumirAgua(double cantidad) {
        agua -= cantidad;
        if (agua < 0) agua = 0;
    }

    public void agregarAgua(double cantidad) {
        agua += cantidad;
    }

    public void modificarFelicidad(int delta) {
        felicidad = Math.max(FELICIDAD_MIN, Math.min(FELICIDAD_MAX, felicidad + delta));
    }


    public void registrarEvento(String evento) {
        historialEventos.add(evento);
    }

    public void agregarRegistroMensual(String entrada) {
        registroMensual.add(entrada);
    }

    public ArrayList<String> getHistorialEventos() { return historialEventos; }
    public ArrayList<String> getRegistroMensual()  { return registroMensual; }

    
     // Reinicia el registro mensual para el nuevo ciclo.
    
    public void iniciarNuevoMes() {
        registroMensual.clear();
    }

    // Getters 

    public double getDinero() { 
    	return dinero; 
    }
    
    public double getEnergia() { 
    	return energia; 
    }
    
    public double getAgua() { 
    	return agua; 
    }
    
    public int    getFelicidad() { 
    	return felicidad; 
    }

    public String getResumenRecursos() {
        String iconFelicidad = felicidad >= 70 ? "😊" : (felicidad >= 40 ? "😐" : "😟");
        String iconEnergia   = energia  >= 0   ? "✅"  : "🔴";
        return String.format(
            "  Dinero:    $%,.0f%n" +
            "  %s Energía:   %.1f MW%n" +
            "  Agua:      %.0f L%n" +
            "  %s Felicidad: %d/100",
            dinero, iconEnergia, energia, agua, iconFelicidad, felicidad
        );
    }

    @Override
    public String toString() {
        return getResumenRecursos();
    }
}
