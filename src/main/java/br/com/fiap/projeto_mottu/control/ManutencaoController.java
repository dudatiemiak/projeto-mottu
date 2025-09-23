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
    public ModelAndView listarManutencoes() {
        ModelAndView mv = new ModelAndView("manutencao/lista");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
        op.ifPresent(func -> mv.addObject("funcionario", func));
        mv.addObject("manutencoes", repManutencao.buscarTodasOrdenadasPorDataEntrada());
        return mv;
    }

    @GetMapping("/manutencao/nova")
    public ModelAndView novaManutencao() {
        ModelAndView mv = new ModelAndView("manutencao/nova");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Funcionario> op = repFunc.findByNmEmailCorporativo(auth.getName());
        op.ifPresent(func -> mv.addObject("funcionario", func));
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<Funcionario> opFunc = repFunc.findByNmEmailCorporativo(auth.getName());
            opFunc.ifPresent(func -> mv.addObject("funcionario", func));
            Manutencao manut = op.get();
            Moto moto = new Moto();
            moto.setId_moto(manut.getMoto().getId_moto());
            moto.setNm_placa(manut.getMoto().getNm_placa());
            moto.setNm_modelo(manut.getMoto().getNm_modelo());
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
