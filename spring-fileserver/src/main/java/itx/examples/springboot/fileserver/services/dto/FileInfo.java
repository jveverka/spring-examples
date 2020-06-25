package itx.examples.springboot.fileserver.services.dto;

public class FileInfo {

    private final String filePath;

    public FileInfo(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
