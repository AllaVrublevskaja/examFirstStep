import model.Employee;
import model.dao.DepartmentDao;
import model.dao.EmployeeDao;
import model.dao.LoginDao;
import model.dao.ProfessionDao;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
}
