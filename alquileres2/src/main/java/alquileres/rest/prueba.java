package alquileres.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class prueba {

	public static void main(String[] args) {
        String info = "Estacionamiento [id=1, nombre=nombre, numPuestos=3, postal=30800, cordX=33.0, cordY=230.5, fechaAlta=15/05/2020]";

        // Definir el patrón de la expresión regular para buscar numPuestos
        Pattern pattern = Pattern.compile("numPuestos=(\\d+)");

        // Crear un objeto Matcher para buscar en la cadena
        Matcher matcher = pattern.matcher(info);

        // Verificar si se encuentra el valor de numPuestos y extraerlo si se encuentra
        if (matcher.find()) {
            String numPuestosStr = matcher.group(1); // Obtiene el primer grupo de captura
            int numPuestos = Integer.parseInt(numPuestosStr);
            System.out.println("El número de puestos es: " + numPuestos);
        } else {
            System.out.println("No se encontró el número de puestos.");
        }
    }

}
