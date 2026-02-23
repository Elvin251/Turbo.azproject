package az.developia.turbo_system_name.Project.repository;





import az.developia.turbo_system_name.Project.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    List<FavoriteEntity> findByUser(UserEntity user);
    boolean existsByUserAndAd(UserEntity user, AdvertisementEntity ad);
    Optional<FavoriteEntity> findByUserAndAd(UserEntity user, AdvertisementEntity ad);
}