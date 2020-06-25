package itx.examples.springboot.fileserver.services;

import itx.examples.springboot.fileserver.services.dto.FileList;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Service for manipulating files on the file system.
 */
public interface FileService {

    /**
     * Get base path used by this service for all file system operations.
     * @return absolute path to base directory.
     */
    Path getBasePath();

    /**
     * Create {@link Resource} for given file.
     * @param filePath relative path to file.
     * @return {@link Resource} representing the file.
     * @throws FileNotFoundException
     */
    Resource loadFileAsResource(Path filePath) throws FileNotFoundException;

    /**
     * Get information about file or list content of directory.
     * @param filePath relative path to file or directory.
     * @return meta data about file or directory content list.
     * @throws IOException
     */
    FileList getFilesInfo(Path filePath) throws IOException;

    /**
     * Writes data in {@link InputStream} into file specified by relative path.
     * @param filePath relative path to file.
     * @param inputStream data to be written into that file.
     * @throws IOException
     */
    void saveFile(Path filePath, InputStream inputStream) throws IOException;

    /**
     * Deletes file or directory. Directories are deleted even when not empty.
     * @param filePath relative path to file or directory.
     * @throws IOException
     */
    void delete(Path filePath) throws IOException;

    /**
     * Creates new empty directory.
     * @param filePath relative path to directory.
     * @throws IOException
     */
    void createDirectory(Path filePath) throws IOException;

}
