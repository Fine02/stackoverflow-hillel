package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final transient ShoppingCartDao shoppingCartDao;
    private final transient ProductDao productDao;
    private static final int MIN_QUANTITY = 1;

    @Autowired
    public ShoppingCartServiceImpl(final ShoppingCartDao shoppingCartDao,final ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.productDao = productDao;
    }


    @Override
    public Long addProductToCart(final Product product, final Member member) {
        if(product != null && member != null) {
            final Item itemInCart = shoppingCartDao.findItem(product.getId());
            if(itemInCart == null){
                final Item itemToSave = new Item(MIN_QUANTITY, product.getPrice(), member.getCart().getId(), product.getId());
                return shoppingCartDao.addItemToCart(itemToSave);
            }else{
                itemInCart.setQuantity(itemInCart.getQuantity()+MIN_QUANTITY);
                itemInCart.setPrice(itemInCart.getPrice()+product.getPrice());
                final boolean updated = shoppingCartDao.updateItemInCart(itemInCart);
                return updated?itemInCart.getItemId():0L;
            }
        } else {
            return 0L;
        }
    }

    @Override
    public void removeItemFromCart(final Item item) {
        if(item != null){
            final Item itemFromDB = shoppingCartDao.findItem(item.getProductId());
            final Product product = productDao.findById(item.getProductId());
            Objects.requireNonNull(itemFromDB);
            Objects.requireNonNull(product);
            if(itemFromDB.getQuantity() > MIN_QUANTITY){
                itemFromDB.setQuantity(itemFromDB.getQuantity() - MIN_QUANTITY);
                itemFromDB.setPrice(itemFromDB.getPrice() - product.getPrice());
                shoppingCartDao.updateItemInCart(itemFromDB);
            } else {
                shoppingCartDao.removeItemFromCart(itemFromDB);
            }
        }
    }

    @Override
    public boolean checkoutItemInCart(final Long itemId) {
        final Item itemFromDB = shoppingCartDao.findItem(itemId);
        return itemFromDB != null;

    }

    @Override
    public Item findItemById(final Long productId) {
        return shoppingCartDao.findItem(productId);
    }

}