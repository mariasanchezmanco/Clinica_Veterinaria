package Hospital;
import java.util.Locale;
public class Hospital {
    private Paciente[] Pacientes;
    private int contadorPacientes;
    private Jaula[] Jaulas;

    public Hospital() {
        Pacientes = new Paciente[10];
        contadorPacientes = 0;
        Jaulas = new Jaula[6];

        cargarJaulas();
        cargarDatosIniciales();
    }

    private void cargarJaulas() {
        Jaulas[0] = new Jaula(1, "S", "A");
        Jaulas[1] = new Jaula(2, "M", "A");
        Jaulas[2] = new Jaula(3, "M", "A");
        Jaulas[3] = new Jaula(4, "L", "A");
        Jaulas[4] = new Jaula(5, "S", "B");
        Jaulas[5] = new Jaula(6, "M", "B");
    }

    public void cargarDatosIniciales() {
        agregarInicial(new Perro("Rocky", 30.0, 4, "labrador", true));
        agregarInicial(new Gato("Misifu", 4.0, 3, 0));
        agregarInicial(new AveExotica("Kiwi", 0.8, 2, 70));
    }

    private void agregarInicial(Paciente paciente) {
        if (contadorPacientes < Pacientes.length) {
            Pacientes[contadorPacientes++] = paciente;
        }
    }

