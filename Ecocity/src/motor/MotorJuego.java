package motor;

import edificios.*;
import interfaz.GeneradorRecursos;
import java.util.ArrayList;
import java.util.List;

public class MotorJuego {

    private final List<Edificio> edificios;
    private final GestorRecursos recursos;
    private int mesActual;

    public MotorJuego(double presupuestoInicial) {
        this.edificios = new ArrayList<>();
        this.recursos = new GestorRecursos(presupuestoInicial);
        this.mesActual = 1;
    }

    public void construir(Edificio edificio) {
        double costo = edificio.getCosto();
        double dinero = recursos.getDinero();
        if (dinero < costo) {
            System.out.printf(
                "No se puede construir '%s'. Fondos insuficientes. " +
                "Necesitas €%,.0f pero solo tienes €%,.0f%n",
                edificio.getNombre(), costo, dinero
            );
            return;
        }
        recursos.gastarDinero(costo);
        edificios.add(edificio);
        System.out.printf(
            " Construido: %-22s | Costo: €%,.0f | Presupuesto restante: €%,.0f%n",
            edificio.getNombre(), costo, recursos.getDinero()
        );
    }

    public boolean ejecutarMes() {
        System.out.println();
        System.out.println("═".repeat(65));
        System.out.printf("MES %d — PROCESANDO CICLO MENSUAL%n", mesActual);
        System.out.println("═".repeat(65));

        recursos.iniciarNuevoMes();

        double energiaProducida = 0.0;
        double energiaConsumida = 0.0;
        double dinerosGenerados = 0.0;
        int felicidadComercial = 0;

        System.out.println("\n  [EFECTOS MENSUALES DE EDIFICIOS]");
        for (Edificio edificio : edificios) {
            try {
                edificio.aplicarEfectoMensual(recursos);
            } catch (RuntimeException e) {
                System.out.println("  " + e.getMessage());
                recursos.modificarFelicidad(-20);
            }

            // Comprobar explosión inmediatamente después de cada edificio
            if (edificio instanceof CentralNuclear nuclear && nuclear.isExplotada()) {
                return true;
            }

            if (edificio instanceof GeneradorRecursos gen) {
                String tipo = gen.getTipoRecurso();
                if (tipo.contains("Energía")) {
                    energiaProducida += gen.producirRecurso();
                } else if (tipo.contains("Dinero")) {
                    dinerosGenerados += gen.producirRecurso();
                }
            }
            energiaConsumida += edificio.getConsumoEnergia();

            if (edificio instanceof EdificioComercial comercial) {
                felicidadComercial += comercial.getBonusFelicidad();
            }
        }

        recursos.agregarEnergia(energiaProducida);
        recursos.consumirEnergia(energiaConsumida);
        recursos.agregarDinero(dinerosGenerados);

        double balanceEnergia = energiaProducida - energiaConsumida;
        System.out.printf("%n  [BALANCE DE ENERGÍA]%n  Producida: %.1f MW | Consumida: %.1f MW | Balance: %+.1f MW%n",
            energiaProducida, energiaConsumida, balanceEnergia
        );

        if (balanceEnergia < 0) {
            int penalizacion = (int)(Math.abs(balanceEnergia) * 0.5);
            recursos.modificarFelicidad(-penalizacion);
            System.out.printf("Déficit energético → Felicidad -%d puntos%n", penalizacion);
        }

        if (felicidadComercial > 0) {
            recursos.modificarFelicidad(felicidadComercial);
            System.out.printf("Comercios aportan +%d felicidad%n", felicidadComercial);
        }

        EventoAleatorio.TipoEvento evento = EventoAleatorio.seleccionarEvento();
        EventoAleatorio.aplicar(evento, edificios, recursos);
        System.out.println();
        System.out.println("[ESTADO DE LA CIUDAD — FIN DEL MES " + mesActual + " ]");
        System.out.println(recursos.getResumenRecursos());

        boolean hayAlertas = false;
        for (Edificio e : edificios) {
            if (e.necesitaMantenimiento()) {
                if (!hayAlertas) {
                    System.out.println("\n EDIFICIOS QUE NECESITAN MANTENIMIENTO:");
                    hayAlertas = true;
                }
                System.out.printf(" %-22s Salud: %d%%%n", e.getNombre(), e.getSalud());
            }
        }
        mesActual++;
        return false;
    }

    public boolean repararEdificio(String nombre) {
        for (Edificio e : edificios) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                e.reparar();
                return true;
            }
        }
        return false;
    }

    public void mostrarInventario() {
        System.out.println();
        System.out.println("═".repeat(65));
        System.out.println("INVENTARIO DE EDIFICIOS DE LA CIUDAD");
        System.out.println("═".repeat(65));
        if (edificios.isEmpty()) {
            System.out.println("  (No hay edificios construidos todavía)");
        } else {
            for (Edificio e : edificios) {
                System.out.println(e);
                System.out.println();
            }
        }
    }

    public List<Edificio> getEdificios() { return edificios; }
    public GestorRecursos getRecursos() { return recursos; }
    public int getMesActual(){ return mesActual; }
}