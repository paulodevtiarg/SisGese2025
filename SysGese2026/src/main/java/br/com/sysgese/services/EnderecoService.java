package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.EnderecoDTO;
import br.com.sysgese.mappers.EnderecoMapper;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;
import br.com.sysgese.repository.AdolescenteRepository;
import br.com.sysgese.repository.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final AdolescenteRepository adolescenteRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoService(EnderecoRepository enderecoRepository,
                           AdolescenteRepository adolescenteRepository,
                           EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.adolescenteRepository = adolescenteRepository;
        this.enderecoMapper = enderecoMapper;
    }

    // ==============================
    // SALVAR NOVO ENDEREÇO (REGRA PRINCIPAL)
    // ==============================
    @Transactional
    public void salvarNovoEndereco(EnderecoDTO dto) {

        // 1. Buscar adolescente
        Adolescente adolescente = adolescenteRepository.findById(dto.getIdAdolescente())
                .orElseThrow(() -> new RuntimeException("Adolescente não encontrado"));

        // 2. Encerrar endereço atual (se existir)
        enderecoRepository.findByAdolescenteIdAndAtivoTrue(adolescente.getId())
                .ifPresent(enderecoAtual -> {
                    enderecoAtual.setAtivo(false);
                    enderecoAtual.setDataFim(LocalDate.now());
                    enderecoRepository.save(enderecoAtual);
                });

        // 3. Criar novo endereço
        Endereco novoEndereco = enderecoMapper.toEntity(dto);

        novoEndereco.setAdolescente(adolescente);
        novoEndereco.setAtivo(true);
        novoEndereco.setDataInicio(LocalDate.now());

        enderecoRepository.save(novoEndereco);
    }

    // ==============================
    // BUSCAR ENDEREÇO ATUAL
    // ==============================
    public EnderecoDTO buscarEnderecoAtual(Long idAdolescente) {
        return enderecoRepository.findByAdolescenteIdAndAtivoTrue(idAdolescente)
                .map(enderecoMapper::toDTO)
                .orElse(null);
    }

    // ==============================
    // HISTÓRICO DE ENDEREÇOS
    // ==============================
    public List<EnderecoDTO> listarHistorico(Long idAdolescente) {
        return enderecoMapper.toDTOList(
                enderecoRepository.findByAdolescenteIdOrderByDataInicioDesc(idAdolescente)
        );
    }
   
    
}