$(document).ready(function () {
    // Define a URL de backend (simulada)
    const backendURL = 'localhost:8080/recoleta/registration';

    // Manipulador de envio do formulário
    $('#login-form').submit(function (event) {
        event.preventDefault();
        
        // Coleta dados do formulário
        const username = $('#username').val();
        const password = $('#password').val();

        // Envia uma solicitação AJAX para o backend
        $.ajax({
            url: backendURL,
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            success: function (response) {
                if (response.success) {
                    $('#result').text('Login bem-sucedido! Redirecionando...');
                    // Redirecione o usuário após um login bem-sucedido, se necessário
                } else {
                    $('#result').text('Credenciais inválidas. Tente novamente.');
                }
            },
            error: function () {
                $('#result').text('Erro ao se comunicar com o servidor.');
            }
        });
    });
});
