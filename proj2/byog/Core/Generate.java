package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
import java.math.*;
public class Generate {
    private static int WIDTH;
    private static int HEIGHT;
    private static Random RANDOM;
    private TETile[][] gameframe;
    private int playPosX;
    private int playPosY;
    private int LockDoorX;
    private int LockDoorY;
    public Generate(int width, int height, Random random) {
        WIDTH = width;
        HEIGHT = height - 3;
        RANDOM = random;
    }
    public TETile[][] generateWorld(Random RANDOM) {
        gameframe = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                gameframe[x][y] = Tileset.NOTHING;
            }
        }
        int roomNum = RANDOM.nextInt(5) + 15;
        RoomArray array = new RoomArray(roomNum);
        for (int i = 0; i < roomNum; i++) {
            genRoom(gameframe, array, Tileset.FLOOR);
        }
        int[][] sortedX = sortArrayX(array.AllRandomPos(RANDOM));
        int[][] sortedY = sortArrayY(array.AllRandomPos(RANDOM));
        linkAllPos(sortedX, gameframe, Tileset.FLOOR, 1);
        linkAllPos(sortedY, gameframe, Tileset.FLOOR, 3);
        createWall(gameframe, Tileset.WALL);
        //linkAllPos(sortedX, gameframe, Tileset.FLOOR, 1);

        createLockedDoor(array ,RANDOM, gameframe);

        createPlayer(array, RANDOM);

        return gameframe;
    }


    //genRom 生成房间位置 长宽 调用is_addable判断数据是否合法 调用addRoom传入数据生成房间
    public static void genRoom(TETile[][] object, RoomArray array, TETile flag) {
        int maxWidth = 5;
        int maxHeight = 5;
        int roomPosX = RANDOM.nextInt(WIDTH - 2) + 1;
        int roomPosY = RANDOM.nextInt(HEIGHT - 2) + 1;
        int roomWidth = RANDOM.nextInt(maxWidth) + 3;
        int roomHeight = RANDOM.nextInt(maxHeight) + 3;
        while (is_notaddable(roomPosX, roomPosY, roomWidth, roomHeight, object)) {
            roomPosX = RANDOM.nextInt(WIDTH - 2) + 1;
            roomPosY = RANDOM.nextInt(HEIGHT - 2) + 1;
            roomWidth = RANDOM.nextInt(maxWidth) + 2;
            roomHeight = RANDOM.nextInt(maxHeight) + 2;
        }
        //在创建room之前把信息存放在array里面
        array.addRoom(roomPosX, roomPosY, roomWidth, roomHeight);
        //创建room
        addRoom(object, roomPosX, roomPosY, roomWidth, roomHeight, flag);
    }

    //用位置 长宽数据生成矩形房间
    public static void addRoom(TETile[][] object, int pos_x, int pos_y, int width, int height, TETile flag) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                object[pos_x + j][pos_y - i] = flag;
            }
        }
    }

    //根据房间生成随机数据和已有位置的object二维数组的数据进行比较 判断整个矩形是否可以添加
    public static boolean is_notaddable(int pos_x, int pos_y, int width, int height, TETile[][] object) {
        if (pos_x + width > WIDTH - 2 || pos_y - height < 1) {
            return true;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (object[pos_x + j][pos_y - i] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void PosLink(TETile[][] object, int x1, int y1, int x2, int y2, TETile flag) {
        //如果random为true 先移动x 再移动y 否则反之
        if (random()) {
            while (x1 != x2) {
                if (x1 < x2) {
                    x1++;
                    object[x1][y1] = flag;
                }
                if (x2 < x1) {
                    x2++;
                    object[x2][y2] = flag;
                }
            }
            while (y1 != y2) {
                if (y1 < y2) {
                    y1++;
                    object[x1][y1] = flag;
                }
                if (y2 < y1) {
                    y2++;
                    object[x2][y2] = flag;
                }
            }
        }
        while (y1 != y2) {
            if (y1 < y2) {
                y1++;
                object[x1][y1] = flag;
            }
            if (y2 < y1) {
                y2++;
                object[x2][y2] = flag;
            }
        }
        while (x1 != x2) {
            if (x1 < x2) {
                x1++;
                object[x1][y1] = flag;
            }
            if (x2 < x1) {
                x2++;
                object[x2][y2] = flag;
            }
        }
    }

    public static boolean random() {
        switch (RANDOM.nextInt(2)) {
            case 0:
                return true;
            case 1:
                return false;
        }
        return false;
    }

    public static void linkAllPos(int[][] allpos, TETile[][] object, TETile flag, int symbol) {
        for (int i = 0; i < allpos.length; i = i + symbol) {
            if (i + 2 > allpos.length) {
                return;
            }
            PosLink(object, allpos[i][0], allpos[i][1], allpos[i + 1][0], allpos[i + 1][1], flag);
        }
    }

    public static void createWall(TETile[][] object, TETile flag) {
        int x, y;
        for (int i = 1; i < HEIGHT - 1; i++) {
            for (int j = 1; j < WIDTH - 1; j++) {
                x = j;
                y = i;
                if (isCreatabe_inside(object, x, y)) {
                    object[j][i] = flag;
                }
            }
        }

        for (int i = 1; i < WIDTH - 1; i++) {
            if (object[i - 1][1] == Tileset.FLOOR || object[i][1] == Tileset.FLOOR || object[i + 1][1] == Tileset.FLOOR) {
                object[i][0] = Tileset.WALL;
            }
        }
        for (int i = 1; i < WIDTH - 1; i++) {
            if (object[i - 1][HEIGHT - 2] == Tileset.FLOOR || object[i][HEIGHT - 2] == Tileset.FLOOR || object[i + 1][HEIGHT - 2] == Tileset.FLOOR) {
                object[i][HEIGHT - 1] = Tileset.WALL;
            }
        }
        for (int i = 1; i < HEIGHT - 1; i++) {
            if (object[1][i - 1] == Tileset.FLOOR || object[1][i] == Tileset.FLOOR || object[1][i + 1] == Tileset.FLOOR) {
                object[0][i] = Tileset.WALL;
            }
        }
        for (int i = 1; i < HEIGHT - 1; i++) {
            if (object[WIDTH - 1][i - 1] == Tileset.FLOOR || object[WIDTH - 1][i] == Tileset.FLOOR || object[WIDTH - 1][i + 1] == Tileset.FLOOR) {
                object[WIDTH][i] = Tileset.WALL;
            }

        }

        if (object[0][HEIGHT - 2] == Tileset.FLOOR || object[1][HEIGHT - 2] == Tileset.FLOOR || object[1][HEIGHT - 1] == Tileset.FLOOR) {
            object[0][HEIGHT - 1] = Tileset.WALL;
        }

    }

    public static boolean isCreatabe_inside(TETile[][] object, int x, int y) {
        if (object[x][y] == Tileset.FLOOR) {
            return false;
        }
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (object[j][i] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printArray(int[][] arr) {
        // 遍历每一行
        for (int i = 0; i < arr.length; i++) {
            // 遍历当前行的每一个元素
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            // 打印换行
            System.out.println();
        }
    }

    public static int[][] sortArrayX(int[][] Array) {
        int[][] array = Array;
        int n = array.length;
        int[] temp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j][0] > array[j + 1][0]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
    public static int[][] sortArrayY(int[][] Array) {
        int[][] array = Array;
        int n = array.length;
        int[] temp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j][1] > array[j + 1][1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public void createLockedDoor(RoomArray array, Random RANDOM, TETile[][] object) {
        int[][] randomPos = array.AllRandomPos(RANDOM);
        int Token = RANDOM.nextInt(randomPos.length);
        LockDoorX = randomPos[Token][0];
        LockDoorY = randomPos[Token][1];
        int find_wall = 0;
        switch (RANDOM.nextInt(4)) {
            case 0:
                while (find_wall == 0) {
                    if (object[LockDoorX][LockDoorY] == Tileset.WALL) {
                        object[LockDoorX][LockDoorY] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    LockDoorY++;
                }

            case 1:
                while (find_wall == 0) {
                    if (object[LockDoorX][LockDoorY] == Tileset.WALL) {
                        object[LockDoorX][LockDoorY] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    LockDoorY--;
                }

            case 2:
                while (find_wall == 0) {
                    if (object[LockDoorX][LockDoorY] == Tileset.WALL) {
                        object[LockDoorX][LockDoorY] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    LockDoorX++;
                }

            case 3:
                while (find_wall == 0) {
                    if (object[LockDoorX][LockDoorY] == Tileset.WALL) {
                        object[LockDoorX][LockDoorY] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    LockDoorX--;
                }

        }
    }

    public void createPlayer(RoomArray array, Random RANDOM) {
        int find = 0;
        double offsize = 1;
        while (true) {
            for (int i = 0; i < 10000; i ++) {
                int[][] randomPos = array.AllRandomPos(RANDOM);
                int j = RANDOM.nextInt(randomPos.length);
                playPosX = randomPos[j][0];
                playPosY = randomPos[j][1];
                if (gameframe[playPosX][playPosY] == Tileset.WALL) {
                    continue;
                }
                double dis = Distance(playPosX, playPosY, LockDoorX, LockDoorY);
                double map_dis = Distance(0, HEIGHT, WIDTH, 0);
                if (dis > map_dis - offsize) {
                    gameframe[playPosX][playPosY] = Tileset.PLAYER;
                    find = 1;
                    break;
                }
            }
            if (find == 0) {
                offsize++;
            } else {
                break;
            }
        }
    }
    public static double Distance(int x1, int y1, int x2, int y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance;
    }
    public void move(char sign) {
        int testX = playPosX;
        int testY = playPosY;
        switch (sign) {
            case 'w' :
                testY++;
                if (gameframe[testX][testY] == Tileset.WALL) {
                    break;
                }
                gameframe[playPosX][playPosY] = Tileset.FLOOR;
                this.playPosY++;
                gameframe[playPosX][playPosY] = Tileset.PLAYER;
                break;
            case 'a' :
                testX--;
                if (gameframe[testX][testY] == Tileset.WALL) {
                    break;
                }
                gameframe[playPosX][playPosY] = Tileset.FLOOR;
                this.playPosX--;
                gameframe[playPosX][playPosY] = Tileset.PLAYER;
                break;
            case 's' :
                testY--;
                if (gameframe[testX][testY] == Tileset.WALL) {
                    break;
                }
                gameframe[playPosX][playPosY] = Tileset.FLOOR;
                this.playPosY--;
                gameframe[playPosX][playPosY] = Tileset.PLAYER;
                break;
            case 'd' :
                testX++;
                if (gameframe[testX][testY] == Tileset.WALL) {
                    break;
                }
                gameframe[playPosX][playPosY] = Tileset.FLOOR;
                this.playPosX++;
                gameframe[playPosX][playPosY] = Tileset.PLAYER;
                break;
        }
    }
    public TETile[][] getGameframe() {
        return gameframe;
    }
    public int getPlayPosX() {
        return playPosX;
    }
    public int getPlayPosY() {
        return playPosY;
    }
    public int getLockDoorX() {
        return LockDoorX;
    }
    public int getLockDoorY() {
        return LockDoorY;
    }

    public boolean isWin() {
        if (gameframe[LockDoorX][LockDoorY] == Tileset.PLAYER) {
            gameframe[LockDoorX][LockDoorY] = Tileset.UNLOCKED_DOOR;
            return true;
        }
        return false;
    }

    public void updateLockDoor() {
        for (int i = 0; i < HEIGHT; i ++) {
            for (int j = 0; j < WIDTH; j ++) {
                if (gameframe[j][i] == Tileset.LOCKED_DOOR) {
                    LockDoorX = j;
                    LockDoorY = i;
                }
            }
        }
    }

    public int mouseUIPos(int posX, int posY) {
        if (posX >= WIDTH || posY >= HEIGHT) {
            return 0;
        } else if (gameframe[posX][posY] == Tileset.FLOOR) {
            return 1;
        } else if (gameframe[posX][posY] == Tileset.WALL) {
            return 2;
        } else if (gameframe[posX][posY] == Tileset.PLAYER) {
            return 3;
        } else if (gameframe[posX][posY] == Tileset.LOCKED_DOOR) {
            return 4;
        } else {
            return 0;
        }

    }

}