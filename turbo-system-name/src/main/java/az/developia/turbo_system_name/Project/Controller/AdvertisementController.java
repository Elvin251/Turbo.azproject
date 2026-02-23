package az.developia.turbo_system_name.Project.Controller;


import az.developia.turbo_system_name.Project.entity.AdvertisementEntity;
import az.developia.turbo_system_name.Project.request.AdCreateRequest;
import az.developia.turbo_system_name.Project.request.AdUpdateRequest;
import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.service.AdvertisementService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin("*")
public class AdvertisementController {

    private final AdvertisementService service;

    public AdvertisementController(AdvertisementService service) {
        this.service = service;
    }

    @GetMapping("/active")
    public ApiResponse<Page<AdvertisementEntity>> active(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="12") int size,
            @RequestParam(required=false) Long brandId,
            @RequestParam(required=false) Long modelId,
            @RequestParam(required=false) BigDecimal priceMin,
            @RequestParam(required=false) BigDecimal priceMax,
            @RequestParam(required=false) Integer yearMin,
            @RequestParam(required=false) Integer yearMax,
            @RequestParam(required=false) String city,
            @RequestParam(required=false) String fuelType
    ) {
        return ApiResponse.ok("Aktiv elanlar",
                service.getActive(page, size, brandId, modelId, yearMin, yearMax, priceMin, priceMax, city, fuelType));
    }
    @GetMapping("/{id}")
    public ApiResponse<AdvertisementEntity> detail(@PathVariable Long id) {
        return ApiResponse.ok("Elan", service.getById(id));
    }

    @PostMapping
    public ApiResponse<AdvertisementEntity> create(Authentication auth, @Valid @RequestBody AdCreateRequest req) {
        return ApiResponse.ok("Elan yaradildi", service.create(auth.getName(), req));
    }

    @GetMapping("/mine")
    public ApiResponse<Page<AdvertisementEntity>> mine(Authentication auth,
                                                       @RequestParam(defaultValue="0") int page,
                                                       @RequestParam(defaultValue="12") int size) {
        return ApiResponse.ok("Menim elanlarim", service.getMine(auth.getName(), page, size));
    }

    @PutMapping("/{id}")
    public ApiResponse<AdvertisementEntity> update(Authentication auth, @PathVariable Long id, @Valid @RequestBody AdUpdateRequest req) {
        return ApiResponse.ok("Elan yenilendi", service.update(auth.getName(), id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(Authentication auth, @PathVariable Long id) {
        service.delete(auth, id);
        return ApiResponse.ok("Elan silindi", "OK");
    }

    @GetMapping
    public ApiResponse<Page<AdvertisementEntity>> all(@RequestParam(defaultValue="0") int page,
                                                      @RequestParam(defaultValue="12") int size) {
        return ApiResponse.ok("Butun elanlar", service.getAll(page, size));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<AdvertisementEntity> status(@PathVariable Long id, @RequestParam boolean active) {
        return ApiResponse.ok("Status deyisdi", service.setActive(id, active));
    }}