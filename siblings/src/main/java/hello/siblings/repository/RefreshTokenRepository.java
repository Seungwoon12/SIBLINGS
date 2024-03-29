package hello.siblings.repository;

import hello.siblings.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenValue(String tokenValue);
    boolean existsByMemberName(String memberName);
    void deleteByMemberName(String memberName);

}
