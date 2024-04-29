
//Snake Game in java Swing 



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
class SnakeGame  extends JPanel implements ActionListener ,KeyListener {
    private class  Tile {
        int x;
        int y;
    
        Tile(int x, int y){
            this.x=x;
            this.y=y;  
        }    
      }

    int  boardWidth;
    int boardHeight;
    int tileSize=25;

    //Snake
     Tile snakeHead;
     ArrayList<Tile>snakeBody;

     //Food
     Tile food;
   
     Random random;
     //gamelogic
     Timer gameLoop;
      int velocityX;
      int velocityY;

       boolean gameOver;

    SnakeGame(int boardWidth,int boardHeight){
         this.boardWidth=boardWidth;
         this.boardHeight=boardHeight;
           
         setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
         setBackground(Color.BLACK);
          snakeHead =new Tile(5,5);
          addKeyListener(this);
          setFocusable(true);

          food=new Tile(10,10);
          snakeBody=new ArrayList<Tile>();
         
          random=new Random();
          placeFood( );

          gameLoop =new Timer(100,this);
          gameLoop.start();

          velocityX=0;
          velocityY=0;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
   
     public void draw(Graphics g){
         //Grid
        //  for(int i=0; i<boardWidth/tileSize; i++){
        //     //(X1,Y1,X2,Y2)
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
        //  }
          
          //food for snake
          g.setColor(Color.red);
        //  g.fillRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize,true);

        //Snake head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x*tileSize ,snakeHead.y*tileSize ,tileSize,tileSize);
        g.fill3DRect(snakeHead.x*tileSize ,snakeHead.y*tileSize ,tileSize,tileSize,true);

        //snake body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart =snakeBody.get(i);
            //g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize,tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize,tileSize, true);
        }

      //Score
      g.setFont(new Font("Arial",Font.PLAIN,16));
      if(gameOver){
        g.setColor(Color.red);
        g.drawString("Game Over : "+ String.valueOf(snakeBody.size()),tileSize-16, tileSize);
      }
      else{
        g.drawString("Score : " + String.valueOf(snakeBody.size()),tileSize-16,tileSize);
      }
     }

     public void placeFood(){
        food.x=random.nextInt(boardWidth/tileSize); // 600/25=24
        food.y=random.nextInt(boardHeight/tileSize);
     }
   
     public boolean  collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x &&  tile1.y==tile2.y;
     } 

     public void move(){
          //eat food
          if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
          }

        //Snake body
        for(int i=snakeBody.size()-1; i>=0; i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }

        //snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;

       //snake over conditions
       for(int i=0; i<snakeBody.size(); i++){
        Tile snakePart=snakeBody.get(i);
        //collide with the snake head
        if(collision(snakeHead, snakePart)){
            gameOver=true;
        }
       }
        
       if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize || snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
        gameOver = true;
    }  
     }

     public void actionPerformed(ActionEvent e){
        move();
        repaint();
       if(gameOver){
        gameLoop.stop();
       }

     } 

       //KeyListener

       public void keyPressed(KeyEvent e){
         if(e.getKeyCode()==KeyEvent.VK_UP && velocityY !=1){
            velocityX=0;
            velocityY=-1;

         }
         else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY != -1){
            velocityX=0;
            velocityY=1;
         }
         else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX !=1){
            velocityX=-1;
            velocityY=0;

         }
         else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX=1;
            velocityY=0;
         }

       }
       public void keyTyped(KeyEvent e){}

       public void keyReleased(KeyEvent e){}
  
}


public class App  {
    
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;
    
        
        JFrame frame = new JFrame("SnakeGame");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
       // frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.setBackground(Color.black);   Remove or comment line because blackground color not works.  
       SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
      //App snakeGame =new App(boardWidth,boardHeight);
      frame.add(snakeGame);
       frame.pack();
        snakeGame.requestFocus();
           }
}
