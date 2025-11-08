const API_BASE_URL = 'http://localhost:8080';
const USER_TOKEN_KEY = 'jwt_token_infosaudepro'; // Chave para localStorage


// FUNÇÕES BÁSICAS DE SEGURANÇA (Token)

function setToken(token) {
    localStorage.setItem(USER_TOKEN_KEY, token);
}

function getToken() {
    return localStorage.getItem(USER_TOKEN_KEY);
}

function updateStatus(message, isError = false) {
    const msgElement = document.getElementById('login-message') || document.getElementById('auth-status');
    msgElement.textContent = message;
    msgElement.style.color = isError ? 'var(--color-error)' : 'var(--color-secondary)';
}

// AUTENTICAÇÃO (Login)


async function handleLogin() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Codificação Base64 para HTTP Basic Auth
    const basicAuth = 'Basic ' + btoa(username + ':' + password);

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Authorization': basicAuth,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            // Se o Spring autenticou, usamos o Basic Auth como nosso "token" para requisições futuras
            setToken(basicAuth); 
            updateStatus(`Login de ${username} realizado com sucesso!`, false);
            showDataSection();
        } else if (response.status === 401) {
            updateStatus('Erro de autenticação! Credenciais inválidas.', true);
        } else {
            updateStatus('Erro ao tentar conectar com a API.', true);
        }
    } catch (error) {
        updateStatus('Erro de rede ou servidor. Verifique o Back-end (8080).', true);
    }
}

function logout() {
    localStorage.removeItem(USER_TOKEN_KEY);
    showLoginSection();
    document.getElementById('paciente-data').innerHTML = '';
    updateStatus('Sessão encerrada.', false);
}


//  REQUISIÇÃO PROTEGIDA (Consulta)

async function buscarPaciente() {
    const pacienteId = document.getElementById('pacienteId').value;
    const token = getToken();

    if (!token) {
        updateStatus("Erro: Não autenticado. Faça login.", true);
        return;
    }

    try {
        // Envia o Basic Auth no cabeçalho Authorization para o Spring Security validar
        const authHeader = token; 

        const response = await fetch(`${API_BASE_URL}/api/pacientes/${pacienteId}`, {
            method: 'GET',
            headers: {
                'Authorization': authHeader, // 🔑 Envio da credencial de segurança
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();
            displayPaciente(data);
            updateStatus("Consulta realizada com sucesso.", false);

        } else if (response.status === 403) {
            // 🚫 Erro de Autorização (Forbidden)
            document.getElementById('paciente-data').innerHTML = '<h3>ERRO 403: Acesso Negado</h3><p>Seu usuário não tem a ROLE (permissão) para consultar prontuários.</p>';
            updateStatus('Acesso Negado (403 Forbidden).', true);
            
        } else if (response.status === 404) {
            document.getElementById('paciente-data').innerHTML = '<h3>Erro 404: Paciente não encontrado.</h3>';
            updateStatus('Paciente não encontrado.', true);

        } else {
            document.getElementById('paciente-data').textContent = `Erro ao buscar paciente: ${response.status} ${response.statusText}`;
        }
    } catch (error) {
        updateStatus('Erro de comunicação com o servidor.', true);
    }
}

// 🎨 CONTROLE DE TELA E DISPLAY


function displayPaciente(paciente) {
    const dataBox = document.getElementById('paciente-data');
    dataBox.innerHTML = `
        <h3>Prontuário #${paciente.id}</h3>
        <p><strong>Nome:</strong> ${paciente.nome}</p>
        <p><strong>CPF:</strong> <span class="dado-sensivel">${paciente.cpfCriptografado}</span></p>
        <p><strong>Diagnóstico:</strong> <span class="dado-sensivel">${paciente.diagnosticoCriptografado}</span></p>
        <p class="nota-seguranca">* CPF e Diagnóstico foram descriptografados pelo Service Java (demonstração de segurança em trânsito).</p>
    `;
}

function showDataSection() {
    document.getElementById('login-section').style.display = 'none';
    document.getElementById('data-section').style.display = 'block';
    document.getElementById('logout-button').style.display = 'block';
    document.getElementById('auth-status').textContent = 'Autenticado. Você pode consultar.';
}

function showLoginSection() {
    document.getElementById('login-section').style.display = 'block';
    document.getElementById('data-section').style.display = 'none';
    document.getElementById('logout-button').style.display = 'none';
    document.getElementById('login-message').textContent = '';
}

// 🎬 Inicialização e Listeners de Eventos (Ponto de correção anterior)

document.addEventListener('DOMContentLoaded', () => {
    // Liga as funções aos botões por ID (Assumindo que você ajustou o HTML)
    
    const loginButton = document.getElementById('login-button');
    const searchButton = document.getElementById('search-button');
    const logoutBtn = document.getElementById('logout-button');
    
    if (loginButton) loginButton.addEventListener('click', handleLogin);
    if (searchButton) searchButton.addEventListener('click', buscarPaciente);
    // Logout button já está ligado via HTML no header, mas ligamos aqui para garantir:
    if (logoutBtn) logoutBtn.addEventListener('click', logout);
    
    // Inicializa a tela com base no token
    if (getToken()) {
        showDataSection();
    } else {
        showLoginSection();
    }
});