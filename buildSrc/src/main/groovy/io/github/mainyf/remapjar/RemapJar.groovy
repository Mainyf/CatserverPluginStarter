package io.github.mainyf.remapjar

import net.md_5.specialsource.Jar
import net.md_5.specialsource.JarMapping
import net.md_5.specialsource.JarRemapper
import net.md_5.specialsource.provider.ClassLoaderProvider
import net.md_5.specialsource.provider.JointProvider
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.util.jar.JarFile

class RemapJar extends DefaultTask {

    @InputFile
    File inputJar

    @OutputFile
    File outputJar = new File(project.buildDir, "output.jar")

    @TaskAction
    void doTask() {
        def inputJar = getInputJar().absoluteFile
        def fileName = inputJar.name.replaceFirst("[.][^.]+\$", "")
        def file = new File(inputJar.parentFile, "${fileName}-mcp.jar")
        inputJar.renameTo(file)
        inputJar.delete()
        def mapping = new JarMapping()
        mapping.loadMappings(
            RemapJarPlugin.class.getResourceAsStream("/mappings/mcp2srg.srg").newReader(),
            null,
            null,
            false
        )
        def inJar = Jar.init(file)


        URLClassLoader classLoader = null;
        try {
            def inheritProvider = new JointProvider()
//            inheritProvider.add(new JarProvider(inJar))
            def java = (JavaPluginConvention) project.getConvention().getPlugins().get("java");
            def classpath = java.sourceSets.getByName("main").getCompileClasspath()
            if (classpath != null && !classpath.isEmpty()) {
                def a = classpath.toList()
                inheritProvider.add(new ClassLoaderProvider(
                    classLoader = new URLClassLoader(toUrls([a[0]]))
                ));
            }
            mapping.setFallbackInheritanceProvider(inheritProvider)
        } catch (Exception ex) {
            ex.printStackTrace()
        }

        def jarRemapper = new JarRemapper(null, mapping)

        def target = new File(file.parentFile, "${fileName}-srg.jar")
        jarRemapper.remapJar(inJar, target)
//        def f = new File('D:\\MinecraftServerv1.12.2\\NewMCLib\\buildSrc\\DemoModule.class')
//        def f2 = new File('D:\\MinecraftServerv1.12.2\\NewMCLib\\buildSrc\\DemoModule2.class')
//        def output = f2.newOutputStream()
//        output.write(
//            jarRemapper.remapClassFile(f.newInputStream(), RuntimeRepo.getInstance())
//        )
//        output.flush()
        setOutputJar(target)
//        newOutputFile().set(target)

        def field = Jar.class.getDeclaredField("jarFiles")
        field.setAccessible(true)
        (field.get(inJar) as List<JarFile>).forEach {
            if (it != null) it.close()
        }
//        if (inJar != null) inJar.close()
        if (classLoader != null) classLoader.close()
    }

    URL[] toUrls(List<File> files) throws MalformedURLException {
        ArrayList<URL> urls = new ArrayList();
        Iterator var2 = files.iterator();

        while (var2.hasNext()) {
            File file = (File) var2.next();
            urls.add(file.toURI().toURL());
        }

        return (URL[]) urls.toArray(new URL[urls.size()]);
    }

//    File getInputJar() {
//        return inputJar
//    }
//
//    void setInputJar(File inputJar) {
//        this.inputJar = inputJar
//    }
//
//    File getOutputJar() {
//        return project.file(outputJar)
//    }
//
//    void setOutputJar(File outputJar) {
//        this.outputJar = outputJar
//    }
}
