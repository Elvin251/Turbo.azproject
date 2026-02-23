package az.developia.turbo_system_name.Project.service;



import az.developia.turbo_system_name.Project.entity.BrandEntity;
import az.developia.turbo_system_name.Project.exception.BadRequestException;
import az.developia.turbo_system_name.Project.exception.ResourceNotFoundException;
import az.developia.turbo_system_name.Project.repository.BrandRepository;
import az.developia.turbo_system_name.Project                                                                     .request.BrandRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository repo;

    public BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    public BrandEntity create(BrandRequest req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new BadRequestException("Bu marka artiq movcuddur");
        }
        BrandEntity b = new BrandEntity();
        b.setName(req.getName());
        return repo.save(b);
    }

    public BrandEntity update(Long id, BrandRequest req) {
        BrandEntity b = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marka tapilmadi"));
        b.setName(req.getName());
        return repo.save(b);
    }

    public void delete(Long id) {
        BrandEntity b = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marka tapilmadi"));
        repo.delete(b);
    }

    public List<BrandEntity> list() {
        return repo.findAll();
    }

    public BrandEntity getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marka tapilmadi"));
    }
}