/* ReviewRepository.java
   ReviewRepository interface
   Author: isheanesu chowuraya (223182192)
   Date: 25 June 2025
*/
package repository;

import domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
