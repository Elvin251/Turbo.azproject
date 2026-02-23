package az.developia.turbo_system_name.Project.Controller;



import az.developia.turbo_system_name.Project.entity.BrandEntity;
import az.developia.turbo_system_name.Project.request.BrandRequest;
import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin("*")
public class BrandController {

    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<BrandEntity> create(@Valid @RequestBody BrandRequest req) {
        return ApiResponse.ok("Marka yaradildi", service.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<BrandEntity> update(@PathVariable Long id, @Valid @RequestBody BrandRequest req) {
        return ApiResponse.ok("Marka yenilendi", service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Marka silindi", "OK");
    }

    @GetMapping
    public ApiResponse<List<BrandEntity>> list() {
        return ApiResponse.ok("Markalar", service.list());
    }
}