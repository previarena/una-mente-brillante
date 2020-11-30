import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.util.stream.Collectors;
/**
* Interpretador de mensajes
* Equipo Zeus
* Franco Soto
* Yuri Perez
* Rafael Ortiz
**/
class Main {

  //Convierte un string formato hexadecimal a decimal
  public static String convertirHexADec(String hex){
    BigInteger conv = new BigInteger(hex, 16);
    return String.valueOf(conv);
  } 

  //Convierte un numero decimal de largo 8 a coordenada
  public static String convertirCoord(String cadena){
    String signo = "";
    if(cadena.substring(0,2).equals("10"))
      signo = "-";      
    return String.format("%s%s.%s", signo, cadena.substring(2,4), cadena.substring(4,10));
  }

  //Recibe un string de largo 4 y lo deja en formato hora hh:mm
  public static String convertirHora(String cadena){  
    return String.format("%s:%s", cadena.substring(0,2), cadena.substring(2,4));
  }

  // indica si la coordenada pertenece a chile
  public static boolean esChile(DecimalInfo item) {
	  return item.getLatitud().contains("-") && item.getLonguitud().contains("-") && evaluarHora(item.getHora());
  }
 //evlua si la hora es correcta
  public static boolean evaluarHora(String hora) {
    return Integer.parseInt(hora.replace(":", ""))< 2400;
  } 

//Lee un archivo con mensajes en hexadecimal
 public static List<DecimalInfo> leeArchivo(String archivo) throws FileNotFoundException, IOException {
      String cadena;
      List<DecimalInfo> decimalInfoList = new ArrayList<>();
      FileReader f = new FileReader(archivo);
      try(BufferedReader b = new BufferedReader(f);){
         while((cadena = b.readLine())!=null) {
          if(!"".equals(cadena)){
            try {
              DecimalInfo decimalInfo = new DecimalInfo(cadena);
              if(decimalInfo.getHexadecimal()!=null)
                decimalInfoList.add(decimalInfo);
            } catch (Exception e){
              //error al convertir numero hexadecimal a decimal
            }
          }
        }
      } 
      return decimalInfoList;
  }
  //Inicio de validacion de archivos con mensajes en hexa
  public static void main(String[] args) {
    String nombreArchivo = "message";
    List<DecimalInfo> listaFinal = null;
    for(int i = 1; i < 6; i++){
      try{
        listaFinal = leeArchivo(nombreArchivo + i + ".txt");
        listaFinal = listaFinal.stream().filter(item-> esChile(item)).collect(Collectors.toList());       
      } catch (Exception e){
        e.printStackTrace();
      }
      listaFinal.forEach( (coordenada) -> System.out.println(coordenada));
    }
  }
}
//Clase que contiene tanto el mensaje cifrado como descifrado
class DecimalInfo {
  private String hexadecimal;
  private String decimal;
  private String latitud;
  private String longuitud;
  private String hora;
	public DecimalInfo(String hexadecimal) {
		super();
		this.hexadecimal = hexadecimal;
    this.decimal = Main.convertirHexADec(hexadecimal);
    this.latitud =  Main.convertirCoord(this.decimal.substring(0,10));
    this.longuitud = this.decimal.substring(10,20);
    this.longuitud = Main.convertirCoord(new StringBuilder(this.longuitud).reverse().toString());
    this.hora = Main.convertirHora(this.decimal.substring(20,24));
    
	}
  public String getHexadecimal() {
		return hexadecimal;
	}
	public void setHexadecimal(String hexadecimal) {
		this.hexadecimal = hexadecimal;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLonguitud() {
		return longuitud;
	}
	public void setLonguitud(String longuitud) {
		this.longuitud = longuitud;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	@Override
	public String toString() {
		return String.format("%s;%s;%s",latitud, longuitud, hora);
	}
}