package Hospital;

public class Paciente {
    private String nombre;
    private double peso;
    private int edad;
    private String estado;
    private double factorDosis;
    private double dosisAcumulada;
    private Jaula jaula;

    public Paciente(String nombre, double peso, int edad, double factorDosis) {
        setNombre(nombre);
        setPeso(peso);
        setEdad(edad);
        setEstado("INGRESADO");
        setFactorDosis(factorDosis);
        setDosisAcumulada(0);
        this.jaula = null;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public int getEdad() {
        return edad;
    }

    public String getEstado() {
        return estado;
    }

    public double getFactorDosis() {
        return factorDosis;
    }

    public double getDosisAcumulada() {
        return dosisAcumulada;
    }

    public Jaula getJaula() {
        return jaula;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public void setPeso(double peso) {
        if (Double.isFinite(peso) && peso > 0) {
            this.peso = peso;
        }
    }

    public void setEdad(int edad) {
        if (edad >= 0 && edad <= 30) {
            this.edad = edad;
        }
    }

    public void setEstado(String estado) {
        if ("INGRESADO".equals(estado)
                || "CUARENTENA".equals(estado)
                || "EN_TRATAMIENTO".equals(estado)) {
            this.estado = estado;
        }
    }

    public void setFactorDosis(double factorDosis) {
        if (Double.isFinite(factorDosis) && factorDosis > 0) {
            this.factorDosis = factorDosis;
        }
    }

    public void setDosisAcumulada(double dosisAcumulada) {
        if (Double.isFinite(dosisAcumulada) && dosisAcumulada >= 0) {
            this.dosisAcumulada = dosisAcumulada;
        }
    }

    public void setJaula(Jaula jaula) {
        this.jaula = jaula;
    }

    public double dosisMaxima() {
        return peso * factorDosis;
    }

    public String getTipo() {
        return getClass().getSimpleName();
    }
}