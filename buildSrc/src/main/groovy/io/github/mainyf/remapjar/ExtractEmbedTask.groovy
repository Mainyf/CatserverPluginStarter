package io.github.mainyf.remapjar

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files

class ExtractEmbedTask extends DefaultTask {

    List<Configuration> configurations

    File output

    @TaskAction
    void doAction() {
        def outputPath = output.toPath()
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath)
        }
        configurations.forEach { configuration ->
            configuration.toList().forEach { file ->
                Files.copy(file.toPath(), outputPath.resolve(file.name))
            }
        }
    }


}