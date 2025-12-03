let fontSize = 100;
let highContrast = false;
let grayScale = false;
let underlineLinks = false;

// Mostrar u ocultar panel
document.getElementById("accessibility-btn").onclick = () => {
    const panel = document.getElementById("accessibility-panel");
    panel.style.display = panel.style.display === "block" ? "none" : "block";
};

// Cambiar tamaño del texto
function changeFontSize(change) {
    fontSize += change * 10;
    document.body.style.fontSize = fontSize + "%";
}

// Modo alto contraste
function toggleContrast() {
    highContrast = !highContrast;
    document.body.classList.toggle("high-contrast", highContrast);
}

// Modo escala de grises
function toggleGrayscale() {
    grayScale = !grayScale;
    document.body.style.filter = grayScale ? "grayscale(100%)" : "none";
}

// Subrayar enlaces
function toggleUnderline() {
    underlineLinks = !underlineLinks;
    const links = document.querySelectorAll("a");
    links.forEach(link => {
        link.style.textDecoration = underlineLinks ? "underline" : "none";
    });
}

// Restablecer todo
function resetAccessibility() {
    fontSize = 100;
    document.body.style.fontSize = "100%";
    document.body.style.filter = "none";
    document.body.classList.remove("high-contrast");

    const links = document.querySelectorAll("a");
    links.forEach(link => link.style.textDecoration = "none");
}
