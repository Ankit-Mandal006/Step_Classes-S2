import java.util.Scanner;

public class OnlineShoppingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize Products
        Product[] products = new Product[10];
        products[0] = new Product("P001", "Laptop", 80000, "Electronics", 10);
        products[1] = new Product("P002", "Smartphone", 45000, "Electronics", 15);
        products[2] = new Product("P003", "Shoes", 2500, "Fashion", 20);
        products[3] = new Product("P004", "T-Shirt", 1200, "Fashion", 30);
        products[4] = new Product("P005", "Fridge", 60000, "Home Appliances", 5);
        products[5] = new Product("P006", "Microwave", 9000, "Home Appliances", 7);
        products[6] = new Product("P007", "Book", 499, "Books", 50);
        products[7] = new Product("P008", "Notebook", 99, "Stationery", 100);
        products[8] = new Product("P009", "Headphones", 2999, "Electronics", 25);
        products[9] = new Product("P010", "Blender", 1800, "Home Appliances", 12);

        System.out.print("Enter your name: ");
        String customerName = sc.nextLine();

        ShoppingCart cart = new ShoppingCart(customerName);

        int choice;
        do {
            System.out.println("\n=== Online Shopping Menu ===");
            System.out.println("1. Browse All Products");
            System.out.println("2. Browse by Category");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    Product.displayAllProducts(products);
                    break;

                case 2:
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    Product.getProductsByCategory(products, category);
                    break;

                case 3:
                    System.out.print("Enter Product ID to add: ");
                    String addId = sc.nextLine();
                    Product productToAdd = Product.findProductById(products, addId);
                    if (productToAdd != null) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        cart.addProduct(productToAdd, qty);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Product ID to remove: ");
                    String removeId = sc.nextLine();
                    cart.removeProduct(removeId);
                    break;

                case 5:
                    cart.displayCart();
                    break;

                case 6:
                    cart.checkout();
                    break;

                case 0:
                    System.out.println("Thank you for shopping!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }
}
class Product {
    private String productId;
    private String productName;
    private double price;
    private String category;
    private int stockQuantity;

    private static int totalProducts = 0;
    private static String[] categories = {"Electronics", "Fashion", "Home Appliances", "Books", "Stationery"};

    public Product(String productId, String productName, double price, String category, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        totalProducts++;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(int quantity) {
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    public static Product findProductById(Product[] products, String productId) {
        for (Product p : products) {
            if (p.getProductId().equalsIgnoreCase(productId)) {
                return p;
            }
        }
        return null;
    }

    public static void getProductsByCategory(Product[] products, String category) {
        boolean found = false;
        System.out.println("\nProducts in category: " + category);
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found in this category.");
        }
    }

    public static void displayAllProducts(Product[] products) {
        System.out.println("\n=== Available Products ===");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    public String toString() {
        return "[" + productId + "] " + productName + " | $" + price + " | Category: " + category + " | Stock: " + stockQuantity;
    }

    public static String[] getCategories() {
        return categories;
    }

    public static int getTotalProducts() {
        return totalProducts;
    }
}
class ShoppingCart {
    private static int cartCounter = 1000;
    private String cartId;
    private String customerName;
    private Product[] products;
    private int[] quantities;
    private int productCount;
    private double cartTotal;

    public ShoppingCart(String customerName) {
        this.cartId = "CART" + (++cartCounter);
        this.customerName = customerName;
        this.products = new Product[20];
        this.quantities = new int[20];
        this.productCount = 0;
        this.cartTotal = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0 || quantity > product.getStockQuantity()) {
            System.out.println("Invalid quantity or insufficient stock.");
            return;
        }

        // Check if product already in cart
        for (int i = 0; i < productCount; i++) {
            if (products[i].getProductId().equalsIgnoreCase(product.getProductId())) {
                quantities[i] += quantity;
                product.reduceStock(quantity);
                System.out.println("Added " + quantity + " more of " + product.getProductName() + " to cart.");
                calculateTotal();
                return;
            }
        }

        // Add new product to cart
        products[productCount] = product;
        quantities[productCount] = quantity;
        productCount++;
        product.reduceStock(quantity);
        System.out.println("Added " + quantity + " of " + product.getProductName() + " to cart.");
        calculateTotal();
    }

    public void removeProduct(String productId) {
        for (int i = 0; i < productCount; i++) {
            if (products[i].getProductId().equalsIgnoreCase(productId)) {
                products[i].increaseStock(quantities[i]); // Return stock
                System.out.println("Removed " + products[i].getProductName() + " from cart.");
                // Shift remaining
                for (int j = i; j < productCount - 1; j++) {
                    products[j] = products[j + 1];
                    quantities[j] = quantities[j + 1];
                }
                products[productCount - 1] = null;
                quantities[productCount - 1] = 0;
                productCount--;
                calculateTotal();
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public void calculateTotal() {
        cartTotal = 0.0;
        for (int i = 0; i < productCount; i++) {
            cartTotal += products[i].getPrice() * quantities[i];
        }
    }

    public void displayCart() {
        if (productCount == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\n=== " + customerName + "'s Cart (" + cartId + ") ===");
        for (int i = 0; i < productCount; i++) {
            System.out.println(products[i].getProductName() + " x" + quantities[i] +
                    " = $" + (products[i].getPrice() * quantities[i]));
        }
        System.out.println("Total: $" + cartTotal);
    }

        public void checkout() {
        if (productCount == 0) {
            System.out.println("Your cart is empty. Add products before checkout.");
            return;
        }

        System.out.println("\n=== Checkout Summary ===");
        displayCart();
        System.out.println("Thank you for your purchase, " + customerName + "!");
        
        // Clear the cart after checkout
        for (int i = 0; i < productCount; i++) {
            products[i] = null;
            quantities[i] = 0;
        }
        productCount = 0;
        cartTotal = 0.0;
    }
}

