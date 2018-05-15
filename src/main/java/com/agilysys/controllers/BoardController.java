package com.agilysys.controllers;

/**
 * Created by amruthaa on 5/14/18.
 */

import com.agilysys.models.Board;
import com.agilysys.models.Cell;
import com.agilysys.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BoardController{
    @Autowired
    BoardRepository boardRepository;

    @RequestMapping("/")
    public String index(){
        return "Welcome to Sudoku!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public Iterable<Board> boards()
    {
        return boardRepository.findAll();
    }
    

    @RequestMapping(method = RequestMethod.GET, value = "/boards/{id}")
    public Board getBoard(@PathVariable String id)
    {
        Optional<Board> temp=boardRepository.findById(id);
        return temp.orElse(null);
    }

    @RequestMapping(method=RequestMethod.POST, value="/boards")
    public String buildPuzzle(@RequestBody Board board) {
        if(isValidBoard(board.getData()))
        {
            boardRepository.save(board);
            return board.getId();
        }
        return "Not able to create a board. Invalid values";
    }

    @RequestMapping(method=RequestMethod.PUT, value="/board/{id}")
    public String updateCell(@PathVariable String id, @RequestBody Cell cell) {
        Optional<Board> temp=boardRepository.findById(id);
        if(!temp.isPresent())
        {
            return "Error: Board id not found";
        }
        Board res=temp.get();

        List<List<Integer>> rowList = res.getData();

        if(rowList!=null)
        {
            List<Integer> col=rowList.get(cell.getRow());
            col.set(cell.getCol(),cell.getVal());
        }

        if(isValidBoard(res.getData()))
        {
            boardRepository.save(res);
            return res.getId();
        }
        return "Not able to update the board. Invalid values entered for update";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/board/{id}")
    public String getCell(@PathVariable String id, @RequestBody Cell cell) {
        Optional<Board> temp=boardRepository.findById(id);
        if(!temp.isPresent())
        {
            return "Error: Board id not found";
        }
        Board res=temp.get();

        List<List<Integer>> rowList = res.getData();

        if(rowList!=null)
        {
            List<Integer> col=rowList.get(cell.getRow());
            int temp1=col.get(cell.getCol());
            return "" + temp1;
        }
        return "No value found for given cell[row,col]";
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/boards/{id}")
    public String removeBoard(@PathVariable String id) {
        Optional<Board> temp=boardRepository.findById(id);
        if(!temp.isPresent())
        {
            return "Error: Board id not found";
        }
        Board res=temp.get();
        boardRepository.delete(res);
        return "Board deleted:" + id;
    }


    public boolean isValidBoard(List<List<Integer>> board) {

        if (board.size() != 9 || board.get(0).size() != 9)
        {
            return false;
        }

        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(!isValidBoard(board,i,j))
                {
                    return false;
                }
            }
        }

        return true;

    }


    public boolean isValidBoard(List<List<Integer>> board,int i,int j)
    {

        // check each column
        boolean[] m = new boolean[9];
        for (int k = 0; k < 9; k++) {
            if (board.get(i).get(k) != 0) {
                if (m[board.get(i).get(k) - 1]) {
                    return false;
                }
                m[board.get(i).get(k) - 1] = true;
            }
        }

        //check each row
        m = new boolean[9];
        for (int l = 0; l < 9; l++)
        {
            if (board.get(l).get(j) != 0) {
                if (m[board.get(l).get(j) - 1]) {
                    return false;
                }
                m[board.get(l).get(j) - 1] = true;
            }
        }

        //check 3*3 matrix
        m = new boolean[9];
        int row=i / 3 * 3;
        int col=j % 3 * 3;
        //System.out.println("row:" + row + " ,col:" + col);
        for (int x = row; x < row + 3; x++) {
            for (int y = col; y < col + 3; y++) {
                if (board.get(x).get(y) != 0) {
                    if (m[board.get(x).get(y) - 1]) {
                        return false;
                    }
                    m[board.get(x).get(y) - 1] = true;
                }
            }
        }
        return true;
    }
}