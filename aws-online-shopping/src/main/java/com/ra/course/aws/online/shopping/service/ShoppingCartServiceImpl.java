package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.payment.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.exception.ElementNotFoundException;
import com.ra.course.aws.online.shopping.exception.ObjectRequireNotBeNullException;

import java.util.List;


public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final transient ShoppingCartDao shoppingCartDao;
    private static final int MIN_QUANTITY = 1;


    public ShoppingCartServiceImpl(final ShoppingCartDao shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }


    @Override
    public Long addProductToCart(final Product product) {
        if(product == null){
            throw new ObjectRequireNotBeNullException("given product must not be Null!");
        }
        final Item itemToSave = new Item(product.getProductID(), MIN_QUANTITY, product.getPrice());
        return shoppingCartDao.addItemToCart(itemToSave);
    }

    @Override
    public void removeProductFromCart(final Product product) {
        if(product == null){
            throw new ObjectRequireNotBeNullException("given productToRemove must not be Null!");
        }
        try{
            final Item itemFromDB = shoppingCartDao.getItemFromCart(product.getProductID());
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

    @Override
    public boolean checkoutItemInCart(final Item item) {
        if(item == null){
            throw new ObjectRequireNotBeNullException("given item must not be Null!");
        }

        try{
            final Item itemFromDB = shoppingCartDao.getItemFromCart(item.getProductID());
            return item.equals(itemFromDB);
        } catch (IllegalArgumentException e){
            throw new ElementNotFoundException(e.getMessage(), e);

        }
    }

    @Override
    public PaymentStatus buyItems() {
        final List<Item> allItemsFromDB =  shoppingCartDao.getAllItems();
        return shoppingCartDao.makePayment(allItemsFromDB);
    }
}