package motor;

import edificios.*;
import excepciones.FondosInsuficientesException;
import excepciones.RiesgoNuclearException;
import interfaces.GeneradorRecursos;
import java.util.ArrayList;
import java.util.List;

/**
 * Motor principal del juego:
 *  - Gestionar la lista de edificios de la ciudad
 *  - Ejecutar el ciclo mensual completo
 *  - Construir nuevos edificios (con validación de fondos)
 *  - Aplicar eventos aleatorios
 *  - Mostrar el estado de la ciudad
 */
public class MotorJuego {

    private final List<Edificio> edificios;
    private final GestorRecursos recursos;
    private int mesActual;

    public MotorJuego(double presupuestoInicial) {
        this.edificios= new ArrayList<>();
        this.recursos= new GestorRecursos(presupuestoInicial);
        this.mesActual= 1;
    }

    //Construye un edificio si hay fondos suficientes.
    public void construir(Edificio edificio) throws FondosInsuficientesException {
        double costo= edificio.getCosto();
        double dinero= recursos.getDinero();
        if (dinero < costo) {
            throw new FondosInsuficientesException(dinero, costo);
        }

        recursos.gastarDinero(costo);
        edificios.add(edificio);
        System.out.printf(
            "  🏗️  Construido: %-22s | Costo: $%,.0f | Presupuesto restante: $%,.0f%n",
            edificio.getNombre(), costo, recursos.getDinero()
        );
    }

    //Ejecuta un ciclo mensual completo:
    public void ejecutarMes() {
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
                edificio.aplicarEfectoMensual();
            } catch (RiesgoNuclearException e) {
                System.out.println("  " + e.getMessage());
                if (edificio instanceof CentralNuclear nuclear) {
                    nuclear.activarProtocoloEmergencia();
                }
                recursos.modificarFelicidad(-20);
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
        System.out.printf(
            "%n  [BALANCE DE ENERGÍA]%n  Producida: %.1f MW | Consumida: %.1f MW | Balance: %+.1f MW%n",
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

        //Evento aleatorio
        EventoAleatorio.TipoEvento evento = EventoAleatorio.seleccionarEvento();
        EventoAleatorio.aplicar(evento, edificios, recursos);

        //Resumen del mes
        System.out.println();
        System.out.println("[ESTADO DE LA CIUDAD — FIN DEL MES " + mesActual + " ]");
        System.out.println(recursos.getResumenRecursos());

        //Alerta de mantenimiento
        boolean hayAlertas = false;
        for (Edificio e: edificios) {
            if (e.necesitaMantenimiento()) {
                if (!hayAlertas) {
                    System.out.println("\n EDIFICIOS QUE NECESITAN MANTENIMIENTO:");
                    hayAlertas = true;
                }
                System.out.println("    → %-22s Salud: %d%%%n", e.getNombre(), e.getSalud());
            }
        }
        mesActual++;
    }
    
//Función para refarar el edificio
    public boolean repararEdificio(String nombre) {
        for (Edificio e: edificios) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                e.reparar();
                return true;
            }
        }
        return false;
    }
    
//Función que muestra el inventario
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
    public List<Edificio> getEdificios() { 
    	return edificios; 
    	}
    public GestorRecursos getRecursos()  { 
    	return recursos; 
    	}
    public int getMesActual() { 
    	return mesActual; 
    	}
}
