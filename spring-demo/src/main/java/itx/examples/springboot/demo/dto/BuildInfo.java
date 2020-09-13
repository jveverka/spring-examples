package itx.examples.springboot.demo.dto;

import java.time.LocalDate;

public class BuildInfo {

    private final String time;
    private final String version;
    private final String name;
    private final String artifact;
    private final String group;
    private final String gitFullHash;
    private final String gitBranchName;

    public BuildInfo(String time, String version, String name, String artifact, String group, String gitFullHash, String gitBranchName) {
        this.time = time;
        this.version = version;
        this.name = name;
        this.artifact = artifact;
        this.group = group;
        this.gitFullHash = gitFullHash;
        this.gitBranchName = gitBranchName;
    }

    public String getTime() {
        return time;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getGroup() {
        return group;
    }

    public String getGitFullHash() {
        return gitFullHash;
    }

    public String getGitBranchName() {
        return gitBranchName;
    }
}
