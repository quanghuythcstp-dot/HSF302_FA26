package fu.de._1.SE20B01_DE190381_SDM_PE.service.impl;

import fu.de._1.SE20B01_DE190381_SDM_PE.dto.DeviceDTO;
import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Category;
import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Device;
import fu.de._1.SE20B01_DE190381_SDM_PE.repository.CategoryRepository;
import fu.de._1.SE20B01_DE190381_SDM_PE.repository.DeviceRepository;
import fu.de._1.SE20B01_DE190381_SDM_PE.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final CategoryRepository categoryRepository;

    // Constructor injection (khong can @Autowired)
    public DeviceServiceImpl(DeviceRepository deviceRepository,
                             CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAllByOrderByInstallationDateDesc();
    }

    @Override
    public List<Device> search(String deviceName) {
        return deviceRepository.findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(deviceName);
    }

    @Override
    public Device findById(Integer id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void validateBusinessRules(DeviceDTO dto, BindingResult bindingResult) {

        // Rule 1: deviceCode phai unique (trim truoc khi check)
        if (dto.getDeviceCode() != null && !dto.getDeviceCode().isBlank()) {
            String trimmedCode = dto.getDeviceCode().trim();
            if (deviceRepository.existsByDeviceCode(trimmedCode)) {
                bindingResult.addError(new FieldError("DeviceDTO", "deviceCode",
                        "Device Code already exists"));
            }
        }

        // Rule 2: categoryId phai ton tai trong DB
        if (dto.getCategoryId() != null) {
            Optional<Category> cat = categoryRepository.findById(dto.getCategoryId());
            if (cat.isEmpty()) {
                bindingResult.addError(new FieldError("DeviceDTO", "categoryId",
                        "Selected category does not exist"));
            }
        }

        // Rule 3: price phai la so duong (parse tu String)
        if (dto.getPrice() != null && !dto.getPrice().isBlank()) {
            try {
                BigDecimal price = new BigDecimal(dto.getPrice().trim());
                if (price.compareTo(BigDecimal.ONE) < 0) {
                    bindingResult.addError(new FieldError("DeviceDTO", "price",
                            "Price must be at least 1"));
                }
            } catch (NumberFormatException e) {
                bindingResult.addError(new FieldError("DeviceDTO", "price",
                        "Price must be a valid number"));
            }
        }

        // Rule 4: installationDate phai dung dinh dang dd/MM/yyyy va phai la ngay trong tuong lai
        if (dto.getInstallationDate() != null && !dto.getInstallationDate().isBlank()) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dto.getInstallationDate().trim(), fmt);
                if (!date.isAfter(LocalDate.now())) {
                    bindingResult.addError(new FieldError("DeviceDTO", "installationDate",
                            "Installation Date must be in the future"));
                }
            } catch (DateTimeParseException e) {
                bindingResult.addError(new FieldError("DeviceDTO", "installationDate",
                        "Installation Date must be in dd/MM/yyyy format"));
            }
        }
    }

    @Override
    public void save(DeviceDTO dto) {
        Device device = new Device();

        // Trim deviceCode truoc khi luu (Q3 yeu cau loai bo space)
        device.setDeviceCode(dto.getDeviceCode().trim());
        device.setDeviceName(dto.getDeviceName().trim());

        // Lay Category entity tu DB
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        device.setCategory(category);

        device.setPrice(new BigDecimal(dto.getPrice().trim()));
        device.setWarrantyMonths(dto.getWarrantyMonths());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        device.setInstallationDate(LocalDate.parse(dto.getInstallationDate().trim(), fmt));

        device.setStatus(dto.getStatus());

        deviceRepository.save(device);
    }

    @Override
    public void deleteById(Integer id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + id));

        // Khong cho xoa device dang Active (Q2)
        if ("AC".equals(device.getStatus())) {
            throw new IllegalStateException(
                    "Cannot delete device \"" + device.getDeviceName() + "\" because it is Active");
        }

        deviceRepository.deleteById(id);
    }
}
