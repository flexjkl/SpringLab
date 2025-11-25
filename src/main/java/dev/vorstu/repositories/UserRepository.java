package dev.vorstu.repositories;

import dev.vorstu.entity.User;
import dev.vorstu.projections.UserProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u.id as id, s.id as student_id, " +
            "p.password as password, u.username as username, " +
            "u.role as role, u.enable as enable " +
            "from User u " +
            "left join u.student s " +
            "left join u.password p " +
            "where u.username = :username")
    UserProjection findByUsername(@Param("username") String username);

}
