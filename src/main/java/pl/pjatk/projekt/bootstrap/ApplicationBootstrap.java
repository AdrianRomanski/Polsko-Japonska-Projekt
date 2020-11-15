package pl.pjatk.projekt.bootstrap;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.pjatk.projekt.model.animal.Bird;
import pl.pjatk.projekt.model.animal.Dog;
import pl.pjatk.projekt.model.books.Author;
import pl.pjatk.projekt.model.books.Book;
import pl.pjatk.projekt.model.events.Course;
import pl.pjatk.projekt.model.events.Expert;
import pl.pjatk.projekt.model.events.Lecture;
import pl.pjatk.projekt.repositories.AuthorRepository;
import pl.pjatk.projekt.repositories.BookRepository;
import pl.pjatk.projekt.repositories.animal.DogRepository;
import pl.pjatk.projekt.repositories.events.CourseRepository;
import pl.pjatk.projekt.repositories.events.ExpertRepository;
import pl.pjatk.projekt.repositories.events.LectureRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final DogRepository dogRepository;
    private final BookRepository bookRepository;
    private final ExpertRepository expertRepository;
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;


    @Override
    @SneakyThrows // bardzo wazne
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Author author = Author.builder()
                .firstName("Andrzej")
                .lastName("Pilipiuk")
                .ageOfBirth(LocalDate.of(1992,11,3))
                .build();

        authorRepository.save(author);

        Book book = Book.builder().name("Achaja").build();

        author.setBook(book);
        book.setAuthor(author);

        bookRepository.save(book);
        authorRepository.save(author);

        Bird bird = Bird.builder().color("Blue").velocity(22.4).wingsType("Long").build();
        Dog dog = Dog.builder().furType("Nice").weight(22.2).build();


        bookRepository.save(book);
        dogRepository.save(dog);

        // EVENTS
        Expert expert = Expert.builder()
                .firstName("Janusz")
                .lastName("Tracz")
                .specialization("Various")
                .build();



        Lecture lecture = Lecture.builder()
                .expert(expert)
                .numberOfLectures(3)
                .availableDuringCovid(true)
                .subject("Wstęp")
                .eventStarts(LocalDate.now())
                .eventEnds(LocalDate.now())
                .build();

        Lecture lecture2 = Lecture.builder()
                .expert(expert)
                .numberOfLectures(15)
                .availableDuringCovid(false)
                .subject("TAK")
                .build();

        expert.getLectures().addAll(Arrays.asList(lecture, lecture2));


        expertRepository.save(expert);

        lectureRepository.save(lecture);
        lectureRepository.save(lecture2);

        Course course = Course.builder()
                .lectures(List.of(lecture, lecture2))
                .eventStarts(LocalDate.now())
                .eventEnds(LocalDate.now())
                .build();

        courseRepository.save(course);
    }
}
