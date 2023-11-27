package scene;

public class Stock {
    private static final int LENGTH = 5;
    
    protected int length;

    private Piece[] pieces;
    private int index;

    public Stock(int length){
        this.length = length;
        this.pieces = new Piece[length];
        reset();
    }

    public Stock(){
        this(LENGTH);
    }

    public Piece[] getPieces(){
        return pieces;
    }

    protected void reset(){
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = random();
        }
    }

    private Piece random(){
        return Piece.random();
    }

    public Piece pop(){
        Piece p = pieces[index];
        pieces[index] = random();
        index = (index + 1) % length;
        return p;
    }
}