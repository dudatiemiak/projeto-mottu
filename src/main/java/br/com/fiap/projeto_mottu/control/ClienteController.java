package br.com.fiap.projeto_mottu.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.model.Bairro;
import br.com.fiap.projeto_mottu.model.Cidade;
import br.com.fiap.projeto_mottu.model.Cliente;
import br.com.fiap.projeto_mottu.model.Estado;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.model.Logradouro;
import br.com.fiap.projeto_mottu.model.Pais;
import br.com.fiap.projeto_mottu.model.Telefone;
import br.com.fiap.projeto_mottu.repository.ClienteRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import br.com.fiap.projeto_mottu.repository.LogradouroRepository;
import br.com.fiap.projeto_mottu.repository.BairroRepository;
import br.com.fiap.projeto_mottu.repository.CidadeRepository;
import br.com.fiap.projeto_mottu.repository.EstadoRepository;
import br.com.fiap.projeto_mottu.repository.PaisRepository;
import br.com.fiap.projeto_mottu.repository.TelefoneRepository;
import jakarta.validation.Valid;

@Controller
public class ClienteController {
	@Autowired
	private BairroRepository repB;
	@Autowired
	private CidadeRepository repCid;
	@Autowired
	private EstadoRepository repE;
	@Autowired
	private PaisRepository repP;

	@Autowired
	private ClienteRepository repC;
	
	@Autowired
	private LogradouroRepository repL;
	
	@Autowired
	private TelefoneRepository repT;
	
	@Autowired
	private FuncionarioRepository repFunc;
	
	@GetMapping("/cliente/lista")
    public ModelAndView listarClientes() {
        ModelAndView mv = new ModelAndView("/cliente/lista"); 
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
		
		if(op.isPresent()) {
			mv.addObject("funcionario", op.get());
		}
        
        mv.addObject("clientes", repC.findAll());
        return mv;
    }

    @GetMapping("/cliente/nova")
    public ModelAndView formularioNovoCliente() {        
        ModelAndView mv = new ModelAndView("/cliente/nova");
        mv.addObject("cliente", new Cliente());
        mv.addObject("logradouros", repL.findAll());
        mv.addObject("telefones", repT.findAll());
        return mv;
    }

    @PostMapping("/cliente/inserir")
	public ModelAndView inserirCliente(@Valid Cliente cliente, BindingResult bd) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cliente/nova");
			mv.addObject("cliente", cliente);
			return mv;
		}

		// Salvar/associar país
		String nomePais = cliente.getLogradouro().getBairro().getCidade().getEstado().getPais().getNm_pais();
		Pais pais = repP.findAll().stream()
			.filter(p -> p.getNm_pais().equalsIgnoreCase(nomePais))
			.findFirst()
			.orElseGet(() -> {
				Pais novo = new Pais();
				novo.setNm_pais(nomePais);
				return repP.save(novo);
			});

		// Salvar/associar estado
		String uf = cliente.getLogradouro().getBairro().getCidade().getEstado().getNm_estado();
		Estado estado = repE.findAll().stream()
			.filter(e -> e.getNm_estado().equalsIgnoreCase(uf) && e.getPais().equals(pais))
			.findFirst()
			.orElseGet(() -> {
				Estado novo = new Estado();
				novo.setNm_estado(uf);
				novo.setPais(pais);
				return repE.save(novo);
			});

		// Salvar/associar cidade
		String nomeCidade = cliente.getLogradouro().getBairro().getCidade().getNm_cidade();
		Cidade cidade = repCid.findAll().stream()
			.filter(c -> c.getNm_cidade().equalsIgnoreCase(nomeCidade) && c.getEstado().equals(estado))
			.findFirst()
			.orElseGet(() -> {
				Cidade novo = new Cidade();
				novo.setNm_cidade(nomeCidade);
				novo.setEstado(estado);
				return repCid.save(novo);
			});

		// Salvar/associar bairro
		String nomeBairro = cliente.getLogradouro().getBairro().getNm_bairro();
		Bairro bairro = repB.findAll().stream()
			.filter(b -> b.getNm_bairro().equalsIgnoreCase(nomeBairro) && b.getCidade().equals(cidade))
			.findFirst()
			.orElseGet(() -> {
				Bairro novo = new Bairro();
				novo.setNm_bairro(nomeBairro);
				novo.setCidade(cidade);
				return repB.save(novo);
			});

		// Salvar logradouro
		Logradouro logradouro = cliente.getLogradouro();
		logradouro.setBairro(bairro);
		repL.save(logradouro);
		cliente.setLogradouro(logradouro);

		// Salva cliente e todos os telefones associados
		if (cliente.getTelefones() != null) {
			cliente.getTelefones().forEach(t -> t.setCliente(cliente));
		}
		repC.save(cliente);
		return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/detalhes/{id}")
    public ModelAndView detalhesCliente(@PathVariable Long id) {
        Optional<Cliente> op = repC.findById(id);
        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/cliente/detalhes");
            mv.addObject("cliente", op.get());
            return mv;
        }
        return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editarClienteForm(@PathVariable Long id) {
        Optional<Cliente> op = repC.findById(id);
        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/cliente/editar");
				Cliente cliente = op.get();
				mv.addObject("cliente", cliente);
            mv.addObject("telefones", repT.findAll());
            return mv;
        }
        return new ModelAndView("redirect:/cliente/lista");
    }

    @PostMapping("/cliente/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Long id, @Valid Cliente cliente, BindingResult bd,
            String nr_ddd, String nr_ddi, String nr_telefone) {
		if (bd.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cliente/editar");
			mv.addObject("cliente", cliente);
			mv.addObject("logradouros", repL.findAll());
			mv.addObject("telefones", repT.findAll());
			return mv;
		}
		Optional<Cliente> op = repC.findById(id);
		if (op.isPresent()) {
			Cliente atual = op.get();
			System.out.println("[DEBUG] Email recebido para atualizar: " + cliente.getNm_email());
			atual.setNm_cliente(cliente.getNm_cliente());
			atual.setNr_cpf(cliente.getNr_cpf());
			atual.setNm_email(cliente.getNm_email());

			// Remove todos os telefones antigos
			atual.getTelefones().clear();

			// Adiciona novo telefone se os dados foram preenchidos
			if (nr_telefone != null && !nr_telefone.trim().isEmpty()) {
				Telefone novoTelefone = new Telefone();
				novoTelefone.setNr_ddd(nr_ddd);
				novoTelefone.setNr_ddi(nr_ddi);
				novoTelefone.setNr_telefone(nr_telefone);
				novoTelefone.setCliente(atual);
				atual.getTelefones().add(novoTelefone);
			}

			repC.save(atual);
		}
		return new ModelAndView("redirect:/cliente/lista");
    }

    @GetMapping("/cliente/remover/{id}")
    public ModelAndView removerClienteExistente(@PathVariable Long id) {
    	Optional<Cliente> op = repC.findById(id);
    	if(op.isPresent()) {
    		repC.deleteById(id);
            return new ModelAndView("redirect:/cliente/lista");
    	}
        return new ModelAndView("redirect:/cliente/lista");
    }

}
