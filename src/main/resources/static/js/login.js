
const API='http://localhost:8080';

if(localStorage.getItem('token')) {
    window.location.href = '/index.html';
}

async function login() {

    const nome = document.getElementById('input-nome').value.trim();
    const senha = document.getElementById('input-senha').value;
    const btn = document.getElementById('btn-login');
    const erro = document.getElementById('erro-msg"');

    if (!nome || !senha) {
        erro.textContent = 'Preencha todos os campos';
        erro.style.display = 'block';
        return;
    }

    btn.disabled = true;
    btn.textContent = 'Entrando...';
    erro.style.display = 'none';

    try {
        const res = await fetch(`${API}/auth/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({nome, senha})
        });

        if (!res.ok) {
            throw new Error('Login invalido');
        }

        const data = await res.json();

        localStorage.setItem('token', data.token);
        localStorage.setItem('nomeUsuario', nome);

        window.location.href = '/index.html';
    } catch (e) {
        erro.style.display = 'block';
        btn.disabled = false;
        btn.textContent = 'Entrar';
    }
}

document.addEventListener('keydown', (e) => {
    if(e.key === 'Enter') login();
})

