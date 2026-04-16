package br.com.sysgese.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.EnderecoDTO;
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
import br.com.sysgese.services.LotacaoService;
import br.com.sysgese.services.TatuagemService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.UrlUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/adolescentes")
public class AdolescenteController {
	
	@Autowired
    private AdolescenteService adolescenteService;
	
	@Autowired
	private UrlUtils urlUtils;
	
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
	 private CicatrizMapper cicatrizMapper;
	 
	 @Autowired
	 private TatuagemMapper tatuagemMapper;

	 
	@GetMapping
    public String index(
    	    @ModelAttribute("filtro") AdolescenteDTO filtro,
	        @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model            
    ) {
		
		// Pega a unidade do usuário logado
		
		boolean isMaster = (Boolean) session.getAttribute("isMaster");         

        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");
        
        Long unidadeFiltro;

        if (isMaster) {
            // MASTER pode escolher unidade
            unidadeFiltro = (filtro.getFiltroUnidadeId() != null) 
                ? filtro.getFiltroUnidadeId() 
                : lotacaoAtiva.getUnidade().getId(); // padrão
        } else {
            // NÃO MASTER sempre usa a própria unidade
            unidadeFiltro = lotacaoAtiva.getUnidade().getId();
        }
        
        System.out.println("UNIDADE LOGADA: " + lotacaoAtiva.getUnidade().getNome());
        System.out.println("IS MASTER: " + AuthUtil.isMaster(session));
        Page<AdolescenteDTO> adolescenteDTO =
                adolescenteService.buscarAdolescentesDaUnidade(
                		unidadeFiltro,
                        filtro, 
                        page
                );
        
        // Popula o model para a view
        model.addAttribute("adolescentes", adolescenteDTO);
        model.addAttribute("pageTitle", "Adolescentes");
        model.addAttribute("activeMenu", "gestao");

        model.addAttribute("isMaster", isMaster);
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }
        model.addAttribute("unidadeId", unidadeFiltro);
        model.addAttribute("lotacaoUsuarioLogado", lotacaoAtiva);
        model.addAttribute("queryParams", urlUtils.adolescenteQuery(filtro, page));

