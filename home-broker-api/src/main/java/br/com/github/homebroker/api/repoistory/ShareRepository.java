package br.com.github.homebroker.api.repoistory;

import br.com.github.homebroker.api.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
}
