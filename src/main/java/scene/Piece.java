package scene;

import java.awt.Color;

public class Piece {
    public static final Piece[] PIECES = new Piece[]{
        new Piece("I", new String[]{ "XXXX" }, Color.CYAN),
        new Piece("O", new String[]{ "XX", "XX" }, Color.YELLOW),
        new Piece("T", new String[]{ "XXX", " X " }, Color.MAGENTA),
        new Piece("L", new String[]{ "XXX", "X  " }, Color.ORANGE),
        new Piece("J", new String[]{ "XXX", "  X" }, Color.BLUE),
        new Piece("Z", new String[]{ "XX ", " XX" }, Color.RED),
        new Piece("S", new String[]{ " XX", "XX " }, Color.GREEN)
        // new Piece("S", new String[]{ "X" }, Color.BLUE),
        // new Piece("S", new String[]{ "X" }, Color.CYAN),
        // new Piece("S", new String[]{ "X" }, Color.DARK_GRAY),
        // new Piece("S", new String[]{ "X" }, Color.GRAY),
        // new Piece("S", new String[]{ "X" }, Color.GREEN),
        // new Piece("S", new String[]{ "X" }, Color.LIGHT_GRAY),
        // new Piece("S", new String[]{ "X" }, Color.MAGENTA),
        // new Piece("S", new String[]{ "X" }, Color.ORANGE),
        // new Piece("S", new String[]{ "X" }, Color.PINK),
        // new Piece("S", new String[]{ "X" }, Color.RED),
        // new Piece("S", new String[]{ "X" }, Color.WHITE),
        // new Piece("S", new String[]{ "X" }, Color.YELLOW)
    };
    
    protected String name;
    private String[] build;
    private Color color;

    protected boolean[][] cells;
    public int width;
    public int height;
    public int x;
    public int y;

    private Piece(String name, String[] build, Color color){
        this.name = name;
        this.build = build;
        this.color = color;
        this.cells = build(build);
        this.height = cells.length;
        this.width = cells.length > 0 ? cells[0].length : 0;
        this.x = 0;
        this.y = 0;
    }

    public boolean[][] getCells(){
        return cells;
    }

    public Color getColor(){
        return color;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean[][] build(String[] build){
        boolean[][] cell = new boolean[build.length][];
        for (int y = 0; y < build.length; y++) {
            cell[y] = new boolean[build[y].length()];
            for (int x = 0; x < build[y].length(); x++) {
                cell[y][x] = false;
                if(build[y].charAt(x) == 'X'){
                    cell[y][x] = true;
                }
            }
        }
        return cell;
    }

    public void move(Move m){
        switch (m) {
            case UP: y--; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case RIGHT: x++; break;
            case ROTATE_LEFT: rotateLeft(); break;
            case ROTATE_RIGHT: rotateRight(); break;
            default: break;
        }
    }

    private void rotateLeft(){
        for (int i = 0; i < 3; i++) {
            rotateRight();
        }
    }

    private void rotateRight(){
        if(width > 0 && height > 0){
            int tmp = width;
            width = height;
            height = tmp;
            boolean[][] b = new boolean[width][height];
            for (int y = 0; y < cells.length; y++) {
                for (int x = 0; x < cells[y].length; x++) {
                    b[x][y] = cells[y][x];
                }
            }
            cells = b;
        }
    }

    public Piece copy(){
        return new Piece(name, build, color);
    }

    public static final Piece random(){
        return PIECES[(int)(PIECES.length * Math.random())].copy();
    }

    public String toString(){
        return getClass().getSimpleName() + "[name:" + name + "]";
    }
}
