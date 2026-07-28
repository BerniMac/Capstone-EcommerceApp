/* CustomerService.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package service;

import domain.Customer;
import java.util.List;

public interface CustomerService extends IService<Customer, String> {
    List<Customer> findAll();
}
