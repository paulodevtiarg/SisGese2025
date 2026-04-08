package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.CicatrizDTO;
import br.com.sysgese.dtos.FotoDTO;
import br.com.sysgese.dtos.TatuagemDTO;
import br.com.sysgese.mappers.AdolescenteMapper;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Cicatriz;
import br.com.sysgese.models.Endereco;
import br.com.sysgese.models.Foto;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.models.Tatuagem;
import br.com.sysgese.repository.AdolescenteRepository;
import br.com.sysgese.repository.CicatrizRepository;
import br.com.sysgese.repository.FotoRepository;
import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.repository.TatuagemRepository;
import br.com.sysgese.specifications.AdolescenteSpecification;
import br.com.sysgese.specifications.InternacaoSpecification;

@Service
public class AdolescenteService {

    @Autowired
    private AdolescenteRepository repository;
    
    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private TatuagemRepository tatuagemRepository;

    @Autowired
    private CicatrizRepository cicatrizRepository;

    @Autowired
    private AdolescenteMapper mapper;

    // ========================= BUSCA =========================

    public Page<AdolescenteDTO> buscarAdolescentesInternadosNaUnidade(
            Long idUnidadeServidor,
            String nome,
            String apelido,
            String cidade,
            String cpf,
            Integer idadeMin,
            Integer idadeMax,
            Integer[] status,
            Pageable pageable
    ) {

        LocalDate hoje = LocalDate.now();

        LocalDate dataInicioNascimento = LocalDate.of(1900, 1, 1);
        LocalDate dataFimNascimento = hoje;

        if (idadeMin != null) dataFimNascimento = hoje.minusYears(idadeMin);
        if (idadeMax != null) dataInicioNascimento = hoje.minusYears(idadeMax + 1).plusDays(1);

        Integer[] filtroStatus = status != null ? status : new Integer[]{1};

        Specification<Internacao> spec =
                Specification.where(InternacaoSpecification.unidadeId(idUnidadeServidor))
                        .and(InternacaoSpecification.statusInternacaoAtiva())
                        .and(InternacaoSpecification.nome(nome))
                        .and(InternacaoSpecification.apelido(apelido))
                        .and(InternacaoSpecification.cidade(cidade))
                        .and(InternacaoSpecification.cpf(cpf))
                        .and(InternacaoSpecification.nascimentoEntre(dataInicioNascimento, dataFimNascimento))
                        .and(InternacaoSpecification.statusCadastro(filtroStatus));

        Page<Internacao> internacoes = internacaoRepository.findAll(spec, pageable);

        List<AdolescenteDTO> adolescentesDTO = internacoes.stream()
                .map(Internacao::getAdolescente)
                .distinct()
                .map(mapper::toDTO)
                .toList();

        return new PageImpl<>(adolescentesDTO, pageable, internacoes.getTotalElements());
    }

    // ========================= SALVAR =========================

    public Page<AdolescenteDTO> buscarAdolescentesDaUnidade(
            Long idUnidadeServidor,
            String nome,
            String apelido,
            String cidade,
            String cpf,
            Integer idadeMin,
            Integer idadeMax,
            Integer[] status,
            Pageable pageable
    ) {

        LocalDate hoje = LocalDate.now();

        LocalDate dataInicioNascimento = LocalDate.of(1900, 1, 1);
        LocalDate dataFimNascimento = hoje;

        if (idadeMin != null) dataFimNascimento = hoje.minusYears(idadeMin);
        if (idadeMax != null) dataInicioNascimento = hoje.minusYears(idadeMax + 1).plusDays(1);

        Integer[] filtroStatus = status != null ? status : new Integer[]{1};

        Specification<Adolescente> spec =
                Specification.where(AdolescenteSpecification.unidadeCadastro(idUnidadeServidor))
                        .and(AdolescenteSpecification.nome(nome))
                        .and(AdolescenteSpecification.apelido(apelido))
                        .and(AdolescenteSpecification.cidade(cidade))
                        .and(AdolescenteSpecification.cpf(cpf))
                        .and(AdolescenteSpecification.nascimentoEntre(dataInicioNascimento, dataFimNascimento))
                        .and(AdolescenteSpecification.statusCadastro(filtroStatus));
        Page<Adolescente> adolescentes = repository.findAll(spec, pageable);

     // 🔥 busca nomes das unidades
     Set<Long> idsUnidades = adolescentes.getContent().stream()
             .map(a -> a.getIdUnidadeCadastro() != null ? a.getIdUnidadeCadastro().longValue() : null)
             .filter(Objects::nonNull)
             .collect(Collectors.toSet());

     Map<Long, String> mapaUnidades =
             unidadeService.buscarNomesPorIds(idsUnidades);

     // 🔥 AQUI é onde entra seu código
     return adolescentes.map(adolescente -> {
         AdolescenteDTO dto = mapper.toDTO(adolescente);

         dto.setNomeUnidadeCadastro(
             adolescente.getIdUnidadeCadastro() != null
                 ? mapaUnidades.get(adolescente.getIdUnidadeCadastro().longValue())
                 : null
         );

         return dto;
     });
    
    }
    @Transactional
    public Adolescente salvar(AdolescenteDTO dto) {

        Adolescente entity;

        if (dto.getId() != null) {
            // 🔥 BUSCA EXISTENTE (IMPORTANTE)
            entity = repository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Adolescente não encontrado"));

            mapper.updateEntityFromDTO(dto, entity);

        } else {
            entity = mapper.toEntity(dto);
            entity.setDataCad(LocalDate.now());
        }

        entity.setDataAlt(LocalDate.now());
        entity.setStatus(1);

        // 🔥 REGRA DE ENDEREÇO (AQUI ESTÁ O PONTO)
        if (dto.getEnderecoAtual() != null) {

            // desativa antigos
            if (entity.getEnderecos() != null) {
                entity.getEnderecos().forEach(e -> e.setAtivo(false));
            }

            // cria novo
            Endereco novo = new Endereco();
            novo.setCep(dto.getEnderecoAtual().getCep());
            novo.setLogradouro(dto.getEnderecoAtual().getLogradouro());
            novo.setNumero(dto.getEnderecoAtual().getNumero());
            novo.setBairro(dto.getEnderecoAtual().getBairro());
            novo.setCidade(dto.getEnderecoAtual().getCidade());
            novo.setUf(dto.getEnderecoAtual().getUf());
            novo.setReferencia(dto.getEnderecoAtual().getReferencia());

            novo.setDataInicio(LocalDate.now());
            novo.setAtivo(true);
            novo.setAdolescente(entity);

            entity.getEnderecos().add(novo);
        }

        return repository.save(entity);
    }

