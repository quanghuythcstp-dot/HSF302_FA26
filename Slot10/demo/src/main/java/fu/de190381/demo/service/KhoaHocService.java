package fu.de190381.demo.service;

import fu.de190381.demo.model.KhoaHoc;
import fu.de190381.demo.repository.KhoaHocRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KhoaHocService {

    private final KhoaHocRepository repo;

    // Constructor injection
    public KhoaHocService(KhoaHocRepository repo) {
        this.repo = repo;
    }

    public List<KhoaHoc> search(String keyword) {
        Sort sort = Sort.by("id");
        if (keyword == null || keyword.isBlank()) {
            return repo.findAll(sort);
        }
        return repo.findByTenKhoaHocContainingIgnoreCase(keyword.trim(), sort);
    }

    public Optional<KhoaHoc> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public KhoaHoc save(KhoaHoc kh) {
        return repo.save(kh);   // id null → INSERT, id có → UPDATE
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
