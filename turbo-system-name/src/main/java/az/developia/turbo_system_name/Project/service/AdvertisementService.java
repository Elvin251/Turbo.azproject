package az.developia.turbo_system_name.Project.service;


import az.developia.turbo_system_name.Project.entity.*;
import az.developia.turbo_system_name.Project.exception.ForbiddenException;
import az.developia.turbo_system_name.Project.exception.ResourceNotFoundException;
import az.developia.turbo_system_name.Project.repository.AdvertisementRepository;
import az.developia.turbo_system_name.Project.request.AdCreateRequest;
import az.developia.turbo_system_name.Project.request.AdUpdateRequest;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdvertisementService {

    private final AdvertisementRepository repo;
    private final UserService userService;
    private final BrandService brandService;
    private final ModelService modelService;

    public AdvertisementService(AdvertisementRepository repo, UserService userService,
                                BrandService brandService, ModelService modelService) {
        this.repo = repo;
        this.userService = userService;
        this.brandService = brandService;
        this.modelService = modelService;
    }

    public AdvertisementEntity create(String sellerEmail, AdCreateRequest req) {
        UserEntity seller = userService.findByEmail(sellerEmail);
        BrandEntity brand = brandService.getById(req.getBrandId());
        ModelEntity model = modelService.getById(req.getModelId());

        AdvertisementEntity ad = new AdvertisementEntity();
        ad.setOwner(seller);
        ad.setBrand(brand);
        ad.setModel(model);
        ad.setReleaseYear(req.getReleaseYear());
        ad.setEngineVolume(req.getEngineVolume());
        ad.setMileageKm(req.getMileageKm());
        ad.setFuelType(req.getFuelType());
        ad.setGearbox(req.getGearbox());
        ad.setPrice(req.getPrice());
        ad.setCity(req.getCity());
        ad.setDescription(req.getDescription());
        ad.setImageUrl(req.getImageUrl());
        ad.setActive(true);

        return repo.save(ad);
    }

    public Page<AdvertisementEntity> getAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<AdvertisementEntity> getActive(int page, int size,
                                               Long brandId, Long modelId,
                                               Integer yearMin, Integer yearMax,
                                               BigDecimal priceMin, BigDecimal priceMax,
                                               String city, String fuelType) {


        Page<AdvertisementEntity> p = repo.findByActiveTrue(PageRequest.of(page, size, Sort.by("createdAt").descending()));

        var filtered = p.getContent().stream().filter(ad -> {
            if (brandId != null && !ad.getBrand().getId().equals(brandId)) return false;
            if (modelId != null && !ad.getModel().getId().equals(modelId)) return false;
            if (yearMin != null && ad.getReleaseYear() < yearMin) return false;
            if (yearMax != null && ad.getReleaseYear() > yearMax) return false;
            if (priceMin != null && ad.getPrice().compareTo(priceMin) < 0) return false;
            if (priceMax != null && ad.getPrice().compareTo(priceMax) > 0) return false;
            if (city != null && !city.isBlank() && !ad.getCity().equalsIgnoreCase(city)) return false;
            if (fuelType != null && !fuelType.isBlank() && !ad.getFuelType().equalsIgnoreCase(fuelType)) return false;
            return true;
        }).toList();

        return new PageImpl<>(filtered, p.getPageable(), p.getTotalElements());
    }

    public AdvertisementEntity getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Elan tapilmadi"));
    }

    public Page<AdvertisementEntity> getMine(String sellerEmail, int page, int size) {
        UserEntity seller = userService.findByEmail(sellerEmail);
        return repo.findByOwner(seller, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public AdvertisementEntity update(String sellerEmail, Long adId, AdUpdateRequest req) {
        UserEntity seller = userService.findByEmail(sellerEmail);
        AdvertisementEntity ad = getById(adId);

        if (!ad.getOwner().getId().equals(seller.getId())) {
            throw new ForbiddenException("Yalniz oz elanini redakte ede bilersen");
        }

        BrandEntity brand = brandService.getById(req.getBrandId());
        ModelEntity model = modelService.getById(req.getModelId());

        ad.setBrand(brand);
        ad.setModel(model);
        ad.setReleaseYear(req.getReleaseYear());
        ad.setEngineVolume(req.getEngineVolume());
        ad.setMileageKm(req.getMileageKm());
        ad.setFuelType(req.getFuelType());
        ad.setGearbox(req.getGearbox());
        ad.setPrice(req.getPrice());
        ad.setCity(req.getCity());
        ad.setDescription(req.getDescription());
        ad.setImageUrl(req.getImageUrl());

        return repo.save(ad);
    }

    public void delete(Authentication auth, Long adId) {
        AdvertisementEntity ad = getById(adId);
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            repo.delete(ad);
            return;
        }
        UserEntity seller = userService.findByEmail(auth.getName());
        if (!ad.getOwner().getId().equals(seller.getId())) {
            throw new ForbiddenException("Yalniz oz elanini sile bilersen");
        }
        repo.delete(ad);
    }

    public AdvertisementEntity setActive(Long adId, boolean active) {
        AdvertisementEntity ad = getById(adId);
        ad.setActive(active);
        return repo.save(ad);
    }
}
