package io.github.mainyf.remapjar

import org.gradle.api.Project

class ServerProject {


    private String projectName
    private boolean forgeSupport
    private File serverFile
    private List<String> includeSubProjects

    ServerProject(String projectName, boolean forgeSupport) {
        this(projectName, forgeSupport, null, [])
    }

    ServerProject(String projectName, boolean forgeSupport, List<String> includeSubProjects) {
        this(projectName, forgeSupport, null, includeSubProjects)
    }

    ServerProject(String projectName, boolean forgeSupport, File serverFile) {
        this(projectName, forgeSupport, serverFile, [])
    }

    ServerProject(String projectName, boolean forgeSupport, File serverFile, List<String> includeSubProjects) {
        this.projectName = projectName
        this.forgeSupport = forgeSupport
        this.serverFile = serverFile
        this.includeSubProjects = includeSubProjects
    }

    String getProjectName() {
        return projectName
    }

    void setProjectName(String projectName) {
        this.projectName = projectName
    }

    boolean getForgeSupport() {
        return forgeSupport
    }

    void setForgeSupport(boolean forgeSupport) {
        this.forgeSupport = forgeSupport
    }

    File getServerFile() {
        return serverFile
    }

    void setServerFile(File serverFile) {
        this.serverFile = serverFile
    }

    List<String> getIncludeSubProjects() {
        return includeSubProjects
    }

    void setIncludeSubProjects(List<String> includeSubProjects) {
        this.includeSubProjects = includeSubProjects
    }

    String getJarBaseName(Project rootProject) {
        return "${rootProject.name}-${projectName}"
    }

}
