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



document.addEventListener("DOMContentLoaded", function () {
    
    const tempTotalEl = document.getElementById('tempTotalAmount');
    if (!tempTotalEl) return; 

    const timeStartInput = document.getElementById('timeStart');
    const timeEndInput = document.getElementById('timeEnd');
    const roomRadios = document.querySelectorAll('input[name="roomId"]');

    function calculateTotal() {
        const startVal = timeStartInput.value;
        const endVal = timeEndInput.value;
        let selectedPrice = 0;

        const selectedRoom = document.querySelector('input[name="roomId"]:checked');
        if (selectedRoom) {
            selectedPrice = parseFloat(selectedRoom.getAttribute('data-price')) || 0;
        }

        if (!startVal || !endVal || selectedPrice === 0) {
            tempTotalEl.innerText = "--- đ";
            tempTotalEl.className = "fw-bold text-danger m-0";
            return;
        }

        const startDate = new Date(startVal);
        const endDate = new Date(endVal);

        let timeDiff = endDate.getTime() - startDate.getTime();
        if (timeDiff <= 0) {
            tempTotalEl.innerText = "Giờ ra phải sau giờ vào!";
            tempTotalEl.className = "fw-bold text-danger m-0";
            return;
        }

        let days = Math.ceil(timeDiff / (1000 * 3600 * 24));
        if (days < 1) days = 1; 

        const total = days * selectedPrice;

        tempTotalEl.innerText = total.toLocaleString('vi-VN') + " đ";
        tempTotalEl.className = "fw-bold text-success m-0 fs-4";
    }

    timeStartInput.addEventListener('change', calculateTotal);
    timeEndInput.addEventListener('change', calculateTotal);
    roomRadios.forEach(radio => radio.addEventListener('change', calculateTotal));


    const btnSearchPhone = document.getElementById('btnSearchPhone');
    const phoneInput = document.getElementById('phone');
    const guestNameInput = document.getElementById('guestName');

    if (btnSearchPhone) {
        btnSearchPhone.addEventListener('click', function () {
            const phoneVal = phoneInput.value.trim();
            if (phoneVal === '') {
                alert('Vui lòng nhập số điện thoại để tra cứu!');
                phoneInput.focus();
                return;
            }
            const icon = btnSearchPhone.querySelector('i');
            icon.className = 'fa-solid fa-spinner fa-spin';

            fetch(`/api/guests/search?phone=${phoneVal}`)
                .then(response => {
                    if (!response.ok) throw new Error('Not found');
                    return response.json();
                })
                .then(data => {
                    guestNameInput.value = data.guestName;
                    
                    guestNameInput.classList.add('is-valid'); 
                    setTimeout(() => guestNameInput.classList.remove('is-valid'), 2500);
                })
                .catch(error => {
                    alert('Khách hàng mới! Vui lòng nhập tay họ và tên.');
                    guestNameInput.value = '';
                    guestNameInput.focus();
                })
                .finally(() => {
                    icon.className = 'fa-solid fa-magnifying-glass';
                });
        });
    }

    const roomTypeSelect = document.getElementById('roomTypeSelect');
    const roomCards = document.querySelectorAll('.room-card-container'); 

    if (roomTypeSelect) {
        roomTypeSelect.addEventListener('change', function () {
            const selectedTypeId = this.value;

            roomCards.forEach(card => {
                const cardTypeId = card.getAttribute('data-type');
                
                if (selectedTypeId === "" || cardTypeId === selectedTypeId) {
                    card.style.display = 'block'; 
                } else {
                    card.style.display = 'none';  
                }
            });
            
            const checkedRadio = document.querySelector('input[name="roomId"]:checked');
            if(checkedRadio) {
                checkedRadio.checked = false;
                calculateTotal(); 
            }
        });
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const btnAddGuest = document.getElementById('btnAddGuest');
    const guestTableBody = document.getElementById('guestTableBody');

    if (btnAddGuest && guestTableBody) {
        
        function updateRowIndices() {
            const rows = guestTableBody.querySelectorAll('.guest-row');
            rows.forEach((row, index) => {
                row.querySelector('.row-number').innerText = index + 1;
                
                const nameInput = row.querySelector('input[name$=".fullName"]');
                const idInput = row.querySelector('input[name$=".identityNumber"]');
                const genderSelect = row.querySelector('select[name$=".gender"]');
                
                if (nameInput) nameInput.name = `guests[${index}].fullName`;
                if (idInput) idInput.name = `guests[${index}].identityNumber`;
                if (genderSelect) genderSelect.name = `guests[${index}].gender`;

                const removeBtn = row.querySelector('.btn-remove-guest');
                if (removeBtn) {
                    removeBtn.disabled = (index === 0);
                }
            });
        }

        btnAddGuest.addEventListener('click', function () {
            const currentRowCount = guestTableBody.querySelectorAll('.guest-row').length;
            
            const newRowHtml = `
                <tr class="guest-row">
                    <td class="align-middle fw-bold text-secondary row-number">${currentRowCount + 1}</td>
                    <td>
                        <input type="text" name="guests[${currentRowCount}].fullName" class="form-control form-control-sm text-uppercase" required>
                    </td>
                    <td>
                        <input type="text" name="guests[${currentRowCount}].identityNumber" class="form-control form-control-sm" required>
                    </td>
                    <td>
                        <select name="guests[${currentRowCount}].gender" class="form-select form-select-sm">
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                        </select>
                    </td>
                    <td class="text-center align-middle">
                        <button type="button" class="btn btn-sm text-danger btn-remove-guest">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    </td>
                </tr>
            `;
            
            guestTableBody.insertAdjacentHTML('beforeend', newRowHtml);
            
            const newInputs = guestTableBody.querySelectorAll('.guest-row:last-child input[type="text"]');
            if (newInputs.length > 0) newInputs[0].focus();
        });

        guestTableBody.addEventListener('click', function (e) {
            const removeBtn = e.target.closest('.btn-remove-guest');
            
            if (removeBtn && !removeBtn.disabled) {
                const rowToRemove = removeBtn.closest('.guest-row');
                rowToRemove.remove();
                
                updateRowIndices();
            }
        });
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const checkoutForm = document.getElementById('checkoutForm');
    
    if (checkoutForm) {
        const rawSubTotalEl = document.getElementById('rawSubTotal');
        const rawDepositEl = document.getElementById('rawDeposit');
        const manualDiscountAmountEl = document.getElementById('manualDiscountAmount');
        const finalAmountDueEl = document.getElementById('finalAmountDue');
        
        const discountRow = document.getElementById('discountRow');
        const displayDiscount = document.getElementById('displayDiscount');

        function calculateFinalCheckout() {
            const subTotal = parseFloat(rawSubTotalEl.value) || 0;
            const deposit = parseFloat(rawDepositEl.value) || 0;
            const manualDiscount = parseFloat(manualDiscountAmountEl.value) || 0;

            let finalDue = subTotal - deposit - manualDiscount;
            
            if (finalDue < 0) finalDue = 0;

            finalAmountDueEl.innerText = finalDue.toLocaleString('vi-VN') + ' đ';

            if (manualDiscount > 0) {
                discountRow.classList.remove('d-none');
                displayDiscount.innerText = manualDiscount.toLocaleString('vi-VN');
            } else {
                discountRow.classList.add('d-none');
            }
        }

        if (manualDiscountAmountEl) {
            manualDiscountAmountEl.addEventListener('input', calculateFinalCheckout);
        }
        
        calculateFinalCheckout();
    }
});