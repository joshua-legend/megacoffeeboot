package com.example.megacoffee.Coffee;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
//new Coffee()
@AllArgsConstructor
//new Coffee(id,name,price)
@RequiredArgsConstructor
//new Coffee(name,price) by NonNull ok?
@Entity
@Table(name = "coffee")
public class Coffee {
    //DB key 설정 및 자동증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int price;
}
