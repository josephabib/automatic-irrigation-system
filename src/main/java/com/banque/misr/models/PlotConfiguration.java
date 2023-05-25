package com.banque.misr.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
public class PlotConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotNull
    private String timeSlot;

    @Column
    @NotNull
    private int waterAmount;

//    @OneToOne(mappedBy = "plotConfiguration")
//    private Plot plot;
}
