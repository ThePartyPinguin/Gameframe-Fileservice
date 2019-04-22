package fileservice.client;

import org.springframework.core.io.Resource;

public interface IFileStorage {

    Resource loadFileAsResousece(String fileName);

}
