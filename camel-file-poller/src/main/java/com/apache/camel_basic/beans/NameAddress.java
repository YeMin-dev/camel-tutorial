package com.apache.camel_basic.beans;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "NAME_ADDRESS")
@NamedQuery(name = "fetchAllRows", query = "select x from NameAddress x")
public class NameAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "house_number")
    private String houseNumber;
    private String city;
    private String province;

    @Column(name = "postal_code")
    private String postalCode;

}
