package byog.Core;
import byog.Core.Room;

import java.util.Random;

public class RoomArray {
    //以room为数据结构的一维数组 存放所有的room
    private Room[] Array;
    //addroom的下一个存放下标
    private int add_index = 0;
    //构造函数 取num为数组大小
    RoomArray(int num) {
        Array = new Room[num];
    }
    //addroom方法 调用room的构造方法 给room赋值
    public void addRoom(int pos_x, int pos_y, int width, int height) {
        Array[add_index] = new Room(pos_x, pos_y, width, height);
        add_index = add_index + 1;
    }
    //获取index下标的room
    public Room getRoom(int index) {
        return this.Array[index];
    }
    //返回RoomArray的长度
    public int size() {
        return Array.length;
    }
    //打印所有的room位置
    public void printAllRoomPos() {
        for (int i = 0; i < size(); i ++) {
            System.out.println("左上 ： " + Array[i].getLeftPos()[0] + " " + Array[i].getLeftPos()[1] + " 右下 ： "
                    + Array[i].getRightPos()[0] + " " + Array[i].getRightPos()[1]);
        }
    }

    //返回index下标room里面的随机位置 使用已有的伪随机变量
    public int[] randomRoomPos(int index, Random random) {
        return Array[index].randomPoint(random);
    }
    //将roomArray里面的所有数据生成随机数据 取一个随机变量
    public int[][] AllRandomPos(Random random) {
        int[][] allRandomPos = new int[size()][2];
        for (int i = 0; i < size(); i++) {
            allRandomPos[i] = this.randomRoomPos(i,random);
        }
        return allRandomPos;
    }

    //返回Array所有的位置左上 二维数组
    public int[][] AllPos() {
        int[][] allPos = new int[size()][2];
        for (int i = 0; i < size(); i++) {
            allPos[i][0] = Array[i].getLeftPos()[0];
            allPos[i][1] = Array[i].getLeftPos()[1];
        }
        return allPos;
    }
}