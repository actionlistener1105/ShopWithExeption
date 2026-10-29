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

    @Test
    void shouldFindMultipleProductsWhenSearchTermMatchesSeveral() {
        ShopRepository shop = new ShopRepository();

        shop.add(new Product(1, "Laptop Pro", 1000));
        shop.add(new Product(2, "Laptop Air", 900));
        shop.add(new Product(3, "Mouse", 50));

        String searchTerm = "laptop"; // ищем по подстроке, регистр не важен
        Product[] found = shop.search(searchTerm);

        assertEquals(2, found.length, "Должно найтись ровно 2 продукта с 'laptop' в названии");
        assertTrue(found[0].getTitle().toLowerCase().contains(searchTerm));
        assertTrue(found[1].getTitle().toLowerCase().contains(searchTerm));
    }

    @Test
    void shouldFindExactlyOneProductWhenSearchTermMatchesSingle() {
        ShopRepository shop = new ShopRepository();

        shop.add(new Product(1, "Keyboard", 70));
        shop.add(new Product(2, "Headphones", 120));
        shop.add(new Product(3, "Monitor Ultra", 300));

        String searchTerm = "monitor";
        Product[] found = shop.search(searchTerm);

        assertEquals(1, found.length, "Должно найтись ровно 1 продукт с 'monitor' в названии");
        assertEquals("Monitor Ultra", found[0].getTitle());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoProductsMatchSearchTerm() {
        ShopRepository shop = new ShopRepository();

        shop.add(new Product(1, "Laptop", 1000));
        shop.add(new Product(2, "Mouse", 50));
        shop.add(new Product(3, "Keyboard", 70));

        String searchTerm = "tv"; // такого нет
        Product[] found = shop.search(searchTerm);

        assertEquals(0, found.length, "При отсутствии совпадений должен возвращаться пустой массив");
    }

}
