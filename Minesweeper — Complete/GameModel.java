import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 */


public class GameModel {

    private Random generator = new Random();
    private int heigthOfGame;
    private DotInfo[][] model;
    private int numberOfMines;
    private int numberOfSteps;
    private int numberUncovered;
    private int widthOfGame;
    private int numberOfFlags;
    

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
    // ADD YOU CODE HERE

        this.widthOfGame = width;
        this.heigthOfGame = heigth;
        this.numberOfMines = numberOfMines;
        this.model = new DotInfo[widthOfGame][heigthOfGame];
        this.numberOfSteps = 0;
        this.numberUncovered = 0;
        this.numberOfFlags = 0;

        for(int i = 0; i < widthOfGame; i++){
            for(int j = 0; j < heigthOfGame; j++){
                DotInfo dot = new DotInfo(i,j);
                model[i][j] = dot;
            }
        }
        //places the mines in a random x and y.
        int count = 0;
        while(count < numberOfMines){
            int x = generator.nextInt(widthOfGame);
            int y = generator.nextInt(heigthOfGame);
            if(model[x][y].isMined() == false){
                model[x][y].setMined();
                count++;
            }
        }

    }

      public void reset(){

        this.model = new DotInfo[widthOfGame][heigthOfGame];

        for(int i = 0; i < widthOfGame; i++){
            for(int j = 0; j < heigthOfGame; j++){
                DotInfo dot = new DotInfo(i,j);
                model[i][j] = dot;
            }
        }
        int count = 0;
        while(count < numberOfMines){
            int x = generator.nextInt(widthOfGame);
            int y = generator.nextInt(heigthOfGame);
            if(model[x][y].isMined() == false){
                model[x][y].setMined();
                count++;
            }
        }

        this.numberOfFlags = 0;
        this.numberOfSteps = 0;

    }
    


 /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
        return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
        return model[i][j].isMined();

    }

    /**
     * Getter for numberOfMines
     * 
     * @return numberOfMines
     */

    public int getNumberOfMines(){

        return numberOfMines;
    }

    /**
     * Getter for numberOfFlags
     * 
     * @return numberOfFlags
     */

    public int getNumberOfFlags(){

        return numberOfFlags;
    }

    /**
     * Method that sets corresponding model to flagged
     * 
     * @param i
     *            the x coordinate
     * @param j
     *            the y coordinate
     */

    public void setFlagged(int i, int j){

        model[i][j].setFlagged(true);
        this.numberOfFlags++;



    }

     /**
     * Method that sets corresponding model to flagged
     * 
     * @param i
     *            the x coordinate
     * @param j
     *            the y coordinate
     */

    public void undoFlagged(int i, int j){

        model[i][j].setFlagged(false);
        this.numberOfFlags--;



    }

     /**
     * Method that sets checks current status of a 
     * model being flagged
     * 
     * @param i
     *            the x coordinate
     * @param j
     *            the y coordinate
     */

    public boolean hasBeenFlagged(int i, int j){

        return model[i][j].hasBeenFlagged();

    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
        return model[i][j].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
        if(getNeighbooringMines(i,j) == 0){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
        return model[i][j].isCovered();

    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
        int mines = 0;
        // this block checks if there is any neighbooring mines. 
        if(!(model[i][j].isMined())){
            if(j-1 >= 0 && j-1 < heigthOfGame &&  i >= 0 && i < widthOfGame){//N
                if(model[i][j-1].isMined()){
                    mines++;
                }
            }
            if(j-1 >= 0 && j-1 < heigthOfGame &&  i+1 >= 0 && i+1 < widthOfGame){//NE
                if(model[i+1][j-1].isMined()){
                    mines++;
                }
            }
            if(j >= 0 && j < heigthOfGame &&  i+1 >= 0 && i+1 < widthOfGame){//E
                if(model[i+1][j].isMined()){
                    mines++;
                }
            }
            if(j+1 >= 0 && j+1 < heigthOfGame &&  i+1 >= 0 && i+1 < widthOfGame){//SE
                if(model[i+1][j+1].isMined()){
                    mines++;
                }
            }
            if(j+1 >= 0 && j+1 < heigthOfGame &&  i >= 0 && i < widthOfGame){//S
                if(model[i][j+1].isMined()){
                    mines++;
                }
            }
            if(j+1 >= 0 && j+1 < heigthOfGame &&  i-1 >= 0 && i-1 < widthOfGame){//SW
                if(model[i-1][j+1].isMined()){
                    mines++;
                }
            }
            if(j >= 0 && j < heigthOfGame &&  i-1 >= 0 && i-1 < widthOfGame){//W
                if(model[i-1][j].isMined()){
                    mines++;
                }
            }
            if(j-1 >= 0 && j-1 < heigthOfGame &&  i-1 >= 0 && i-1 < widthOfGame){//NW
                if(model[i-1][j-1].isMined()){
                    mines++;
                }
            }
        }
        model[i][j].setNeighbooringMines(mines);
        return mines;
    }

 
    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
        model[i][j].uncover();
        this.numberUncovered++; 

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
        this.model[i][j].click();

    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
        for(int i = 0; i < widthOfGame; i++){
            for(int j = 0; j < heigthOfGame; j++){
                model[i][j].uncover();
            }
        }

    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
        return numberOfSteps;

    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
        return this.model[i][j];

    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
        this.numberOfSteps++;

    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
        int dots = (heigthOfGame * widthOfGame) - numberOfMines;
        if(numberUncovered == dots){
            return true;
        }
        else{
            return false;
        }
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
        String stringModel = "";

        for(int i = 0; i < widthOfGame; i++){
            stringModel += "\n";
            for(int j = 0; j < heigthOfGame; j++){
                if(isMined(i,j)){
                    stringModel += "* ";
                }else{
                    stringModel += Integer.toString(getNeighbooringMines(i,j)) + " ";
                }
            }
        }
        return stringModel;
    }
}