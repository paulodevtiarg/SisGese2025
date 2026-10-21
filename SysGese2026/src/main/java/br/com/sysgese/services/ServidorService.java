package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sysgese.dtos.ServidorDTO;
import br.com.sysgese.mappers.ServidorMapper;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.ServidorRepository;

@Service
public class ServidorService {

    @Autowired
    private ServidorRepository servidorRepository;

    @Autowired
    private ServidorMapper servidorMapper;

    // ==========================================
    // BUSCAS (Utilizadas pelo Atendimento e Login)
    // ==========================================

    /**
     * Busca o servidor pelo ID. Essencial para o AtendimentoEnfermagemService
     * e para validar a sessão do usuário.
     */
    public Optional<Servidor> buscarPorId(Long id) {
        return servidorRepository.findById(id);
    }

    /**
     * Método de login (usado na sua tela de autenticação).
     */
    public Optional<Servidor> buscarPorLoginCpfOuMatricula(String login, String cpf, String matricula) {
        return servidorRepository.findByLoginOrCpfOrMatricula(login, cpf, matricula);
    }

    // ==========================================
    // SALVAR / ATUALIZAR
    // ==========================================

    @Transactional
    public Servidor salvar(ServidorDTO dto) {
        Servidor entity;

        if (dto.getId() != null) {
            // ==============================
            // ALTERAÇÃO
            // ==============================
            entity = servidorRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Servidor não encontrado."));

            // Se o usuário não informou uma nova senha na edição, mantemos a antiga.
            // (Ajuste isso se a sua tela sempre envia a senha preenchida)
            if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
                dto.setSenha(entity.getSenha());
            }

            // Atualiza a entidade com os dados do DTO
            servidorMapper.updateEntityFromDTO(dto, entity);

        } else {
            // ==============================
            // NOVO REGISTRO
            // ==============================

            // Verifica se o CPF já existe (segurança extra)
            if (dto.getCpf() != null) {
                Optional<Servidor> servidorExistente = servidorRepository.findByLoginOrCpfOrMatricula(
                        dto.getLogin(), dto.getCpf(), dto.getMatricula()
                );

                if (servidorExistente.isPresent()) {
                    throw new RuntimeException("Já existe um servidor cadastrado com este Login, CPF ou Matrícula.");
                }
            }

            // Converte o DTO para a Entidade (o Mapper já linka o Perfil via @AfterMapping)
            entity = servidorMapper.toEntity(dto);

            // Define o status inicial (1 = Ativo) caso não venha do formulário
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }

            // Por padrão, todo novo usuário começa com primeiro acesso = true
            if (entity.getPrimeiroAcesso() == null) {
                entity.setPrimeiroAcesso(true);
            }
        }

        // O `@PrePersist` e `@PreUpdate` da sua Entidade já vão cuidar de setar
        // as datas de cadastro e alteração automaticamente antes de salvar!

        return servidorRepository.save(entity);
    }
}