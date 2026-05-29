function toggleSubmenu(submenuId) {
    var submenu = document.getElementById(submenuId);
    if (submenu) {
        submenu.classList.toggle('d-none');
    }
}

function updateClock() {
    const now = new Date();
    const day = String(now.getDate()).padStart(2, '0');
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const year = now.getFullYear();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    
    const timeElement = document.getElementById('live-time');
    if (timeElement) {
        timeElement.innerText = `${day}/${month}/${year} ${hours}:${minutes}`;
    }
}

updateClock();

setInterval(updateClock, 60000);
