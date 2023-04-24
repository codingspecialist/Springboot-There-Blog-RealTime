package shop.mtcoding.thereblog.model.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private final EntityManager em;
    private final int SIZE = 8;

    public Page<Board> findAll(int page) {
        int startPosition = page*SIZE;

        List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user order by b.id desc")
                .setFirstResult(startPosition) // 시작 번호
                .setMaxResults(SIZE) // 개수
                .getResultList();

        Long totalCount = em.createQuery("select count(b) from Board b", Long.class).getSingleResult();

        return new PageImpl<>(boardListPS, PageRequest.of(page, SIZE), totalCount);
    }
}
