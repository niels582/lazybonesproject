import static org.apache.commons.io.FileUtils.moveFileToDirectory

def props = [:]
props.groupId = ask("Define value for 'group' [org.example]: ", "org.example", "group")
props.artifactId=ask("Define value for 'artifact' [artifact]: ", "artifact", "artifact")
props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
props.pkg= ask("Define value for 'package' [pe.irt.config]: ","mypackage",props.groupId)
props.message= ask("Hola","min")

processTemplates 'pom.xml', props
String packegeDir= props.pkg.replaceAll(/\./, '/')
moveFileToDirectory(new File(projectDir, 'App.java'), new File(projectDir, "src/main/java/$packegeDir"), true)
processTemplates "build.gradle", props
processTemplates "src/main/java/**/App.java", props