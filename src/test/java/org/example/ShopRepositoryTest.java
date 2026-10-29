package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopRepositoryTest {

    @Test
    void shouldThrowNotFoundExceptionWhenRemovingNonExistingProduct() {
        ShopRepository shop = new ShopRepository();
        assertThrows(NotFoundException.class, () -> {
            shop.removeById(9999);
        });
    }

    @Test
    void shouldRemoveExistingProduct() {
        Product product = new Product(123, "Test Product", 1000);
        ShopRepository shop = new ShopRepository();
        shop.add(product);

        shop.removeById(123);

        assertNull(shop.findById(123), "Продукт с id 123 должен быть удалён");
    }

    @Test
    void shouldAddProductSuccessfully() {
        ShopRepository shop = new ShopRepository();

        int testId = 123;
        String testTitle = "Laptop";
        int testPrice = 999;

        Product product = new Product(testId, testTitle, testPrice);
        shop.add(product);

        assertEquals(1, shop.sizeProducts(), "После добавления должен быть 1 элемент");

        Product[] allProducts = shop.getProducts();
        assertNotNull(allProducts[0], "Первый элемент массива не должен быть null");
        assertEquals(testId, allProducts[0].getId(), "ID добавленного продукта должен совпадать");

        Product found = shop.findById(testId);
        assertNotNull(found, "Продукт должен быть найден по ID");
        assertEquals(testTitle, found.getTitle());
        assertEquals(testPrice, found.getPrice());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenDuplicateId() {
        ShopRepository shop = new ShopRepository();

        int duplicateId = 101;
        Product firstProduct = new Product(duplicateId, "Mouse", 29);
        Product duplicateProduct = new Product(duplicateId, "Another Mouse", 39);

        shop.add(firstProduct);
        
        assertThrows(AlreadyExistsException.class, () -> {
            shop.add(duplicateProduct);
        });
    }
}
