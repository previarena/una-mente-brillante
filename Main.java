import java.util.List;
/**
* <h1>Detecta Bombas de Nexo</h1>
*@author : FBadilla, CQuezada, GMarambio y CAngulo.
*@Teams  : Ares
*@since  : 2020-11-27
**/
class Main {
  public static void main(String[] args) {
    // elimina los que aparecen vacio 
    Main main = new Main();
    Util.crearArchivo();
    main.obtenerLineaCoordenada(Util.leerArchivosCarpeta(),0);    
  }
  /**
  * Se obtiene la coordenadas validas e imprimir
  */
  public void obtenerLineaCoordenada(List<String> listaCoordenadas, int indice){    
    if(listaCoordenadas.size()>indice){
      String strCoordenadaCifrada = listaCoordenadas.get(indice);
      Coordenada coordenada = Util.obtenerCoordenada(Util.descifrarHexaAdecimal(strCoordenadaCifrada));
      if(coordenada!=null && coordenada.getLatitud()<0 && coordenada.getLongitud()<0){
        Util.mostrarCoordenada(coordenada);
      }
      obtenerLineaCoordenada(listaCoordenadas,  indice + 1 );
    }
  }
}

