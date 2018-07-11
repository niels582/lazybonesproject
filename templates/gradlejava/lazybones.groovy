import static org.apache.commons.io.FileUtils.moveFileToDirectory

def props = [:]
props.projectName = ask("Definir nombre del Proyecto: ", "projectName", "projectName")
props.pkg = ask("Definir nombre del 'package' [com.intercorp.retail]: ", "pkg")
props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
props.awsId = ask("Define value for 'ID aws' [085763616923]: ", "awsId", "awsId")
props.awsRegion = ask("Define value for 'region aws' [us-east-1]: ", "us-east-1", "awsRegion")
// directorios
def list = ['canonical', 'config', 'entities', 'facade', 'repository', 'rest', 'service', 'task', 'util']
//process
def filesProcess = ['build.gradle','settings.gradle', 'src/main/java/**/App.java','Jenkinsfile.groovy','base.yml','docker-compose.yml']

//creacion archivo principal
String packegeDir = props.pkg.replaceAll(/\./, '/')
moveFileToDirectory(new File(projectDir, "App.java"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "Greeting.java"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "GreetingController.java"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "application.yml"), new File(projectDir, "src/main/resources/"), true)


list.each { p ->
    new File(projectDir, "src/main/java/$packegeDir/$p").mkdirs()
}

filesProcess.each { p ->
     processTemplates p,props
}

//postbuild

def replace = { File source, String toSearch, String replacement ->
        source.write(source.text.replaceAll(toSearch, replacement))
    }

def filebuild = new File(projectDir,"build.gradle")
replace(filebuild,'&#','\\${')
replace(filebuild,'#&','\\}')

def filejenkins = new File(projectDir,"Jenkinsfile.groovy")
replace(filejenkins,'&#','\\${')
replace(filejenkins,'#&','\\}')
replace(filejenkins,'&1','\\[\\$')
replace(filejenkins,'&7','\\\$')
