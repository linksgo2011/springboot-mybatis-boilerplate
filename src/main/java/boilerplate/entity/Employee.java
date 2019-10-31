package boilerplate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Employee implements Serializable {
    private int id;
    private String name;
    private String age;
}
