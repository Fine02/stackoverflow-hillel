package dao;

import com.ra.course.aws.online.shopping.entity.Item;

public interface ShoppingCartDao {
    void addProductToCartDao(Item item);
    void removeProductFromCartDao(Item item);
}
