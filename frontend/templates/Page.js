// Carrega o conteúdo do footer.html no div com ID "footer-container"
document.addEventListener('DOMContentLoaded', () => {
    fetch('footer.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('footer-container').innerHTML = html;
        })
        .catch(error => console.error('Erro ao carregar o footer:', error));

    // Carrega o conteúdo do navbar.html no div com ID "navbar-container"
    fetch('navbar.html')
        .then(response => response.text())
        .then(html => {
        document.getElementById('navbar-container').innerHTML = html;
    })
        .catch(error => console.error('Erro ao carregar a barra de navegação:', error));

    // Show/hide navigation on mobile
    document.addEventListener("DOMContentLoaded", () => {
        const hamburger = document.querySelector(".hamburger");
        const navLinks = document.querySelector(".nav-links");

        hamburger.addEventListener("click", () => {
            navLinks.classList.toggle("show");
        });
    });
});
