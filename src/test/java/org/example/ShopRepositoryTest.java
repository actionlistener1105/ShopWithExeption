package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
