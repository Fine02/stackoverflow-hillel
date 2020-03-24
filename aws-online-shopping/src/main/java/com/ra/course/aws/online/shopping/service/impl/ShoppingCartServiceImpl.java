package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.exceptions.ElementNotFoundException;
import com.ra.course.aws.online.shopping.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final transient ShoppingCartDao shoppingCartDao;
    private static final int MIN_QUANTITY = 1;

    public ShoppingCartServiceImpl(final ShoppingCartDao shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }


    @Override
    public Long addProductToCart(final Product product) {
        Objects.requireNonNull(product);
        final Item itemToSave = new Item(product.getId(), MIN_QUANTITY, product.getPrice());
        return shoppingCartDao.addItemToCart(itemToSave);
    }

    @Override
    public void removeProductFromCart(final Product product) {
        if(product != null){
            try{
                final Item itemFromDB = shoppingCartDao.getItemFromCart(product.getId());
                if(itemFromDB.getQuantity() > MIN_QUANTITY){
                    itemFromDB.setQuantity(itemFromDB.getQuantity() - MIN_QUANTITY);
                    itemFromDB.setPrice(itemFromDB.getPrice() - product.getPrice());
                    shoppingCartDao.updateItemInCart(itemFromDB);
                }
                shoppingCartDao.removeItemFromCart(itemFromDB);
            }catch (IllegalArgumentException e){
                throw new ElementNotFoundException(e.getMessage() + "\n element not found!" , e);
            }
        }
    }

    @Override
    public boolean checkoutItemInCart(final Item item) {
       Objects.requireNonNull(item);
          final Item itemFromDB = shoppingCartDao.getItemFromCart(item.getProductID());
            return item.equals(itemFromDB);

    }

    @Override
    public PaymentStatus buyItems() {
        final List<Item> allItemsFromDB =  shoppingCartDao.getAllItems();
        return shoppingCartDao.makePayment(allItemsFromDB);
    }
}