package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service
public class ProductoService {

  private final ProductoRepository productoRepository;

  public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
  }

  public List<Producto> getAllProductos() {
    return productoRepository.findAll();
  }

  public Optional<Producto> getProductoById(String id) {
    return productoRepository.findById(id);
  }

  public Producto createProducto(Producto producto) {
      return productoRepository.save(producto);
  }

  public Producto updateProducto(String id, Producto producto) {
      producto.setId(id);
      return productoRepository.save(producto);
  }

  public void deleteProducto(String id) {
      productoRepository.deleteById(id);
  }

}
