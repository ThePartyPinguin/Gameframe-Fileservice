package fileservice.repository.jpa;

import fileservice.model.entity.FileType;
import fileservice.model.entity.SavedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFileRepository extends JpaRepository<SavedFile, Long> {

    Optional<SavedFile> findFirstByUserIdAndFileType(long userId, int fileType);
    boolean existsByUserIdAndFileType(long userId, int fileType);

}
