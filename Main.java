
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    //    Из коллеции объектов Person необходимо:
//    Найти количество несовершеннолетних (т.е. людей младше 18 лет).
//    Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
//    Получить отсортированный по фамилии список потенциально работоспособных
//    людей с высшим образованием в выборке (т.е. людей с высшим образованием
//    от 18 до 60 лет для женщин и до 65 лет для мужчин).
    /*
    Из созданной коллекции persons для каждого задания создавайте новый стрим методом stream() и далее применяйте к
    нему ряд промежуточных операций и одну терминальную:
 1. Для поиска несовершеннолетних используйте промежуточный метод filter() и терминальный метод count().
 2.Для получения списка призывников потребуется применить несколько промежуточных методов filter(), а также
 для преобразования данных из Person в String (так как нужны только фамилии) используйте метод map().
 Так как требуется получить список List<String> терминальным методом будет collect(Collectors.toList()).
 3.Для получения отсортированного по фамилии списка потенциально работоспособных людей с высшим образованием
необходимо применить ряд промежуточных методов filter(), метод sorted() в который нужно будет положить
компаратор по фамилиям Comparator.comparing(). Завершить стрим необходимо методом collect().
     */
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        System.out.println(persons.stream().filter(age -> age.getAge() < 18).count());
        System.out.println(persons.stream()
                .filter(family -> family.getSex().equals(Sex.MAN))
                .filter(age -> age.getAge() >= 18 && age.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList()));
        System.out.println(persons.stream()
                .filter(education -> education.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getSex().equals(Sex.MAN) && person.getAge() >= 18
                        && person.getAge() <= 65 ||
                        person.getSex().equals(Sex.WOMAN) && person.getAge() >= 18
                                && person.getAge() <= 60)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList()));
    }
}