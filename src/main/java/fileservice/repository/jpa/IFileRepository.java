package fileservice.repository.jpa;

import fileservice.model.entity.SavedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository<T extends SavedFile> extends JpaRepository<T, Long> {
}
