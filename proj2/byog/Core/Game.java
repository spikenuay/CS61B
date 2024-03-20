package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        initKeyboard();
        drawUI();
        char ui_input = Character.toLowerCase(charInput());
        switch (ui_input) {
            case 'n':
                newGame();
            case 'l' :
                System.exit(0);

            case 'q':
                System.exit(0);
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            int i = 1;
            String str_seed = "";
            while (Character.isDigit(input.charAt(i))) {
                str_seed += input.charAt(i);
                i++;
            }
            char action = input.charAt(i);
            long seed = Long.parseLong(str_seed);
            Random RANDOM = new Random(seed);
            Generate generatedWorld = new Generate(WIDTH,HEIGHT,RANDOM);
            TETile[][] finalWorldFrame = generatedWorld.generateWorld(RANDOM);
            return finalWorldFrame;
        }
        return null;
    }

    public void newGame() {
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);

        Random RANDOM = new Random();
        Generate gameFrame = new Generate(WIDTH,HEIGHT,RANDOM);
        gameFrame.generateWorld(RANDOM);
        gameFrame.updateLockDoor();
        while (true) {
            if (gameFrame.isWin()) {

                ter.renderFrame(gameFrame.getGameframe());
                StdDraw.pause(200);
                newGame();
                System.exit(0);
            }
            //ter.renderFrame(gameFrame.getGameframe());
            System.out.println(" player: " + gameFrame.getPlayPosX()
                    + " " + gameFrame.getPlayPosY() + " 门坐标：" + gameFrame.getLockDoorX() +
                    " " + gameFrame.getLockDoorY());
            while (true) {
                StdDraw.clear(Color.BLACK);
                StdDraw.enableDoubleBuffering();
                ter.renderFrame(gameFrame.getGameframe());
                StdDraw.setPenColor(Color.RED);
                int mouseX = (int) Math.floor(StdDraw.mouseX());
                int mouseY = (int) Math.floor(StdDraw.mouseY());
                switch (gameFrame.mouseUIPos(mouseX, mouseY)) {
                    case 0:
                        break;
                    case 1:
                        StdDraw.textLeft(1, 29, "FLOOR");
                        break;
                    case 2:
                        StdDraw.textLeft(1, 29, "WALL");
                        break;
                    case 3:
                        StdDraw.textLeft(1, 29, "PLAYER");
                        break;
                    case 4:
                        StdDraw.textLeft(1, 29, "LOCKEDOOR");
                        break;
                }
                StdDraw.show();
                StdDraw.pause(1);
                System.out.println("x:" + mouseX + " y:" + mouseY);
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char sign = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (sign == ':') {
                    while (true) {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        char sign2 = Character.toLowerCase(StdDraw.nextKeyTyped());
                        if (sign2 == 'q') {
                            System.exit(0);
                        }
                    }

                }

                if (sign == 'e') {
                    newGame();
                }

                gameFrame.move(sign);
                break;
            }
        }
    }
    public void initKeyboard() {
        //初始化界面
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.enableDoubleBuffering();
    }

    public void drawUI() {
        int MidWidth = WIDTH / 2;
        int MidHeight = HEIGHT /2;
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(MidWidth, MidHeight + 10, "Game");

        Font font1 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font1);
        StdDraw.text(MidWidth, MidHeight + 4, "New Game : N");
        StdDraw.text(MidWidth, MidHeight + 1 , "Load Game : L");
        StdDraw.text(MidWidth, MidHeight - 2, "Quit : Q");

        StdDraw.show();
        StdDraw.pause(200);
    }
    public char charInput() {
        char input;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input = StdDraw.nextKeyTyped();
            break;
        }
        StdDraw.pause(500);
        return input;
    }
}
