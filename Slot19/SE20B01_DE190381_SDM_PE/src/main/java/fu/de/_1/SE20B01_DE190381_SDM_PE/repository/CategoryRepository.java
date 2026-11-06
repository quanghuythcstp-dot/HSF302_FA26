package fu.de._1.SE20B01_DE190381_SDM_PE.repository;

import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
