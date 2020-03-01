package service;

import com.ra.course.aws.online.shopping.entity.product.Product;

import java.util.List;

public interface Search {
    List<Product> searchProductByName(String name);
    List<Product> searchProductByCategory(String category);
}