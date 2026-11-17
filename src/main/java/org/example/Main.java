package org.example;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        System.out.println("1. Додавання нового товару (Create):");
        Product newProduct = new Product(10, "Notebook", 15000.00, 15, "Electronics");
        // productDAO.addProduct(newProduct);

        System.out.println("\n2. Виведення всіх товарів (Read):");
        productDAO.getAllProducts().forEach(System.out::println);

        System.out.println("\n3. Оновлення ціни (Update):");
        // productDAO.updateProductPrice(10, 14500.00);

        productDAO.getDeliveriesWithProducts();
        productDAO.getProductsFilteredByPrice(28.0);
        productDAO.getAggregateProductStats();
    }
}