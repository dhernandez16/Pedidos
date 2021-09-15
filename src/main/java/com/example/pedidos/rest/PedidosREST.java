package com.example.pedidos.rest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.pedidos.DTO.AltaResponse;
import com.example.pedidos.entity.DetallePedidoEntity;
import com.example.pedidos.entity.PedidoEntity;
import com.example.pedidos.service.PedidoService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/pedidos")
public class PedidosREST {

	@Autowired
	private PedidoService pedidoService;

	protected static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	protected static Validator validator = factory.getValidator();

	@RequestMapping(value = "/agregarPedido", method = { RequestMethod.POST })
	public ResponseEntity<AltaResponse> agregarPedido(@RequestBody PedidoEntity pedido) {

		AltaResponse alta = new AltaResponse();
		
		try {
			Set<ConstraintViolation<PedidoEntity>> violations = validator.validate(pedido);
			for (ConstraintViolation<PedidoEntity> vi : violations) {
				throw new Exception(vi.getMessage());
			}
	
			for (DetallePedidoEntity detalle : pedido.getDetallePedido()) {
				Set<ConstraintViolation<DetallePedidoEntity>> violationsDet = validator.validate(detalle);
				for (ConstraintViolation<DetallePedidoEntity> vi : violationsDet) {
					throw new Exception(vi.getMessage());
				}
	
			}
			
			pedido = pedidoService.save(pedido);
	
			
			alta.setIdPedido(pedido.getIdPedido());
			alta.setMensaje(
					"El Pedido de " + pedido.getNombreCliente() + " se genero con el ticket: " + pedido.getIdPedido());
		}catch(Exception e){
			alta.setIdPedido(null);
			alta.setMensaje("El pedido no se pudo dar de alta: "+e.getMessage());
		}finally {
			
		}

		
		return ResponseEntity.ok(alta);
	}

	@RequestMapping(value = "/obtenerPedidos", method = { RequestMethod.GET })
	public ResponseEntity<List<PedidoEntity>> obtenerPedidos() {
		List<PedidoEntity> responsePedidos = (List<PedidoEntity>) pedidoService.findAll();
		return ResponseEntity.ok(responsePedidos);
	}

	@RequestMapping(value = "/obtenerPedidoId/{idPedido}", method = { RequestMethod.GET })
	public ResponseEntity<Optional<PedidoEntity>> obtenerPedidoId(@PathVariable Long idPedido) {
		Optional<PedidoEntity> responsePedido = pedidoService.findById(idPedido);
		return ResponseEntity.ok(responsePedido);
	}
}
