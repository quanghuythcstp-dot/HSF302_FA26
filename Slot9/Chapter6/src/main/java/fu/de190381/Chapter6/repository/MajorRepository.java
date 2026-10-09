package fu.de190381.Chapter6.repository;

import fu.de190381.Chapter6.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    Optional<Major> findByCode(String code);

    boolean existsByCode(String code);
}
