/* CustomerController.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package controller;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public Customer create(@RequestBody Customer entity) {
        return customerService.create(entity);
    }

    @GetMapping("/read/{id}")
    public Customer read(@PathVariable String id) {
        return customerService.read(id);
    }

    @PostMapping("/update")
    public Customer update(@RequestBody Customer entity) {
        return customerService.update(entity);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        return customerService.delete(id);
    }

    @GetMapping("/getall")
    public List<Customer> getAll() {
        return customerService.findAll();
    }
}
