package byog.Core;
import java.util.Random;
public class Room {
    //Room数据结构 两个数组 分别存放room左上的pos 右下的pos
    private int[] leftUpPos;
    private int[] rightDownPos;
    //构造函数 取room位置 和长宽
    public Room(int pos_x, int pos_y, int width, int height) {
        leftUpPos = new int[]{pos_x,pos_y};
        rightDownPos = new int[]{pos_x + width, pos_y - height};
    }
    //返回room左上位置数组
    public int[] getLeftPos() {
        return leftUpPos;
    }
    //返回room右下位置数组
    public int[] getRightPos() {
        return rightDownPos;
    }

    //返回room里面随机的一个坐标点 randomX randomY
    public int[] randomPoint(Random random) {
        int randomPointX;
        int randomPointY;
        if (rightDownPos[0] == leftUpPos[0]) {
            randomPointX = rightDownPos[0];
        } else {
            randomPointX = random.nextInt(rightDownPos[0] - leftUpPos[0]) + leftUpPos[0];
        }
        if (leftUpPos[1] == rightDownPos[1]) {
            randomPointY = leftUpPos[1];
        } else {
            randomPointY = random.nextInt(leftUpPos[1] - rightDownPos[1]) + rightDownPos[1];
        }
        int[] pointXY = new int[]{randomPointX, randomPointY};
        return pointXY;
    }


}