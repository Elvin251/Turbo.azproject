package az.developia.turbo_system_name.Project.repository;






import  az.developia.turbo_system_name.Project.entity.AdvertisementEntity;
import  az.developia.turbo_system_name.Project.entity.UserEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
    Page<AdvertisementEntity> findByActiveTrue(Pageable pageable);
    Page<AdvertisementEntity> findByOwner(UserEntity owner, Pageable pageable);
}