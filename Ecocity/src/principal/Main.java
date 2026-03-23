package principal;

import motor.*;
import edificios.*;
import funcionhash.Encriptacion;
import motor.MotorJuego;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REGISTRO DEL JUGADOR
        System.out.println("=== BIENVENIDO A ECOCITY ===");
        System.out.println();

        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Introduce tu apodo: ");
        String apodo = sc.nextLine();

        System.out.print("Introduce tu contraseña: ");
        String contrasena = sc.nextLine();

        String contrasenaEncriptada = Encriptacion.hashear(contrasena);

        System.out.println();
        System.out.println("Jugador registrado correctamente.");
        System.out.println("Nombre:" + nombre);
        System.out.println("Apodo: " + apodo);
        System.out.println("Contraseña: " + contrasenaEncriptada + " (encriptada)");
        System.out.println();

        // INICIALIZAR MOTOR
        MotorJuego motor = new MotorJuego(500_000.0);

        // MENÚ PRINCIPAL
        boolean jugando = true;
        while (jugando) {

            System.out.println();
            System.out.println("--- ESTADO DE ECOCITY - MES " + motor.getMesActual() + " ---");
            System.out.println(motor.getRecursos().getResumenRecursos());
            System.out.println();

            if (!motor.getEdificios().isEmpty()) {
                System.out.println("Edificios construidos:");
                for (int i = 0; i < motor.getEdificios().size(); i++) {
                    Edificio e = motor.getEdificios().get(i);
                    String alerta = e.necesitaMantenimiento() ? " — ¡REQUIERE MANTENIMIENTO!" : "";
                    System.out.println("  " + (i + 1) + ". " + e.getNombre() +
                                       " [Salud: " + e.getSalud() + "%]" + alerta);
                }
                System.out.println();
            }

            System.out.println("¿Qué deseas hacer?");
            System.out.println("> 1. Construir   2. Reparar   3. Pasar Mes   4. Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {

                case "1":
                    System.out.println();
                    System.out.println("¿Qué deseas construir?");
                    System.out.println("  1. Edificio Residencial — 80,000 €");
                    System.out.println("  2. Edificio Comercial — 40,000€");
                    System.out.println("  3. Central Nuclear — 150,000€");
                    System.out.println("  4. Parque Eólico — 50,000€");
                    System.out.print("Opción: ");
                    String opcionConstruir = sc.nextLine().trim();

                    System.out.print("Nombre del edificio: ");
                    String nombreEdificio = sc.nextLine().trim();

                    switch (opcionConstruir) {
                        case "1":
                            motor.construir(new EdificioResidencial(nombreEdificio, 80_000, 300));
                            break;
                        case "2":
                            motor.construir(new EdificioComercial(nombreEdificio, 40_000, 10, 5_000));
                            break;
                        case "3":
                            motor.construir(new CentralNuclear(nombreEdificio, 150_000));
                            break;
                        case "4":
                            motor.construir(new ParqueEolico(nombreEdificio, 50_000, 8));
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;

                case "2":
                    if (motor.getEdificios().isEmpty()) {
                        System.out.println("No hay edificios construidos todavía.");
                        break;
                    }
                    System.out.println();
                    System.out.println("¿Qué edificio deseas reparar?");
                    for (int i = 0; i < motor.getEdificios().size(); i++) {
                        Edificio e = motor.getEdificios().get(i);
                        System.out.println("  " + (i + 1) + ". " + e.getNombre() +
                                           " [Salud: " + e.getSalud() + "%]");
                    }
                    System.out.print("Opción: ");
                    String opcionReparar = sc.nextLine().trim();
                    try {
                        int reparacion = Integer.parseInt(opcionReparar) - 1;
                        if (reparacion >= 0 && reparacion < motor.getEdificios().size()) {
                            motor.repararEdificio(motor.getEdificios().get(reparacion).getNombre());
                        } else {
                            System.out.println("Número fuera de rango.");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Opción no válida.");
                    }
                    break;

                case "3":
                    boolean explosion = motor.ejecutarMes();
                    if (explosion) {
                        jugando = false;
                    }
                    break;

                case "4":
                    System.out.println();
                    System.out.println("¡Hasta pronto, " + apodo + "! Tu ciudad queda en buenas manos.");
                    jugando = false;
                    break;
                default:
                    System.out.println("Opción no válida. Elige entre 1 y 4.");
            }
        }

        sc.close();
    }
}