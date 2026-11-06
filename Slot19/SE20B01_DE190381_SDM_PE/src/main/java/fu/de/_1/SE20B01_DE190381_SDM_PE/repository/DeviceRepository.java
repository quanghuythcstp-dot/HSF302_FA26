package fu.de._1.SE20B01_DE190381_SDM_PE.repository;

import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    // Q1.1 - List tat ca device, sort theo installationDate giam dan
    List<Device> findAllByOrderByInstallationDateDesc();

    // Q1.2 - Tim kiem theo ten device (LIKE %...%), khong phan biet hoa thuong, sort giam dan
    List<Device> findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String deviceName);

    // Check trung deviceCode truoc khi save (Q3 validate)
    boolean existsByDeviceCode(String deviceCode);
}