    public AdolescenteDTO buscarPorId(Long id) {
        Adolescente adol = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adolescente não encontrado"));
        return mapper.toDTO(adol);
    }

    // ========================= ATUALIZAR COMPLETO =========================

    @Transactional
    public Adolescente atualizar(Long id, AdolescenteDTO dto) {

        Adolescente entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado"));

        entity.setDataAlt(LocalDate.now());

        mapper.updateEntityFromDTO(dto, entity);

        if (dto.getFotoRegistro() != null) {
            entity.setFotoRegistro(dto.getFotoRegistro());
        }

        // ========================= FOTOS =========================

        if (dto.getFotos() != null) {

            Set<Long> idsForm = dto.getFotos().stream()
                    .filter(f -> f.getId() != null)
                    .map(FotoDTO::getId)
                    .collect(Collectors.toSet());

            List<Foto> atuais = fotoRepository.findByAdolescente(entity);

            for (Foto foto : atuais) {
                if (!idsForm.contains(foto.getId())) {
                    fotoRepository.delete(foto);
                }
            }

            for (FotoDTO fDto : dto.getFotos()) {

                Foto foto;

                if (fDto.getId() != null) {
                    foto = fotoRepository.findById(fDto.getId()).orElseThrow();
                } else {
                    foto = new Foto();
                    foto.setAdolescente(entity);
                }

                foto.setDescricaoDetalhe(fDto.getDescricaoDetalhe());

                // 🔥 IMPORTANTE: só atualiza se veio nova foto
                if (fDto.getFoto() != null) {
                    foto.setFoto(fDto.getFoto());
                }

                fotoRepository.save(foto);
            }
        }

        // ========================= TATUAGENS =========================

        if (dto.getTatuagens() != null) {

            Set<Long> idsForm = dto.getTatuagens().stream()
                    .filter(t -> t.getId() != null)
                    .map(TatuagemDTO::getId)
                    .collect(Collectors.toSet());

            List<Tatuagem> atuais = tatuagemRepository.findByAdolescente(entity);

            for (Tatuagem t : atuais) {
                if (!idsForm.contains(t.getId())) {
                    tatuagemRepository.delete(t);
                }
            }

            for (TatuagemDTO tDto : dto.getTatuagens()) {

                Tatuagem tatuagem;

                if (tDto.getId() != null) {
                    tatuagem = tatuagemRepository.findById(tDto.getId()).orElseThrow();
                } else {
                    tatuagem = new Tatuagem();
                    tatuagem.setAdolescente(entity);
                }

                tatuagem.setLocal(tDto.getLocal());
                tatuagem.setDescricao(tDto.getDescricao());

                if (tDto.getFoto() != null) {
                    tatuagem.setFoto(tDto.getFoto());
                }

                tatuagemRepository.save(tatuagem);
            }
        }

        // ========================= CICATRIZES =========================

        if (dto.getCicatrizes() != null) {

            Set<Long> idsForm = dto.getCicatrizes().stream()
                    .filter(c -> c.getId() != null)
                    .map(CicatrizDTO::getId)
                    .collect(Collectors.toSet());

            List<Cicatriz> atuais = cicatrizRepository.findByAdolescente(entity);

            for (Cicatriz c : atuais) {
                if (!idsForm.contains(c.getId())) {
                    cicatrizRepository.delete(c);
                }
            }

            for (CicatrizDTO cDto : dto.getCicatrizes()) {

                Cicatriz cicatriz;

                if (cDto.getId() != null) {
                    cicatriz = cicatrizRepository.findById(cDto.getId()).orElseThrow();
                } else {
                    cicatriz = new Cicatriz();
                    cicatriz.setAdolescente(entity);
                }

                cicatriz.setLocal(cDto.getLocal());
                cicatriz.setForma(cDto.getForma());

                if (cDto.getFoto() != null) {
                    cicatriz.setFoto(cDto.getFoto());
                }

                cicatrizRepository.save(cicatriz);
            }
        }

        return repository.save(entity);
    }

    // ========================= EXCLUIR =========================

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}