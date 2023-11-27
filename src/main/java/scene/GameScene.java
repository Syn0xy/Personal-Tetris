package scene;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import view.util.Subject;

public class GameScene extends Subject{
    private static final int WIDTH = 125;
    private static final int HEIGHT = 50;
    private static final int STOCK_SIZE = 10;
    
    private int width;
    private int height;
    private boolean[][] cells;

    private Stock stock;
    private List<Piece> pieces;
    private Piece currentPiece;

    public GameScene(int width, int height, int stockSize){
        this.width = width;
        this.height = height;
        this.cells = new boolean[width][height];
        this.pieces = new CopyOnWriteArrayList<>();
        this.stock = new Stock(stockSize);
        this.currentPiece = null;
        resetCells();
    }

    public GameScene(int width, int height){
        this(width, height, STOCK_SIZE);
    }

    public GameScene(){
        this(WIDTH, HEIGHT);
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public boolean[][] getCells(){
        return cells;
    }

    public List<Piece> getPieces(){
        return pieces;
    }

    public Stock getPiecesStock(){
        return stock;
    }

    private void addPiece(Piece p){
        pieces.add(p);
    }
    
    private void resetCells(){
        for (boolean[] row : cells) {
            Arrays.fill(row, false);
        }
    }
    
    public void run(){
        new Thread(new Runnable() {
            @Override public void run() { start(); }
        }).start();
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (Exception e) {}
    }

    private void start(){
        boolean finish = false;
        while(!finish){
            // System.out.println("Nouvelle piece !");
            currentPiece = preparePiece();
            reload();
            while (!pieceFinish(currentPiece)) {
                currentPiece.move(Move.DOWN);
                reload();
                sleep(100);
            }
            finish = isFinish();
        }
        System.out.println("Finish !");
    }
    
    private Piece preparePiece(){
        Piece p = stock.pop();
        p.x = (int)(Math.random() * (width - p.width));
        p.y = 0;
        addPiece(p);
        return p;
    }

    private boolean isFinish(){
        for (int i = 0; i < cells.length; i++) {
            if(cells[i].length > 0 && cells[i][0]) return true;
        }
        return false;
    }
    
    private boolean pieceFinish(Piece p){
        for (int y = 0; y < p.cells.length; y++) {
            for (int x = 0; x < p.cells[y].length; x++) {
                if(!p.cells[y][x]) continue;
                if(p.y + y >= height - 1) return true;
                if(y < p.cells.length - 1 && p.cells[y + 1][x]) continue;
                if(isFull(p.x + x, p.y + y + 1)) return true;
            }
        }
        return false;
    }

    public void keyPressed(String key){
        switch (key) {
            case "Z": currentPiece.move(Move.UP); break;
            case "S": currentPiece.move(Move.DOWN); break;
            case "Q": currentPiece.move(Move.LEFT); break;
            case "D": currentPiece.move(Move.RIGHT); break;
            case "A": currentPiece.move(Move.ROTATE_LEFT); break;
            case "E": currentPiece.move(Move.ROTATE_RIGHT); break;
            default: break;
        }
    }

    private void reload(){
        resetCells();
        placePieces();
        notifyObservers();
    }

    private void placePieces(){
        for (Piece p : pieces) {
            if(p != currentPiece) placePiece(p);
        }
    }

    private void placePiece(Piece p){
        for (int y = 0; y < p.cells.length; y++) {
            for (int x = 0; x < p.cells[y].length; x++) {
                if(p.cells[y][x]){
                    placeCell(p.x + x, p.y + y);
                }
            }
        }
    }
    
    private void placeCell(int x, int y){
        if(isValid(x, y)) cells[x][y] = true;
    }

    private boolean isFull(int x, int y){
        return isValidCoordinate(x, y) && cells[x][y];
    }

    private boolean isValid(int x, int y){
        return isValidCoordinate(x, y) && !cells[x][y];
    }

    private boolean isValidCoordinate(int x, int y){
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
