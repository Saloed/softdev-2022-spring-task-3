package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Board;
import kanban.Repository.BoardRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepo boardRepo;
    @Autowired
    private UserRepo userRepo;
    public Board createBoard(BoardEntity board, Long userId){
        UserEntity user = userRepo.findById(userId).get();
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        board.setUsers(users);
        return Board.toModel(boardRepo.save(board));
    }

    public Board changeBoard(List<UserEntity> users, Long id){
        BoardEntity board = boardRepo.findById(id).get();
        board.setUsers(users);
        return Board.toModel(boardRepo.save(board));
    }
}
