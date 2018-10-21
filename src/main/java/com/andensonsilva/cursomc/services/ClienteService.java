package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Cidade;
import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.domain.Endereco;
import com.andensonsilva.cursomc.domain.enums.TipoCliente;
import com.andensonsilva.cursomc.dto.ClienteDTO;
import com.andensonsilva.cursomc.dto.ClienteNewDTO;
import com.andensonsilva.cursomc.repositories.ClienteRepository;
import com.andensonsilva.cursomc.repositories.EnderecoRepository;
import com.andensonsilva.cursomc.services.exceptions.DataIntegrityException;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public Cliente buscar(Integer id) {

        Optional<Cliente> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Cliente não encontrado. Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente inserir(Cliente c) {
        c.setId(null);
        c = this.repository.save(c);
        enderecoRepository.saveAll(c.getEnderecos());
        return c;
    }

    public Cliente atualizar(Cliente c) {
        Cliente cliente = this.buscar(c.getId());

        updateData(cliente, c);

        return this.repository.save(c);
    }

    public void remover(Integer id) {
        buscar(id);
        try{

            this.repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cliente com entidades relacionadas");
        }
    }

    public List<Cliente> listar() {
        return  this.repository.findAll();
    }

    public Page<Cliente> encontrarPagina(Integer page, Integer size, String orderBy, String order) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(order), orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente converterDTOparaCliente(ClienteDTO clienteDTO) {

        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    public Cliente converterDTOparaCliente(ClienteNewDTO clienteNewDTO) {

        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()));
        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, new Cidade(clienteNewDTO.getCidadeId(), null, null ));

        cliente.getEnderecos().add(endereco);

        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        cliente.getTelefones().add(clienteNewDTO.getTelefone1() == null ? null : clienteNewDTO.getTelefone2());
        cliente.getTelefones().add(clienteNewDTO.getTelefone1() == null ? null : clienteNewDTO.getTelefone3());

        return cliente;

    }

    /*
    * Para atualizar apenas os campos possíveis*/
    private void updateData(Cliente cliente, Cliente clientenewData) {
        cliente.setNome(clientenewData.getNome());
        cliente.setEmail(clientenewData.getEmail());
    }

}
