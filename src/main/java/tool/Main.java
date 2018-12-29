package tool;

import io.xjar.boot.XBoot;

import java.io.File;
import java.util.Scanner;
import java.util.UUID;

public class Main {

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {

    System.out.println("请输入源文件地址：");
    Scanner scanner = new Scanner(System.in);
    String src = scanner.nextLine();

    String dest = src.substring(0, src.indexOf(".jar"))+".xjar";
    String uuid = UUID.randomUUID().toString();

    XBoot.encrypt(new File(src), new File(dest),
        uuid, (entry) -> {
          String name = entry.getName();
          return name.startsWith("BOOT-INF/classes/com");
        });

    System.out.println("加密完成！密码是："+ uuid);
  }
}
