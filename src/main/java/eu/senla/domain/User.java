package eu.senla.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 10)
    Long id;

    String username;
    String email;
    String password;
    boolean isEnabled;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.MERGE)
    Set<Task> assignedTasks = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name = "users_to_tasks",
            joinColumns = @JoinColumn(name = "observer_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    Set<Task> observedTasks = new HashSet<>();

    public void addCreatedTask(Task task) {
        this.createdTasks.add(task);
        task.setAuthor(this);
    }

    public void addAssignedTask(Task task) {
        this.assignedTasks.add(task);
        task.setAssignee(this);
    }

    public void addObservedTasks(Task task) {
        task.getObservers().add(this);
        this.observedTasks.add(task);
    }
}