        return "adolescente/index";
				
	}
	
	@GetMapping("/novo")
	public String novo(
	        @ModelAttribute("filtro") AdolescenteDTO filtro,
	        @RequestParam(defaultValue = "0") int page,
	        Model model
	) {

	    model.addAttribute("adolescente", new AdolescenteDTO());
	    model.addAttribute("endereco", new EnderecoDTO());

	    model.addAttribute("generos", GeneroEnum.values());
	    model.addAttribute("racas", RacaCorEnum.values());
	    model.addAttribute("estados", EstadosEnum.values());

	    model.addAttribute("anoEscolaridade", AnoEscolaridadeEnum.values());
	    model.addAttribute("bioFisico", BiofisicoEnum.values());
	    model.addAttribute("boca", BocaEnum.values());
	    model.addAttribute("cabelo", CabeloEnum.values());
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

	    // 🔥 AGORA SIM
	    model.addAttribute("queryParams", urlUtils.adolescenteQuery(filtro, page));
	    
	    return "adolescente/form";
	}
	
	@PostMapping("/salvar")
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
		      model.addAttribute("activeMenu", "gestao");

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

		            List<Cicatriz> cicatrizesSalvas = new ArrayList<>();

		            for (int i = 0; i < dto.getCicatrizes().size(); i++) {

		                var dtoCicatriz = dto.getCicatrizes().get(i);

		                MultipartFile file = (cicatrizFiles != null && i < cicatrizFiles.size())
		                        ? cicatrizFiles.get(i)
		                        : null;

		                boolean temDados = !dtoCicatriz.isVazio();
		                boolean temArquivo = (file != null && !file.isEmpty());

		                // 🔥 REGRA DE OURO
		                if (!temDados && !temArquivo) {
		                    continue;
		                }

		                Cicatriz cicatriz = cicatrizMapper.toEntity(dtoCicatriz);
		                cicatriz.setAdolescente(adolescente);

		                if (temArquivo) {
		                    String url = cloudinaryService.upload(file, "cicatrizes");
		                    cicatriz.setFoto(url);
		                }

		                cicatrizesSalvas.add(cicatriz);
		            }

		            if (!cicatrizesSalvas.isEmpty()) {
		                cicatrizService.salvarLista(cicatrizesSalvas);
		            }
		        }

		
		    
		    if (dto.getTatuagens() != null && !dto.getTatuagens().isEmpty()) {

		        List<Tatuagem> tatuagensSalvas = new ArrayList<>();

		        for (int i = 0; i < dto.getTatuagens().size(); i++) {

		            var dtoTatuagem = dto.getTatuagens().get(i);

		            MultipartFile file = (tatuagemFiles != null && i < tatuagemFiles.size())
		                    ? tatuagemFiles.get(i)
		                    : null;

		            boolean temDados = !dtoTatuagem.isVazio();
		            boolean temArquivo = (file != null && !file.isEmpty());

		            // 🔥 REGRA DE OURO
		            if (!temDados && !temArquivo) {
		                continue; // ignora completamente
		            }

		            Tatuagem tatuagem = tatuagemMapper.toEntity(dtoTatuagem);
		            tatuagem.setAdolescente(adolescente);

		            if (temArquivo) {
		                String url = cloudinaryService.upload(file, "tatuagens");
		                tatuagem.setFoto(url);
		            }

		            tatuagensSalvas.add(tatuagem);
		        }

		        if (!tatuagensSalvas.isEmpty()) {
		            tatuagemService.salvarLista(tatuagensSalvas);
		        }
		    }

		    // =====================================================
		    // 📷 FOTOS DO ADOLESCENTE
		    // =====================================================
		    if (dto.getFotos() != null && !dto.getFotos().isEmpty()) {

		        List<Foto> fotosSalvas = new ArrayList<>();

		        for (int i = 0; i < dto.getFotos().size(); i++) {

		            var dtoFoto = dto.getFotos().get(i);

		            MultipartFile file = (fotosFiles != null && i < fotosFiles.size())
		                    ? fotosFiles.get(i)
		                    : null;

		            boolean temDados = !dtoFoto.isVazio();
		            boolean temArquivo = (file != null && !file.isEmpty());

		            if (!temDados && !temArquivo) {
		                continue;
		            }

		            Foto foto = new Foto();
		            foto.setAdolescente(adolescente);
		            foto.setDescricaoDetalhe(dtoFoto.getDescricaoDetalhe());

		            if (temArquivo) {
		                String url = cloudinaryService.upload(file, "adolescentes");
		                foto.setFoto(url);
		            }

		            fotosSalvas.add(foto);
		        }

		        if (!fotosSalvas.isEmpty()) {
		            fotoService.salvarLista(fotosSalvas);
		        }
		    }
		    redirectAttributes.addFlashAttribute("msgOk", "Adolescente salvo com sucesso!");
		   
	   } catch (Exception e) {
           e.printStackTrace();
           redirectAttributes.addFlashAttribute("msgErro", "Erro ao salvar Adolescente: " + e.getMessage());
       }

	    

	    return "redirect:/adolescentes";
	}
	
    @GetMapping("/detalhes/{id}")
    public String detalhes(
            @PathVariable Long id,
            @ModelAttribute("filtro") AdolescenteDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

      AdolescenteDTO dto = adolescenteService.buscarPorId(id);

        model.addAttribute("adolescente", dto);
        model.addAttribute("pageTitle", "Detalhes do Registro");
        model.addAttribute("activeMenu", "gestao");

        String queryParams = urlUtils.adolescenteQuery(filtro, page);
        model.addAttribute("queryParams", queryParams);

        return "adolescente/detalhes";
    }
    
    @GetMapping("/verificar-cpf")
    @ResponseBody
    public ResponseEntity<?> verificarCpf(@RequestParam String cpf) {

        Map<String, Object> response = adolescenteService.verificarCpf(cpf);

        return ResponseEntity.ok(response);
    }
    
    
	
}



