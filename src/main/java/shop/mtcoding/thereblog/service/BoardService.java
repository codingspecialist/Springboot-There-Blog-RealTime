package shop.mtcoding.thereblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.thereblog.dto.board.BoardRequest;
import shop.mtcoding.thereblog.model.board.Board;
import shop.mtcoding.thereblog.model.board.BoardQueryRepository;
import shop.mtcoding.thereblog.model.board.BoardRepository;
import shop.mtcoding.thereblog.model.user.User;
import shop.mtcoding.thereblog.model.user.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveInDTO saveInDTO, Long userId){
        try {
            // 1. 유저 존재 확인
            User userPS = userRepository.findById(userId).orElseThrow(
                    ()-> new RuntimeException("유저를 찾을 수 없습니다")
            );

            // 2. 게시글 쓰기
            boardRepository.save(saveInDTO.toEntity(userPS));
        }catch (Exception e){
            throw new RuntimeException("글쓰기 실패 : "+e.getMessage());
        }
    }

    @Transactional(readOnly = true) // 변경감지 하지마, 고립성(REPEATABLE READ)
    public Page<Board> 글목록보기(int page) { // CSR은 DTO로 변경해서 돌려줘야 함.
        // 1. 모든 전략은 Lazy : 이유는 필요할때만 가져오려고
        // 2. 필요할때는 직접 fetch join을 사용해라
        return boardQueryRepository.findAll(page);
    }
}
