package SODEFEND.SODEFEND.RepositoryLayer;

import SODEFEND.SODEFEND.RegisterLayer.RegisterModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoInter extends JpaRepository<RegisterModel, Long> {
    boolean  existsByUserName(String userName);
    Optional<RegisterModel> deleteByUserName(String userName);
    Optional<RegisterModel> findByUserName(String userName);
}
