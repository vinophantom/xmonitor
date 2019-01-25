package com.vino.xmonitor.ds;

/**
 * @author phantom
 */
public class CircularLinkedList<T> {

    /**
     * 头结点
     */
    public CircularLinkedElement<T> header = null;

    /**
     * 初始化链表
     */
    public void initList() {
        header = new CircularLinkedElement<T>();
        header.value = null;
        header.next = header;
    }

    /**
     * 插入链表
     */
    public void insertList(T o) {
        CircularLinkedElement<T> e = new CircularLinkedElement<T>();
        e.value = o;
        //第一次插入元素
        if (header.next == header) {
            header.next = e;
            e.next = header;
        } else{//不是第一次插入元素
            //temp引用在栈中，temp和header引用都指向堆中的initList()中new的CircularLinkedElement<T>对象
            CircularLinkedElement<T> temp = header;
            //寻找最后一个元素
            while (temp.next != header) {
                temp = temp.next;
            }
            temp.next = e;
            //新插入的最后一个节点指向头结点
            e.next = header;
        }
    }

    /**
     * 删除链表中第i个元素
     */
    void deletelist(T o) {
        CircularLinkedElement<T> temp = header;
        while (temp.next != header) {
            //判断temp当前指向的结点的下一个结点是否是要删除的结点
            if (temp.next.value.equals(o)) {
                //删除结点
                temp.next = temp.next.next;
            } else {
                //temp“指针”后移
                temp = temp.next;
            }
        }
    }

    /**
     * 获取链表的第i个位置的元素
     */
    CircularLinkedElement<T> getCircularLinkedElement(int i) {
        if (i <= 0 || i > size()) {
            System.out.println("获取链表的位置有误！返回null");
            return null;
        } else {
            int count = 0;
            CircularLinkedElement<T> element = new CircularLinkedElement<T>();
            CircularLinkedElement<T> temp = header;
            while (temp.next != header) {
                count++;
                if (count == i) {
                    element.value = (T) temp.next.value;
                }
                temp = temp.next;
            }
            return element;
        }
    }

    /**
     * 链表长度
     */
    int size() {
        CircularLinkedElement<T> temp = header;
        int size = 0;
        while (temp.next != header) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    /**
     * 判断链表中是否存在某元素
     */
    Boolean isContain(T o) {
        CircularLinkedElement<T> temp = header;
        while (temp.next != header) {
            if (temp.next.value.equals(o)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    /**
     * 打印链表
     * */
//    void print()
//    {
//        System.out.print("打印链表：");
//        CircularLinkedElement<T> temp =header;
//        while(temp.next!=header)
//        {
//            temp=temp.next;
//            System.out.print(temp.value+"\t");
//        }
//        System.out.println();
//    }
}
