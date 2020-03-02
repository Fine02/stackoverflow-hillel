package service;

import com.ra.course.aws.online.shopping.entity.product.Product;

import java.util.List;

public interface ProductService {
  String  addNewProductToSell();
  void  modifyProduct(Product product);
  List<Product>  findProductByName (String name);
  List<Product> searchProductByCategory(String category);
  String viewProductDetails(Product product);
  void buyProduct(Product product);
  void rateAndMakeReview(Product product);

}
