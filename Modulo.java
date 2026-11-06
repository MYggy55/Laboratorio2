public final class Modulo {
    private final String codigo;
    private final String nombre;
    private final String tipo;
    private double consumoEnergia;
    private String estado;

    public Modulo(String codigo, String nombre, String tipo, double consumoEnergia, String estado) {
        this.codigo = Validacion.texto(codigo, "Código");
        this.nombre = Validacion.texto(nombre, "Nombre");
        this.tipo = Validacion.texto(tipo, "Tipo");
        actualizar(consumoEnergia, estado);
    }

    public void actualizar(double consumoEnergia, String estado) {
        double nuevoConsumo = Validacion.positivo(consumoEnergia, "Consumo");
        String nuevoEstado = Validacion.texto(estado, "Estado");
        this.consumoEnergia = nuevoConsumo;
        this.estado = nuevoEstado;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public double getConsumoEnergia() { return consumoEnergia; }
    public String getEstado() { return estado; }
}
