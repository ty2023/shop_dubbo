package com.yj.shop_web_item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopWebItemApplicationTests {

    @Test
    public void contextLoads() {

        int a = 270;
        int[][] arrays =
                {
                        {1,2,3,4},
                        {2,3,4,5},
                        {4,7,19,20}
                };

        int x = 0;
        int y = 0;

        int width = arrays[0].length;
        int heigh = arrays.length;

        //计算旋转了几个90度
        int s = a / 90;

        if(s % 4 == 0){
            //4个90度 - 没有变化
            printArrays(arrays);
        } else if(s % 3 == 0){
            //3个90端
            int[][] arrays2 = new int[width][heigh];

            x = 0;
            y = width - 1;

            for(int i = 0; i < arrays2.length; i++){
                for (int j = 0; j < arrays2[i].length; j++){
//                    System.out.println("(" + x + ", " + y + ")");
                    arrays2[i][j] = arrays[x][y];
                    x++;
                }
//                System.out.println("-----------");
                x = 0;
                y--;
            }
            printArrays(arrays2);

        } else if(s % 2 == 0){
            //2个90度
            int[][] arrays2 = new int[heigh][width];

            x = heigh - 1;
            y = width - 1;

            for(int i = 0; i < arrays2.length; i++){
                for (int j = 0; j < arrays2[i].length; j++){
//                    System.out.println("(" + x + ", " + y + ")");
                    arrays2[i][j] = arrays[x][y];
                    y--;
                }
//                System.out.println("-----------");
                x--;
                y=width - 1;
            }
            printArrays(arrays2);

        } else if(s % 1 == 0){
            //1个90度
            x = heigh - 1;
            y = 0;

            int[][] arrays2 = new int[width][heigh];
            for(int i = 0; i < arrays2.length; i++){
                for (int j = 0; j < arrays2[i].length; j++){
//                    System.out.println("(" + x + ", " + y + ")");
                    arrays2[i][j] = arrays[x][y];
                    x--;
                }
//                System.out.println("-----------");
                y++;
                x=heigh - 1;
            }
            printArrays(arrays2);


        }


    }

    public static void printArrays(int[][] arrays){
        for (int[] array : arrays) {
            System.out.println(Arrays.toString(array));
        }
    }

    }


