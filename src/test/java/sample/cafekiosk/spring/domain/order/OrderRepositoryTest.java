package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;


@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("입력받은 시간과 주문상태에 맞는 주문을 읽어온다.")
    @Test
    void findOrdersBy() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));
        productRepository.saveAll(products);

        LocalDateTime registeredDateTime = LocalDateTime.now();
        Order order = Order.create(products, registeredDateTime);
        Order order2 = Order.create(products, registeredDateTime);
        orderRepository.saveAll(List.of(order, order2));

        // when
        LocalDateTime startDateTime = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(23).withMinute(59);
        OrderStatus orderStatus = OrderStatus.INIT;
        List<Order> orderList = orderRepository.findOrdersBy(startDateTime, endDateTime, orderStatus);

        //then
        assertThat(orderList).hasSize(2)
                .extracting("orderStatus", "registeredDateTime")
                .containsExactlyInAnyOrder(tuple(orderStatus, registeredDateTime),
                        tuple(orderStatus, registeredDateTime));
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }


}