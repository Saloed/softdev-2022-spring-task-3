package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.ListEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Board;
import kanban.Model.Column;
import kanban.Model.User;
import kanban.Repository.BoardRepo;
import kanban.Repository.ListRepo;
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
    @Autowired
    private ListRepo listRepo;

    public Board create(BoardEntity board, Long creatorID) {
        List<UserEntity> users = new ArrayList<>();
        users.add(userRepo.findById(creatorID).get());
        board.setUsers(users);
        BoardEntity entity = boardRepo.save(board);
        return new Board(entity.getId(),entity.getTitle());
    }

    public Board getOne(Long id) {
        BoardEntity board = boardRepo.findById(id).get();
        List<User> users = new ArrayList<>();
        for(int i = 0; i < board.getUsers().toArray().length; i++){
            UserEntity entity = board.getUsers().get(i);
            users.add(new User(entity.getId(),entity.getUsername()));
        }
        List<Column> columns = new ArrayList<>();
        for(int i = 0; i < board.getColumns().toArray().length; i++){
            ListEntity entity = board.getColumns().get(i);
            columns.add(new Column(entity.getId(), entity.getTitle()));
        }
        return new Board(board.getId(), board.getTitle(), users, columns);
    }

    public BoardEntity addUser(Long userID, Long id){
        UserEntity userEntity = userRepo.findById(userID).get();
        BoardEntity board = boardRepo.findById(id).get();
        List<UserEntity> users = board.getUsers();
        if(users == null) users = new ArrayList<>();
        users.add(userEntity);
        board.setUsers(users);
        return boardRepo.save(board);
    }

    public BoardEntity addColumn(Long listID, Long id){
        ListEntity listEntity = listRepo.findById(listID).get();
        BoardEntity board = boardRepo.findById(id).get();
        List<ListEntity> lists = board.getColumns();
        if(lists.toArray().length == 0)
            lists = new ArrayList<>();
        lists.add(listEntity);
        board.setColumns(lists);
        return boardRepo.save(board);
    }

    public Long delete(Long id){
        boardRepo.findById(id);
        return id;
    }
}
