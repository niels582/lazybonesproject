import static org.apache.commons.io.FileUtils.moveFileToDirectory

def props = [:]
props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
props.pkg = ask("Definir nombre del 'package' [com.intercorp.retail]: ", "pkg")
props.projectName = ask("Definir nombre del Proyecto: ", "projectName", "projectName")
// directorios
def list = ['canonical', 'config', 'entities', 'facade', 'repository', 'rest', 'service', 'task', 'util']
//process
def filesProcess = ['build.gradle','settings.gradle', 'src/main/java/**/App.java']

//creacion archivo principal
def contenido= """
    package ${props.pkg};

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication
    public class ${props.projectName}Application {

        public static void main(String[] args) {
            SpringApplication.run(Project1Application.class, args);
        }
    }
"""
def file = new File("${props.projectName}Application").createNewFile()
file << contenido
String packegeDir = props.pkg.replaceAll(/\./, '/')
moveFileToDirectory(new File(projectDir, "${props.projectName}Application"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "Greeting.java"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "GreetingController.java"), new File(projectDir, "src/main/java/$packegeDir"), true)
moveFileToDirectory(new File(projectDir, "application.yml"), new File(projectDir, "src/main/resources/"), true)


list.each { p ->
    new File(projectDir, "src/main/java/$packegeDir/$p").mkdirs()
}

filesProcess.each { p ->
    processTemplates "build.gradle", props
    processTemplates "settings.gradle", props
    //processTemplates "src/main/java/**/App.java", props

}
//props.each { k, v ->
//    System.err.println("${k}===>${v}")
//}