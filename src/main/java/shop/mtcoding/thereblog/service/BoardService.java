package shop.mtcoding.thereblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.thereblog.core.exception.csr.ExceptionApi400;
import shop.mtcoding.thereblog.core.exception.ssr.Exception400;
import shop.mtcoding.thereblog.core.util.MyParseUtil;
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

            // 2. 섬네일 만들기
            String thumbnail = MyParseUtil.getThumbnail(saveInDTO.getContent());

            // 3. 게시글 쓰기
            boardRepository.save(saveInDTO.toEntity(userPS, thumbnail));
        }catch (Exception e){
            throw new RuntimeException("글쓰기 실패 : "+e.getMessage());
        }
    }

    /*
        isEmpty() 메소드는 문자열의 길이가 0인지 검사합니다.
        isBlank() 메소드는 문자열이 비어있거나 공백 문자열(whitespace-only string)인지 검사합니다.
    */
    @Transactional(readOnly = true) // 변경감지 하지마, 고립성(REPEATABLE READ)
    public Page<Board> 글목록보기(int page, String keyword) { // CSR은 DTO로 변경해서 돌려줘야 함.
        if(keyword.isBlank()){
            return boardQueryRepository.findAll(page);
        }else{
            Page<Board> boardPGPS = boardQueryRepository.findAllByKeyword(page, keyword);
            return boardPGPS;
        }
    } // openinview = false (리턴되고 나면 PS를 빼도됨)

    public Board 게시글상세보기(Long id) {
        Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                ()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
        );
        // 1. Lazy Loading 하는 것 보다 join fetch 하는 것이 좋다.
        // 2. 왜 Lazy를 쓰냐면, 쓸데 없는 조인 쿼리를 줄이기 위해서이다.
        // 3. 사실 @ManyToOne은 Eager 전략을 쓰는 것이 좋다.
        // boardPS.getUser().getUsername();
        return boardPS;
    }
}
