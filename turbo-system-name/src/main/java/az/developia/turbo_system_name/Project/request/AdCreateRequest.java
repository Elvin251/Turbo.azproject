package az.developia.turbo_system_name.Project.request;

import java.math.BigDecimal;

public class AdCreateRequest {
    private Long brandId;
    private Long modelId;
    private Integer releaseYear;
    private Double engineVolume;
    private Integer mileageKm;
    private String fuelType;
    private String gearbox;
    private BigDecimal price;
    private String city;
    private String description;
    private String imageUrl;

    public AdCreateRequest() {}

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getModelId() { return modelId; }
    public void setModelId(Long modelId) { this.modelId = modelId; }

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
}