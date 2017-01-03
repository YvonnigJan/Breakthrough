public class Board {
    private int[][] whiteBoard;
    private int[][] blackBoard;
    private boolean currentPlayer;

    public Board(){
        int[][] whiteB = {{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0},{1,1,0,0,0,0,0,0}};
        int[][] blackB = {{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,1,1}};
        whiteBoard = whiteB;
        blackBoard = blackB;
        currentPlayer = true;
    }
    /*public Board(int[][] whiteB, int[][] blackB, boolean current){
        whiteBoard = whiteB;
        blackBoard = blackB;
        currentPlayer = current;
    }

    public int[][] getWBoard(){
        return whiteBoard;
    }

    public int[][] getBBoard(){
        return blackBoard;
    }*/

    public int[][] getBoard(boolean player){
        if(player){
            return whiteBoard;
        }else{
            return blackBoard;
        }
    }

    public int getNumberPawns(boolean player){
        int[][] board = getBoard(player);
        int result = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j]==1){
                    result++;
                }
            }
        }
        return result;
    }

    public int[][] getPawns(boolean player){
        int pawns = getNumberPawns(player);
        int[][] board = getBoard(player);
        int[][] result = new int[pawns][2];
        int index = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j]==1){
                    result[index][0]=i;
                    result[index][1]=j;
                    index++;
                }
            }
        }
        return result;
    }

    public boolean getPlayer(){
        return currentPlayer;
    }

    /*public void changeBoard(int[][] boardW, int[][] boardB){
            blackBoard = boardB;
            whiteBoard = boardW;
    }*/

    public void changePlayer(){
        currentPlayer = !currentPlayer;
    }

    /*public boolean equals(Board b){
        return((b.getWBoard()==whiteBoard)&&(b.getBBoard()==blackBoard)&&(b.getPlayer()==currentPlayer));
    }*/

    public boolean validMove(int[][] move, boolean straight){
        //débordement en Y
        if((move[1][1] < 0)||(8 < move[1][1])){return false;}
        //pion personnel déjà présent
        int[][] board = getBoard(currentPlayer);
        if(board[move[1][0]][move[1][1]] == 1){return false;}
        //si tout droit, pas de pion adverse
        if(straight){
            board = getBoard(!currentPlayer);
            if(board[move[1][0]][move[1][1]] == 1){return false;}
        }
        return true;
    }

    public void applyMove(int[][] move){
        int[][] board = getBoard(currentPlayer);
        board[move[0][0]][move[1][0]] = 0;
        board[move[1][0]][move[1][1]] = 1;
        board = getBoard(!currentPlayer);
        if(board[move[1][0]][move[1][1]] == 1){
            board[move[1][0]][move[1][1]] = 0;
        }
    }

    /*public Board copy(){
        return(new Board(whiteBoard,blackBoard,currentPlayer));
    }*/

    public int analyze(boolean player){
        //TO DO : heuristique, appréciation de la position
        //Heuristique actuelle très naive
        if(winningPosition()){
            if(winner()==player) {
                return(Integer.MAX_VALUE);
            }else {
                return(Integer.MIN_VALUE);
            }
        }else {
            return 0;
        }
    }

    public boolean winningPosition() {
        for(int i = 0; i < 8; i++){
            if((whiteBoard[i][7]==1)||(blackBoard[i][0]==1)) {
                return true;
            }
        }
        return false;
    }

    //on suppose que l'on sait déjà qu'il y a un vainqueur
    public boolean winner() {
        for(int i = 0; i < 8; i++){
            if(whiteBoard[i][7]==1) {
                return true;
            }
        }
        return false;
    }
}
