
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;

public class SnakeGame  extends JPanel{
     int  boardWidth;
     int boardHeight;

     SnakeGame(int boardWidth,int boardHeight){
          this.boardWidth=boardWidth;
          this.boardHeight=boardHeight;
            
          setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
          setBackground(Color.BLACK);

     }
}
