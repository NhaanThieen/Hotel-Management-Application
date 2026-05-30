// Biến toàn cục để lưu trữ các instance của Chart, giúp hủy (destroy) khi cần thiết
const activeCharts = {};

document.addEventListener("DOMContentLoaded", function() {

    function readJsonScript(id, fallback) {
        const el = document.getElementById(id);
        if (!el) return fallback;
        try {
            const txt = el.textContent || el.innerHTML || '';
            return JSON.parse(txt || '{}');
        } catch (e) {
            return fallback;
        }
    }

    const dbData = window.dashboardData || readJsonScript('dashboard-data', {
        revenueLabels: [], revenueData: [], serviceLabels: [], serviceData: []
    });

    const reportData = window.reportData || readJsonScript('report-data', { labels: [], revenue: [] });

    if (window.Chart) {
        Chart.defaults.font.family = "system-ui, -apple-system, sans-serif";
        Chart.defaults.color = '#6c757d';
    }

    function drawChart(canvasId, config) {
        if (activeCharts[canvasId]) {
            activeCharts[canvasId].destroy();
        }
        const ctx = document.getElementById(canvasId);
        if (!ctx) return;
        activeCharts[canvasId] = new Chart(ctx.getContext('2d'), config);
    }
    if (document.getElementById('revenueChart')) {
        drawChart('revenueChart', {
            type: 'bar',
            data: {
                labels: dbData.revenueLabels,
                datasets: [{
                    label: 'Doanh thu (Triệu VNĐ)',
                    data: dbData.revenueData,
                    backgroundColor: '#198754', 
                    borderRadius: 4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } } }
        });
    }

    if (document.getElementById('serviceChart')) {
        drawChart('serviceChart', {
            type: 'doughnut',
            data: {
                labels: dbData.serviceLabels, 
                datasets: [{
                    data: dbData.serviceData,
                    backgroundColor: ['#198754', '#0d6efd', '#ffc107', '#dc3545'], 
                    borderWidth: 0,
                    hoverOffset: 4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { position: 'bottom' } }, cutout: '65%' }
        });
    }

    if (reportData && Array.isArray(reportData.labels) && Array.isArray(reportData.revenue) && reportData.labels.length) {
        try { renderReportChart(reportData.labels, reportData.revenue); } catch (e) { /* ignore */ }
    }

    const paymentModalEl = document.getElementById('paymentModal');
    const paymentModal = paymentModalEl ? new bootstrap.Modal(paymentModalEl) : null;

    const btnAddPayment = document.getElementById('btnAddPayment');
    if (btnAddPayment && paymentModal) {
        btnAddPayment.addEventListener('click', function() {
            document.getElementById('methodId').value = ''; 
            document.getElementById('paymentForm').reset(); 
            document.getElementById('statusSwitch').checked = true; 
            document.getElementById('paymentModalLabel').innerText = "Thêm mới Phương thức";
            paymentModal.show(); 
        });
    }

    document.querySelectorAll('.btn-edit-payment').forEach(btn => {
        btn.addEventListener('click', function() {
            document.getElementById('methodId').value = this.getAttribute('data-id') || '';
            document.getElementById('methodName').value = this.getAttribute('data-name') || '';
            document.getElementById('methodCode').value = this.getAttribute('data-code') || '';
            document.getElementById('methodDesc').value = this.getAttribute('data-desc') || '';
            document.getElementById('statusSwitch').checked = (this.getAttribute('data-active') === 'true');

            document.getElementById('paymentModalLabel').innerText = "Cập nhật Phương thức";
            if (paymentModal) paymentModal.show();
        });
    });

    const roomDetailModalEl = document.getElementById('roomDetailModal');
    const roomDetailModal = roomDetailModalEl ? new bootstrap.Modal(roomDetailModalEl) : null;
    document.querySelectorAll('.btn-room-detail').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const id = this.getAttribute('data-id');
            const code = this.getAttribute('data-code') || this.getAttribute('data-name') || '';
            const type = this.getAttribute('data-type') || '';
            const status = this.getAttribute('data-status') || '';

            const setText = (idSelector, text) => {
                const el = document.getElementById(idSelector);
                if (el) el.textContent = text || '-';
            };

            setText('roomDetailCode', code);
            setText('roomDetailType', type);
            setText('roomDetailStatus', status);

            const detailLink = document.getElementById('roomDetailLink');
            if (detailLink) detailLink.setAttribute('href', '/admin/rooms/' + (id || ''));

            if (roomDetailModal) roomDetailModal.show();
        });
    });

    const roomTypeModalEl = document.getElementById('roomTypeModal');
    const roomTypeModal = roomTypeModalEl ? new bootstrap.Modal(roomTypeModalEl) : null;
    const roomTypeDeleteModalEl = document.getElementById('roomTypeDeleteModal');
    const roomTypeDeleteModal = roomTypeDeleteModalEl ? new bootstrap.Modal(roomTypeDeleteModalEl) : null;

    const roomTypeForm = document.getElementById('roomTypeForm');
    const roomTypeSubmitBtn = document.getElementById('roomTypeSubmitBtn');
    const roomTypeDeleteForm = document.getElementById('roomTypeDeleteForm');

    const setRoomTypeFormMode = (mode) => {
        const modalTitle = document.getElementById('roomTypeModalLabel');
        if (modalTitle) modalTitle.textContent = mode === 'edit' ? 'Cập nhật loại phòng' : 'Thêm loại phòng';
        if (roomTypeSubmitBtn) roomTypeSubmitBtn.textContent = mode === 'edit' ? 'Cập nhật' : 'Lưu thay đổi';
    };

    const clearRoomTypeForm = () => {
        if (roomTypeForm) roomTypeForm.reset();
        const roomTypeId = document.getElementById('roomTypeId');
        if (roomTypeId) roomTypeId.value = '';
    };

    document.querySelectorAll('.btn-roomtype-add').forEach(btn => {
        btn.addEventListener('click', function() {
            clearRoomTypeForm();
            setRoomTypeFormMode('add');
            if (roomTypeModal) roomTypeModal.show();
        });
    });

    document.querySelectorAll('.btn-roomtype-edit').forEach(btn => {
        btn.addEventListener('click', function() {
            clearRoomTypeForm();
            setRoomTypeFormMode('edit');

            const roomTypeId = document.getElementById('roomTypeId');
            const roomTypeName = document.getElementById('roomTypeName');
            const roomTypePrice = document.getElementById('roomTypePrice');

            if (roomTypeId) roomTypeId.value = this.getAttribute('data-id') || '';
            if (roomTypeName) roomTypeName.value = this.getAttribute('data-name') || '';
            if (roomTypePrice) roomTypePrice.value = this.getAttribute('data-price') || '';

            if (roomTypeModal) roomTypeModal.show();
        });
    });

    document.querySelectorAll('.btn-roomtype-delete').forEach(btn => {
        btn.addEventListener('click', function() {
            const roomTypeDeleteId = document.getElementById('roomTypeDeleteId');
            const roomTypeDeleteName = document.getElementById('roomTypeDeleteName');

            if (roomTypeDeleteId) roomTypeDeleteId.value = this.getAttribute('data-id') || '';
            if (roomTypeDeleteName) roomTypeDeleteName.textContent = this.getAttribute('data-name') || '-';

            if (roomTypeDeleteForm) {
                const roomTypeId = this.getAttribute('data-id') || '';
                roomTypeDeleteForm.setAttribute('action', '/admin/room-types/delete/' + roomTypeId);
            }

            if (roomTypeDeleteModal) roomTypeDeleteModal.show();
        });
    });

    const serviceModalEl = document.getElementById('serviceModal');
    const serviceModal = serviceModalEl ? new bootstrap.Modal(serviceModalEl) : null;
    const serviceDeleteModalEl = document.getElementById('serviceDeleteModal');
    const serviceDeleteModal = serviceDeleteModalEl ? new bootstrap.Modal(serviceDeleteModalEl) : null;

    const voucherModalEl = document.getElementById('voucherModal');
    const voucherModal = voucherModalEl ? new bootstrap.Modal(voucherModalEl) : null;
    const voucherDeleteModalEl = document.getElementById('voucherDeleteModal');
    const voucherDeleteModal = voucherDeleteModalEl ? new bootstrap.Modal(voucherDeleteModalEl) : null;

    const resetFormField = (id) => {
        const el = document.getElementById(id);
        if (el) el.value = '';
    };

    document.querySelectorAll('.btn-service-add').forEach(btn => {
        btn.addEventListener('click', function() {
            const serviceForm = document.getElementById('serviceForm');
            if (serviceForm) serviceForm.reset();
            resetFormField('serviceId');
            const title = document.getElementById('serviceModalLabel');
            const submitBtn = document.getElementById('serviceSubmitBtn');
            if (title) title.textContent = 'Thêm dịch vụ';
            if (submitBtn) submitBtn.textContent = 'Lưu thay đổi';
            if (serviceModal) serviceModal.show();
        });
    });

    document.querySelectorAll('.btn-service-edit').forEach(btn => {
        btn.addEventListener('click', function() {
            const serviceForm = document.getElementById('serviceForm');
            if (serviceForm) serviceForm.reset();

            const serviceId = document.getElementById('serviceId');
            const serviceName = document.getElementById('serviceName');
            const servicePrice = document.getElementById('servicePrice');
            const serviceStock = document.getElementById('serviceStock');
            const serviceTypeId = document.getElementById('serviceTypeId');
            const title = document.getElementById('serviceModalLabel');
            const submitBtn = document.getElementById('serviceSubmitBtn');

            if (serviceId) serviceId.value = this.getAttribute('data-id') || '';
            if (serviceName) serviceName.value = this.getAttribute('data-name') || '';
            if (servicePrice) servicePrice.value = this.getAttribute('data-price') || '';
            if (serviceStock) serviceStock.value = this.getAttribute('data-stock') || '';
            if (serviceTypeId) serviceTypeId.value = this.getAttribute('data-type-id') || '';
            if (title) title.textContent = 'Cập nhật dịch vụ';
            if (submitBtn) submitBtn.textContent = 'Cập nhật';

            if (serviceModal) serviceModal.show();
        });
    });

    document.querySelectorAll('.btn-service-delete').forEach(btn => {
        btn.addEventListener('click', function() {
            const serviceDeleteId = document.getElementById('serviceDeleteId');
            const serviceDeleteName = document.getElementById('serviceDeleteName');
            if (serviceDeleteId) serviceDeleteId.value = this.getAttribute('data-id') || '';
            if (serviceDeleteName) serviceDeleteName.textContent = this.getAttribute('data-name') || '-';
            if (serviceDeleteModal) serviceDeleteModal.show();
        });
    });

    document.querySelectorAll('.btn-voucher-add').forEach(btn => {
        btn.addEventListener('click', function() {
            const voucherForm = document.getElementById('voucherForm');
            if (voucherForm) voucherForm.reset();
            resetFormField('voucherId');
            const title = document.getElementById('voucherModalLabel');
            const submitBtn = document.getElementById('voucherSubmitBtn');
            if (title) title.textContent = 'Thêm voucher';
            if (submitBtn) submitBtn.textContent = 'Lưu thay đổi';
            if (voucherModal) voucherModal.show();
        });
    });

    document.querySelectorAll('.btn-voucher-edit').forEach(btn => {
        btn.addEventListener('click', function() {
            const voucherForm = document.getElementById('voucherForm');
            if (voucherForm) voucherForm.reset();

            const voucherId = document.getElementById('voucherId');
            const voucherName = document.getElementById('voucherName');
            const voucherQuantity = document.getElementById('voucherQuantity');
            const voucherPercentDiscount = document.getElementById('voucherPercentDiscount');
            const voucherMaxDiscount = document.getElementById('voucherMaxDiscount');
            const voucherMinRequire = document.getElementById('voucherMinRequire');
            const voucherIsActive = document.getElementById('voucherIsActive');
            const title = document.getElementById('voucherModalLabel');
            const submitBtn = document.getElementById('voucherSubmitBtn');

            if (voucherId) voucherId.value = this.getAttribute('data-id') || '';
            if (voucherName) voucherName.value = this.getAttribute('data-name') || '';
            if (voucherQuantity) voucherQuantity.value = this.getAttribute('data-quantity') || '';
            if (voucherPercentDiscount) voucherPercentDiscount.value = this.getAttribute('data-percent') || '';
            if (voucherMaxDiscount) voucherMaxDiscount.value = this.getAttribute('data-max') || '';
            if (voucherMinRequire) voucherMinRequire.value = this.getAttribute('data-min') || '';
            if (voucherIsActive) voucherIsActive.value = this.getAttribute('data-active') || '0';
            if (title) title.textContent = 'Cập nhật voucher';
            if (submitBtn) submitBtn.textContent = 'Cập nhật';

            if (voucherModal) voucherModal.show();
        });
    });

    document.querySelectorAll('.btn-voucher-delete').forEach(btn => {
        btn.addEventListener('click', function() {
            const voucherDeleteId = document.getElementById('voucherDeleteId');
            const voucherDeleteName = document.getElementById('voucherDeleteName');
            if (voucherDeleteId) voucherDeleteId.value = this.getAttribute('data-id') || '';
            if (voucherDeleteName) voucherDeleteName.textContent = this.getAttribute('data-name') || '-';
            if (voucherDeleteModal) voucherDeleteModal.show();
        });
    });
});


function renderReportChart(labels, revenueData) {
    drawChart('reportChart', {
        type: 'line', 
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: revenueData,
                borderColor: '#0d6efd',
                tension: 0.3,
                fill: true,
                backgroundColor: 'rgba(13, 110, 253, 0.1)'
            }]
        },
        options: { responsive: true, maintainAspectRatio: false }
    });
}