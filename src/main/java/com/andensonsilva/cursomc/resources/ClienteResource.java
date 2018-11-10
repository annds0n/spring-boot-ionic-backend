package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.dto.ClienteDTO;
import com.andensonsilva.cursomc.dto.ClienteNewDTO;
import com.andensonsilva.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;


    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll() {

        List<Cliente> clientes = this.service.listar();

        List<ClienteDTO> clienteDTOs = clientes.stream().map(c -> new ClienteDTO(c)).collect(Collectors.toList());

        return ResponseEntity.ok().body(clienteDTOs);
    }

    /*
     * Colocar o tamanho de página 24 por padrão
     * */
    @PreAuthorize("hasAnyRole('ADMIN')") // Apenas usuários com oermissão ADMIN, podem acessar o end-point
    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String order
    ) {

        Page<Cliente> clientes = this.service.encontrarPagina(page, size, orderBy, order);

        Page<ClienteDTO> clienteDTOs = clientes.map(c -> new ClienteDTO(c));

        return ResponseEntity.ok().body(clienteDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) {

        Cliente cliente = this.service.buscar(id);

        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO c) {
        Cliente cliente = this.service.converterDTOparaCliente(c);
        cliente = service.inserir(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                          .path("/{id}").buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){

        Cliente cliente = this.service.converterDTOparaCliente(clienteDTO);
        cliente.setId(id);

        cliente = this.service.atualizar(cliente);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.remover(id);

        return ResponseEntity.noContent().build();
    }

}
