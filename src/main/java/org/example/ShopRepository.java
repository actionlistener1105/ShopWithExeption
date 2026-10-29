package org.example;

public class ShopRepository {

    private Product[] products = new Product[0];

    private Product[] addToArray(Product[] current, Product product){
        Product[] tmp = new Product[current.length + 1];
        for (int i = 0; i < current.length; i++) {
            tmp[i] = current[i];
        }
        tmp[tmp.length - 1] = product;
        return tmp;
    }

    public void add(Product product){
        for (Product existing : products) {
            if (existing.getId() == product.getId()) {
                throw new AlreadyExistsException("Продукт с id: " + product.getId() + " уже существует");
            }
        }
        products = addToArray(products, product);
    }

    public void remote(int id){
        Product[] tmp = new Product[products.length - 1];
        int copyToIndex = 0;
        for (Product product : products){
            if (product.getId() != id){
                tmp[copyToIndex] = product;
                copyToIndex++;
            }
        }
        products = tmp;
    }

    public Product findById(int id){
        for (Product product : products){
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public void removeById(int id){
        Product product = findById(id);
        if(product == null){
            throw new NotFoundException("Element with id: " + id + " not found");
        }
        else {
            remote(id);
        }
    }

    public Product[] getProducts(){
        return products;
    }

    public int sizeProducts(){
        return products.length;
    }
}
