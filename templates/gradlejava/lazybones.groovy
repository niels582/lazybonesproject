import static org.apache.commons.io.FileUtils.moveFileToDirectory

def props = [:]
props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
props.pkg = ask("Definir nombre del 'package' [com.intercorp.retail]: ", "pkg")
props.projectName = ask("Definir nombre del Proyecto: ", "projectName", "projectName")



// directorios
def list = ['canonical', 'config', 'entities', 'facade', 'repository', 'rest', 'service', 'task', 'util']
//process
def filesProcess = ['build.gradle','settings.gradle', 'src/main/java/**/App.java']

String packegeDir = props.pkg.replaceAll(/\./, '/')
moveFileToDirectory(new File(projectDir, 'App.java'), new File(projectDir, "src/main/java/$packegeDir"), true)


list.each { p ->
    new File(projectDir, "src/main/java/$packegeDir/$p").mkdirs()
}

filesProcess.each { p ->
    processTemplates "build.gradle", props
    processTemplates "settings.gradle", props
    processTemplates "src/main/java/**/App.java", props

}
//props.each { k, v ->
//    System.err.println("${k}===>${v}")
//}