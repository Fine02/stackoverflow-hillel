package dao;

import com.ra.course.aws.online.shopping.entity.product.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findProductByNameDao();
    List<Product> findProductByCategoryDao();
    Product updateProduct(Product product);
    void removeProduct(Product product);
}
