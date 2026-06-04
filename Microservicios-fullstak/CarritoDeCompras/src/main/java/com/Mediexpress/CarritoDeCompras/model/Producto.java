package com.Mediexpress.CarritoDeCompras.model;

import lombok.Data;

@Data
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;
}