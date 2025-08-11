package com.springboot.product_shop;

import com.springboot.product_shop.enums.RoleEnum;
import com.springboot.product_shop.models.Category;
import com.springboot.product_shop.models.Product;
import com.springboot.product_shop.models.Role;
import com.springboot.product_shop.models.UserEntity;
import com.springboot.product_shop.repositories.CategoryRepository;
import com.springboot.product_shop.repositories.ProductRepository;
import com.springboot.product_shop.repositories.RoleRepository;
import com.springboot.product_shop.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProductShopApplication implements CommandLineRunner {

	private static final Logger LOG= LoggerFactory.getLogger(ProductShopApplication.class);

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final CategoryRepository categoryRepository;

	private final ProductRepository productRepository;

	@Autowired
    public ProductShopApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ProductShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*Role role1= new Role(null,RoleEnum.ADMIN);
		Role role2= new Role(null,RoleEnum.MANAGER);
		Role role3= new Role(null,RoleEnum.CUSTOMER);
		roleRepository.saveAll(List.of(role1,role2,role3));

		String user1Password="123";
		String user1encodedPassword= passwordEncoder.encode(user1Password);
		System.out.println("Password: " +user1Password+ " Encoded Password: "+user1encodedPassword);
		UserEntity user1= new UserEntity(null,"111","marcos",user1encodedPassword,"marcos soto","ADMIN_m@gmail.com",List.of(role1));
		UserEntity user2= new UserEntity(null,"222","pedro",user1encodedPassword,"pedro diaz","MANAGER_p@gmail.com",List.of(role2));
		UserEntity user3= new UserEntity(null,"333","lina",user1encodedPassword,"lina diaz","CUSTOMER_l@gmail.com",List.of(role3));

		userRepository.saveAll(Arrays.asList(user1,user2,user3));

		//-----
		Category category1= new Category(null,"Tech","description 1...","img_url");
		//TESTING
		Category category2= new Category(null,"Sports","description 1...","img_url");
		categoryRepository.saveAll(Arrays.asList(category1,category2));

		Product product1= new Product(null,"tv z","description 1...","img_url", BigDecimal.valueOf(100),2,category1);
		Product product2= new Product(null,"pc z","description 1...","img_url", BigDecimal.valueOf(50),2,category1);
		//TESTING
		Product product3= new Product(null,"ball","description 1...","img_url", BigDecimal.valueOf(10),2,category2);

		productRepository.saveAll(Arrays.asList(product1,product2,product3));
		*/
	}

}
