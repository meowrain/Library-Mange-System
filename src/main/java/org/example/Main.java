package org.example;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static List<Book> LIST;

    public static void main(String[] args) {

        load(); //初始化程序
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("========图书管理系统========");
            System.out.println("1.录入书籍信息");
            System.out.println("2.查询书籍信息");
            System.out.println("3.删除书籍");
            System.out.println("4.修改书籍信息");
            System.out.println("5.获取所有图书信息");
            System.out.println("6.保存并退出系统");
            System.out.println("==========================");
            System.out.println("请输入编号：");
            int select = sc.nextInt();//读入选择
            switch (select) {
                case 1:
                    insert(sc);
                    break;
                case 2:
                    search(sc);
                    break;
                case 3:
                    delete(sc);
                    break;
                case 4:
                    modify(sc);
                    break;
                case 5:
                    getAllBooks();
                    break;
                case 6:
                    exit_system();
            }

        }
    }

    private static void load() { //初始化程序
        System.out.println("正在初始化程序.......");
        File file = new File("database");
        if (file.exists()) {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("database"))) {
                LIST = (List<Book>) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            LIST = new LinkedList<>();
        }

    }

    private static void save() { //保存图书信息到数据库
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("database"))) {
            stream.writeObject(LIST);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void insert(Scanner sc) { //录入书籍信息
        sc.nextLine();
        System.out.println("\uD83D\uDC96请输入书籍信息:");
        System.out.print("\uD83D\uDC96书名:");
        String title = sc.nextLine();
        System.out.print("\uD83D\uDC96作者:");
        String author = sc.nextLine();
        System.out.print("\uD83D\uDC96价格:");
        int price = sc.nextInt();
        if (price <= 0) {
            System.out.println("😅价格不能小于等于0！请重新输入！");
            return;
        }
        Book book = new Book(title, author, price);
        sc.nextLine();
        LIST.add(book);
        System.out.println("信息录入成功!");
    }

    private static void getAllBooks() { //获取所有图书信息
        System.out.println("目前有📕数量： " + LIST.size());
        for (int i = 1; i <= LIST.size(); i++) {
            System.out.println(i + "." + LIST.get(i - 1));
        }
    }

    private static void search(Scanner sc) { //获取指定id书本信息
        System.out.println("请输入书本id");
        int id = sc.nextInt();
        if (id <= 0 && id >= LIST.size()) {
            System.out.println("输入的id错误");
        } else {
            System.out.println(LIST.get(id - 1));
        }

    }

    private static void delete(Scanner sc) { //删除书籍
        sc.nextLine();
        System.out.print("请输入要删除得图书id： ");
        int id = sc.nextInt();
        if (id <= 0 || id >= LIST.size()) {
            System.out.println("输入的id错误");
        } else {
            LIST.remove(id - 1);
            System.out.println("书籍信息删除成功！❤");
        }
    }

    private static void modify(Scanner sc) { //修改书籍信息
        sc.nextLine();
        System.out.print("请输入要修改的图书id： ");
        int id = sc.nextInt();
        if (id <= 0 || id >= LIST.size()) {
            System.out.println("输入的id错误");
        } else {
            Book book = LIST.get(id - 1);
            System.out.print("\uD83D\uDC96书名:");
            sc.nextLine();
            book.setTitle(sc.nextLine());
            System.out.print("\uD83D\uDC96作者:");
            book.setAuthor(sc.nextLine());
            System.out.print("\uD83D\uDC96价格:");
            book.setPrice(sc.nextInt());
            sc.nextLine();
        }
    }

    private static void exit_system() { //保存并退出系统
        System.out.println("正在写入数据....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        save();
        System.out.println("数据录入成功！");
        System.out.println("系统退出成功!感谢你的使用！");
        System.exit(0);
    }
}

