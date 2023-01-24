package model;

import java.util.function.Predicate;

public class EmployeePredicates {
    public static Predicate<Employee> surname(String str){
        return arr->arr.getSurname().equals(str);
    }
}
