// Health Worker Dashboard JS
document.addEventListener('DOMContentLoaded', function() {
    console.log('Health Worker Dashboard loaded');
    
    // Copy phone numbers
    const callButtons = document.querySelectorAll('.btn-outline-success');
    callButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            const phone = this.dataset.phone || this.closest('td').querySelector('span').textContent;
            navigator.clipboard.writeText(phone).then(() => {
                alert('Phone copied to clipboard!');
            });
        });
    });
    
    // Low stock alert
    const lowStock = document.querySelectorAll('.badge.bg-danger');
    if (lowStock.length > 0) {
        console.log(`${lowStock.length} low stock items`);
    }
    
    // Camp status colors
    const badges = document.querySelectorAll('.badge');
    badges.forEach(badge => {
        if (badge.textContent.toLowerCase().includes('scheduled')) {
            badge.classList.add('bg-success');
        } else if (badge.textContent.toLowerCase().includes('completed')) {
            badge.classList.add('bg-success');
        } else if (badge.textContent.toLowerCase().includes('cancelled')) {
            badge.classList.add('bg-danger');
        }
    });
});

