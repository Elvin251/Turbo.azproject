package az.developia.turbo_system_name.Project.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="ads")
public class AdvertisementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private UserEntity owner;

    @ManyToOne(optional=false)
    private BrandEntity brand;

    @ManyToOne(optional=false)
    private ModelEntity model;

    @Column(nullable=false)
    private Integer releaseYear;

    @Column(nullable=false)
    private Double engineVolume;

    @Column(nullable=false)
    private Integer mileageKm;

    @Column(nullable=false)
    private String fuelType;

    @Column(nullable=false)
    private String gearbox;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable=false)
    private String city;

    @Column(length=2000)
    private String description;

    private String imageUrl;

    @Column(nullable=false)
    private Boolean active = true;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public AdvertisementEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getOwner() { return owner; }
    public void setOwner(UserEntity owner) { this.owner = owner; }

    public BrandEntity getBrand() { return brand; }
    public void setBrand(BrandEntity brand) { this.brand = brand; }

    public ModelEntity getModel() { return model; }
    public void setModel(ModelEntity model) { this.model = model; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public Double getEngineVolume() { return engineVolume; }
    public void setEngineVolume(Double engineVolume) { this.engineVolume = engineVolume; }

    public Integer getMileageKm() { return mileageKm; }
    public void setMileageKm(Integer mileageKm) { this.mileageKm = mileageKm; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getGearbox() { return gearbox; }
    public void setGearbox(String gearbox) { this.gearbox = gearbox; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}