package principal;

import edificios.*;
import excepciones.FondosInsuficientesException;
import funcionhash.Encriptacion;
import motor.MotorJuego;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REGISTRO DEL JUGADOR 
        System.out.println("BIENVENIDO A ECOCITY");
        System.out.println();

        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Introduce tu nickname: ");
        String nickname = sc.nextLine();

        System.out.print("Introduce tu contraseña: ");
        String password = sc.nextLine();

        String passwordEncriptada = Encriptacion.hashear(password);

        System.out.println();
        System.out.println("✅ Jugador registrado correctamente.");
        System.out.println("Nombre: " + nombre);
        System.out.println("Nickname: " + nickname);
        System.out.println("Password: " + passwordEncriptada + " (encriptada)");
        System.out.println();

        // SIMULADOR 
        System.out.println("SIMULADOR DE GESTIÓN DE CIUDAD️");
        System.out.println();

        // Inicializar motor con presupuesto inicial
        MotorJuego motor = new MotorJuego(500_000.0);
        System.out.println("Presupuesto inicial: $500,000");

        // FASE 1: Construir la ciudad
        System.out.println();
        System.out.println("FASE 1: CONSTRUCCIÓN DE LA CIUDAD");
        try {
            // Nivel 2 de jerarquía
            motor.construir(new EdificioResidencial("Barrio Norte", 80_000, 500));
            motor.construir(new EdificioResidencial("Barrio Sur", 60_000, 300));
            motor.construir(new EdificioComercial("Centro Comercial", 40_000, 15, 8_000));
            motor.construir(new EdificioComercial("Mercado Municipal", 20_000, 8, 3_000));
            // Nivel 3 de jerarquía (hijos de EdificioIndustrial)
            motor.construir(new CentralNuclear("Central Atómica", 150_000));
            motor.construir(new ParqueEolico("Parque Eólico Norte", 50_000, 12));
        } catch (FondosInsuficientesException e) {
            System.out.println("  " + e.getMessage());
        }

        // FASE 2: Intentar construir sin fondos
        System.out.println();
        System.out.println("FASE 2: PRUEBA DE EXCEPCIÓN FondosInsuficientesException");
        try {
            motor.construir(new CentralNuclear("Central Extra", 999_999));
        } catch (FondosInsuficientesException e) {
            System.out.println("  " + e.getMessage());
            System.out.printf("(Faltaban $%.0f para construirla)%n", e.getFaltante());
        }

        // FASE 3: Mostrar inventario
        motor.mostrarInventario();

        // FASE 4: Simular 4 meses
        System.out.println();
        System.out.println("FASE 4: SIMULACIÓN — 4 MESES");
        for (int i = 0; i < 4; i++) {
            motor.ejecutarMes();
        }

        // FASE 5: Prueba de riesgo nuclear
        System.out.println();
        System.out.println("FASE 5: PRUEBA DE RIESGO NUCLEAR");
        System.out.println("Forzando daño masivo en la Central Atómica...");
        motor.getEdificios().stream()
            .filter(e -> e instanceof CentralNuclear)
            .findFirst()
            .ifPresent(e -> {
                e.setSalud(15);
                System.out.printf("Salud de '%s' forzada a %d%%%n", e.getNombre(), e.getSalud());
            });
        motor.ejecutarMes();

        // FASE 6: Reparar edificios
        System.out.println();
        System.out.println("FASE 6: REPARACIÓN DE EDIFICIOS");
        motor.repararEdificio("Central Atómica");
        motor.repararEdificio("Barrio Norte");

        // FASE 7: Inventario final
        motor.mostrarInventario();
        System.out.println("Simulación completada. Ciudad gestionada con éxito.");

        sc.close();
    }
}