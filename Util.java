import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
public class Util {

  private final static String NOMBRE_ARCHIVO_RESPUESTA = "respuesta.txt";

  private Util(){}

  /**
  * Parse los datos de un string a un objeto de tipo Coordenada
  */
  public static Coordenada obtenerCoordenada(String textoDescifrado)  {
    try {
      String valorNegativo = "10";
      double lat = 0;
      double lon = 0;

      String latitud = textoDescifrado.substring(0, 10);
      String longitud = reversarTexto(textoDescifrado.substring(10,20));
      int hora = Integer.parseInt(textoDescifrado.substring(20, 22));
      int minuto = Integer.parseInt(textoDescifrado.substring(22, 24));

      boolean esLatNeg = latitud.substring(0, 2).equals(valorNegativo);
      boolean esLonNeg = longitud.substring(0, 2).equals(valorNegativo);
      lat = Double.parseDouble(latitud.substring(2,latitud.length()))/1000000;
      lon = Double.parseDouble(longitud.substring(2,latitud.length()))/1000000;
          if(esLatNeg)
              lat = lat * -1;
          if(esLonNeg)
              lon = lon * -1;      
      return new Coordenada(lat, lon, hora, minuto);
    } catch(Exception e) { return null; }    
  }

/**
*Reversa el texto
*/
  private static String reversarTexto(String longitud) {
      StringBuffer sbr = new StringBuffer(longitud);
      sbr.reverse();
      return sbr.toString();
  }
  /**
  * Transforma un valor Hexadecimal a uno decimal.
  */
  public static String descifrarHexaAdecimal(String hexa){
    try{
      return String.valueOf(new BigInteger(hexa, 16));
    }catch(Exception e){     
      return null;
    }    
  }
  /**
  * Crea un archivo nuevo
  */
  public static void crearArchivo()  {
    try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File(NOMBRE_ARCHIVO_RESPUESTA),false )); ){} 
    catch(Exception e) {System.out.println("Error en crearArchivo()");}    
  }

  /**
   * Escribe el texto en el archivo de respuesta
  */
  public static void escribirArchivo(String texto)  {
    try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File(NOMBRE_ARCHIVO_RESPUESTA),true )); ){      
      pw.println(texto);
    } catch(Exception e) {System.out.println("Error en escribirArchivo()");}    
  }

  /**
  * Obtiene el texto formatiado de las coordenadas
  *
  */
  public static void mostrarCoordenada (Coordenada coordenada){
    String salida = MessageFormat.format("{0,number,##.########};{1,number,##.########};{2}:{3}", 
        coordenada.getLatitud(),  
        coordenada.getLongitud(),
        coordenada.getHora(),
        String.format("%02d", coordenada.getMinuto()) );
        
    System.out.println(salida);
    escribirArchivo(salida);
  }
  /**
  * Lee los archivo de una carpeta
  */
  public static List<String> leerArchivosCarpeta() {
    List<String> listaLineas = new ArrayList<>();
    Pattern pattern = Pattern.compile("message[\\d].txt");

    //se leen archivos de la carpeta
    File carpeta = new File(".");
     for (final File archivo : carpeta.listFiles()) {
        Matcher matcher = pattern.matcher(archivo.getName());        
        if (archivo.isFile() && matcher.matches() ){
          try (FileReader fr =  new FileReader (archivo);
              BufferedReader br = new BufferedReader(fr);) {
              // Lectura del fichero
              String linea;
              while((linea=br.readLine())!=null){
                listaLineas.add(linea);  
              }
          }
          catch(Exception e){System.out.println("Error en leerArchivosCarpeta()");}
        }
    }
    return listaLineas;
  }

}