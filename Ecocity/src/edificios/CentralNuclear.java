package edificios;
import motor.GestorRecursos;

public class CentralNuclear extends EdificioIndustrial {
    private static final int  UMBRAL_RIESGO = 20;
    private static final double PRODUCCION_BASE_MW = 500.0;
    private boolean modo_emergencia;
    private boolean explotada;

    public CentralNuclear(String nombre, double costo) {
        super(nombre, costo, 0.0, PRODUCCION_BASE_MW, 50_000.0);
        this.modo_emergencia = false;
        this.explotada = false;
    }

    @Override
    public double producirRecurso() {
        return capacidadProduccion * factorEficiencia();
    }

    @Override
    public String getTipoRecurso() {
        return "Energía Nuclear (MW)";
    }

    @Override
    public void aplicarEfectoMensual(GestorRecursos recursos) {
        if (explotada) return;

        if (salud <= UMBRAL_RIESGO && !modo_emergencia) {
            modo_emergencia = true;
            explotada = true;
            System.out.println();
            System.out.println("EXPLOSION NUCLEAR EN " + nombre     );
            System.out.println("La ciudad ha sido destruida.");
            System.out.println("FIN DEL JUEGO");
            return;
        }

        double energia = producirRecurso();
        System.out.printf("%-20s → +%.1f MW energía | Eficiencia: %.0f%% | Salud: %d%%%n",
            nombre, energia, factorEficiencia() * 100, salud);
        desgastar(5);

        if (salud <= UMBRAL_RIESGO + 10) {
            System.out.println("AVISO: " + nombre + " está cerca del umbral crítico. ¡Repárala!");
        }
    }

    public boolean isExplotada() { return explotada; }
    public boolean isEnModoEmergencia() { return modo_emergencia; }

    @Override
    public void reparar() {
        super.reparar();
        if (salud > UMBRAL_RIESGO) {
            modo_emergencia = false;
            capacidadProduccion = PRODUCCION_BASE_MW;
            System.out.println("Central nuclear fuera de zona de riesgo. Modo normal restaurado.");
        }
    }

    @Override
    public String toString() {
        String alerta = (salud <= UMBRAL_RIESGO) ? " ¡RIESGO CRÍTICO!" : "";
        return super.toString() + alerta + String.format(
            "%n Nuclear | Producción: %.0f MW | Umbral peligro: %d%% salud%s",
            capacidadProduccion, UMBRAL_RIESGO, modo_emergencia ? " |EN EMERGENCIA" : ""
        );
    }
}