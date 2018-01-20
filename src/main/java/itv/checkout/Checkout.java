package itv.checkout;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Checkout {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Checkout.class).run(args);
    }
}