package motor;

import edificios.Edificio;
import edificios.EdificioIndustrial;
import interfaces.GeneradorRecursos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Motor de eventos aleatorios climáticos y urbanos que afectan la ciudad.
 * En cada mes puede ocurrir (o no) un evento que impacta los edificios y recursos.
 */
public class EventoAleatorio {

    private static final Random RANDOM = new Random();

    // Tipos de eventos disponibles
    public enum TipoEvento {
        TORMENTA_ELECTRICA  ("⛈️  Tormenta eléctrica",    "Los generadores de energía pierden 10% de salud."),
        OLA_DE_CALOR        ("🌡️  Ola de calor",           "Todos los edificios consumen 20% más energía."),
        TERREMOTO           ("🌍 Terremoto",               "Todos los edificios pierden 15% de salud."),
        EPIDEMIA            ("🦠 Epidemia",                "La felicidad baja -10 puntos."),
        BONANZA_ECONOMICA   ("📈 Bonanza económica",       "La ciudad recibe un subsidio de $50,000."),
        LLUVIA_ABUNDANTE    ("🌧️  Lluvia abundante",        "Se recargan +5,000 L de agua."),
        SIN_EVENTO          ("🌤️  Mes tranquilo",           "No ocurre ningún evento especial.");

        public final String nombre;
        public final String descripcion;

        TipoEvento(String nombre, String descripcion) {
            this.nombre      = nombre;
            this.descripcion = descripcion;
        }
    }

    // Seleccionar evento aleatorio 30% de probabilidad de mes tranquilo.

    public static TipoEvento seleccionarEvento() {
        int dado = RANDOM.nextInt(100);
        if (dado < 30) return TipoEvento.SIN_EVENTO;

        TipoEvento[] eventos = {
            TipoEvento.TORMENTA_ELECTRICA,
            TipoEvento.OLA_DE_CALOR,
            TipoEvento.TERREMOTO,
            TipoEvento.EPIDEMIA,
            TipoEvento.BONANZA_ECONOMICA,
            TipoEvento.LLUVIA_ABUNDANTE
        };
        return eventos[RANDOM.nextInt(eventos.length)];
    }

    //Aplicar evento a la ciudad
    public static void aplicar(TipoEvento evento, List<Edificio> edificios, GestorRecursos recursos) {
        System.out.println();
        System.out.println("EVENTO DEL MES: " + evento.nombre + " ━━━");
        System.out.println("  " + evento.descripcion);

        switch (evento) {

            case TORMENTA_ELECTRICA:
                // Solo afecta a edificios que implementen GeneradorRecursos 
                for (Edificio e : edificios) {
                    if (e instanceof GeneradorRecursos) {
                        e.desgastar(10);
                        System.out.printf("    ⚡ %s pierde 10%% de salud → %d%%%n", e.getNombre(), e.getSalud());
                    }
                }
                recursos.registrarEvento("Tormenta eléctrica: generadores dañados.");
                break;

            case OLA_DE_CALOR:
                // Todos los edificios consumen más energía este mes
                double energiaExtra = edificios.size() * 2.0;
                recursos.consumirEnergia(energiaExtra);
                System.out.printf("    🌡️  La ciudad consume %.1f MW adicionales.%n", energiaExtra);
                recursos.registrarEvento("Ola de calor: sobreconsumo de energía.");
                break;

            case TERREMOTO:
                for (Edificio e : edificios) {
                    e.desgastar(15);
                    System.out.printf("    🌍 %s dañado → Salud: %d%%%n", e.getNombre(), e.getSalud());
                }
                recursos.modificarFelicidad(-15);
                recursos.registrarEvento("Terremoto: daños en toda la ciudad.");
                break;

            case EPIDEMIA:
                recursos.modificarFelicidad(-10);
                System.out.println("    🦠 La moral de los ciudadanos cae.");
                recursos.registrarEvento("Epidemia: felicidad reducida.");
                break;

            case BONANZA_ECONOMICA:
                recursos.agregarDinero(50_000);
                System.out.println("    📈 ¡La ciudad recibe un subsidio de $50,000!");
                recursos.registrarEvento("Bonanza económica: +$50,000 recibidos.");
                break;

            case LLUVIA_ABUNDANTE:
                recursos.agregarAgua(5_000);
                System.out.println("    🌧️  Las reservas de agua aumentan en 5,000 L.");
                recursos.registrarEvento("Lluvia abundante: agua repuesta.");
                break;

            case SIN_EVENTO:
                System.out.println("    ☀️  Los ciudadanos disfrutan de un mes en calma.");
                break;
        }
    }

    //Listado de todos los eventos (para mostrar al jugador)

    public static ArrayList<String> getDescripcionEventos() {
        ArrayList<String> lista = new ArrayList<>();
        for (TipoEvento t : TipoEvento.values()) {
            lista.add(t.nombre + ": " + t.descripcion);
        }
        return lista;
    }
}
