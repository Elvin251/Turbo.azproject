package az.developia.turbo_system_name.Project.request;




public class ModelRequest {
    private String name;
    private Long brandId;

    public ModelRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
}