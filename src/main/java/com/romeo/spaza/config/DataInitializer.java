package com.romeo.spaza.config;

import com.romeo.spaza.model.Product;
import com.romeo.spaza.model.Role;
import com.romeo.spaza.model.Sale;
import com.romeo.spaza.model.User;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.repository.SaleRepository;
import com.romeo.spaza.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!production")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, ProductRepository productRepository, SaleRepository saleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User owner = User.builder()
                    .username("owner")
                    .password(passwordEncoder.encode("password123"))
                    .fullName("Romeo Mangena (Owner)")
                    .role(Role.ROLE_OWNER)
                    .build();

            User staff = User.builder()
                    .username("staff")
                    .password(passwordEncoder.encode("password123"))
                    .fullName("Thabo Mokoena (Staff)")
                    .role(Role.ROLE_STAFF)
                    .build();

            userRepository.saveAll(Arrays.asList(owner, staff));
        }

        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                    Product.builder()
                            .name("Albany Superior White Bread 700g")
                            .category("Bakery")
                            .price(new BigDecimal("17.50"))
                            .quantity(15)
                            .reorderLevel(5)
                            .barcode("600100100101")
                            .supplierName("Albany Bakery")
                            .build(),

                    Product.builder()
                            .name("Clover Full Cream Milk 2L")
                            .category("Dairy")
                            .price(new BigDecimal("34.90"))
                            .quantity(3)
                            .reorderLevel(5)
                            .barcode("600100100102")
                            .supplierName("Clover SA")
                            .build(),

                    Product.builder()
                            .name("Iwisa Super Maize Meal 5kg")
                            .category("Staples")
                            .price(new BigDecimal("69.00"))
                            .quantity(8)
                            .reorderLevel(4)
                            .barcode("600100100103")
                            .supplierName("Premier Foods")
                            .build(),

                    Product.builder()
                            .name("Huletts White Sugar 2kg")
                            .category("Staples")
                            .price(new BigDecimal("48.50"))
                            .quantity(2)
                            .reorderLevel(5)
                            .barcode("600100100104")
                            .supplierName("Tongaat Hulett")
                            .build(),

                    Product.builder()
                            .name("Sunfoil Sunflower Cooking Oil 750ml")
                            .category("Groceries")
                            .price(new BigDecimal("32.00"))
                            .quantity(12)
                            .reorderLevel(4)
                            .barcode("600100100105")
                            .supplierName("Willowton Group")
                            .build(),

                    Product.builder()
                            .name("Coca-Cola Original Taste 1.25L")
                            .category("Beverages")
                            .price(new BigDecimal("21.00"))
                            .quantity(1)
                            .reorderLevel(6)
                            .barcode("600100100106")
                            .supplierName("Coca-Cola Beverages SA")
                            .build(),

                    Product.builder()
                            .name("Sunlight Laundry Soap Bar 500g")
                            .category("Household")
                            .price(new BigDecimal("18.50"))
                            .quantity(20)
                            .reorderLevel(5)
                            .barcode("600100100107")
                            .supplierName("Unilever")
                            .build(),

                    Product.builder()
                            .name("Simba Potato Chips Smoked Beef 120g")
                            .category("Snacks")
                            .price(new BigDecimal("16.00"))
                            .quantity(25)
                            .reorderLevel(8)
                            .barcode("600100100108")
                            .supplierName("PepsiCo SA")
                            .build(),

                    Product.builder()
                            .name("Koo Baked Beans in Tomato Sauce 410g")
                            .category("Canned Goods")
                            .price(new BigDecimal("15.50"))
                            .quantity(14)
                            .reorderLevel(5)
                            .barcode("600100100109")
                            .supplierName("Tiger Brands")
                            .build(),

                    Product.builder()
                            .name("Nescafe Ricoffy Instant Coffee 250g")
                            .category("Beverages")
                            .price(new BigDecimal("45.00"))
                            .quantity(4)
                            .reorderLevel(5)
                            .barcode("600100100110")
                            .supplierName("Nestle")
                            .build()
            );

            productRepository.saveAll(products);

            Product bread = productRepository.findAll().stream().filter(p -> p.getName().contains("Bread")).findFirst().orElse(null);
            Product milk = productRepository.findAll().stream().filter(p -> p.getName().contains("Milk")).findFirst().orElse(null);
            Product maize = productRepository.findAll().stream().filter(p -> p.getName().contains("Maize")).findFirst().orElse(null);

            if (bread != null && milk != null && maize != null) {
                Sale sale1 = Sale.builder()
                        .product(bread)
                        .quantity(2)
                        .unitPrice(bread.getPrice())
                        .totalPrice(bread.getPrice().multiply(new BigDecimal("2")))
                        .saleDate(LocalDateTime.now().minusHours(4))
                        .recordedByUsername("staff")
                        .build();

                Sale sale2 = Sale.builder()
                        .product(milk)
                        .quantity(1)
                        .unitPrice(milk.getPrice())
                        .totalPrice(milk.getPrice())
                        .saleDate(LocalDateTime.now().minusHours(2))
                        .recordedByUsername("owner")
                        .build();

                Sale sale3 = Sale.builder()
                        .product(maize)
                        .quantity(1)
                        .unitPrice(maize.getPrice())
                        .totalPrice(maize.getPrice())
                        .saleDate(LocalDateTime.now().minusHours(1))
                        .recordedByUsername("staff")
                        .build();

                saleRepository.saveAll(Arrays.asList(sale1, sale2, sale3));
            }
        }
    }
}
