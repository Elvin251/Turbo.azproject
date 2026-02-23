package az.developia.turbo_system_name.Project.service;

import az.developia.turbo_system_name.Project.entity.*;
import az.developia.turbo_system_name.Project.exception.BadRequestException;
import az.developia.turbo_system_name.Project.exception.ResourceNotFoundException;
import az.developia.turbo_system_name.Project.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository repo;
    private final UserService userService;
    private final AdvertisementService adService;

    public FavoriteService(FavoriteRepository repo, UserService userService, AdvertisementService adService) {
        this.repo = repo;
        this.userService = userService;
        this.adService = adService;
    }

    public FavoriteEntity add(String buyerEmail, Long adId) {
        UserEntity buyer = userService.findByEmail(buyerEmail);
        AdvertisementEntity ad = adService.getById(adId);

        if (!Boolean.TRUE.equals(ad.getActive())) {
            throw new BadRequestException("Deaktiv elani favorit edebilmezsen");
        }

        if (repo.existsByUserAndAd(buyer, ad)) {

            throw new BadRequestException("Bu elan artiq secilmislerdedir");
        }

        return repo.save(new FavoriteEntity(buyer, ad));
    }

    public void remove(String buyerEmail, Long adId) {
        UserEntity buyer = userService.findByEmail(buyerEmail);
        AdvertisementEntity ad = adService.getById(adId);

        FavoriteEntity fav = repo.findByUserAndAd(buyer, ad)
                .orElseThrow(() -> new ResourceNotFoundException("Seçilmiş tapilmadi"));
        repo.delete(fav);
    }

    public List<FavoriteEntity> list(String buyerEmail) {
        UserEntity buyer = userService.findByEmail(buyerEmail);
        return repo.findByUser(buyer);
    }
}