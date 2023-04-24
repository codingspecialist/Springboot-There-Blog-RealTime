package shop.mtcoding.thereblog.model.board;

import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public void subquery(){
        // 엄청난 긴 쿼리를 짤때는, 결국 QueryDSL 사용하는게 좋음
        String sql = "select id, title, content, (select count(id) from love) like_count, 1 n1,2 n2, 3 n3 from board where id = 1"; // 30줄
        Query query = em.createNativeQuery(sql);
        JpaResultMapper result = new JpaResultMapper();
        MyDTO myDTO = result.uniqueResult(query, MyDTO.class);
    }

    public static class MyDTO {
        private int id;
        private String title;
        private String content;
        private int likeCount;
        private int n1;
        private int n2;
        private int n3;
    }
}
