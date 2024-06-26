package com.riwi.simulacro_prueba_spring_boot.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "course") //Nombre en la DB
@Data // Esta anotación trae: get, set, toString, equal hashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id // Especificando la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String course_name;
    @Column(columnDefinition = "TEXT")
    private String description;

//    Tiene 1 llave foránea
//    instructor_id

    // Muchos a 1 con user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id") // reference o constraint column (esta anotación hace la relación del constraint), name para colocarle como se va a llamar y referencedColumnName para colocar de donde viene, es decir, el nombre de la llave primaria, columnDefinition es para especificarle un nombre en específico
    private User userInstructor; // Con quien vamos a hacer la relación. Esto es lo mismo que va en el mappedBy

    // Relación de 1 a muchos con enrollment
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.EAGER, // Puede ser EAGER o LAZY, pero como queremos que nos traiga toda la lista, queremos que venga con el join de una vez, entonces lo dejamos como EAGER
            cascade = CascadeType.ALL,
            mappedBy = "courseId", // mappedBy es el nombre de quien lo está mapeando a él en la otra clase
            orphanRemoval = false
    )
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    //Relación de 1 a muchos con messages
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.EAGER, // Puede ser EAGER o LAZY, pero como queremos que nos traiga toda la lista, queremos que venga con el join de una vez, entonces lo dejamos como EAGER
            cascade = CascadeType.ALL,
            mappedBy = "courseId", // mappedBy es el nombre de quien lo está mapeando a él en la otra clase
            orphanRemoval = false
    )
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    //Relación de 1 a muchos con lesson
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.EAGER, // Puede ser EAGER o LAZY, pero como queremos que nos traiga toda la lista, queremos que venga con el join de una vez, entonces lo dejamos como EAGER
            cascade = CascadeType.ALL, // Que se actualicen todos los assignments
            mappedBy = "courseId", // mappedBy es el nombre de quien lo está mapeando a él en la otra clase
            orphanRemoval = false // Si se elimina esto, también se eliminan todas las submissions de esto
    )
    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();

}
