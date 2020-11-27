## Temática: Una mente brillante ( A beautiful mind)

Es Noviembre del 20XX y Chile está enfrentando una amenaza terrorista de un grupo radical llamado el Nexo. Acusan de que nuestro país está sumido en
el consumismo y la hipocrecía y se debe purgar. Sin embargo, como todo grupo radical juegan con la esperanza y el miedo de las masas y están colocando
bombas de 5 kilotones al azar. Inteligencia ha detectado mensajes entre estos radicales, han interceptado mensajes de 10 bytes sospechosos por protocolo
TCP, y la brigada contra terrorismo BCT es forzada a buscar fuera de sus filas a alguien que pueda interpretar estos mensajes.

Para nuestra suerte, en Previred trabajas tú, te dicen el Jhon Nash chileno, sufres un poco de esquizofrenia pero tu habilidad para resolver problemas y
encontrar patrones hacen que el equilibro de Nash parezca el teorema de pitágoras. Una hora después de revisar las trazas de 10 bytes te das cuenta de
como descifrarlo y te preparas para desarrollar un programa que lo haga de forma automática.

### Desafío:

La BCT te ha entregado unos archivos planos con un par de trazas T de 10 bytes, donde cada fila del documento representa una traza diferente y cada una 
se puede visualizar como el ejemplo siguiente:

| Ejemplo de traza        |                
|-------------------------|
| 15E20E10BC76623F9804    |

Después de una hora analizando las trazas te das cuenta que son coordenadas y la hora en un sistema horario de 24 horas, que suponemos que pueden ser
cuando explotará la bomba en esa ubicación, el día de hoy, y tu técnica es como sigue:

1- Pasar de Hexadecimal a Decimal

2- Te das cuenta que los primeros 20 números con sistema décimal son coordenadas y los últimos 4 es la hora

| Ejemplo de coordenadas  | Ejemplo de Hora Sistema 24 horas |                
|-------------------------|----------------------------------|
| 10333967381805370701    | 2100                             |

3- También detectas que la latitud y la longitud se dividen en 10 dígitos cada uno, y que la longitud está invertida.

| Latitud                          | Latitud                           |                
|----------------------------------|-----------------------------------|
| 1033396738                       | 1070735081                        |

4- En este paso, debemos identificar si es una coordenada negativa o positiva, identificando que 10 es igual a negativo y 11 es positivo, quedando las
coordenadas como sigue:

| Latitud                          | Latitud                           |                
|----------------------------------|-----------------------------------|
| -33.396738                       | -70.735081                        |

Tu programa debe imprimir todas las coordenadas que estén dentro de Chile, es decir, debes discriminar las coordenadas
que tengan la latitud o la longitud positiva, debes omitirlas. Por lo tanto, en el caso de que en todos los archivos
leídos hayan solo 3 trazas válidas, sólo esas 3 imprimiremos de la siguiente manera:

| Ejemplo de salida              |                
|--------------------------------|
| -33.396738;-70.735081;21:00    |
| -33.396738;-70.735081;13:00    |
| -33.396738;-70.735081;12:00    |


Importante: Debes leer todos los archivos de texto plano que tienen por nombre messageX.txt, mientras más leas más puntaje tendrás. Y el programa nunca
debe caerse, si encuentra trazas que no son compatibles entonces debe omitirlas y continuar. Finalmente, existe un archivo respuesta donde si lees todos
los archivos deberías obtener un output en la consola idéntico.

Motivación: Operaciones similares se utilizan para obtener información enviada de dispositivos electrónicos
como GPS, sensores y alarmas, para finalmente ser visualizadas por sistemas de monitoreo.

Ánimo Previdiano, ya termina la semana, 
aprovecha de hacer amigos y aprender en el proceso!.
