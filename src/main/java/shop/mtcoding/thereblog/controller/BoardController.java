package shop.mtcoding.thereblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.thereblog.core.auth.MyUserDetails;
import shop.mtcoding.thereblog.dto.board.BoardRequest;
import shop.mtcoding.thereblog.model.board.Board;
import shop.mtcoding.thereblog.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @PostMapping("/s/board/{id}/delete")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        // 소유자 확인?
        boardService.게시글삭제(id, myUserDetails.getUser().getId());
        return "redirect:/";
    }

    // RestAPI 주소 설계 규칙에서 자원에는 복수를 붙인다. boards 정석!!
    @GetMapping({"/", "/board"})
    public String main(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ){
        Page<Board> boardPG = boardService.글목록보기(page, keyword);
        model.addAttribute("boardPG", boardPG);
        return "board/main";
    }

    @GetMapping("/s/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/s/board/save")
    public String save(BoardRequest.SaveInDTO saveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
        boardService.글쓰기(saveInDTO, myUserDetails.getUser().getId());
        return "redirect:/";
    }

    @GetMapping( "/board/{id}")
    public String detail(@PathVariable Long id, Model model){
        Board board = boardService.게시글상세보기(id);
        model.addAttribute("board", board);

        return "board/detail"; // RequestDispatcher => reuqest 덮어쓰기 기술
    }

}