    private Paciente buscarPaciente(String nombre) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (Pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return Pacientes[i];
            }
        }
        return null;
    }

    private boolean datosValidos(String nombre, double peso,
                                 int edad, double pesoMaximo) {
        return nombre != null && !nombre.trim().isEmpty()
                && Double.isFinite(peso)
                && peso > 0 && peso <= pesoMaximo
                && edad >= 0 && edad <= 30;
    }

    public String ingresarPerro(String nombre, double peso, int edad,
                                String raza, boolean vacunado) {
        if (!datosValidos(nombre, peso, edad, 80)
                || raza == null || raza.trim().isEmpty()) {
            return "ERROR: datos invalidos";
        }

        if (buscarPaciente(nombre) != null) {
            return "ERROR: nombre duplicado";
        }

        if (contadorPacientes >= Pacientes.length) {
            return "ERROR: hospital lleno";
        }

        Perro perro = new Perro(nombre, peso, edad, raza, vacunado);
        Pacientes[contadorPacientes++] = perro;

        if (!vacunado) {
            return "OK: " + perro.getNombre()
                    + " ingresado en CUARENTENA";
        }

        return "OK: " + perro.getNombre() + " ingresado";
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        if (!datosValidos(nombre, peso, edad, 15)) {
            return "ERROR: datos invalidos";
        }

        if (buscarPaciente(nombre) != null) {
            return "ERROR: nombre duplicado";
        }

        if (contadorPacientes >= Pacientes.length) {
            return "ERROR: hospital lleno";
        }

        Pacientes[contadorPacientes++] =
                new Gato(nombre, peso, edad, 0);

        return "OK: " + nombre + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad,
                              double envergadura) {
        if (!datosValidos(nombre, peso, edad, 15)
                || !Double.isFinite(envergadura)
                || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }

        if (buscarPaciente(nombre) != null) {
            return "ERROR: nombre duplicado";
        }

        if (contadorPacientes >= Pacientes.length) {
            return "ERROR: hospital lleno";
        }

        Pacientes[contadorPacientes++] =
                new AveExotica(nombre, peso, edad, envergadura);

        return "OK: " + nombre + " ingresado";
    }

    public String vacunar(String nombre) {
        Paciente paciente = buscarPaciente(nombre);

        if (paciente == null) {
            return "ERROR: paciente no encontrado";
        }

        if (!(paciente instanceof Perro)) {
            return "ERROR: solo aplica a perros";
        }

        Perro perro = (Perro) paciente;

        if (perro.isVacunado()) {
            return "ERROR: ya vacunado";
        }

        perro.setVacunado(true);
        perro.setEstado("INGRESADO");

        return "OK: " + perro.getNombre()
                + " vacunado (pasa a INGRESADO)";
    }

    public String asignar(String nombre) {
        Paciente paciente = buscarPaciente(nombre);

        if (paciente == null) {
            return "ERROR: paciente no encontrado";
        }

        if ("CUARENTENA".equals(paciente.getEstado())) {
            return "ERROR: paciente en cuarentena";
        }

        if (paciente.getJaula() != null) {
            return "ERROR: paciente ya tiene jaula";
        }

        String zonaNecesaria =
                paciente instanceof AveExotica ? "B" : "A";

        String tamanoNecesario = calcularTamano(paciente);

        for (Jaula jaula : Jaulas) {
            if (jaula.estaLibre()
                    && jaula.getZona().equals(zonaNecesaria)
                    && tamanoSuficiente(
                    jaula.getTamano(), tamanoNecesario)) {

                jaula.setOcupante(paciente);
                paciente.setJaula(jaula);

                return "OK: " + paciente.getNombre()
                        + " asignado a jaula " + jaula.getNumero();
            }
        }

        return "ERROR: no hay jaula compatible";
    }

    private String calcularTamano(Paciente paciente) {
        String tamano;

        if (paciente.getPeso() < 5) {
            tamano = "S";
        } else if (paciente.getPeso() <= 25) {
            tamano = "M";
        } else {
            tamano = "L";
        }

        if (paciente instanceof AveExotica
                && ((AveExotica) paciente).getEnvergadura() > 60
                && tamano.equals("S")) {
            tamano = "M";
        }

        return tamano;
    }

    private boolean tamanoSuficiente(String disponible,
                                     String requerido) {
        return nivelTamano(disponible) >= nivelTamano(requerido);
    }

    private int nivelTamano(String tamano) {
        switch (tamano) {
            case "S":
                return 1;
            case "M":
                return 2;
            case "L":
                return 3;
            default:
                return 0;
        }
    }

    public String medicar(String nombre, double mg) {
        Paciente paciente = buscarPaciente(nombre);

        if (paciente == null) {
            return "ERROR: paciente no encontrado";
        }

        if (!Double.isFinite(mg) || mg <= 0) {
            return "ERROR: datos invalidos";
        }

        if ("CUARENTENA".equals(paciente.getEstado())) {
            return "ERROR: paciente en cuarentena";
        }

        if (paciente.getJaula() == null) {
            return "ERROR: paciente sin jaula";
        }

        if (paciente instanceof Gato
                && ((Gato) paciente).getNivelEstres() >= 8) {
            return "ERROR: gato con estres alto";
        }

        double dosisMaxima = paciente.dosisMaxima();

        if (mg > dosisMaxima) {
            return "ERROR: dosis excede el maximo";
        }

        if (paciente.getDosisAcumulada() + mg
                > 3 * dosisMaxima + 0.000000001) {
            return "ERROR: tope acumulado superado";
        }

        paciente.setDosisAcumulada(
                paciente.getDosisAcumulada() + mg);

        paciente.setEstado("EN_TRATAMIENTO");

        if (paciente instanceof Gato) {
            Gato gato = (Gato) paciente;
            gato.setNivelEstres(
                    Math.min(10, gato.getNivelEstres() + 2));
        }

        return "OK: " + paciente.getNombre()
                + " medicado con " + Double.toString(mg) + " mg";
    }

    public String alta(String nombre) {
        int indice = -1;

        for (int i = 0; i < contadorPacientes; i++) {
            if (Pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            return "ERROR: paciente no encontrado";
        }

        Paciente paciente = Pacientes[indice];

        if (!"EN_TRATAMIENTO".equals(paciente.getEstado())) {
            return "ERROR: paciente no apto para alta";
        }

        if (paciente.getJaula() != null) {
            paciente.getJaula().setOcupante(null);
            paciente.setJaula(null);
        }

        String nombrePaciente = paciente.getNombre();

        for (int i = indice; i < contadorPacientes - 1; i++) {
            Pacientes[i] = Pacientes[i + 1];
        }

        Pacientes[contadorPacientes - 1] = null;
        contadorPacientes--;

        return "OK: " + nombrePaciente + " dado de alta";
    }

    public String reporte() {
        StringBuilder reporte = new StringBuilder();

        reporte.append("--- REPORTE ---\n");
        reporte.append("Pacientes: ")
                .append(contadorPacientes).append("/10\n");

        for (int i = 0; i < contadorPacientes; i++) {
            Paciente paciente = Pacientes[i];

            String numeroJaula = paciente.getJaula() == null
                    ? "-" : String.valueOf(
                    paciente.getJaula().getNumero());

            reporte.append(paciente.getNombre()).append(" | ")
                    .append(paciente.getTipo()).append(" | ")
                    .append(String.format(Locale.US, "%.1f",
                            paciente.getPeso()))
                    .append(" kg | ")
                    .append(paciente.getEstado())
                    .append(" | jaula: ")
                    .append(numeroJaula).append("\n");
        }

        int libres = 0;

        for (Jaula jaula : Jaulas) {
            if (jaula.estaLibre()) {
                libres++;
            }
        }

        reporte.append("Jaulas libres: ").append(libres);

        return reporte.toString();
    }
}

