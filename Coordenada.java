	/**
	 * Clase de Coordenadas 
	 * @author poseidon
	 *
	 */
	public class Coordenada {
		private Double latitud;
		private Double longitud;
		private String hora;

		public Double getLatitud() {
			return latitud;
		}

		public void setLatitud(Double latitud) {
			this.latitud = latitud;
		}

		public Double getLongitud() {
			return longitud;
		}

		public void setLongitud(Double longitud) {
			this.longitud = longitud;
		}

		public String getHora() {
			return hora;
		}

		public void setHora(String hora) {
			this.hora = hora;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(latitud);
			builder.append(";");
			builder.append(longitud);
			builder.append(";");
			builder.append(hora.substring(0,2));
			builder.append(":");
			builder.append(hora.substring(2));
			return builder.toString();
		}

	}