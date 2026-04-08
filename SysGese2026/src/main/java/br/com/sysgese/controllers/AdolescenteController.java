package br.com.sysgese.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.EnderecoDTO;
import br.com.sysgese.dtos.LotacaoDTO;
import br.com.sysgese.enumerators.AnoEscolaridadeEnum;
import br.com.sysgese.enumerators.BiofisicoEnum;
import br.com.sysgese.enumerators.BocaEnum;
import br.com.sysgese.enumerators.CabeloEnum;
import br.com.sysgese.enumerators.CorOlhosEnum;
import br.com.sysgese.enumerators.EstadosEnum;
import br.com.sysgese.enumerators.EstaturaEnum;
import br.com.sysgese.enumerators.GeneroEnum;
import br.com.sysgese.enumerators.NarizEnum;
import br.com.sysgese.enumerators.NivelEscolaridadeEnum;
import br.com.sysgese.enumerators.OlhosEnum;
import br.com.sysgese.enumerators.OrelhaEnum;
import br.com.sysgese.enumerators.PescocoEnum;
import br.com.sysgese.enumerators.RacaCorEnum;
import br.com.sysgese.enumerators.ReligiaoEnum;
import br.com.sysgese.enumerators.RostoEnum;
import br.com.sysgese.enumerators.SobrancelhaEnum;
import br.com.sysgese.mappers.CicatrizMapper;
import br.com.sysgese.mappers.TatuagemMapper;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Cicatriz;
import br.com.sysgese.models.Foto;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.models.Tatuagem;
import br.com.sysgese.services.AdolescenteService;
import br.com.sysgese.services.CicatrizService;
import br.com.sysgese.services.CloudinaryService;
import br.com.sysgese.services.EnderecoService;
import br.com.sysgese.services.FotoService;
import br.com.sysgese.services.InternacaoService;
import br.com.sysgese.services.LotacaoService;
import br.com.sysgese.services.TatuagemService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.StatusUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class AdolescenteController {
	
	@Autowired
    private AdolescenteService adolescenteService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private CicatrizService cicatrizService;
	
	@Autowired
	private TatuagemService tatuagemService;
	
	@Autowired
	private LotacaoService lotacaoService;
	   
	 @Autowired
	 private InternacaoService internacaoService;
	
	 @Autowired
	 private CicatrizMapper cicatrizMapper;
	 
	 @Autowired
	 private TatuagemMapper tatuagemMapper;

	 
	@GetMapping("/adolescente")
    public String index(
            HttpSession session,
            Model model,
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "apelido", required = false, defaultValue = "") String apelido,
            @RequestParam(value = "cidade", required = false, defaultValue = "") String cidade,
            @RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
            @RequestParam(value = "unidadeId", required = false) Long unidadeId,
            @RequestParam(value = "idadeMin", required = false) Integer idadeMin,
            @RequestParam(value = "idadeMax", required = false) Integer idadeMax,
            @RequestParam(value = "status", required = false, defaultValue = "1") String statusFiltro,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", required = false, defaultValue = "10") int tamanho
    ) {
		
		// Pega a unidade do usuário logado
		Servidor usuarioLogado = (Servidor) session.getAttribute("usuarioLogado");

        // Converte status do filtro em Integer[]
        Integer[] status = StatusUtil.parseStatusFiltro(statusFiltro);
        
        Pageable pageable = PageRequest.of(pagina, tamanho);
        
     // =========================
        // Pega a unidade do usuário via lotação ativa
        // =========================
        Optional<Lotacao> lotacaoOpt = lotacaoService.findAtivaByServidorId(usuarioLogado.getId());

        if (lotacaoOpt.isEmpty()) {
            model.addAttribute("mensagemErro", "Usuário não possui lotação ativa.");
            return "adolescente/index";
        }

        Lotacao lotacaoAtiva = lotacaoOpt.get();
        
        Long unidadeFiltro;

        if (AuthUtil.isMaster(session)) {
            // MASTER pode escolher unidade
            unidadeFiltro = (unidadeId != null) 
                ? unidadeId 
                : lotacaoAtiva.getUnidade().getId(); // padrão
        } else {
            // NÃO MASTER sempre usa a própria unidade
            unidadeFiltro = lotacaoAtiva.getUnidade().getId();
        }
        
        System.out.println("UNIDADE LOGADA: " + unidadeId);
        System.out.println("IS MASTER: " + AuthUtil.isMaster(session));
        Page<AdolescenteDTO> page =
                adolescenteService.buscarAdolescentesDaUnidade(
                		unidadeFiltro,
                        nome,
                        apelido,
                        cidade,
                        cpf,
                        idadeMin,
                        idadeMax,
                        status,
                        pageable
                );
        
        // Popula o model para a view
        model.addAttribute("pageTitle", "Adolescentes");
        model.addAttribute("activeMenu", "gestao");
        model.addAttribute("adolescentes", page.getContent());
        model.addAttribute("paginaAtual", page.getNumber());
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("tamanhoPagina", tamanho);
        model.addAttribute("nomeBusca", nome);
        model.addAttribute("apelidoBusca", apelido);
        model.addAttribute("cidadeBusca", cidade);
        model.addAttribute("cpfBusca", cpf);
        model.addAttribute("idadeMin", idadeMin);
        model.addAttribute("idadeMax", idadeMax);
        model.addAttribute("statusFiltro", statusFiltro);
        model.addAttribute("isMaster", AuthUtil.isMaster(session));
        if (AuthUtil.isMaster(session)) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        model.addAttribute("unidadeId", unidadeFiltro);
        model.addAttribute("lotacaoUsuarioLogado", lotacaoAtiva);

        return "adolescente/index";
		
		
	}
	
	@GetMapping("/adolescente/novo")
	public String novo(Model model) {
	    model.addAttribute("adolescente", new AdolescenteDTO());
	    model.addAttribute("endereco", new EnderecoDTO());
	    model.addAttribute("generos", GeneroEnum.values());
	    model.addAttribute("racas", RacaCorEnum.values());
	    model.addAttribute("estados", EstadosEnum.values());
	    /*novos enums*/
	    model.addAttribute("anoEscolaridade", AnoEscolaridadeEnum.values());
	    model.addAttribute("bioFisico", BiofisicoEnum.values());
	    model.addAttribute("boca", BocaEnum.values());
	    model.addAttribute("cabelo", CabeloEnum.values());
	    model.addAttribute("bioFisico", BiofisicoEnum.values());
	    model.addAttribute("olhos", OlhosEnum.values());
	    model.addAttribute("corOlhos", CorOlhosEnum.values());
	    model.addAttribute("estaturas", EstaturaEnum.values());
	    model.addAttribute("nariz", NarizEnum.values());
	    model.addAttribute("nivelEscolaridade", NivelEscolaridadeEnum.values());
	    model.addAttribute("pescoco", PescocoEnum.values());
	    model.addAttribute("religiao", ReligiaoEnum.values());
	    model.addAttribute("rosto", RostoEnum.values());
	    model.addAttribute("sobrancelhas", SobrancelhaEnum.values());
	    model.addAttribute("orelhas", OrelhaEnum.values());
	    
	      model.addAttribute("pageTitle", "Adolescentes");
	        model.addAttribute("activeMenu", "gestao");
	    return "adolescente/form";
	}
	
	@PostMapping("/adolescente/salvar")
	public String salvar(
			    HttpSession session,
			    @Valid @ModelAttribute("adolescente") AdolescenteDTO dto,
			    BindingResult resultAdolescente,
			    @Valid @ModelAttribute("endereco") EnderecoDTO endereco,
			    BindingResult resultEndereco,
			    @RequestParam(value = "fotosFiles", required = false) List<MultipartFile> fotosFiles,
	        @RequestParam(value="cicatrizFiles" , required = false)List<MultipartFile> cicatrizFiles,
	        @RequestParam(value="tatuagemFiles" , required = false)List<MultipartFile> tatuagemFiles,
	        Model model,
	       	        @RequestParam(value = "arquivoFoto", required = false) MultipartFile arquivoFoto,
	       	     RedirectAttributes redirectAttributes
	) throws IOException{
		
		if (arquivoFoto == null || arquivoFoto.isEmpty()) {
		    resultAdolescente.rejectValue(
		        "fotoRegistro",
		        "error.adolescente",
		        "Foto do Registro é obrigatória"
		    );
		}
	    // 🚨 VALIDAÇÃO
		if (resultAdolescente.hasErrors() || resultEndereco.hasErrors()) {

		    Map<String, String> errosEndereco = new HashMap<>();

		    resultEndereco.getFieldErrors().forEach(erro -> {
		        errosEndereco.put(erro.getField(), erro.getDefaultMessage());
		    });

		    model.addAttribute("errosEndereco", errosEndereco);

		    model.addAttribute("generos", GeneroEnum.values());
		    model.addAttribute("racas", RacaCorEnum.values());
		    model.addAttribute("estados", EstadosEnum.values());
		    model.addAttribute("anoEscolaridade", AnoEscolaridadeEnum.values());
		    model.addAttribute("bioFisico", BiofisicoEnum.values());
		    model.addAttribute("boca", BocaEnum.values());
		    model.addAttribute("cabelo", CabeloEnum.values());
		    model.addAttribute("bioFisico", BiofisicoEnum.values());
		    model.addAttribute("olhos", OlhosEnum.values());
		    model.addAttribute("corOlhos", CorOlhosEnum.values());
		    model.addAttribute("estaturas", EstaturaEnum.values());
		    model.addAttribute("nariz", NarizEnum.values());
		    model.addAttribute("nivelEscolaridade", NivelEscolaridadeEnum.values());
		    model.addAttribute("pescoco", PescocoEnum.values());
		    model.addAttribute("religiao", ReligiaoEnum.values());
		    model.addAttribute("rosto", RostoEnum.values());
		    model.addAttribute("sobrancelhas", SobrancelhaEnum.values());
		    model.addAttribute("orelhas", OrelhaEnum.values());
		    
		      model.addAttribute("pageTitle", "Adolescentes");
		       model.addAttribute("activeMenu", "administrativo");

		    return "adolescente/form";
		}

	    // =====================================================
	    // 📸 FOTO DE REGISTRO
	    // =====================================================
		if (arquivoFoto != null && !arquivoFoto.isEmpty()) {
		    String url = cloudinaryService.upload(arquivoFoto, "adolescentes");
		    dto.setFotoRegistro(url);
		}

	    // =====================================================
	    // 💾 SALVA ADOLESCENTE
	    // =====================================================
	   try {
		    
		    
		    Servidor usuarioLogado = (Servidor) session.getAttribute("usuarioLogado");

		    Optional<Lotacao> lotacaoOpt =
		        lotacaoService.findAtivaByServidorId(usuarioLogado.getId());

		    if (lotacaoOpt.isPresent()) {
		        dto.setIdUnidadeCadastro(
		            lotacaoOpt.get().getUnidade().getId()
		        );
		    } else {
		        redirectAttributes.addFlashAttribute("msgErro",
		            "Usuário sem lotação ativa");

		        return "redirect:/adolescentes/novo";
		    }
		    
		    
		    
		   Adolescente adolescente = adolescenteService.salvar(dto);

		    // =====================================================
		    // 🏠 ENDEREÇO (NOVO 🔥)
		    // =====================================================
		   
		        endereco.setIdAdolescente(adolescente.getId());
		        enderecoService.salvarNovoEndereco(endereco);
		    

		    // =====================================================
		    // 🩹 CICATRIZES
		    // =====================================================
		    if (dto.getCicatrizes() != null && !dto.getCicatrizes().isEmpty()) {

		        List<Cicatriz> cicatrizes = dto.getCicatrizes()
		                .stream()
		                .map(cicatrizMapper::toEntity)
		                .toList();

		        if (cicatrizFiles != null && !cicatrizFiles.isEmpty()) {

		            for (int i = 0; i < cicatrizes.size(); i++) {
		                Cicatriz cicatriz = cicatrizes.get(i);
		                cicatriz.setAdolescente(adolescente);

		                if (i < cicatrizFiles.size() && !cicatrizFiles.get(i).isEmpty()) {

		                    String url = cloudinaryService.upload(cicatrizFiles.get(i), "cicatrizes");
		                    cicatriz.setFoto(url);
		                }
		            }

		            cicatrizService.salvarLista(cicatrizes);
		        }

		     
		    }

		    // =====================================================
		    // 🖋️ TATUAGENS
		    // =====================================================
		    if (dto.getTatuagens() != null && !dto.getTatuagens().isEmpty()) {

		        List<Tatuagem> tatuagens = dto.getTatuagens()
		                .stream()
		                .map(tatuagemMapper::toEntity)
		                .toList();

		        if (tatuagemFiles != null && !tatuagemFiles.isEmpty()) {

		            for (int i = 0; i < tatuagens.size(); i++) {
		                Tatuagem tatuagem = tatuagens.get(i);
		                tatuagem.setAdolescente(adolescente);

		                if (i < tatuagemFiles.size() && !tatuagemFiles.get(i).isEmpty()) {

		                    String url = cloudinaryService.upload(tatuagemFiles.get(i), "tatuagens");
		                    tatuagem.setFoto(url);
		                }
		            }

		            tatuagemService.salvarLista(tatuagens);
		        }

		       
		    }

		    // =====================================================
		    // 📷 FOTOS DO ADOLESCENTE
		    // =====================================================
		    if (fotosFiles != null && !fotosFiles.isEmpty()) {

		        List<Foto> listaFotos = new ArrayList<>();

		        for (MultipartFile file : fotosFiles) {
		            if (!file.isEmpty()) {

		                Foto foto = new Foto();
		                foto.setAdolescente(adolescente);

		                String url = cloudinaryService.upload(file, "adolescentes");
		                foto.setFoto(url);

		                listaFotos.add(foto);
		            }
		        }

		        fotoService.salvarLista(listaFotos);
		    }
		    redirectAttributes.addFlashAttribute("msgOk", "Adolescente salvo com sucesso!");
		   
	   } catch (Exception e) {
           e.printStackTrace();
           redirectAttributes.addFlashAttribute("msgErro", "Erro ao salvar Adolescente: " + e.getMessage());
       }

	    

	    return "redirect:/adolescente";
	}
}



