import java.util.ArrayList;

public final class Nave {
    public static final int CAPACIDAD = 5;
    private final String nombre;
    private final String codigo;
    private final String comandante;
    private final Modulo[] modulos;
    private final ArrayList<Planeta> planetas;

    public Nave(String nombre, String codigo, String comandante) {
        this.nombre = Validacion.texto(nombre, "Nombre de la nave");
        this.codigo = Validacion.texto(codigo, "Código de la nave");
        this.comandante = Validacion.texto(comandante, "Comandante");
        this.modulos = new Modulo[CAPACIDAD];
        this.planetas = new ArrayList<>();
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= modulos.length) {
            throw new IndexOutOfBoundsException("La posición debe estar entre 1 y 5.");
        }
    }

    public void validarEspacio(int posicion) {
        validarPosicion(posicion);
        if (modulos[posicion] != null) {
            throw new IllegalStateException("La posición ya está ocupada.");
        }
    }

    public void instalarModulo(int posicion, Modulo modulo) {
        validarEspacio(posicion);
        if (modulo == null) throw new IllegalArgumentException("El módulo no puede ser null.");
        modulos[posicion] = modulo;
    }

    public Modulo consultarModulo(int posicion) {
        validarPosicion(posicion);
        if (modulos[posicion] == null) {
            throw new IllegalStateException("No hay un módulo instalado en esa posición.");
        }
        return modulos[posicion];
    }

    public Modulo[] getModulos() { return modulos.clone(); }

    public void modificarModulo(int posicion, double consumo, String estado) {
        consultarModulo(posicion).actualizar(consumo, estado);
    }

    public void retirarModulo(int posicion) {
        consultarModulo(posicion);
        modulos[posicion] = null;
    }

    private int indicePlaneta(String codigo) {
        String buscado = Validacion.texto(codigo, "Código");
        for (int i = 0; i < planetas.size(); i++) {
            if (planetas.get(i).getCodigo().equalsIgnoreCase(buscado)) return i;
        }
        return -1;
    }

    public void registrarPlaneta(Planeta planeta) {
        if (planeta == null) throw new IllegalArgumentException("El planeta no puede ser null.");
        if (indicePlaneta(planeta.getCodigo()) >= 0) {
            throw new IllegalArgumentException("Ya existe un planeta con ese código.");
        }
        planetas.add(planeta);
    }

    public ArrayList<Planeta> getPlanetas() { return new ArrayList<>(planetas); }

    public Planeta buscarPlaneta(String codigo) {
        int indice = indicePlaneta(codigo);
        if (indice < 0) throw new IllegalStateException("No existe un planeta con ese código.");
        return planetas.get(indice);
    }

    public void modificarPlaneta(String codigoActual, String codigoNuevo, String nombre, double distancia, double temperatura, double habitabilidad) {
        int indice = indicePlaneta(codigoActual);
        if (indice < 0) throw new IllegalStateException("No existe un planeta con ese código.");
        Planeta nuevo = new Planeta(codigoNuevo, nombre, distancia, temperatura, habitabilidad);
        int repetido = indicePlaneta(nuevo.getCodigo());
        if (repetido >= 0 && repetido != indice) {
            throw new IllegalArgumentException("Otro planeta ya utiliza ese código.");
        }
        planetas.set(indice, nuevo);
    }

    public void eliminarPlaneta(String codigo) {
        int indice = indicePlaneta(codigo);
        if (indice < 0) throw new IllegalStateException("No existe un planeta con ese código.");
        planetas.remove(indice);
    }

    public int contarModulos() {
        int total = 0;
        for (Modulo modulo : modulos) if (modulo != null) total++;
        return total;
    }

    public int espaciosDisponibles() { return CAPACIDAD - contarModulos(); }

    public Modulo moduloMayorConsumo() {
        Modulo mayor = null;
        for (Modulo modulo : modulos) {
            if (modulo != null && (mayor == null || modulo.getConsumoEnergia() > mayor.getConsumoEnergia())) {
                mayor = modulo;
            }
        }
        return mayor;
    }

    public int contarPlanetas() { return planetas.size(); }

    private void exigirPlanetas() {
        if (planetas.isEmpty()) throw new IllegalStateException("No hay planetas registrados.");
    }

    public Planeta planetaMayorHabitabilidad() {
        exigirPlanetas();
        Planeta mayor = planetas.get(0);
        for (Planeta planeta : planetas) {
            if (planeta.getHabitabilidad() > mayor.getHabitabilidad()) mayor = planeta;
        }
        return mayor;
    }

    public Planeta planetaMenorHabitabilidad() {
        exigirPlanetas();
        Planeta menor = planetas.get(0);
        for (Planeta planeta : planetas) {
            if (planeta.getHabitabilidad() < menor.getHabitabilidad()) menor = planeta;
        }
        return menor;
    }

    public double promedioHabitabilidad() {
        exigirPlanetas();
        double total = 0;
        for (Planeta planeta : planetas) total += planeta.getHabitabilidad();
        return total / planetas.size();
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getComandante() { return comandante; }
}
