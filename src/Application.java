import model.Employee;
import model.dao.DepartmentDao;
import model.dao.EmployeeDao;
import model.dao.LoginDao;
import model.dao.ProfessionDao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {
    private final LoginDao loginDao;
    Map<String,String> login;
    private final DepartmentDao departmentDao;
    Map <String,String> departmentMap;
    private final ProfessionDao professionDao;
    Map <String,String[]> professionMap;
    private final EmployeeDao employeeDao;
    List<Employee> data;
    private final Scanner scanner;
    private boolean running;
    private String currentUser;
    List <Employee> findEmployee;
    String punktName;

    public Application(LoginDao loginDao,DepartmentDao departmentDao,
                       ProfessionDao professionDao,EmployeeDao employeeDao) {
        this.loginDao = loginDao;
        this.departmentDao = departmentDao;
        this.professionDao = professionDao;
        this.employeeDao = employeeDao;
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.currentUser = null;
        login = loginDao.allLogin();
        departmentMap = departmentDao.allDepartment();
        professionMap = professionDao.allProfession();
        data = employeeDao.allEmployee();
    }

    public void run() {
        while(running) {
            //Если пользователь авторизован
            if(currentUser != null) {
                selectionMenu();
            }
            else {
                mainMenu();
            }
        }
        loginDao.save();
        departmentDao.save();
        professionDao.save();
        employeeDao.save();
    }
    private void mainMenu() {
        System.out.println("------------ГЛАВНОЕ МЕНЮ------------");
        System.out.println("1 - Авторизация\n2 - Регистрация\n3 -  Выход");
        int action = scanner.nextInt();
        if(action == 1) {
            authHandler();
        }
        else if(action == 2) {
            registerHandler();
        }
        else if(action == 3) {
            this.running = false;
        }
        else {
            System.out.println("Неизвестная команда...");
        }
    }
    private void selectionMenu() {
        System.out.println("---------Выберите действие---------");
        System.out.println("1 - Список сотрудников\n2 - Найти сотрудника\n"+
                "3 - Отчеты\n4 - Выход");
        int action = scanner.nextInt();
        if(action == 1) {

        }
        else if(action == 2) {
            menuPunkt2();

        }
        else if(action == 3) {

        }
        else if(action == 4) {
//            logoutHandler();
            this.running = false;
        }
        else {
            System.out.println("Неизвестная команда...");
        }
    }
    private void registerHandler() {
        System.out.println("Введите Login:");
        String nickName = scanner.next();
        System.out.println("Введите Password:");
        String password = scanner.next();
        if (login.size()>0 && login.get(nickName).equals(password)){
            System.out.println("Такой пользователь уже существует.");
        }
        else {
            this.currentUser = nickName;
            login.put(nickName,password);
            System.out.println("Вы успешно зарегистрированы!");
        }
    }

    //Обработка авторизации
    private void authHandler() {
        System.out.println("Введите Login:");
        String nickName = scanner.next();
        System.out.println("Введите Password:");
        String password = scanner.next();
        if (login.size()>0 && login.get(nickName).equals(password)){
            this.currentUser = nickName;
            System.out.println("Вы успешно авторизованы...");
        }
        else {
            System.out.println("Неверный логин или пароль!");
        }
    }
    public void menuPunkt2(){
        int punkt;
        boolean quit = false;
        punkt=5;
        while(true){
            System.out.println("Найти сотрудника");

            System.out.println("1. по фамилии");
            System.out.println("2. по должности");
            System.out.println("3. по названию отдела");
            System.out.println("4. по фамилии начальника");
            System.out.println("5. Выход");

            System.out.println("Введите пункт меню:");
            if (scanner.hasNextInt())
                punkt = scanner.nextInt();
            else {
                System.out.println("Ошибка");
                break;
            }

            switch (punkt){
                case 1:
                    punktName = "по фамилии";
                    System.out.println("Введите Фамилию");
                    findEmployee(findEmployee);
                    break;
                case 2:
                    punktName = "по должности";
                    break;
                case 3:
                    punktName = "по названию отдела";
                    break;
                case 4:
                    punktName = "по фамилии начальника";
                    break;
                case 5:
                    quit=true;
                    break;
            }
            if (quit) break;
        }
    }
    public void findEmployee(List <Employee> findEmployee,Predicate<Employee> predicate){

        String str = scanner.nextLine();
        findEmployee=data.stream()
//                .filter(arr->arr.getSurname().equals(str))
                .filter(predicate)
                .collect(Collectors.toList());
        findEmployee.stream()
                .forEach(System.out::println);
        String filename="writeReports\\findEmployeeSurname.txt";
        writeFindEmployee(filename,str);
    }
    public void writeFindEmployee(String filename,String str){
        try(FileOutputStream fos = new FileOutputStream(filename);
            PrintWriter pw = new PrintWriter(fos)){
            pw.println("Список сотрудников "+punktName+": "+str);
            for (Employee employee : findEmployee)
                pw.println(employee);
        }
        catch(IOException ex){
            System.out.println("Нет сотрудников "+punktName+": "+str);
        }
    }
}
