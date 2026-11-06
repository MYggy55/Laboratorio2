public final class Validacion {
    private Validacion() { }

    public static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    public static double positivo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser un número finito mayor que 0.");
        }
        return valor;
    }

    public static double temperatura(double valor) {
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException("La temperatura debe ser un número finito.");
        }
        return valor;
    }

    public static double habitabilidad(double valor) {
        if (!Double.isFinite(valor) || valor < 0 || valor > 100) {
            throw new IllegalArgumentException("La habitabilidad debe estar entre 0 y 100.");
        }
        return valor;
    }
}
