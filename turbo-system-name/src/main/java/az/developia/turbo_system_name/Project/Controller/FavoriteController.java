package az.developia.turbo_system_name.Project.Controller;



import az.developia.turbo_system_name.Project.entity.FavoriteEntity;
import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.service.FavoriteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin("*")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @PostMapping("/{adId}")
    public ApiResponse<FavoriteEntity> add(Authentication auth, @PathVariable Long adId) {
        return ApiResponse.ok("Seçilmişə əlavə olundu", service.add(auth.getName(), adId));
    }

    @DeleteMapping("/{adId}")
    public ApiResponse<String> remove(Authentication auth, @PathVariable Long adId) {
        service.remove(auth.getName(), adId);
        return ApiResponse.ok("Seçilmişdən silindi", "OK");
    }

    @GetMapping
    public ApiResponse<List<FavoriteEntity>> list(Authentication auth) {
        return ApiResponse.ok("Seçilmişlər", service.list(auth.getName()));
    }
}