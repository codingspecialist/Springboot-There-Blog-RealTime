package shop.mtcoding.thereblog.model.board;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = "user")
    Page<Board> findAll(Pageable pageable);
}
