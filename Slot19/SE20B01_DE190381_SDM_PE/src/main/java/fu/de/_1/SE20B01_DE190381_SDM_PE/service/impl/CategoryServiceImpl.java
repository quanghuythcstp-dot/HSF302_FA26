package fu.de._1.SE20B01_DE190381_SDM_PE.service.impl;

import fu.de._1.SE20B01_DE190381_SDM_PE.entity.Category;
import fu.de._1.SE20B01_DE190381_SDM_PE.repository.CategoryRepository;
import fu.de._1.SE20B01_DE190381_SDM_PE.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
