package az.developia.turbo_system_name.Project.Controller;



import az.developia.turbo_system_name.Project.entity.ModelEntity;
import az.developia.turbo_system_name.Project.request.ModelRequest;
import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@CrossOrigin("*")
public class ModelController {

    private final ModelService service;

    public ModelController(ModelService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<ModelEntity> create(@Valid @RequestBody ModelRequest req) {
        return ApiResponse.ok("Model yaradildi", service.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ModelEntity> update(@PathVariable Long id, @Valid @RequestBody ModelRequest req) {
        return ApiResponse.ok("Model yenilendi", service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Model silindi", "OK");
    }

    @GetMapping
    public ApiResponse<List<ModelEntity>> list(@RequestParam(required = false) Long brandId) {
        return ApiResponse.ok("Modeller", service.list(brandId));
    }
}