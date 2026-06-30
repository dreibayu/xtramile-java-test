import java.time.LocalDate;

public class PatientMatchingAlgorithm {

    enum MatchDecision {
        AUTO_MATCH,
        REVIEW,
        NO_MATCH
    }

    static class PatientRecord {
        String name;
        LocalDate dob;
        String phone;
        String email;

        PatientRecord(String name, LocalDate dob, String phone, String email) {
            this.name = name;
            this.dob = dob;
            this.phone = phone;
            this.email = email;
        }
    }

    public static void main(String[] args) {
        PatientRecord existingPatient1 = new PatientRecord(
                "Budi Santoso",
                LocalDate.of(1990, 1, 10),
                "0812-3456-789",
                "Budi@email.com"
        );

        PatientRecord incomingPatient1 = new PatientRecord(
                " budi   santoso ",
                LocalDate.of(1990, 1, 10),
                "08123456789",
                "budi@email.com"
        );

        PatientRecord existingPatient2 = new PatientRecord(
                "Siti Aminah",
                LocalDate.of(1988, 5, 21),
                "0811-2222-3333",
                "siti@email.com"
        );

        PatientRecord incomingPatient2 = new PatientRecord(
                "Siti Amina",
                LocalDate.of(1988, 5, 21),
                "081122223333",
                "new.siti@email.com"
        );

        System.out.println("Sample 1: " + match(existingPatient1, incomingPatient1));
        System.out.println("Sample 2: " + match(existingPatient2, incomingPatient2));
    }

    public static MatchDecision match(PatientRecord existingPatient, PatientRecord incomingPatient) {
        int matchedFields = 0;

        if (normalizeName(existingPatient.name).equals(normalizeName(incomingPatient.name))) {
            matchedFields++;
        }

        if (existingPatient.dob != null && existingPatient.dob.equals(incomingPatient.dob)) {
            matchedFields++;
        }

        if (normalizePhone(existingPatient.phone).equals(normalizePhone(incomingPatient.phone))) {
            matchedFields++;
        }

        if (normalizeEmail(existingPatient.email).equals(normalizeEmail(incomingPatient.email))) {
            matchedFields++;
        }

        if (matchedFields >= 3) {
            return MatchDecision.AUTO_MATCH;
        }

        if (matchedFields == 2) {
            return MatchDecision.REVIEW;
        }

        return MatchDecision.NO_MATCH;
    }

    private static String normalizeName(String value) {
        if (value == null) {
            return "";
        }

        return value.trim()
                .toLowerCase()
                .replaceAll("\\s+", " ");
    }

    private static String normalizePhone(String value) {
        if (value == null) {
            return "";
        }

        return value.replaceAll("[^0-9]", "");
    }

    private static String normalizeEmail(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().toLowerCase();
    }
}
