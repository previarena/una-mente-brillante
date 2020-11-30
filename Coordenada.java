/**
* Clase que representa una Coordenada
*/
public class Coordenada{
  private double latitud;
  private double longitud;
  private int hora;
  private int minuto;
  
public Coordenada(){}

public Coordenada(double latitud, double longitud, int hora, int minuto){
  this.latitud = latitud;
  this.longitud = longitud;
  this.hora = hora;
  this.minuto = minuto;
}

  public double getLatitud() {
      return latitud;
    }
    public void setLatitud(double latitud) {
      this.latitud = latitud;
    }
    public double getLongitud() {
      return longitud;
    }
    public void setLongitud(double longitud) {
      this.longitud = longitud;
    }
    public int getHora() {
      return hora;
    }
    public void setHora(int hora) {
      this.hora = hora;
	}
  public int getMinuto(){
    return minuto;
  }
  public void setMinuto(int minuto) {
    this.minuto=minuto;
  }
}