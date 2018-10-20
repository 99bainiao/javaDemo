import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

/**
 * <p>Description:以xml文件作为数据库的console交互界面</p>
 *
 * @author Cheng
 * @version 1.0
 * @date 2018/10/20
 */
public class Demo1 {

    public static void main(String[] args) throws IOException, DocumentException {
        while (true) {
            // 样式
            style();
            // 读取操作
           BufferedReader bufferedReader =
                   new BufferedReader(new InputStreamReader(System.in));
           String option = bufferedReader.readLine();

           // 判断是否有xml这个文件,路径按实际更改,不存在的话，2,3,4不能进行
            File file = new File("d://contact.xml");
            boolean flag = false;
            if (file.exists()) {
                flag = true;
            }
            if ("1".equals(option)) {
                System.out.println("--- 添加联系人 ---");
                addContact(bufferedReader,file);
            } else if ("2".equals(option) && flag) {
                System.out.println("--- 修改联系人 ---");
                editContact(bufferedReader);
            } else if ("3".equals(option) && flag) {
                System.out.println("--- 删除联系人 ---");
                delContact(bufferedReader);
            } else if ("4".equals(option) && flag) {
                System.out.println("--- 查询所有联系人 ---");
                selectContact();
            } else if ("q".equalsIgnoreCase(option)) {
                break;
            }
        }

    }
    public static void selectContact() throws DocumentException {
        File file = new File("d://contact.xml");
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);

        List<Node> list = document.selectNodes("//Contact");
        for (Node node : list) {
            Element el = (Element) node;
            StringBuilder sb = new StringBuilder();
            sb.append("编号："+el.attributeValue("id")+
                    "，姓名："+el.elementText("name")+
                    "，年龄："+el.elementText("age")+
                    "，性别："+ el.elementText("gender")+
                    "，电话："+el.elementText("phone")+
                    "，邮箱："+el.elementText("email"));
            System.out.println(sb.toString());
        }
    }
    public static void delContact(BufferedReader bufferedReader) throws IOException, DocumentException {
        System.out.println("请输入编号");
        String id = bufferedReader.readLine();
        File file = new File("d://contact.xml");
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);

        Element contact = (Element) document.selectSingleNode("//Contact[@id='"+id+"']");
        contact.detach();
        write(document);
        System.out.println("删除成功");
    }
    public static void editContact(BufferedReader bufferedReader) throws IOException, DocumentException {
        System.out.println("请输入联系人编号");
        String id = bufferedReader.readLine();

        File file = new File("d://contact.xml");
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element contact = (Element) document.selectSingleNode("//Contact[@id='"+id+"']");
        if (contact == null) {
            System.out.println("对不起没查到");
            return ;
        }
        System.out.println("请输入姓名");
        String name = bufferedReader.readLine();

        System.out.println("请输入性别");
        String gender = bufferedReader.readLine();

        System.out.println("请输入年龄");
        String age = bufferedReader.readLine();

        System.out.println("请输入电话");
        String phone = bufferedReader.readLine();

        System.out.println("请输入邮箱");
        String email = bufferedReader.readLine();

        contact.element("name").setText(name);
        contact.element("gender").setText(gender);
        contact.element("age").setText(age);
        contact.element("phone").setText(phone);
        contact.element("email").setText(email);

        write(document);
        System.out.println("编辑成功");
    }
    public static void addContact(BufferedReader bufferedReader,File file) throws IOException, DocumentException {

            System.out.println("请输入编号");
            String id = bufferedReader.readLine();

            System.out.println("请输入姓名");
            String name = bufferedReader.readLine();

            System.out.println("请输入性别");
            String gender = bufferedReader.readLine();

            System.out.println("请输入年龄");
            String age = bufferedReader.readLine();

            System.out.println("请输入电话");
            String phone = bufferedReader.readLine();

            System.out.println("请输入邮箱");
            String email = bufferedReader.readLine();


            Document document = null;
            Element rootEl = null;
            if (file.exists()) {
                SAXReader saxReader = new SAXReader();
                document = saxReader.read(file);
                rootEl = document.getRootElement();

            } else {
                document = DocumentHelper.createDocument();
                rootEl = document.addElement("ContactList");
            }

        rootEl.addText("\r\n    ");

        Element contactEl = rootEl.addElement("Contact");
        contactEl.addAttribute("id",id);
        contactEl.addText("\r\n         ");

        Element nameEl = contactEl.addElement("name");
        nameEl.addText(name);
        contactEl.addText("\r\n         ");

        Element ageEl = contactEl.addElement("age");
        ageEl.addText(age);
        contactEl.addText("\r\n         ");

        Element genderEl = contactEl.addElement("gender");
        genderEl.addText(gender);
        contactEl.addText("\r\n         ");

        Element phoneEl = contactEl.addElement("phone");
        phoneEl.addText(phone);
        contactEl.addText("\r\n         ");

        Element emailEl = contactEl.addElement("email");
        emailEl.addText(email);
        contactEl.addText("\r\n    ");
        rootEl.addText("\r\n");
        write(document);
        System.out.println("添加成功");
    }
    public static void write(Document document) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("d://contact.xml");
        OutputFormat outputFormat = new OutputFormat();
        outputFormat.createPrettyPrint();
        outputFormat.setEncoding("utf-8");

        XMLWriter xmlWriter = new XMLWriter(fileOutputStream,outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    public  static void style(){
        System.out.println("================");
        System.out.println("【1】添加联系人\n【2】修改联系人\n【3】删除联系人\n" +
                "【4】查询所有联系人\n" +
                "【Q】退出程序");
        System.out.println("================");
    }
}
