# Inventory & Prescription Management - Quick Start Guide

## Quick Access Links

### Inventory Management
- **Dashboard**: http://localhost:8080/inventory/dashboard
- **All Medicines**: http://localhost:8080/inventory/list
- **Add Medicine**: http://localhost:8080/inventory/create
- **Low Stock Alert**: http://localhost:8080/inventory/low-stock

### Diagnoses & Prescriptions
- **All Diagnoses**: http://localhost:8080/diagnosis/list
- **Create Diagnosis**: http://localhost:8080/diagnosis/create
- **All Prescriptions**: http://localhost:8080/prescription/list

---

## Common Operations

### Operation 1: Add Medicine to Inventory
```
1. Go to http://localhost:8080/inventory/create
2. Fill in form:
   - Medicine Name: "Aspirin 500mg"
   - Batch Number: "BATCH-20260419-001"
   - Expiry Date: "2026-12-31"
   - Quantity: "100"
   - Location: "Village A Health Camp"
   - Specialty: "General"
   - Cost Per Unit: "5.50"
3. Click "Add Medicine"
```

### Operation 2: Create Diagnosis & Prescription
```
1. Go to http://localhost:8080/diagnosis/create
2. Create Diagnosis:
   - Diagnosis: "Type 2 Diabetes"
   - Symptoms: "Increased thirst, frequent urination, fatigue"
   - Notes: "Family history of diabetes"
   - Click "Create Diagnosis"

3. After diagnosis created, click "Create Rx"
4. Create Prescription:
   - Medicines: "Metformin 500mg"
   - Dosage: "1 tablet twice daily after meals"
   - Instructions: "Take with water, avoid skipping doses"
   - Duration: "30" days
   - Click "Create Prescription"
```

### Operation 3: Use Medicine (Reduce Stock)
```
1. Go to http://localhost:8080/inventory/list
2. Click "View" on a medicine
3. In "Actions" section, enter quantity to use
4. Click "Use" button
5. Stock will be reduced automatically
```

### Operation 4: Check Low Stock Medicines
```
1. Go to http://localhost:8080/inventory/dashboard
2. See "Low Stock Items" card (Qty < 10)
3. Click "Low Stock Items" button or go to
   http://localhost:8080/inventory/low-stock
4. Review medicines needing restock
```

### Operation 5: Restock Medicine
```
1. Go to http://localhost:8080/inventory/view/{id}
2. Enter quantity to add
3. Click "Add Stock" button
4. Quantity will increase automatically
```

---

## Database Queries (Advanced)

### Find All Low Stock Medicines
```java
List<MedicineInventory> lowStock = medicineInventoryService.getLowStockMedicines(10);
```

### Get Medicines by Specialty
```java
List<MedicineInventory> cardioMeds = medicineInventoryService.getMedicinesBySpecialty("Cardiology");
```

### Get Medicines for a Location
```java
List<MedicineInventory> villageMeds = medicineInventoryService.getMedicinesForVillage("Village A", 0);
```

### Get Prescription for a Diagnosis
```java
Optional<Prescription> rx = prescriptionService.getPrescriptionByDiagnosis(diagnosis);
```

---

## API Endpoints (JSON)

### Get Medicines by Specialty
```
GET /prescription/api/medicines-by-specialty/Cardiology
Response: List of medicines for Cardiology
```

---

## Key Classes

### Models
- **MedicineInventory** - Medicine stock tracking
- **Prescription** - Prescription details
- **Diagnosis** - Diagnosis information
- **MedicalRecord** - Patient medical history link

### Services
- **MedicineInventoryService** - Inventory operations
- **PrescriptionService** - Prescription management
- **DiagnosisService** - Diagnosis management

### Controllers
- **InventoryManagementController** - Inventory web interface
- **PrescriptionController** - Prescription web interface
- **DiagnosisController** - Diagnosis web interface

---

## Validation Rules

### Medicine Inventory
- ✅ Medicine Name: Required, max 100 chars
- ✅ Batch Number: Required, unique
- ✅ Quantity: Required, must be ≥ 0
- ✅ Location: Required, identifies where stored
- ✅ Specialty: Optional, helps filter by medical specialty

### Diagnosis
- ✅ Diagnosis Name: Required
- ✅ Symptoms: Required
- ✅ Notes: Optional

### Prescription
- ✅ Medicines: Required (comma-separated list)
- ✅ Dosage: Required (e.g., "1 tablet twice daily")
- ✅ Instructions: Optional
- ✅ Duration: Required (days)

---

## Error Scenarios

### Scenario: Trying to Use More Medicine Than Available
```
Error: "Insufficient quantity of medicine. Available: 5, Required: 10"
Action: Check current stock or reduce quantity requested
```

### Scenario: Medicine Not Found
```
Result: Redirects to inventory list with message
Action: Verify medicine ID and try again
```

### Scenario: Invalid Batch Number
```
Result: Cannot delete or update medicine with invalid batch
Action: Correct batch number format
```

---

## Testing Workflow

### Test 1: Complete Prescription Flow
```
1. Add 10 units of Aspirin to inventory
2. Create diagnosis "Headache"
3. Generate prescription with Aspirin
4. Use 2 units (should have 8 left)
5. Verify quantity updated in inventory
```

### Test 2: Low Stock Alert
```
1. Add medicine with quantity = 5
2. Go to /inventory/low-stock
3. Should see this medicine in low stock list
```

### Test 3: Specialty Filtering
```
1. Add multiple medicines with different specialties
2. Go to /inventory/by-specialty/Cardiology
3. Should see only Cardiology medicines
```

---

## Performance Tips

1. **Batch Operations**: Use batch imports for large inventory uploads (future feature)
2. **Indexing**: Batch number and location fields are indexed for fast queries
3. **Low Stock Cache**: Consider caching low stock list for dashboard
4. **Search**: Use search-by-batch for quick medicine lookup

---

## Troubleshooting

### Issue: Prescription not showing medicines
- **Check**: Verify medicines field is populated in prescription form
- **Fix**: Re-enter medicine names and save

### Issue: Inventory dashboard showing 0 medicines
- **Check**: Verify medicines were successfully created
- **Fix**: Add test medicines via create form

### Issue: Cannot delete medicine
- **Check**: May have related prescriptions
- **Fix**: Update or delete related prescriptions first

---

## Next Steps (Development)

1. Implement barcode scanning for medicine lookup
2. Add expiry date alerts (30 days before expiry)
3. Create medicine usage reports
4. Add prescription effectiveness tracking
5. Integrate with SMS notification system
6. Create bulk import/export for medicines

---

For detailed implementation information, see `IMPLEMENTATION_SUMMARY.md`
