public class Game {
    public static boolean player;
    public static Board board;
    public static boolean gameFinished = false;

    public static void main(String[] args){
        newGame(true);
    }

    public static void newGame(boolean IAPlayer){
        player = IAPlayer;
        board = new Board();
        while(!gameFinished){
            long startTime = System.currentTimeMillis();
            if(board.getPlayer()!=player){
                //jeu du joueur humain
                if(board.winningPosition()){
                    gameFinished=true;
                }
            }else{
                int actualDepth = 0;
                long newTime,duration;
                Node nextMove;
                do{
                    actualDepth++;
                    nextMove = new Node(actualDepth,null,null, board, 0);
                    nextMove.process();
                    newTime = System.currentTimeMillis();
                    duration = newTime - startTime;
                }while(duration < 20000);
                board.applyMove(nextMove.move);
                //appliquer au front le mouvement
                if(board.winningPosition()){
                    gameFinished=true;
                }
            }
        }
        if(board.winner()==player){
            //display "IA WINS"
        }else{
            //display "PLAYER WINS"
        }
    }
}
