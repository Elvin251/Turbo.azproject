package az.developia.turbo_system_name.Project.service;



import az.developia.turbo_system_name.Project.entity.BrandEntity;
import az.developia.turbo_system_name.Project.entity.ModelEntity;
import az.developia.turbo_system_name.Project.exception.ResourceNotFoundException;
import az.developia.turbo_system_name.Project.repository.ModelRepository;
import az.developia.turbo_system_name.Project.request.ModelRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    private final ModelRepository repo;
    private final BrandService brandService;

    public ModelService(ModelRepository repo, BrandService brandService) {
        this.repo = repo;
        this.brandService = brandService;
    }

    public ModelEntity create(ModelRequest req) {
        BrandEntity brand = brandService.getById(req.getBrandId());
        ModelEntity m = new ModelEntity();
        m.setName(req.getName());
        m.setBrand(brand);
        return repo.save(m);
    }

    public ModelEntity update(Long id, ModelRequest req) {
        ModelEntity m = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Model tapilmadi"));
        BrandEntity brand = brandService.getById(req.getBrandId());
        m.setName(req.getName());
        m.setBrand(brand);
        return repo.save(m);
    }

    public void delete(Long id) {
        ModelEntity m = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Model tapilmadi"));
        repo.delete(m);
    }

    public List<ModelEntity> list(Long brandId) {
        if (brandId == null) return repo.findAll();
        BrandEntity brand = brandService.getById(brandId);
        return repo.findByBrand(brand);
    }

    public ModelEntity getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Model tapilmadi"));
    }
}