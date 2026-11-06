package fu.de._1.SE20B01_DE190381_SDM_PE.service;

import fu.de._1.SE20B01_DE190381_SDM_PE.dto.DeviceDTO;
import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Device;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface DeviceService {

    // Q1.1 - List tat ca, sort installationDate giam dan
    List<Device> findAll();

    // Q1.2 - Tim kiem theo ten, sort giam dan
    List<Device> search(String deviceName);

    // Q4 - Xem chi tiet
    Device findById(Integer id);

    // Q3 - Validate business rules roi luu
    void validateBusinessRules(DeviceDTO dto, BindingResult bindingResult);

    void save(DeviceDTO dto);

    // Q2 - Xoa (nem IllegalStateException neu status == AC)
    void deleteById(Integer id);
}
