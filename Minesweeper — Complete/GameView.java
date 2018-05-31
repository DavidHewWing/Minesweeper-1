import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 *
 */

public class GameView extends JFrame {

    private JLabel nbreOfStepsLabel;
    private GameModel gameModel;
    private DotButton[][] board;
    private JLabel nbreOfMinesLabel;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {

        this.gameModel = gameModel;

        //get buttons
        this.board = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];

        setTitle("MineSweeper it ——— the ITI 1121 version");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel totalWindow = new JPanel();
        totalWindow.setLayout(new GridLayout(gameModel.getWidth(), gameModel.getHeigth()));

        //create dotButton objects

        for (int i = 0; i<gameModel.getWidth(); i++){

            for (int j = 0; j<gameModel.getHeigth(); j++){

                board[i][j] = new DotButton(i,j,11);
                board[i][j].addActionListener(gameController);
                board[i][j].addMouseListener(gameController);
                totalWindow.add(board[i][j]);

            }
        }

        totalWindow.setPreferredSize(new Dimension(400,600));

        JButton reset;
        reset = new JButton("Reset");
        reset.addActionListener(gameController);
        add(reset);

        JButton quit;
        quit = new JButton("Quit");
        quit.addActionListener(gameController);
        add(quit);

        nbreOfStepsLabel = new JLabel("Number of Steps: " + Integer.toString(gameModel.getNumberOfSteps()),javax.swing.SwingConstants.CENTER);
        add(nbreOfStepsLabel);

        nbreOfMinesLabel = new JLabel("Number of Mines: " + Integer.toString(gameModel.getNumberOfMines() - gameModel.getNumberOfFlags()),javax.swing.SwingConstants.CENTER);
        add(nbreOfMinesLabel);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.add(nbreOfStepsLabel);
        buttonPanel.add(nbreOfMinesLabel);
        buttonPanel.add(quit);
        buttonPanel.add(reset);
        

        this.add(totalWindow, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);




        setResizable(false);
        repaint();
        pack(); 
        setLocationRelativeTo(null);
        setVisible(true);
        


    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){

        nbreOfStepsLabel.setText("Number of Steps: " + Integer.toString(gameModel.getNumberOfSteps()));
        nbreOfMinesLabel.setText("Number of Mines: " + Integer.toString(gameModel.getNumberOfMines() - gameModel.getNumberOfFlags()));

        for (int i = 0; i<gameModel.getWidth(); i++){

            for (int j = 0; j<gameModel.getHeigth(); j++){

                int number = getIcon(i, j);
                board[i][j].setIconNumber(number);


            }
        }


        repaint();

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){

        if (!(gameModel.isCovered(i,j))){

                if (gameModel.hasBeenClicked(i, j) && gameModel.isMined(i,j)){

                    return 10;
                } 

                if (gameModel.isMined(i,j)){


                    return 9;
                }

                if (gameModel.isBlank(i, j)){

                    return 0;
                }

                if (gameModel.getNeighbooringMines(i, j) == 1){

                    return 1; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 2){


                    return 2; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 3){

                    return 3; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 4){

                    return 4; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 5){

                    return 5; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 6){

                    return 6; 


                }


                if (gameModel.getNeighbooringMines(i, j) == 7){

                    return 7; 


                }



                if (gameModel.getNeighbooringMines(i, j) == 8){

                    return 8; 


                } 
      
            }

        if (gameModel.hasBeenFlagged(i, j)){

            return 12;

        }

        if ((!gameModel.hasBeenClicked(i,j))){


            return 11;
        }


        return 11; 



    }


}
