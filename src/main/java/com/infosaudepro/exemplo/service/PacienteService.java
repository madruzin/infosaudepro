package com.infosaudepro.exemplo.service;

import com.infosaudepro.exemplo.model.Paciente;
import com.infosaudepro.exemplo.repository.PacienteRepository;
import com.infosaudepro.exemplo.util.CriptografiaUtil;
import com.infosaudepro.exemplo.dto.PacienteCadastroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    /**
     * Salva um novo paciente aplicando a criptografia nos dados sensíveis.
     */
    public Paciente cadastrarSeguro(PacienteCadastroDTO dto) {
        try {
            // Cria a entidade Paciente
            Paciente paciente = new Paciente();
            paciente.setNome(dto.getNome());

            // 🔒 CRIPTOGRAFIA DE DADOS EM REPOUSO (AES)
            // Esta chamada só funciona se CriptografiaUtil.encrypt for 'static'
            String cpfCriptografado = CriptografiaUtil.encrypt(dto.getCpf());
            String diagnosticoCriptografado = CriptografiaUtil.encrypt(dto.getDiagnostico());

            paciente.setCpfCriptografado(cpfCriptografado);
            paciente.setDiagnosticoCriptografado(diagnosticoCriptografado);

            return repository.save(paciente);

        } catch (Exception e) {
            // Tratamento de exceção seguro
            e.printStackTrace(); // ⬅️ DEBUG: Imprime o erro detalhado no console do IntelliJ
            throw new RuntimeException("Falha de segurança ao salvar paciente: erro de criptografia.", e);
        }
    }

    /**
     * Busca um paciente e descriptografa os dados sensíveis para uso em memória.
     */
    public Paciente buscarDescriptografadoPorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

        try {
            // 🔓 DESCRIPTOGRAFIA JUST-IN-TIME
            // Esta chamada só funciona se CriptografiaUtil.decrypt for 'static'
            String cpfDescriptografado = CriptografiaUtil.decrypt(paciente.getCpfCriptografado());
            String diagnosticoDescriptografado = CriptografiaUtil.decrypt(paciente.getDiagnosticoCriptografado());

            // Atualiza o objeto para retornar o dado legível
            paciente.setCpfCriptografado(cpfDescriptografado);
            paciente.setDiagnosticoCriptografado(diagnosticoDescriptografado);

            return paciente;

        } catch (Exception e) {
            e.printStackTrace(); // ⬅️ DEBUG: Imprime o erro detalhado no console do IntelliJ
            throw new RuntimeException("Falha na descriptografia dos dados.", e);
        }
    }
}