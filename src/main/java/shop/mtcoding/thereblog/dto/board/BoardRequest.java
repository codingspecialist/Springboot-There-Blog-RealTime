package shop.mtcoding.thereblog.dto.board;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.thereblog.model.board.Board;
import shop.mtcoding.thereblog.model.user.User;

public class BoardRequest {

    @Getter @Setter
    public static class SaveInDTO {
        private String title;
        private String content;

        public Board toEntity(User user, String thumbnail){
            return Board.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .thumbnail(thumbnail)
                    .build();
        }
    }
}
