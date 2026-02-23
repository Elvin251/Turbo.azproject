package az.developia.turbo_system_name.Project.repository;



import az.developia.turbo_system_name.Project.entity.ModelEntity;
import az.developia.turbo_system_name.Project.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findByBrand(BrandEntity brand);
}