package market.marketuz.repository;

import market.marketuz.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("update User u set u.money = u.money + :money where u.id = :id")
    void updateMoney(Integer id, Double money);

    boolean existsByName(String name);


}
