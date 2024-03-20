package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;
public class generateTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 28732351;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();

        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.enableDoubleBuffering();

        //ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int roomNum = RANDOM.nextInt(5) + 15;
        //int roomNum = 3;

        //获取room数量 并创建RoomArray
        RoomArray array = new RoomArray(roomNum);

        for (int i = 0; i < roomNum; i++) {
            genRoom(world, array, Tileset.FLOOR);
        }

        int[][] sortedX = sortArrayX(array.AllRandomPos(RANDOM));
        int[][] sortedY = sortArrayY(array.AllRandomPos(RANDOM));

        //连接所有的排序过x或y轴的随机坐标
        linkAllPos(sortedX, world, Tileset.FLOOR, 1);
        linkAllPos(sortedY, world, Tileset.FLOOR, 3);

        //链接到所有的随机坐标
        //linkAllPos(array.AllRandomPos(RANDOM), world, Tileset.FLOOR, 1);

        //生成墙壁
        createWall(world, Tileset.WALL);

        //生成锁住的门
        createLockedDoor(array ,RANDOM, world);

        //打印终端
        System.out.println(TETile.toString(world));

        world[0][0] = Tileset.FLOWER;
        ter.renderFrame(world);
    }


    //genRom 生成房间位置 长宽 调用is_addable判断数据是否合法 调用addRoom传入数据生成房间
    public static void genRoom(TETile[][] object, RoomArray array, TETile flag) {
        int maxWidth = 6;
        int maxHeight = 6;
        int roomPosX = RANDOM.nextInt(WIDTH - 2) + 1;
        int roomPosY = RANDOM.nextInt(HEIGHT - 2) + 1;
        int roomWidth = RANDOM.nextInt(maxWidth) + 2;
        int roomHeight = RANDOM.nextInt(maxHeight) + 2;
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

    public static void createLockedDoor(RoomArray array, Random RANDOM, TETile[][] object) {
        int[][] randomPos = array.AllRandomPos(RANDOM);
        int Token = RANDOM.nextInt(randomPos.length);
        int gold_x = randomPos[Token][0];
        int gold_y = randomPos[Token][1];
        int find_wall = 0;
        switch (RANDOM.nextInt(4)) {
            case 0:
                while (find_wall == 0) {
                    if (object[gold_x][gold_y] == Tileset.WALL) {
                        object[gold_x][gold_y] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    gold_y++;
                }

            case 1:
                while (find_wall == 0) {
                    if (object[gold_x][gold_y] == Tileset.WALL) {
                        object[gold_x][gold_y] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    gold_y--;
                }

            case 2:
                while (find_wall == 0) {
                    if (object[gold_x][gold_y] == Tileset.WALL) {
                        object[gold_x][gold_y] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    gold_x++;
                }

            case 3:
                while (find_wall == 0) {
                    if (object[gold_x][gold_y] == Tileset.WALL) {
                        object[gold_x][gold_y] = Tileset.LOCKED_DOOR;
                        find_wall = 1;
                    }
                    gold_x--;
                }

        }
    }
}