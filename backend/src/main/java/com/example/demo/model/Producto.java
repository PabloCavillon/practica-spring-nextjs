package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
  @Id
  private String id = UUID.randomUUID().toString();

  @Column(nullable = false)
  private String nombre;

  private String descripcion;

  @Column(nullable = false)
  private double precio;

  @Column(nullable = false)
  private int stock;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

}
