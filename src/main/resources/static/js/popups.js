export function showBasicPopup(data) {
    const modalContent = `
        <div class="modal fade" id="sessionAlertModal" tabindex="-1" aria-labelledby="sessionAlertModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header justify-content-center">
                        <h5 class="modal-title text-center w-100" id="sessionAlertModalLabel">${data.title}</h5>
                        <button type="button" class="btn-close position-absolute top-0 end-0 mt-2 me-2" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body text-center">
                        ${data.desc}
                    </div>
                    <div class="modal-footer justify-content-center">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${data.closeBtnTitle}</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalContent);

    const modal = new bootstrap.Modal(document.getElementById('sessionAlertModal'));
    modal.show();

    document.getElementById('sessionAlertModal').addEventListener('hidden.bs.modal', () => {
        document.getElementById('sessionAlertModal').remove();
    });
}