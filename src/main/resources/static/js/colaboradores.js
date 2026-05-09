<!-- ── JAVASCRIPT ── -->

const API = 'http://localhost:8080/colaborador';
let colaboradores = [];
let modoEdicao = false;

// Busca todos os colaboradores da API
async function carregar() {
    try {
        const res = await fetchComToken(API);
        if (!res.ok) throw new Error();
        colaboradores = await res.json();
        renderTabela();
        renderStats();
    } catch {
        document.getElementById('tabela-body').innerHTML =
            '<tr><td colspan="6">Erro ao conectar com o servidor.</td></tr>';
    }
}

// Verifica se tem token — se não tiver, vai para o login
const token = localStorage.getItem('token');
if (!token) {
    window.location.href = '/login.html';
}

// Função auxiliar que já inclui o token em todas as requisições
async function fetchComToken(url, options = {}) {
    const token = localStorage.getItem('token');
    return fetch(url, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
            ...options.headers
        }
    });
}

// Mostra o nome do usuário logado
document.getElementById('nome-usuario').textContent =
    localStorage.getItem('nomeUsuario') || '';

// Logout
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('nomeUsuario');
    window.location.href = '/login.html';
}

// Atualiza os cards de estatísticas
function renderStats() {
    document.getElementById('stat-total').textContent = colaboradores.length;

    const scores = colaboradores.map(c => c.score || 0);
    const top = scores.length ? Math.max(...scores) : 0;
    const media = scores.length ? Math.round(scores.reduce((a, b) => a + b, 0) / scores.length) : 0;

    document.getElementById('stat-top').textContent = top;
    document.getElementById('stat-media').textContent = media;
}

// Monta as linhas da tabela
function renderTabela() {
    const tbody = document.getElementById('tabela-body');

    if (colaboradores.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6">Nenhum colaborador cadastrado</td></tr>';
        return;
    }

    tbody.innerHTML = colaboradores.map(c => `
                <tr>
                    <td>${c.id}</td>
                    <td>${c.nome}</td>
                    <td>${c.score ?? 0}</td>
                    <td>${c.chefe ? c.chefe.nome : '—'}</td>

                    <td>
                        <input class='score-input' type='number' id='pts-${c.id}' placeholder='0' min='1'>
                        <button class='btn btn-success btn-sm' onclick='adicionarPontos(${c.id})'>+ pts</button>
                    </td>
                    <td>
                        <button class='btn btn-ghost btn-sm' onclick='abrirModalEditar(${c.id})'>Editar</button>
                        <button class='btn btn-danger btn-sm' onclick="deletar(${c.id})">Deletar</button>
                    </td>
                </tr>
            `).join('');
}

// Abre o modal para criar novo colaborador
function abrirModalCriar() {
    modoEdicao = false;
    document.getElementById('modal-titulo').textContent = 'Novo Colaborador';
    document.getElementById('form-id').value = '';
    document.getElementById('form-nome').value = '';
    document.getElementById('form-senha').value = '';
    preencherSelectChefe(null);
    document.getElementById('modal-overlay').classList.add('open');
}

// Abre o modal para editar colaborador existente
function abrirModalEditar(id) {
    const c = colaboradores.find(x => x.id === id);
    modoEdicao = true;
    document.getElementById('modal-titulo').textContent = 'Editar Colaborador';
    document.getElementById('form-id').value = c.id;
    document.getElementById('form-nome').value = c.nome;
    document.getElementById('form-senha').value = '';
    preencherSelectChefe(c.chefe?.id);
    document.getElementById('modal-overlay').classList.add('open');
}

// Preenche o select de chefe com os colaboradores disponíveis
function preencherSelectChefe(chefeAtualId) {
    const select = document.getElementById('form-chefe');
    const idAtual = document.getElementById('form-id').value;
    select.innerHTML = '<option value="">Nenhum</option>';
    colaboradores
        .filter(c => String(c.id) !== String(idAtual))
        .forEach(c => {
            const opt = document.createElement('option');
            opt.value = c.id;
            opt.textContent = c.nome;
            if (c.id === chefeAtualId) opt.selected = true;
            select.appendChild(opt);
        });
}

// Fecha o modal
function fecharModal() {
    document.getElementById('modal-overlay').classList.remove('open');
}

// Salva (cria ou edita) o colaborador
async function salvar() {
    try {
        const id = document.getElementById('form-id').value;
        const nome = document.getElementById('form-nome').value.trim();
        const senha = document.getElementById('form-senha').value;
        const chefeId = document.getElementById('form-chefe').value;

        if (!nome) {
            alert('Informe o nome');
            return;
        }
        if (!modoEdicao && !senha) {
            alert('Informe a senha');
            return;
        }

        const body = {nome};
        if (senha) body.senha = senha;
        if (chefeId) body.chefe = {id: parseInt(chefeId)};

        const url = modoEdicao ? `${API}/${id}` : API;
        const method = modoEdicao ? 'PUT' : 'POST';

        await fetchComToken(url, {
            method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        });

        fecharModal();
        await carregar();
    } catch {
        alert('Erro ao salvar. Tente novamente');
    }
}

// Deleta o colaborador
async function deletar(id) {
    if (!confirm('Deletar este colaborador?')) return;
    try {
        await fetchComToken(`${API}/${id}`, {method: 'DELETE'});
        await carregar();
    } catch {
        alert('Erro ao deletar.');
    }
}

//Diciona pontos ao colaborador
async function adicionarPontos(id) {
    const input = document.getElementById(`pts-${id}`);
    const pontos = parseInt(input.value);

    if (!pontos || pontos <= 0) {
        alert('Informe um valor valido');
        return;
    }
    try {
        await fetchComToken(`${API}/${id}/add-score?pontos=${pontos}`, {method: 'PATCH'});
        input.value = '';
        await carregar();
    } catch {
        alert('Erro ao adicionar pontos');
    }
}

// Inicia carregando os dados
carregar();