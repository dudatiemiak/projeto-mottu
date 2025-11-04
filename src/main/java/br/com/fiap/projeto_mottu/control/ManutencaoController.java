package br.com.fiap.projeto_mottu.control;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.projeto_mottu.model.Manutencao;
import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.model.Moto;
import br.com.fiap.projeto_mottu.repository.ManutencaoRepository;
import br.com.fiap.projeto_mottu.repository.MotoRepository;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;
import jakarta.validation.Valid;

@Controller
public class ManutencaoController {

    @Autowired
    private ManutencaoRepository repManutencao;
    @Autowired
    private MotoRepository repMoto;
    @Autowired
    private FuncionarioRepository repFunc;

    @GetMapping("/manutencao/lista")
    public ModelAndView listarManutencoes(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "abertas", required = false) Boolean abertas) {
        ModelAndView mv = new ModelAndView("manutencao/lista");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
        op.ifPresent(func -> mv.addObject("funcionario", func));

        // Base: todas ordenadas por data de entrada
        List<Manutencao> lista = repManutencao.buscarTodasOrdenadasPorDataEntrada();

        // Filtro: somente em aberto (sem data de saída)
        if (Boolean.TRUE.equals(abertas)) {
            lista = lista.stream()
                    .filter(m -> m.getDt_saida() == null)
                    .collect(Collectors.toList());
        }

        // Busca por descrição (case-insensitive)
        if (q != null && !q.trim().isEmpty()) {
            final String kw = q.trim().toLowerCase();
            lista = lista.stream()
                    .filter(m -> m.getDs_manutencao() != null && m.getDs_manutencao().toLowerCase().contains(kw))
                    .collect(Collectors.toList());
        }

        mv.addObject("manutencoes", lista);
        mv.addObject("q", q);
        mv.addObject("abertas", Boolean.TRUE.equals(abertas));
        return mv;
    }

    @GetMapping("/manutencao/nova")
    public ModelAndView novaManutencao() {
        ModelAndView mv = new ModelAndView("manutencao/nova");
        mv.addObject("manutencao", new Manutencao());
        mv.addObject("motos", repMoto.findAll());
        return mv;
    }

    @PostMapping("/manutencao/inserir")
    public ModelAndView inserirManutencao(@Valid Manutencao manutencao, BindingResult bd) {
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("manutencao/nova");
            mv.addObject("manutencao", manutencao);
            mv.addObject("motos", repMoto.findAll());
            return mv;
        }
        repManutencao.save(manutencao);
        return new ModelAndView("redirect:/manutencao/lista");
    }

    @GetMapping("/manutencao/editar/{id}")
    public ModelAndView editarManutencaoForm(@PathVariable Long id) {
        Optional<Manutencao> op = repManutencao.findById(id);
        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("manutencao/editar");
            Manutencao manut = op.get();
            Moto moto = new Moto();
            moto.setId_moto(manut.getMoto().getId_moto());
            moto.setNm_placa(manut.getMoto().getNm_placa());
            manut.setMoto(moto);
            mv.addObject("manutencao", manut);
            mv.addObject("motos", repMoto.findAll());
            return mv;
        }
        return new ModelAndView("redirect:/manutencao/lista");
    }

    @PostMapping("/manutencao/editar/{id}")
    public ModelAndView editarManutencao(@PathVariable Long id, @Valid Manutencao manutencao, BindingResult bd) {
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("manutencao/editar");
            mv.addObject("manutencao", manutencao);
            mv.addObject("motos", repMoto.findAll());
            return mv;
        }

        Optional<Manutencao> opManutencao = repManutencao.findById(id);
        if (opManutencao.isEmpty()) {
            return new ModelAndView("redirect:/manutencao/lista");
        }

        Manutencao atual = opManutencao.get();

        // Atualiza a moto vinculada
        Moto moto = null;
        if (manutencao.getMoto() != null && manutencao.getMoto().getId_moto() != null) {
            moto = repMoto.findById(manutencao.getMoto().getId_moto()).orElse(null);
        }
        if (moto != null) {
            atual.setMoto(moto);
        }

        // Atualiza os demais campos
        atual.setDt_entrada(manutencao.getDt_entrada());
        atual.setDt_saida(manutencao.getDt_saida());
        atual.setDs_manutencao(manutencao.getDs_manutencao());

        repManutencao.save(atual);
        return new ModelAndView("redirect:/manutencao/lista");
    }

    @GetMapping("/manutencao/excluir/{id}")
    public ModelAndView excluirManutencao(@PathVariable Long id) {
        repManutencao.deleteById(id);
        return new ModelAndView("redirect:/manutencao/lista");
    }

    @GetMapping("/manutencao/detalhes/{id}")
    public ModelAndView detalhesManutencao(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("manutencao/detalhes");
        Manutencao manutencao = repManutencao.findById(id).orElseThrow();
        mv.addObject("manutencao", manutencao);
        return mv;
    }
}
