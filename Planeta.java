public final class Planeta {
    private String codigo;
    private String nombre;
    private double distancia;
    private double temperatura;
    private double habitabilidad;

    public Planeta(String codigo, String nombre, double distancia, double temperatura, double habitabilidad) {
        actualizar(codigo, nombre, distancia, temperatura, habitabilidad);
    }

    private void actualizar(String codigo, String nombre, double distancia, double temperatura, double habitabilidad) {
        String nuevoCodigo = Validacion.texto(codigo, "Código");
        String nuevoNombre = Validacion.texto(nombre, "Nombre");
        double nuevaDistancia = Validacion.positivo(distancia, "Distancia");
        double nuevaTemperatura = Validacion.temperatura(temperatura);
        double nuevaHabitabilidad = Validacion.habitabilidad(habitabilidad);
        this.codigo = nuevoCodigo;
        this.nombre = nuevoNombre;
        this.distancia = nuevaDistancia;
        this.temperatura = nuevaTemperatura;
        this.habitabilidad = nuevaHabitabilidad;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getDistancia() { return distancia; }
    public double getTemperatura() { return temperatura; }
    public double getHabitabilidad() { return habitabilidad; }
}
