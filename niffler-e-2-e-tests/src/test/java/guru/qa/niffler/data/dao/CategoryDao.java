package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDao {
    CategoryEntity create(CategoryEntity category);
    Optional<CategoryEntity> findCategoryById(UUID id);
    void deleteCategory (CategoryEntity category);
    Optional<CategoryEntity> findCategoryByUsernameAndCategoryName(String username, String categoryName);
    List<CategoryEntity> findAllByUsername(String username);
}
