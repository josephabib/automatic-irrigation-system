package com.banque.misr.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table
public class Utility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int utilityId;

    @Column
    String service;
}
