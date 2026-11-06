import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in)) {
            entrada.useLocale(Locale.US);
            System.out.println("MISIÓN DE EXPLORACIÓN ESPACIAL");
            System.out.println("Los decimales se ingresan con punto.");
            Nave nave = null;
            while (nave == null) {
                try {
                    nave = crearNave(entrada);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                int opcion = leerEntero(entrada, "Opción: ");
                try {
                    switch (opcion) {
                        case 1 -> {
                            Nave nueva = crearNave(entrada);
                            nave = nueva;
                            System.out.println("Nueva nave registrada. Misión vacía.");
                        }
                        case 2 -> instalarModulo(entrada, nave);
                        case 3 -> mostrarModulos(nave);
                        case 4 -> mostrarModulo(nave.consultarModulo(leerPosicion(entrada)));
                        case 5 -> modificarModulo(entrada, nave);
                        case 6 -> {
                            nave.retirarModulo(leerPosicion(entrada));
                            System.out.println("Módulo retirado.");
                        }
                        case 7 -> {
                            nave.registrarPlaneta(leerPlaneta(entrada));
                            System.out.println("Planeta registrado.");
                        }
                        case 8 -> mostrarPlanetas(nave);
                        case 9 -> mostrarPlaneta(nave.buscarPlaneta(leerTexto(entrada, "Código: ")));
                        case 10 -> modificarPlaneta(entrada, nave);
                        case 11 -> {
                            nave.eliminarPlaneta(leerTexto(entrada, "Código: "));
                            System.out.println("Planeta eliminado.");
                        }
                        case 12 -> mostrarReporte(nave);
                        case 13 -> continuar = false;
                        default -> System.out.println("Selecciona una opción entre 1 y 13.");
                    }
                } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            System.out.println("Programa finalizado.");
        } catch (NoSuchElementException e) {
            System.out.println("Entrada finalizada. Programa cerrado.");
        }
    }

    private static Nave crearNave(Scanner entrada) {
        String nombre = leerTexto(entrada, "Nombre de la nave: ");
        String codigo = leerTexto(entrada, "Código de identificación: ");
        String comandante = leerTexto(entrada, "Nombre del comandante: ");
        return new Nave(nombre, codigo, comandante);
    }

    private static void mostrarMenu() {
        System.out.println("\n1. Nueva nave\n2. Instalar módulo\n3. Consultar módulos"
            + "\n4. Consultar un módulo\n5. Modificar módulo\n6. Retirar módulo"
            + "\n7. Registrar planeta\n8. Consultar planetas\n9. Buscar planeta"
            + "\n10. Modificar planeta\n11. Eliminar planeta\n12. Mostrar reporte de misión\n13. Salir");
    }

    private static void instalarModulo(Scanner entrada, Nave nave) {
        int posicion = leerPosicion(entrada);
        nave.validarEspacio(posicion);
        String codigo = leerTexto(entrada, "Código del módulo: ");
        String nombre = leerTexto(entrada, "Nombre: ");
        String tipo = leerTexto(entrada, "Tipo: ");
        double consumo = leerDecimal(entrada, "Consumo de energía: ");
        String estado = leerTexto(entrada, "Estado: ");
        nave.instalarModulo(posicion, new Modulo(codigo, nombre, tipo, consumo, estado));
        System.out.println("Módulo instalado.");
    }

    private static void modificarModulo(Scanner entrada, Nave nave) {
        int posicion = leerPosicion(entrada);
        nave.consultarModulo(posicion);
        double consumo = leerDecimal(entrada, "Nuevo consumo: ");
        String estado = leerTexto(entrada, "Nuevo estado: ");
        nave.modificarModulo(posicion, consumo, estado);
        System.out.println("Módulo modificado.");
    }

    private static void mostrarModulos(Nave nave) {
        Modulo[] modulos = nave.getModulos();
        if (nave.contarModulos() == 0) System.out.println("No hay módulos instalados.");
        for (int i = 0; i < modulos.length; i++) {
            if (modulos[i] != null) {
                System.out.println("Posición " + (i + 1) + ":");
                mostrarModulo(modulos[i]);
            }
        }
    }

    private static void mostrarModulo(Modulo modulo) {
        System.out.println("Código: " + modulo.getCodigo() + " | Nombre: " + modulo.getNombre()
            + " | Tipo: " + modulo.getTipo() + " | Consumo: " + modulo.getConsumoEnergia()
            + " | Estado: " + modulo.getEstado());
    }

    private static Planeta leerPlaneta(Scanner entrada) {
        String codigo = leerTexto(entrada, "Código del planeta: ");
        String nombre = leerTexto(entrada, "Nombre: ");
        double distancia = leerDecimal(entrada, "Distancia desde la nave: ");
        double temperatura = leerDecimal(entrada, "Temperatura: ");
        double habitabilidad = leerDecimal(entrada, "Habitabilidad (0 a 100): ");
        return new Planeta(codigo, nombre, distancia, temperatura, habitabilidad);
    }

    private static void modificarPlaneta(Scanner entrada, Nave nave) {
        String codigoActual = leerTexto(entrada, "Código del planeta a modificar: ");
        nave.buscarPlaneta(codigoActual);
        Planeta nuevo = leerPlaneta(entrada);
        nave.modificarPlaneta(codigoActual, nuevo.getCodigo(), nuevo.getNombre(), nuevo.getDistancia(),
            nuevo.getTemperatura(), nuevo.getHabitabilidad());
        System.out.println("Planeta modificado.");
    }

    private static void mostrarPlanetas(Nave nave) {
        ArrayList<Planeta> planetas = nave.getPlanetas();
        if (planetas.isEmpty()) System.out.println("No hay planetas registrados.");
        for (Planeta planeta : planetas) mostrarPlaneta(planeta);
    }

    private static void mostrarPlaneta(Planeta planeta) {
        System.out.println("Código: " + planeta.getCodigo() + " | Nombre: " + planeta.getNombre()
            + " | Distancia: " + planeta.getDistancia() + " | Temperatura: " + planeta.getTemperatura()
            + " | Habitabilidad: " + planeta.getHabitabilidad());
    }

    private static void mostrarReporte(Nave nave) {
        System.out.println("\nREPORTE DE MISIÓN | " + nave.getNombre() + " | " + nave.getCodigo()
            + " | Comandante: " + nave.getComandante());
        System.out.println("Módulos instalados: " + nave.contarModulos());
        System.out.println("Espacios disponibles: " + nave.espaciosDisponibles());
        Modulo mayor = nave.moduloMayorConsumo();
        if (mayor == null) System.out.println("Mayor consumo: no hay módulos instalados.");
        else {
            System.out.println("Módulo con mayor consumo:");
            mostrarModulo(mayor);
        }
        System.out.println("Planetas descubiertos: " + nave.contarPlanetas());
        if (nave.contarPlanetas() > 0) {
            System.out.println("Planeta con mayor habitabilidad:");
            mostrarPlaneta(nave.planetaMayorHabitabilidad());
            System.out.println("Planeta con menor habitabilidad:");
            mostrarPlaneta(nave.planetaMenorHabitabilidad());
            System.out.printf(Locale.US, "Promedio de habitabilidad: %.2f%n", nave.promedioHabitabilidad());
        } else {
            System.out.println("Habitabilidad máxima, mínima y promedio: sin datos.");
        }
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    private static int leerPosicion(Scanner entrada) {
        int posicion = leerEntero(entrada, "Posición del módulo (1 a 5): ");
        if (posicion < 1 || posicion > Nave.CAPACIDAD) {
            throw new IndexOutOfBoundsException("La posición debe estar entre 1 y 5.");
        }
        return posicion - 1;
    }

    private static int leerEntero(Scanner entrada, String mensaje) {
        Integer valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            try {
                valor = entrada.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            } finally {
                if (entrada.hasNextLine()) entrada.nextLine();
            }
        }
        return valor;
    }

    private static double leerDecimal(Scanner entrada, String mensaje) {
        Double valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            try {
                valor = entrada.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida: escribe un número; usa punto decimal.");
            } finally {
                if (entrada.hasNextLine()) entrada.nextLine();
            }
        }
        return valor;
    }
}
