$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession

function Register-User($username, $password, $role, $email, $fullName) {
    Write-Host "Registering $username as $role..."
    $body = @{
        username = $username
        password = $password
        role = $role
        email = $email
        fullName = $fullName
    }
    try {
        Invoke-WebRequest -Uri "http://localhost:8084/register" -Method Post -Body $body -WebSession $session -UseBasicParsing -ErrorAction Ignore
    } catch {}
}

function Login-User($username, $password) {
    Write-Host "Logging in as $username..."
    $body = @{
        username = $username
        password = $password
    }
    $res = Invoke-WebRequest -Uri "http://localhost:8084/login" -Method Post -Body $body -WebSession $session -UseBasicParsing
    return $res
}

# 1. Register Patient and Doctor (Ignore 400 if user exists)
Register-User "p2" "pass" "USER" "p2@test.com" "Patient Two"
Register-User "d2" "pass" "DOCTOR" "d2@test.com" "Doctor Two"

# 2. Login as Patient and Book Appointment
Login-User "p2" "pass"
Write-Host "Booking appointment..."
# We need to find the doctor's ID for d2. Usually, it's incrementing.
# Let's try to find it from /doctors page or just guess/try a few.
# Better to fetch /doctors and search for d2.
$docsPage = Invoke-WebRequest -Uri "http://localhost:8084/doctors" -Method Get -WebSession $session -UseBasicParsing
if ($docsPage.Content -match 'href="/appointments/book\?doctorId=(\d+)"[^>]*>Book Appointment</a>\s*</td>\s*<td>Doctor Two') {
    # This regex is a bit hopeful, let's try a simpler one
}
# Actually, the form usually has doctorId. Let's look for "Doctor Two" and find the ID before it.
if ($docsPage.Content -match '(\d+)</td>\s*<td>Doctor Two') {
    $docId = $matches[1]
} else {
    $docId = 1 # Fallback
}
Write-Host "Using Doctor ID: $docId"

$bookBody = @{
    doctorId = $docId
    appointmentDate = "2025-12-31T10:00"
    reason = "Checkup"
}
Invoke-WebRequest -Uri "http://localhost:8084/appointments/book" -Method Post -Body $bookBody -WebSession $session -UseBasicParsing

# 3. Login as Doctor and Reject
Login-User "d2" "pass"
$appointmentsPage = Invoke-WebRequest -Uri "http://localhost:8084/doctor-dashboard" -Method Get -WebSession $session -UseBasicParsing
if ($appointmentsPage.Content -match '/appointments/reject/(\d+)') {
    $apptId = $matches[1]
    Write-Host "Rejecting appointment $apptId..."
    Invoke-WebRequest -Uri "http://localhost:8084/appointments/reject/$apptId" -Method Post -WebSession $session -UseBasicParsing
} else {
    Write-Host "Could not find appointment to reject. Snippet:"
    if ($appointmentsPage.Content -match 'Checkup') { Write-Host "Found Checkup but no reject link" }
}

# 4. Login as Patient and check status
Login-User "p2" "pass"
$dashboard = Invoke-WebRequest -Uri "http://localhost:8084/patient-dashboard" -Method Get -WebSession $session -UseBasicParsing
$content = $dashboard.Content

# Extract status cell
if ($content -match '<td>(?i)(PENDING|REJECTED|CONFIRMED)</td>') {
    $status = $matches[0]
    Write-Host "FOUND STATUS CELL: $status"
}

# Snippet search
$start = $content.IndexOf("Checkup")
if ($start -ge 0) {
    $snippet = $content.Substring($start - 50, [Math]::Min(300, $content.Length - $start + 50))
    Write-Host "SNIPPET: $snippet"
} else {
    Write-Host "Checkup not found on patient dashboard."
}
