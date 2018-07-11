# Template Inicial SpringBoot

La presente plantilla nos brinda un proyecto java con las siguientes caracteristicas:
    
    - Framework: SpringBoot
    - Builder: Gradle
    - Integración Continua: Jenkins(Jenkinsfile.groovy)
    
## Pre-Requisitos(local)

####Tener instalado:

-  Docker
-  Gradle
-  Java(JDK 1.8)
## Pasos De Compilación
* Compilacion local:
```sh
$ gradle docker
```

Esto hara la compilación y dockerizara la aplicación(jar) 

```sh
$ docker images
```
![docker.png](docker.png)
[docker.png](docker.png)

- Como se puede observar el proceso local crearia la imagen(1)
- El proceso de Integración Continua crearia la imagen(2)

#### Integración Continua(IC)

- La IC esta ligada principalmente con el archivo [Jenkinsfile.groovy](Jenkinsfile.groovy)
- El archivo Jenkinsfile sera procesado por en el servidor de IC el cual debe tener todos los requisitos necesarios la para la compilación y el traslado hacia la nube de AWS.

# Requisitos del Servidor(IC)
El Servidor IC debe tener instalado lo siguiente:
* Docker
* Gradle
* JDK 8
* AWS CLI

### Sugerencias de configuración de servidor de IC
* En cuestión del AWS-CLI se puede instalar con pip(Python) [Guia Oficial](https://docs.aws.amazon.com/es_es/cli/latest/userguide/installing.html).
```sh
$ pip install awscli --upgrade --user
```

* Una vez instalada se podría setear las credenciales AWS.
```sh
$ aws configure

aws_access_key_id = AKIAIOSFODNN7EXAMPLE
aws_secret_access_key = wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
```

* O en el proceso de IC(Jenkinsfile.groovy) se podria hacer un export de variables de entorno [Guia Oficial](https://docs.aws.amazon.com/es_es/cli/latest/userguide/cli-environment.html).
```groovy
            stage("Push ECR"){
              sh """ 
               export AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE
               export AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
               export AWS_DEFAULT_REGION=us-east-1
              """
              .
              .
              .
              .

```