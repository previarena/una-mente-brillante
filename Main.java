import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class Main {

  final static int NEGATIVE_VALUE_DET = 10;
  final static int POSITIVE_VALUE_DET = 11;
  final static String LATITUDE_STR = "Latitud";
  final static String LONGITUDE_STR = "Longitud";
  final static String HORA_STR = "hora";


  public static void main(String[] args) throws IOException {

    try (Stream<Path> paths = Files.walk(Paths.get(""))) {
          paths
	            .filter(Files::isRegularFile)
	            .filter(path -> path.toString().startsWith("message"))
	            .flatMap(path -> {
					try {
						return Files.lines(path, StandardCharsets.UTF_8);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Stream.empty();
				}).filter(Main::esHexConvertible)
        .map(valorHexadecimal -> String.valueOf(new BigInteger(valorHexadecimal, 16)))
        .filter(valorDecimal -> validarLargoDecimal(valorDecimal))
        .map(valorBigInteger -> obtenerCoordenadasyHora(valorBigInteger))
        .map(valorBigInteger -> obtenerCoordenadasFinal(valorBigInteger))
        .filter(data -> discriminaNeg(data))
        .map(Main::formatOutput)
        .forEach(System.out::println);
    }

    
  }

private static boolean esHexConvertible(String hexadecimal){
  try{
    new BigInteger(hexadecimal, 16);
       return true;
  } catch(NumberFormatException nfe){
    return false;
  }
}

private static boolean validarLargoDecimal(String decimal){
  return decimal.length() >= 24;
}

 //paso 2 y 3
  private static Map<String, String>  obtenerCoordenadasyHora(String coordenadasDecimales){
       Map<String, String> resultado = new HashMap<>();
       resultado.put(HORA_STR, coordenadasDecimales.substring(coordenadasDecimales.length()-4, coordenadasDecimales.length()));
       resultado.put(LATITUDE_STR, coordenadasDecimales.substring(0, 10));
       resultado.put(LONGITUDE_STR, new StringBuilder(coordenadasDecimales.substring(10, 20)).reverse().toString());
       return resultado;
  }

 //paso 4.1
  private static String identificarSignoCoordenada(String input) {
      int det = Integer.parseInt(input.substring(0, 2));
      int res = Integer.parseInt(input.substring(2));

      if (NEGATIVE_VALUE_DET == det) {
        return Integer.toString(-res);
      }if (POSITIVE_VALUE_DET == det) {
        return Integer.toString(res);
      }return "";//Exception  
  }

//paso 4.2 
 private static Map<String, String> obtenerCoordenadasFinal(Map<String, String> c) {

    c.put(LATITUDE_STR, identificarSignoCoordenada(c.get(LATITUDE_STR)));
    c.put(LONGITUDE_STR, identificarSignoCoordenada(c.get(LONGITUDE_STR)));
    return c;
  }

 //paso 4.2
  private static boolean discriminaNeg(Map<String, String> coordenadas){
    int Latitud = Integer.parseInt(coordenadas.get(LATITUDE_STR));
    int Longitud = Integer.parseInt(coordenadas.get(LONGITUDE_STR));
    int hora = Integer.parseInt(coordenadas.get(HORA_STR));
    return (Longitud<0 && Latitud<0 && hora <2400);
  }
   
  
   private static String addChar(String str, char ch, int position) {
    return str.substring(0, position) + ch + str.substring(position);
  }

  private static String formatOutput(Map<String, String> coordenadas){
    StringBuilder outputStr  = new StringBuilder();
    outputStr.append(addChar(coordenadas.get(LATITUDE_STR), '.', 3));
    outputStr.append(";");
    outputStr.append(addChar(coordenadas.get(LONGITUDE_STR), '.', 3));
    outputStr.append(";");
    outputStr.append(addChar(coordenadas.get(HORA_STR), ':', 2));
    return outputStr.toString();
  }
}