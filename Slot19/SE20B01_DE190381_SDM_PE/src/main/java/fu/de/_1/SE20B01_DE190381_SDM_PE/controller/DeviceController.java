package fu.de._1.SE20B01_DE190381_SDM_PE.controller;

import fu.de._1.SE20B01_DE190381_SDM_PE.dto.DeviceDTO;
import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Device;
import fu.de._1.SE20B01_DE190381_SDM_PE.service.CategoryService;
import fu.de._1.SE20B01_DE190381_SDM_PE.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class DeviceController {

    private final DeviceService deviceService;
    private final CategoryService categoryService;

    public DeviceController(DeviceService deviceService, CategoryService categoryService) {
        this.deviceService = deviceService;
        this.categoryService = categoryService;
    }

    // Map cac tuy chon Status dung chung
    private Map<String, String> getStatuses() {
        Map<String, String> statuses = new LinkedHashMap<>();
        statuses.put("AC", "Active");
        statuses.put("IN", "Inactive");
        statuses.put("DR", "Draft");
        return statuses;
    }

    // Q1 — Hien thi danh sach, ho tro search theo ten
    @GetMapping("/")
    public String listDevices(@RequestParam(required = false) String deviceName, Model model) {
        String trimmedName = (deviceName != null) ? deviceName.trim() : "";

        if (trimmedName.isEmpty()) {
            model.addAttribute("devices", deviceService.findAll());
        } else {
            model.addAttribute("devices", deviceService.search(trimmedName));
        }

        model.addAttribute("deviceName", trimmedName);
        return "device-list";
    }

    // Q3.1 — Hien thi form them moi (GET)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("DeviceDTO", new DeviceDTO());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", getStatuses());
        return "device-add";
    }

    // Q3.2 — Xu ly them moi (POST)
    @PostMapping("/add")
    public String addDevice(@Valid @ModelAttribute("DeviceDTO") DeviceDTO dto,
                            BindingResult bindingResult,
                            Model model) {

        // Validate business rules (them loi vao bindingResult)
        deviceService.validateBusinessRules(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            // Giu lai DTO cu (co gia tri da nhap) de hien thi lai
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("statuses", getStatuses());
            return "device-add";
        }

        // Luu thanh cong
        deviceService.save(dto);

        // Clear form (tao DTO moi rong) + hien thong bao
        model.addAttribute("DeviceDTO", new DeviceDTO());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", getStatuses());
        model.addAttribute("successMessage", "Created new device successfully");
        return "device-add";
    }

    // Q2 — Xoa device (POST theo PRG pattern)
    @PostMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            deviceService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Deleted successfully");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    // Q4 — Xem chi tiet (read-only)
    @GetMapping("/view/{id}")
    public String viewDevice(@PathVariable Integer id, Model model) {
        Device device = deviceService.findById(id);
        if (device == null) {
            model.addAttribute("errorMessage", "Device not found with id: " + id);
        }
        model.addAttribute("device", device);
        return "device-view";
    }
}
