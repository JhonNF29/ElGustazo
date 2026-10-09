const apiUrl = "/api/v1/pedidos";
const orderForm = document.querySelector("#order-form");
const ordersList = document.querySelector("#orders-list");
const orderCount = document.querySelector("#order-count");
const formMessage = document.querySelector("#form-message");
const statusLabels = { PENDIENTE: "Pendiente", EN_PREPARACION: "En preparación", LISTO: "Listo", ENTREGADO: "Entregado" };

function showMessage(message, isError = false) {
    formMessage.textContent = message;
    formMessage.classList.toggle("error", isError);
}

function escapeHtml(value) {
    return String(value).replace(/[&<>'"]/g, (character) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&#39;", '"': "&quot;" }[character]));
}

function renderOrders(orders) {
    orderCount.textContent = orders.length;
    if (!orders.length) {
        ordersList.innerHTML = '<div class="empty-state">Todavía no hay pedidos registrados.</div>';
        return;
    }
    ordersList.innerHTML = orders.map((order) => `<article class="order-item"><div><p class="order-name">${escapeHtml(order.clienteNombre)}</p><p class="order-meta">#${order.id} · ${order.tipoServicio === "DELIVERY" ? "Delivery" : "En salón"}</p><label class="status-label" for="status-${order.id}">Estado</label><select class="status-select" id="status-${order.id}" data-status-id="${order.id}">${Object.entries(statusLabels).map(([value, label]) => `<option value="${value}" ${value === order.estado ? "selected" : ""}>${label}</option>`).join("")}</select></div><button class="delete-button" type="button" data-id="${order.id}" title="Eliminar pedido">Eliminar</button></article>`).join("");
}

async function loadOrders() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("No se pudieron cargar los pedidos.");
        renderOrders(await response.json());
    } catch (error) {
        ordersList.innerHTML = `<div class="empty-state">${error.message} Revisa que el servidor esté activo.</div>`;
    }
}

orderForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(orderForm);
    const pedido = { clienteNombre: formData.get("clienteNombre"), tipoServicio: formData.get("tipoServicio") };
    showMessage("Guardando pedido...");
    try {
        const response = await fetch(apiUrl, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(pedido) });
        if (!response.ok) throw new Error("No se pudo registrar el pedido.");
        orderForm.reset();
        showMessage("Pedido registrado correctamente.");
        await loadOrders();
    } catch (error) { showMessage(error.message, true); }
});

ordersList.addEventListener("click", async (event) => {
    const button = event.target.closest("[data-id]");
    if (!button || !window.confirm("¿Eliminar este pedido?")) return;
    const response = await fetch(`${apiUrl}/${button.dataset.id}`, { method: "DELETE" });
    if (response.ok) loadOrders();
});

ordersList.addEventListener("change", async (event) => {
    const select = event.target.closest("[data-status-id]");
    if (!select) return;
    const response = await fetch(`${apiUrl}/${select.dataset.statusId}`, { method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ estado: select.value }) });
    if (!response.ok) showMessage("No se pudo actualizar el estado.", true);
});

document.querySelector("#refresh-button").addEventListener("click", loadOrders);
loadOrders();