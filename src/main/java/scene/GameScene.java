package scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import view.util.Subject;

public class GameScene extends Subject{
    private static final int PIECE_LENGTH = 100;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    
    private int width;
    private int height;
    private boolean[][] cells;
    private List<Piece> pieces;
    private Stock stock;

    public GameScene(int width, int height){
        this.width = width;
        this.height = height;
        this.cells = new boolean[width][height];
        this.pieces = new CopyOnWriteArrayList<>();
        this.stock = new Stock();
        resetCells();
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
            System.out.println("Nouvelle piece !");
            Piece p = preparePiece();
            finish = isBlocked(p);
            if(finish){
                System.out.println("Cette piece n'a pas pu être placé : " + p);
                break;
            }
            reload();
            while (!pieceFinish(p)) {
                p.y++;
                reload();
                sleep(1);
            }
        }
        System.out.println("Finish !");
    }

    static Random ra = new Random(0);
    
    private Piece preparePiece(){
        Piece p = stock.pop();
        p.x = (int)(ra.nextDouble(width - 4));
        p.y = 0;
        addPiece(p);
        return p;
    }

    private boolean isBlocked(Piece p){
        for (int y = 0; y < p.cell.length; y++) {
            for (int x = 0; x < p.cell[y].length; x++) {
                if(!p.cell[y][x]) continue;
                if(isFull(p.x + x, p.y + y)) return true;
            }
        }
        return false;
    }
    
    private boolean pieceFinish(Piece p){
        for (int y = 0; y < p.cell.length; y++) {
            for (int x = 0; x < p.cell[y].length; x++) {
                if(!p.cell[y][x]) continue;
                if(p.y + y >= height - 1) return true;
                if(y < p.cell.length - 1 && p.cell[y + 1][x]) continue;
                if(isFull(p.x + x, p.y + y + 1)) return true;
            }
        }
        return false;
    }

    private void reload(){
        resetCells();
        placePieces();
        notifyObservers();
    }

    private void placePieces(){
        for (Piece p : pieces) {
            placePiece(p);
        }
    }

    private void placePiece(Piece p){
        for (int y = 0; y < p.cell.length; y++) {
            for (int x = 0; x < p.cell[y].length; x++) {
                if(p.cell[y][x]){
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
