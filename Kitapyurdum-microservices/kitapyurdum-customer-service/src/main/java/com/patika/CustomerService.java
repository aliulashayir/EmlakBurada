package com.patika;

import com.patika.config.RabbitMQConfig;
import com.patika.dto.request.CustomerSaveRequest;
import com.patika.dto.request.CustomerUpdateRequest;
import com.patika.dto.response.CustomerResponse;
import com.patika.dto.response.OrderResponse;
import com.patika.dto.response.ProductResponse;
import com.patika.exception.ExceptionMessages;
import com.patika.exception.KitapYurdumException;
import com.patika.model.Customer;
import com.patika.model.enums.AccountType;
import com.patika.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void save(CustomerSaveRequest request) {
        Customer customer = new Customer(
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPassword()
        );
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddresses(request.getAddresses());
        customerRepository.save(customer);
        log.info("Customer saved: {}", customer);

        // RabbitMQ kuyruğuna mesaj gönderimi
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, customer.getEmail());
    }

    public List<Customer> getCustomerList() {
        log.info("customer listed.");
        return customerRepository.getCustomerList();
    }

    public void updateCustomerInfo(CustomerUpdateRequest request) {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(request.getEmail());

        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            customer.setEmail(request.getNewEmail());
            customer.setAddresses(request.getAddresses());
            customer.setAccountType(request.getAccountType());
            customerRepository.save(customer);
        } else {
            log.error("Customer not found with email: {}", request.getEmail());
            throw new RuntimeException("Customer not found");
        }
    }

    public void changeAccountType(String email, AccountType accountType) {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            customer.setAccountType(accountType);
            customerRepository.save(customer);
        }
    }

    public void changeAccountTypeByCredit(String email) {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);
        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            int credit = customer.getCredit();

            if (credit >= 4000) {
                customer.setAccountType(AccountType.PLATINUM);
            } else if (credit >= 2000) {
                customer.setAccountType(AccountType.GOLD);
            } else if (credit >= 1000) {
                customer.setAccountType(AccountType.SILVER);
            } else {
                customer.setAccountType(AccountType.STANDARD);
            }
            customerRepository.save(customer);
            log.info("Account type updated by credit for customer: {}", customer);
        } else {
            log.error("Customer not found with email: {}", email);
            throw new RuntimeException("Customer not found with email: " + email);
        }
    }

    @Cacheable(value = "customers", key = "#id")
    public Customer getById(Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        if (foundCustomer.isEmpty()) {
            log.error(ExceptionMessages.CUSTOMER_NOT_FOUND);
            throw new KitapYurdumException(ExceptionMessages.CUSTOMER_NOT_FOUND);
        }

        return foundCustomer.get();
    }

    public Customer getByEmail(String email) {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

        if (!foundCustomer.get().getIsActive()) {
            log.error(ExceptionMessages.CUSTOMER_NOT_ACTIVE);
            throw new KitapYurdumException(ExceptionMessages.CUSTOMER_NOT_ACTIVE);
        }
        log.info("customer found. {}", email);
        return foundCustomer.get();
    }

    public long countAllCustomers() {
        return customerRepository.getCustomerList().size();
    }

    public List<ProductResponse> findProductsByCustomerName(String name) {
        return customerRepository.getCustomerList().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .flatMap(customer -> getOrderListFromOrderService(customer.getId()).stream())
                .flatMap(order -> order.getProductNames().stream())
                .map(this::getProductResponseByName)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalAmountByCustomerNameAndAge(String name, int minAge, int maxAge) {
        return customerRepository.getCustomerList().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .filter(customer -> customer.getAge() > minAge && customer.getAge() < maxAge)
                .flatMap(customer -> getOrderListFromOrderService(customer.getId()).stream())
                .map(order -> order.getProductNames().stream()
                        .map(this::getProductResponseByName)
                        .map(ProductResponse::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderResponse> getOrderListFromOrderService(Long customerId) {
        String orderServiceUrl = "http://kitapyurdum-order-service/api/v1/orders/customer/" + customerId;
        return restTemplate.getForObject(orderServiceUrl, List.class);
    }

    private ProductResponse getProductResponseByName(String productName) {
        String productServiceUrl = "http://kitapyurdum-product-service/api/v1/products/name/" + productName;
        return restTemplate.getForObject(productServiceUrl, ProductResponse.class);
    }
}
