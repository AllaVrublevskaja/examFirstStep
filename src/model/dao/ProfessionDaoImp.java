package model.dao;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProfessionDaoImp implements ProfessionDao{
    String fileName;
    Map<String,String[]> professionalMap = new HashMap<>();
//    String[] depArr;

    public ProfessionDaoImp(String fileName) {
        this.fileName = fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String[][] lines = br.lines()
                    .skip(1)
                    .map(s -> s.split(","))
                    .toArray(String[][]::new);
            Set <String> dep = Arrays.stream(lines)
                    .map(arr ->arr[0])
                    .collect(Collectors.toSet());

            for (String str : dep) {
                String[] depArr = Arrays.stream(lines)
                        .filter(arr -> arr[0].equals(str))
                        .map(arr -> arr[1])
                        .toArray(String[]::new);
                professionalMap.put(str,depArr);
            }
        }
        catch(IOException ex) {
            professionalMap = new HashMap<>();
        }
    }

    @Override
    public void save() {
        try(FileOutputStream outputStream = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println("department,professional");
            for(Map.Entry<String,String[]> entry : professionalMap.entrySet())
                for (String strings : entry.getValue())
                    writer.println(entry.getKey()+","+strings);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Map<String, String[]> allProfession() {
        return professionalMap;
    }
}
