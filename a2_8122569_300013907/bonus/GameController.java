import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


// Author: Kyle Pinkerton, David Hew-Wing
// Student number: 8122569, 300013907
// Course: ITI 1121-B0
// Assignment: 2
// Question: 


public class GameController implements ActionListener, MouseListener{

    // ADD YOUR INSTANCE VARIABLES HERE

    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param heigth
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int heigth, int numberOfMines) {

        this.gameModel = new GameModel(width, heigth, numberOfMines);
        this.gameView = new GameView(this.gameModel, this);

    // ADD YOU CODE HERE

    }

    /**
     * Callback used when the user clicks the mouse
     *
     * @param e
     *            the MouseEvent
     */

    @Override
    public void mouseReleased(MouseEvent e) {

      if(SwingUtilities.isRightMouseButton(e) || e.isControlDown()){
        if (e.getSource() instanceof DotButton){

          //cast from object type to DotButton type (getSource() returns type object)

          DotButton src = (DotButton) e.getSource(); 

          //we can now use methods of DotButton on the object src

          int row = src.getColumn();
          int column = src.getRow();

          DotInfo dot = gameModel.get(row, column);

          if (dot.hasBeenFlagged()){
            gameModel.undoFlagged(row, column);
            gameView.update();

          }

          else if (!(dot.hasBeenFlagged())){
            gameModel.setFlagged(row, column);
            gameView.update();
              
          }

      }


    }

    else if (e.getSource() instanceof DotButton){

            //cast from object type to DotButton type (getSource() returns type object)

            DotButton src = (DotButton) e.getSource(); 

            //we can now use methods of DotButton on the object src

            int row = src.getColumn();
            int column = src.getRow();
            play(row, column);
        }
  }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    } 

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
    // ADD YOU CODE HERE

        if(e.getActionCommand().equals("Reset")) {
            reset();
        }
        if(e.getActionCommand().equals("Quit")) {
            System.exit(0);
        }

        if(e.getActionCommand().equals("PlayAgain")){
                reset();
            }
            

    }

    /**
     * resets the game
     */
    private void reset(){

    // ADD YOU CODE HERE

        gameModel.reset(); 
        gameView.update();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

        //USE JOPTIONPANE

        String[] options = {"Play Again", "Quit"};

        DotInfo dot = gameModel.get(width,heigth);
       
        if(!(dot.hasBeenClicked()) && dot.isCovered()){
            gameModel.step();
            gameView.update();
            //if you lose (click on a mine)           
            if(dot.isMined()){
                dot.uncover();
                dot.click();
                gameView.update();

                gameModel.uncoverAll();
                gameView.update();

                //JOptionPane

                int choice = JOptionPane.showOptionDialog(null,//parent container of JOptionPane
                "Aouch,you lost in " + gameModel.getNumberOfSteps() + " steps! Would you like to play again?",
                "Boom!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,//do not use a custom Icon
                options,//the titles of buttons
                options[0]);//default button title
                       

                // interpret the user's choice
                if (choice == JOptionPane.YES_OPTION){
                
                  reset();

                }

                if (choice == JOptionPane.NO_OPTION){
                
                  System.exit(0);
                  
                }
            
            }

            else if(gameModel.isFinished()){
                
                //JOptionPane
                int choice = JOptionPane.showOptionDialog(null,//parent container of JOptionPane
                "Congratulations, you won in " + gameModel.getNumberOfSteps() + " steps! Would you like to play again?",
                "Won",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,//do not use a custom Icon
                options,//the titles of buttons
                options[0]);//default button title
                       

                // interpret the user's choice
                if (choice == JOptionPane.YES_OPTION){
                
                  reset();

                }

                if (choice == JOptionPane.NO_OPTION){
                
                  System.exit(0);
                  
                }


                gameModel.uncoverAll();
                gameView.update();
                //if you still playing.
            }

            else{
                dot.uncover();
                dot.click();
                if(gameModel.getNeighbooringMines(dot.getX(), dot.getY()) == 0){
                    clearZone(dot);
                }
                gameView.update();
            }
        }

        }

    // ADD YOU CODE HERE

 private void clearZone(DotInfo initialDot) {

        GenericArrayStack<DotInfo> stack = new GenericArrayStack(gameModel.getHeigth() * gameModel.getWidth());
        stack.push(initialDot);

        while(!(stack.isEmpty())){
            DotInfo d = stack.pop();
            int countNeighbouringDots = 0;
            //use an array for all the neighbourdots with lenght of the maximum possible neighbours
            DotInfo[] neighbours = {null,null,null,null,null,null,null,null,null};

            int row = d.getX();
            int col = d.getY();
            int length = gameModel.getWidth();
            int height = gameModel.getHeigth();
            
            //check where the row and cols end and starts to check for arrayoutofbounds
            int rowStart  = Math.max(row - 1, 0);
            int rowFinish = Math.min(row + 1, length - 1 );
            int colStart  = Math.max(col - 1, 0);
            int colFinish = Math.min(col + 1, height - 1 );
            int count = 0;
            //add every neighbour to the var.
            for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {  
                for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {
                     countNeighbouringDots++; 
                     neighbours[count] = gameModel.get(curRow,curCol);
                     count++;
                }
            }
            for(int i = 0; i < countNeighbouringDots; i++){
                if(neighbours[i].isCovered()){
                    neighbours[i].uncover();
                    neighbours[i].click();
                    if(gameModel.getNeighbooringMines(neighbours[i].getX(),neighbours[i].getY()) == 0 && !(neighbours[i].isMined()) && gameModel.getNeighbooringMines(neighbours[i].getX(),neighbours[i].getY()) < 1){
                        stack.push(neighbours[i]);
                    }
                }
            }
        }
    }

}
