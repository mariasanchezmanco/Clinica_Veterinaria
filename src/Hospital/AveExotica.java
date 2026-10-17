package Hospital;

public class AveExotica extends Paciente {
    private double envergadura;

    public AveExotica(String nombre, double peso, int edad,
                      double envergadura) {
        super(nombre, peso, edad, 3);
        setEnvergadura(envergadura);
    }

    public double getEnvergadura() {
        return envergadura;
    }

    public void setEnvergadura(double envergadura) {
        if (Double.isFinite(envergadura) && envergadura > 0) {
            this.envergadura = envergadura;
        }
    }
}
