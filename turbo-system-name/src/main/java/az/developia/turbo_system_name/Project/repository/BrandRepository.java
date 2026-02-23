package az.developia.turbo_system_name.Project.repository;



import az.developia.turbo_system_name.Project.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}