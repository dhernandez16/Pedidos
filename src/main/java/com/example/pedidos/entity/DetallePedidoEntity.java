package com.example.pedidos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="detalle_pedido")
public class DetallePedidoEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824535769826609242L;

	@Id
	@GeneratedValue
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_detpedido")
	@SequenceGenerator(name="seq_detpedido", sequenceName = "seq_detpedido")*/
	@Column(name = "id_detalle_pedido")
	private Long idDetPedido;
	
	@NotNull(message="La clave del producto no puede ir vacio")
	@NotEmpty(message="La clave del producto no puede ir vacio")
	@Column(name="clave_producto")
	private String claveProducto;
	
	@NotNull(message="El precio no puede ir vacio")
	private Double precio;
	
	@NotNull(message="La cantidad no puede ir vacio")
	private Integer cantidad;
	
	private Double total;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_pedido_fk", nullable = false, updatable = false)
	private PedidoEntity pedido;
	
	
	@PrePersist
	public void prePersist() {
		
		total = precio * cantidad;
	}
	
	
}
