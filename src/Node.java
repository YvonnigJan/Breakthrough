public class Node {
    public Node parent;
    public Integer value;
    public int level;
    public int[][] move;
    public Board board;
    public int depth;
    public boolean cut = false;

    public Node(int depth, Node parent, int[][] move, Board board, int level){
        this.parent = parent;
        this.move = move;
        this.board = board;
        this.depth = depth;
        this.level = level;
    }

    public void process(){

        if (this.move != null){
            board.applyMove(move);
            board.changePlayer();
            depth--;
        }

        if (depth > 0){
            int childLevel = level + 1;
            int offsetY = board.getPlayer() ? 1 : -1;
            int[][] pawns = board.getPawns(board.getPlayer());

            if (pawns.length == 0){
                if (board.getPlayer() == Game.player){
                    value = Integer.MIN_VALUE;
                }else{
                    value = Integer.MAX_VALUE;
                }
                if (this.parent != null){
                    this.propagate(this);
                }
                return;
            }

            Node node = null;
            for (int i=0;i<pawns.length;i++){
                int[][] newMove1 = {pawns[i], {pawns[i][0]-1, pawns[i][1]+offsetY}}; //Front left move
                if (!cut && newMove1[1][0]>=0 && board.validMove(newMove1, false)){
                    node = new Node(depth, this, newMove1, board, childLevel);
                    node.process();
                }

                int[][] newMove2 = {pawns[i], {pawns[i][0], pawns[i][1]+offsetY}}; //Front move
                if (!cut && board.validMove(newMove2, true)){
                    node = new Node(depth, this, newMove2, board, childLevel);
                    node.process();
                }

                int[][] newMove3 = {pawns[i], {pawns[i][0]+1, pawns[i][1]+offsetY}}; //Front right move
                if (!cut && newMove3[1][0]<=7 && board.validMove(newMove3, false)){
                    node = new Node(depth, this, newMove3, board, childLevel);
                    node.process();
                }
                if (cut){
                    break;
                }
                else if(i == pawns.length-1){
                    cut = true;
                }
            }
            if (node != null && this.parent != null){
                propagate(this);
            }

        }else{
            value = board.analyze(Game.player);
            if (parent != null){
                propagate(this);
            }
        }
    }

    public void propagate(Node node){
        //negamax
        if (node.parent.value == null || ((node.board.getPlayer()== Game.player)&& node.value > node.parent.value) || ((node.board.getPlayer()!=Game.player) && node.value < node.parent.value)){
            node.parent.value = node.value;

            //coupe
            boolean IAPlayer = (node.board.getPlayer()== Game.player);
            if (node.level > 1 && node.parent.parent.value != null &&
                    ((IAPlayer && node.parent.value <= node.parent.parent.value) ||
                            (!IAPlayer && node.parent.value >= node.parent.parent.value))){
                node.parent.cut = true;
            }

            if (node.level == 1){
                node.parent.move = node.move;
            }
        }
    }

}
