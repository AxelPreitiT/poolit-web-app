# Poolit
Poolit es un sitio web de **carpooling**, el cual permite a los usuarios compartir viajes en auto con otros usuarios que se dirigen al mismo destino. 
El objetivo del proyecto es reducir el tráfico, la contaminación y los costos del transporte.
<br/>
Esto es un proyecto de la asignatura **Proyectos de Aplicaciones Web - 72.38** del ITBA.


## Configuración del proyecto

En caso de querer utilizar el sitio, puede probarlo directamente haciendo click en este [link](http://pawserver.it.itba.edu.ar/paw-2023a-07/).
<br/>
Alternativamente, en caso de querer replicar el sitio de forma local (http://localhost:8080/), se recomienda seguir el siguiente instructivo.

### Pre-requisitos
- [Java 1.8](https://www.java.com/es/download/ie_manual.jsp)
- [Tomcat 8.5.87](https://tomcat.apache.org/download-80.cgi)
- [Docker 23.0.3](https://docs.docker.com/get-docker/)


### Configuración de Tomcat

1. Una vez instalado Tomcat, verificar que el servicio se esté ejecutando, preferiblemente en el puerto 8080.
   
      * En linux, esto se puede comprobar desde la terminal con el comando `sudo service tomcat8 status`.
      * En Windows, esto se puede comprobar desde la ventana de Servicios.
      * En Mac, esto se puede comprobar desde la terminal con el comando `sudo launchctl list | grep tomcat`.
   
2. Desde su IDE de preferencia, deberá descargar el plugin de Tomcat para poder ejecutar el proyecto.

3. Una vez descargado el plugin, deberá configurar el servidor Tomcat en el IDE. Los parámetros a configurar son los siguientes:

      * La versión de Tomcat, apuntando a la carpeta donde se encuentra instalado.
      * El puerto en el que se ejecuta el servidor Tomcat (8080 por defecto).
      * La versión de Java (JRE) que se utiliza para ejecutar el proyecto.
      * La URL raíz del proyecto (http://localhost:8080/).
      * El artefacto del proyecto (war, o war-exploded).
      * El contexto de la aplicación (/ por defecto).

4. Una vez configurado el servidor Tomcat, deberá ejecutar el proyecto desde el IDE. Esto iniciará el servidor Tomcat y desplegará el proyecto en la URL configurada.

### Configuración de la base de datos 
#### Se utilizará un contenedor Docker, el cual ejecutará una instancia de base de datos PostgresSQL.

1. Descargar la imagen de postgres.
    
        docker pull postgres

    * Se puede optar por una imagen más liviana, postgres:alpine.

            docker pull postgres:alpine


2. Crear un contenedor docker para la base de datos.

        docker run --name postgres-paw -e POSTGRES_PASSWORD=test -d -p 5433:5432 postgres

    * En caso de utilizar postgres:alpine, el comando es el siguiente.

            docker run --name postgres-paw -e POSTGRES_PASSWORD=test -d -p 5433:5432 postgres:alpine


3. Ingresar al contenedor docker y crear la base de datos del proyecto. Luego, salir del contenedor.

        docker exec -it postgres-paw bash
        psql -U postgres
        CREATE DATABASE "paw_2023a_07";
        \q

4. Ahora, resulta necesario correr el proyecto para generar las tablas. Esto retornará un error 500, ya que aún no están cargados los datos requeridos en las bases de datos.

5. Ubicar el CWD de la terminal en el directorio raíz del proyecto y copiar el archivo cities.sql al contenedor

        docker cp persistence/src/main/resources/cities.sql [dockerContainerId]:/cities.sql

    * Para obtener el id del contenedor, ejecutar el comando `docker ps` y copiar el id del contenedor postgres-paw.

6. Finalmente, ingresar nuevamente al contenedor y ejecutar el archivo .sql para popular la base de datos
       
        docker exec -it postgres-paw bash
        psql -U postgres -d "paw_2023a_07" -f cities.sql

7. Reiniciar el servidor Tomcat. Ahora, el proyecto debería funcionar correctamente.

### Tests de frontend

- Para correr los test de frontend se debe dirigir al directorio `/frontend` y ejecutar el comando
```shell
npm run test
```
Se mostrarán los resultados en una ventana del navegador web. 
Es necesario utilizar node, versión 18 o superiores, para ejecutar los tests.

## Soporte

En caso de necesitar información adicional puede contactarse con cualquiera de los desarrolladores.

- [Axel Facundo Preiti Tasat](https://github.com/AxelPreitiT) - 62618
- [Gaston Ariel Francois](https://github.com/francoisgaston) - 62500
- [Jose Rodolfo Mentasti](https://github.com/JoseMenta) - 62248
- [Nicolas Suarez Durrels](https://github.com/nicosuarez272) - 62468

<hr />

### Correcciones
- La SPA está consultando a `/api/` en cada link navegado (a veces múltiples veces seguidas), esto no tiene sentido ya que no aporta información, sólo latencia.
- El historial de viajes muestra el monto total máximo teórico del viaje, no el real (ie: si no hubo pasajeros o se canceló antes de realizarse). El detalle muestra el valor correcto.
- La API de `GET /trips/{id}/passengers` no está bien modelada. La misma retorna cosas completamente distintas según el header `Accept` enviado, pero esto no son "representaciones distintas del mismo dato", sino que son respuestas a preguntas completamente distintas (ie: proyecciones como `count`). Lo correcto sería siempre retornar la misma entidad. Las proyecciones podrían ser enviadas como headers en la respuesta, sin necesidad de usar un mime type diferente. Esto es un error conceptual grave.
- El repositorio incluye archivos de IDEs particulares, lo que es incorrecto.
- La cobertura de tests de frontend es limitada. Esto era pedido por enunciado.
