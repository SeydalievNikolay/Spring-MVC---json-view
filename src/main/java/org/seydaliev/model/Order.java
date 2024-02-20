package org.seydaliev.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.seydaliev.service.Views;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.UserDetails.class)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    @JsonView(Views.UserDetails.class)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonView(Views.UserDetails.class)
    private Users users;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="order_items", joinColumns=@JoinColumn(name="order_id"))
    @Column(name="item")
    @JsonView(Views.UserDetails.class)
    private List<Item> items;
}
