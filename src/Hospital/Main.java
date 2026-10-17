package Hospital;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

        while (true) {
            String linea = scanner.nextLine().trim();

            if (linea.equalsIgnoreCase("salir")) {
                break;
            }

            if (linea.isEmpty()) {
                continue;
            }

            String[] partes = linea.split("\\s+");
            String respuesta;

            try {
                switch (partes[0].toLowerCase()) {
                    case "ingresar":
                        if (partes.length == 7
                                && partes[1].equalsIgnoreCase("perro")) {

                            if (!partes[6].equalsIgnoreCase("si")
                                    && !partes[6].equalsIgnoreCase("no")) {
                                respuesta = "ERROR: datos invalidos";
                                break;
                            }

                            respuesta = hospital.ingresarPerro(
                                    partes[2],
                                    Double.parseDouble(partes[3]),
                                    Integer.parseInt(partes[4]),
                                    partes[5],
                                    partes[6].equalsIgnoreCase("si")
                            );

                        } else if (partes.length == 5
                                && partes[1].equalsIgnoreCase("gato")) {

                            respuesta = hospital.ingresarGato(
                                    partes[2],
                                    Double.parseDouble(partes[3]),
                                    Integer.parseInt(partes[4])
                            );

                        } else if (partes.length == 6
                                && partes[1].equalsIgnoreCase("ave")) {

                            respuesta = hospital.ingresarAve(
                                    partes[2],
                                    Double.parseDouble(partes[3]),
                                    Integer.parseInt(partes[4]),
                                    Double.parseDouble(partes[5])
                            );

                        } else {
                            respuesta = "ERROR: datos invalidos";
                        }
                        break;

                    case "vacunar":
                        if (partes.length == 2) {
                            respuesta = hospital.vacunar(partes[1]);
                        } else {
                            respuesta = "ERROR: comando desconocido.";
                        }
                        break;

                    case "asignar":
                        if (partes.length == 2) {
                            respuesta = hospital.asignar(partes[1]);
                        } else {
                            respuesta = "ERROR: comando desconocido.";
                        }
                        break;

                    case "medicar":
                        if (partes.length == 3) {
                            respuesta = hospital.medicar(
                                    partes[1],
                                    Double.parseDouble(partes[2])
                            );
                        } else {
                            respuesta = "ERROR: comando desconocido.";
                        }
                        break;

                    case "alta":
                        if (partes.length == 2) {
                            respuesta = hospital.alta(partes[1]);
                        } else {
                            respuesta = "ERROR: comando desconocido.";
                        }
                        break;

                    case "reporte":
                        if (partes.length == 1) {
                            respuesta = hospital.reporte();
                        } else {
                            respuesta = "ERROR: comando desconocido.";
                        }
                        break;

                    default:
                        respuesta = "ERROR: comando desconocido.";
                }

            } catch (NumberFormatException e) {
                respuesta = "ERROR: datos invalidos";
            }

            System.out.println(respuesta);
        }

        scanner.close();
    }
}