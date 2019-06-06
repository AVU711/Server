

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Commands {
    private CatVector catVector;

    public Commands(CatVector catVector){
        this.catVector = catVector;
    }

    public String doSave()  {
        try {
            FileWriter fileWriter = new FileWriter(System.getenv("Start"));
            for (Cat elem : catVector.Home) {
                String string = elem.getName() + "," + elem.getAge() + "," + elem.getVolume() + "," + elem.getPosition().getX() + ',' + elem.getPosition().getY() + "," + elem.getDate();
                fileWriter.write(string);
                fileWriter.write('\n');
            }
            fileWriter.close();
            return "Коллекция сохранена.";
        }catch (IOException e){
            return ("Проблема с файлом.");
        }

    }

    public String doCommand(Request obj){
        // Нужен импорт
        switch (obj.getCommand()) {
            case "save":
                try {
                    File file = new File(System.getenv("Start"));
                    FileWriter fileWriter = new FileWriter(file);
                    for (Cat cat : catVector.Home) {
                        fileWriter.write(cat.getName() + ',' + cat.getAge() + ',' + cat.getPosition().getX() + ',' + cat.getPosition().getY() + ',' + cat.getVolume() + ','
                                + cat.getDate() + '\n');
                    }
                    fileWriter.close();
                    return "Коллекция сохранилась.";

                }catch (FileNotFoundException e) {
                    return ("Не удаётся открыть файл");
                }catch (IOException e){
                    return ("Файл не открыть.");
                }
            case "add":
                int x = catVector.Home.size();
                if (obj.getDate() != null) {
                    catVector.add(new Cat(obj.getName(), obj.getAge(), obj.getVolume(), obj.getPosition().getX(), obj.getPosition().getY(), obj.getDate()));
                    if (catVector.Home.size() - x == 1) {
                        return "Добавление прошло успешно";
                    } else {
                        return "Что-то пошло не так";
                    }
                }else{
                    catVector.add(new Cat(obj.getName(), obj.getAge(), obj.getVolume(),obj.getPosition().getX(),obj.getPosition().getY()));
                    if (catVector.Home.size() - x == 1) {
                        return "Добавление прошло успешно";
                    } else {
                        return "Что-то пошло не так";
                    }
                }

            case "remove":
                x = catVector.Home.size();
                catVector.remove(new Cat(obj.getName(), obj.getAge(), obj.getVolume(), obj.getPosition().getX(), obj.getPosition().getY()));
                if (x - catVector.Home.size() == 1) {
                    return "Удаление прошло успешно";
                } else {
                    return "Что-то пошло не так";
                }

            case "remove_greater":
                x = catVector.Home.size();
                catVector.remove_greater(new Cat(obj.getName(), obj.getAge(), obj.getVolume(), obj.getPosition().getX(), obj.getPosition().getY()));
                if (x > catVector.Home.size()) {
                    return "Удаление прошло успешно";
                } else {
                    return "Что-то пошло не так";
                }
            case "help":
                return "Команда add позволяет пользователю добавить элемент в коллекцию.\nФормат ввода : add{ name : String, age : int, x : int, y : int Stomach{ volume : int}}\nКоманда remove позволяет пользователю удалить элемент из коллекции.\nФормат ввода : remove{ name : String, age : int, x : int, y : int, Stomach{ volume : int}}\nКоманда remove_greater позволяет пользователю удалить элементы, которые старше данного, из коллекции.\nФормат ввода : remove_greater{ name : String, age : int, x : int, y : int, Stomach{ volume : int}}\nКоманда info предоставляет пользователю информацию о коллекции.\nФормат ввода : info\nКоманда show предоставляет пользователю информацию о содержимом коллекции в строковом представлении.\nФормат ввода : show\nКоманда save сохраняет содержимое коллекции в файл.\nФормат ввода : save\nКоманда import передаёт программе адрес файла\nФормат ввода : import{String}";
            case "show":
                String string = "";
                for (Object elem : catVector.Home.stream().sorted(Comparator.comparing(Cat::getAge)).toArray()){
                    Cat elem1 = (Cat) elem;
                    string += elem1.toString() + "\n";
                }
                return string;
            case "info":
                return "Тип коллекции - Vector\nДата инициализации " + catVector.dataInizialization.toString()+"\nКоличество элементов " + catVector.Home.size();
            default:
                return "";
        }
    }
}
