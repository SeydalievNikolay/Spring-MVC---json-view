package org.seydaliev.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.hibernate.validator.constraints.NotBlank;
import org.seydaliev.service.Views;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @CollectionTable(name="order_items", joinColumns=@JoinColumn(name="order_id"))
    @AttributeOverride(name="quantity", column=@Column(name="item_quantity"))
    @JsonView(Views.UserDetails.class)
    private List<AbstractReadWriteAccess.Item> items;
}
