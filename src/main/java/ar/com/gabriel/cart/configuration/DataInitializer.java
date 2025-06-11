package ar.com.gabriel.cart.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.domain.model.Discount;
import ar.com.gabriel.cart.domain.model.Product;
import ar.com.gabriel.cart.domain.model.emun.Category;
import ar.com.gabriel.cart.domain.model.emun.UserStatus;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.repository.DiscountRepository;
import ar.com.gabriel.cart.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public DataInitializer(AppUserRepository userRepository, ProductRepository productRepository, DiscountRepository discountRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializando datos...");
        // Initialize Users
        for (int i = 0; i < 100; i++) {
            AppUser user = new AppUser();
            user.setUsername("user" + i);
            user.setStatus(UserStatus.USER_STATUS_ACTIVE);
            user.setPassword("password" + i);
            userRepository.save(user);
        }

        // Initialize Products
        for (int i = 1; i <= 10000; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(100.00 + (i * 10));
            product.setCategory(Category.ELECTRONICS);
            productRepository.save(product);
        }

        // Initialize Discounts
        for (int i = 1; i <= 3; i++) {
            Discount discount = new Discount();
            discount.setCategory(Category.ELECTRONICS);
            discount.setPercentage(10.0 + (i * 5));
            discountRepository.save(discount);
        }

        log.info("Datos inicializados correctamente");
    }

}