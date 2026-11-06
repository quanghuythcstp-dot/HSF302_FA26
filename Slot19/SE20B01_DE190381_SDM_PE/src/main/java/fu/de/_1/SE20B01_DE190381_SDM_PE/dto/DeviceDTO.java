package fu.de._1.SE20B01_DE190381_SDM_PE.dto;

import jakarta.validation.constraints.*;

public class DeviceDTO {

    // Format: chi chua chu, so va dau gach ngang, 2-50 ky tu
    @NotBlank(message = "Device Code is required")
    @Pattern(regexp = "^[A-Za-z0-9\\-]{2,50}$",
             message = "Device Code must be 2-50 characters, only letters, numbers and hyphens")
    private String deviceCode;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    @NotBlank(message = "Device Name is required")
    private String deviceName;

    // Price: text o form, parse thanh BigDecimal o service
    @NotBlank(message = "Price is required")
    private String price;

    // warrantyMonths: validate range 1-60
    @NotNull(message = "Warranty is required")
    @Min(value = 1, message = "Warranty must be at least 1 month")
    @Max(value = 60, message = "Warranty must not exceed 60 months")
    private Integer warrantyMonths;

    // installationDate: nguoi dung nhap text dd/MM/yyyy, service tu parse
    @NotBlank(message = "Installation Date is required")
    private String installationDate;

    @NotBlank(message = "Status is required")
    private String status;

    // Constructors
    public DeviceDTO() {}

    // Getters & Setters
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
