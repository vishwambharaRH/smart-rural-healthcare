# Quick Reference - Appointment & Inventory Features

## 🗓️ **Appointment Management**

### Cancelling an Appointment
1. Login as **Patient** (username: `patient1`, password: `password`)
2. Go to **Patient Dashboard** → `My Appointments`
3. Click **Cancel** button on appointment
4. Confirm cancellation
5. Appointment status changes to **CANCELLED**

### Rescheduling an Appointment
1. Login as **Patient**
2. Go to **Patient Dashboard** → `My Appointments`
3. Click **Reschedule** button on appointment
4. Enter **New Date & Time** for appointment
5. Optionally update **Reason**
6. Click **Reschedule Appointment**
7. Appointment status resets to **PENDING**

---

## 💊 **Medicine Inventory Management**

### Adding a New Medicine
1. Login as **Admin** (username: `admin`, password: `admin`) or **HealthWorker**
2. Go to **Admin Dashboard** → Quick Actions → **Manage Medicines**
   - OR go directly to `/medicines`
3. Fill the form on the left:
   - **Medicine Name** (e.g., "Aspirin")
   - **Quantity** (number of units)
   - **Cost per Unit** (in rupees)
   - **Expiry Date** (YYYY-MM-DD)
   - **Location** (storage location)
   - **Specialty** (e.g., General, Cardiology)
   - **Batch Number** (required)
4. Click **Add Medicine**
5. Success message appears
6. Medicine appears in the inventory list

### Viewing Inventory
- Navigate to `/medicines`
- See all medicines in a table with:
  - Medicine name
  - Current quantity
  - Cost per unit
  - Expiry date
  - Batch number
  - Storage location
- **⚠️ Low Stock Alert**: Quantity ≤ 10 units shown in orange

### Editing a Medicine
1. Go to `/medicines`
2. Find medicine in the list
3. Click **✏️ (pencil)** button
4. Update the form fields
5. Click **Update Medicine**
6. Changes saved successfully

### Deleting a Medicine
1. Go to `/medicines`
2. Find medicine in the list
3. Click **🗑️ (trash)** button
4. Confirm deletion
5. Medicine removed from inventory

---

## 📊 **Inventory Statistics**

When viewing the inventory page (`/medicines`), you'll see:
- **Total Items**: Count of all medicines in inventory
- **Low Stock Items**: Count of medicines with quantity ≤ 10 units

---

## 🔐 **Access Control**

| Feature | Admin | HealthWorker | Doctor | Patient |
|---------|-------|-------------|--------|---------|
| View Medicines | ✅ | ✅ | ❌ | ❌ |
| Add Medicine | ✅ | ✅ | ❌ | ❌ |
| Edit Medicine | ✅ | ✅ | ❌ | ❌ |
| Delete Medicine | ✅ | ✅ | ❌ | ❌ |
| Cancel Appointment | ❌ | ❌ | ❌ | ✅ |
| Reschedule Appointment | ❌ | ❌ | ❌ | ✅ |
| View Own Appointments | ❌ | ❌ | ✅ | ✅ |

---

## 🔗 **URL Reference**

| URL | Purpose | Roles |
|-----|---------|-------|
| `/medicines` | View & manage inventory | ADMIN, HEALTHWORKER |
| `/medicines/edit/{id}` | Edit medicine form | ADMIN, HEALTHWORKER |
| `/medicines/delete/{id}` | Delete medicine | ADMIN, HEALTHWORKER |
| `/patient-dashboard` | Patient appointments | USER |
| `/appointments/cancel/{id}` | Cancel appointment | USER |
| `/appointments/reschedule/{id}` | Reschedule form | USER |
| `/appointments/reschedule` | Submit reschedule | USER |

---

## ⚙️ **Appointment Status Values**

After operations, appointments have these statuses:
- **PENDING**: New or rescheduled appointment awaiting confirmation
- **APPROVED**: Doctor has approved the appointment
- **CANCELLED**: Patient cancelled (marked, not deleted)
- **REJECTED**: Doctor rejected the appointment

---

## 📋 **Default Test Accounts**

```
Admin:
  Username: admin
  Password: admin

Doctor 1:
  Username: doctor1
  Password: password

HealthWorker:
  Username: worker1
  Password: password

Patient:
  Username: patient1
  Password: password
```

---

## 💡 **Tips & Tricks**

### Medicine Management
- Use **Specialty** field to categorize medicines by medical department
- Update **Quantity** regularly to track stock levels
- Set **Expiry Date** format as YYYY-MM-DD
- **Batch Number** is required for all medicines (regulatory compliance)

### Appointment Management
- When rescheduling, the appointment status resets to PENDING
- Doctor must approve the rescheduled appointment
- Cancelled appointments remain in database (soft delete) for audit trail
- Cannot cancel appointment after doctor has approved (app design)

---

## 🐛 **Troubleshooting**

### Issue: Appointment links not working
**Solution**: Make sure you're using Thymeleaf links correctly (should auto-fill IDs)

### Issue: Cannot add medicine
**Solution**: 
- Ensure you're logged in as ADMIN or HEALTHWORKER
- All fields must be filled (especially Batch Number)
- Click the **Add Medicine** button, not Enter key

### Issue: Low stock alert not showing
**Solution**: Ensure quantity is set to 10 or less units

### Issue: Cannot delete medicine
**Solution**: 
- You need ADMIN or HEALTHWORKER role
- Confirm the deletion in the browser dialog
- Medicine should be deleted immediately

---

## 📞 **Support**

For issues or feature requests:
1. Check the FIXES_REPORT.md for technical details
2. Review SETUP_GUIDE.md for configuration
3. Check application logs at `/admin-dashboard` for debugging
4. Verify your user role has correct permissions
