package edificios;
import excepciones.RiesgoNuclearException;

public class CentralNuclear extends EdificioIndustrial {

    private static final int    UMBRAL_RIESGO      = 20;   // % salud crítica
    private static final double PRODUCCION_BASE_MW = 500.0; // MW máximos

    private boolean en_modo_emergencia;

    public CentralNuclear(String nombre, double costo) {
        super(
            nombre,
            costo,
            0.0,              // Las centrales no consumen
            PRODUCCION_BASE_MW,
            50_000.0          // Mantenimiento muy costoso
        );
        this.en_modo_emergencia = false;
    }

    // GeneradorRecursos 

    @Override
    public double producirRecurso() {
        verificarEstadoNuclear();   // Siempre verifica antes de producir
        return capacidadProduccion * factorEficiencia();
    }

    @Override
    public String getTipoRecurso() {
        return "Energía Nuclear (MW)";
    }

    // Efecto mensual 

    @Override
    public void aplicarEfectoMensual() {
        verificarEstadoNuclear();
        double energia = producirRecurso();
        System.out.printf(
            "    %-20s → +%.1f MW energía | Eficiencia: %.0f%% | Salud: %d%%%n",
            nombre, energia, factorEficiencia() * 100, salud
        );
        desgastar(5);  // Las centrales nucleares se desgastan más rápido
        verificarEstadoNuclear();  // Verifica tras el desgaste mensual
    }

    // Riesgo nuclear 

    public void verificarEstadoNuclear() {
        if (salud <= UMBRAL_RIESGO && !en_modo_emergencia) {
            en_modo_emergencia = true;
            throw new RiesgoNuclearException(nombre, salud);
        }
    }

    public void activarProtocoloEmergencia() {
        System.out.printf(
            "   [EMERGENCIA] Activando protocolo de contención en %s...%n", nombre
        );
        this.capacidadProduccion = PRODUCCION_BASE_MW * 0.5;
        this.en_modo_emergencia  = true;
        System.out.println("   Protocolo activado. Producción reducida al 50%.");
    }

    @Override
    public void reparar() {
        super.reparar();
        if (salud > UMBRAL_RIESGO) {
            en_modo_emergencia   = false;
            capacidadProduccion  = PRODUCCION_BASE_MW;
            System.out.println("   Central nuclear fuera de zona de riesgo. Modo normal restaurado.");
        }
    }

    public boolean isEnModoEmergencia() { return en_modo_emergencia; }

    @Override
    public String toString() {
        String alerta = (salud <= UMBRAL_RIESGO) ? "  ¡RIESGO CRÍTICO!" : "";
        return super.toString() + alerta + String.format(
            "%n    └─   Nuclear | Producción: %.0f MW | Umbral peligro: %d%% salud%s",
            capacidadProduccion, UMBRAL_RIESGO, en_modo_emergencia ? " |  EN EMERGENCIA" : ""
        );
    }
}
