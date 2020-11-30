import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Objects;

class Main {
/*
Pasar de Hexadecimal a Decimal OK
primeros 20 numeros son coordenadas
los ultimos 4 son la hora

los primeros 10 digitos son latitud
los los otros 10 digitos que siguen son longitud invertida, si es 10 es  - 11 es +
*/
  private static final String SIGNO_NEGATIVO = "10";
  private static final String SIGNO_NEGATIVO_INVERSO = "01";
  
  public static void main(String[] args) throws IOException {
    //Aquí va tu código
    List<String> listHexa = obtenerListaHexadecimal();
    List<String> listaDecimal = obtenerListaDecimal(listHexa);
    List<Coordenada> coordenadas = 
    listaDecimal.stream()
                  .filter(linea -> validarDato(linea))
                  .map(linea -> {
                    Coordenada coord = separarData(linea);
                    return coord;})
                  .collect(Collectors.toList());
    coordenadas.stream().forEach(coordenada->System.out.println(coordenada.toString()));

  }


  /**
	 * Metodo obtiene los datos hexa de los archivos messageX
	 * @return Lista de valores Hexa
	 * @throws IOException
	 */
	private static List<String> obtenerListaHexadecimal() throws IOException {
		List<Path> listaArchivos = Files.list(Paths.get("."))
				.filter(path -> path.toString().endsWith(".txt") && path.toString().contains("message"))
				.collect(Collectors.toList());
		List<String> allLines =  new ArrayList<>();

		if (!listaArchivos.isEmpty()) {
			for (Path item : listaArchivos) {
				allLines.addAll(Files.readAllLines(item));
			}
		}
    //System.out.println(allLines);
		return allLines;
	}

	/**
	 * Metodo convierte String de Hexa a Decimal
	 * @param hexValue valor Hexadecimal
	 * @return String decimal
	 */
	private static String hexadecimalToDecimal(String hexValue) {
    String decimal;
    try{
        decimal = new BigInteger(hexValue, 16).toString();
    }catch(NumberFormatException e){
      return null;
    }
		return decimal;
	}

	/**
	 * Metodo transforma la lista hexadecimal a decimal incluye las validaciones
	 * de hexa a decimal. 
	 * @param listaHexa
	 * @return nueva lista
	 */
	private static List<String> obtenerListaDecimal(List<String> listaHexa) {
		List<String> listaDecimal = new ArrayList<>();
		listaHexa.stream().forEach(linea -> {
			if (lineaValida(linea)) {
				listaDecimal.add(hexadecimalToDecimal(linea));
			}
		});
		return listaDecimal;
	}

	/**
	 * Metodo valida la data decimal para convertir a coordenada
	 * @param dato valor decimal
	 * @return flag
	 */
	private static boolean validarDato(String dato) {
		if (dato == null || dato.isEmpty() || dato.length() != 24)
			return false;
    
		String signo = dato.substring(0, 2);
		if (!SIGNO_NEGATIVO.equals(signo))
			return false;

		if (!SIGNO_NEGATIVO_INVERSO.equals(dato.substring(18, 20)))
			return false;
    
		int hora = Integer.parseInt(dato.substring(20, 22));
		int min = Integer.parseInt(dato.substring(22));
		if (hora > 24 || min > 59)
			return false;

		return true;
	}
  
	/**
	 * Metodo separa los valores de decimales en latitud, longitud y hora
	 * @param data valor decimal
	 * @return Coordenada datos
	 */
  private static Coordenada separarData(String data){

    Coordenada datos = new Coordenada();
    
    datos.setLatitud(formateaCoordenada(data.substring(0, 10))) ;
    datos.setLongitud(formateaCoordenada(reverseString(data.substring(10, 20))));
    datos.setHora(data.substring(20));
       
    return datos;
  }
	/**
	 * Metodo retorna el valor de la coordenada formateada
	 * @param latlon valor en string 
	 * @return Double coordenada
	 */
  private static Double formateaCoordenada(String latLon){
    String signo = latLon.substring(0, 2);
    String coordenadaEntero = latLon.substring(2, 4);
    String coordenadaResto = latLon.substring(4, 10);
    return Double.parseDouble(coordenadaEntero+"."+coordenadaResto)*(SIGNO_NEGATIVO.equals(signo)?-1.0:1.0);
  }
  
/**
  * Metodo invierte String 
  * @param String str
  * @return String 
  */   
  private static String reverseString(String str) {
    StringBuilder sbr = new StringBuilder(str);    

    return sbr.reverse().toString();
  }

	/**
	 * Metodo encargado de validar linea del archivo hexa.
	 * @param linea lina del archivo.
	 * @return flag true || false
	 */
	private static boolean lineaValida(String linea) {
		if (Objects.isNull(linea) || linea.isEmpty() || !esBigInteger(hexadecimalToDecimal(linea))) {
			return false;
		}
		return true;
	}

      
	/**
	 * Metodo valida que el texto ingresado sea numerico
	 * @param texto
	 * @return flag
	 */
	private static boolean esBigInteger(String texto) {
		if (texto == null)
			return false;
		try {
			new BigInteger(texto);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
  
}



