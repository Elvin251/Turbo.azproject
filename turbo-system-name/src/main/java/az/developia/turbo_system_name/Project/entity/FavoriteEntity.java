package az.developia.turbo_system_name.Project.entity;



import jakarta.persistence.*;

@Entity
@Table(
        name="favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","ad_id"})
)
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private UserEntity user;

    @ManyToOne(optional=false)
    private AdvertisementEntity ad;

    public FavoriteEntity() {}
    public FavoriteEntity(UserEntity user, AdvertisementEntity ad) {
        this.user = user;
        this.ad = ad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public AdvertisementEntity getAd() { return ad; }
    public void setAd(AdvertisementEntity ad) { this.ad = ad; }
}