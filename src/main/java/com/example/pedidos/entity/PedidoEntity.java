package com.example.pedidos.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "pedido")
public class PedidoEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6233315110703581577L;
	
	
	@Id
	@GeneratedValue
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
	@SequenceGenerator(name="seq_pedido", sequenceName = "seq_pedido")*/
	@Column(name = "id_pedido")
	private Long idPedido;
	
	@NotNull(message="El nombre del Ciente no puede ir vacio")
	@NotEmpty(message="El nombre del Ciente no puede ir vacio")
	@Column(name="nombre_cliente")
	private String nombreCliente;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="fecha_compra")
	private Date fechaCompra;
	
	private Double total;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
	private List<DetallePedidoEntity> detallePedido;
	
	
	@PrePersist
	public void prePersist() {
		
		fechaCompra = new Date();
		total = detallePedido
				.stream()
				.mapToDouble(item -> item.getTotal() == null ?item.getCantidad() *item.getPrecio() :item.getTotal())
				.sum();
				
	}
	

}
