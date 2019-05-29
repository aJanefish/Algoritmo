package com;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 字符串移动 
 * */
public class Main {
    ReentrantLock reentrantLock = new ReentrantLock();

    private void show() {

    }

    public static void main(String args[]) {
        //递归
        char[] chars = {'w', 'w', 'w', '.', 't', 'i', 't', 'u', 'f', '.', 'c', 'o', 'm', 'm','.','s','s'};
        test(chars, 0, chars.length);


        System.out.println(Arrays.toString(chars) + " - " + chars.length);
    }

    //[0      3         ) [9         14)
    //[start,start_next,) [end_next,end)
    private static void test(char s[], int start, int end) {
        System.out.println(Arrays.toString(s) + " - " + s.length + " [" + start + "," + end + ")");

        int start_next = start;
        for (int i = start; i < s.length; i++) {
            if (s[i] == '.') {
                start_next = i;
                break;
            }
        }

        System.out.println("[" + start + "," + start_next + ")");

        //[start,start_next)
        int end_next = end;
        for (int i = end - 1; i >= 0; i--) {
            if (s[i] == '.') {
                end_next = i;
                break;
            }
        }
        //(end_next,end_next-1]
        System.out.println("[" + (end_next + 1) + "," + end + ")");

        // 需要对换的数据[start,start_next)  -  [end_next+1,end)
        // {'w', 'w', 'w', '.', 't', 'i', 't', 'u', 'f', '.', 'c', 'o', 'm', 'm'}
        if (start_next <= end_next) {

            if (start_next == end_next) {
                //交换
                exchange(s, start, start_next, end_next + 1, end);

            } else {
                //交换
                exchange(s, start, start_next, end_next + 1, end);

                //进入下一个级别的对换
                //[start_next+1,end_next)

                int startTmp = start_next - start;
                int endTmp = end - end_next - 1;

                if (startTmp == endTmp) {
                    test(s, start_next + 1, end_next);
                } else if (startTmp < endTmp) {

                    int tmp = endTmp - startTmp;

                    test(s, start_next + 1 + tmp, end_next + tmp);
                } else {
                    //startTmp > endTmp
                    int tmp = startTmp - endTmp;
                    test(s, start_next + 1 - tmp, end_next - tmp);
                }


            }
        } else {
            //do nothing
            return;
        }
    }

    // 需要对换的数据[start,start_next)  -  [end_next+1,end)
    //              0           3           10          14
    // {'w', 'w', 'w', '.', 't', 'i', 't', 'u', 'f', '.', 'c', 'o', 'm', 'm'}
    private static void exchange(char[] arr, int start, int start_next, int end_next, int end) {

        System.out.println("[" + start + "," + start_next + ") [" + end_next + "," + end + ")");

        char tmp;
        int sTmp = start;
        int eTmp = end_next;
        for (; sTmp < start_next && eTmp < end; sTmp++, eTmp++) {
            tmp = arr[sTmp];
            arr[sTmp] = arr[eTmp];
            arr[eTmp] = tmp;
        }


        for (; sTmp < start_next; sTmp++) {

            tmp = arr[sTmp];
            //字符前移
            for (int s = sTmp + 1; s < end; s++) {
                arr[s - 1] = arr[s];
            }
            arr[end - 1] = tmp;
        }


        // System.out.println("sTmp- " + sTmp + "  eTmp- " + eTmp);
        for (; eTmp < end; eTmp++) {
            tmp = arr[eTmp];
            //后移
            for (int s = eTmp - 1; s >= sTmp; s--) {
                arr[s + 1] = arr[s];
            }
            arr[sTmp++] = tmp;
        }
    }
}
