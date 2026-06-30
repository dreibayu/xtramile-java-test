# Xtramile Java Test

## BasicAlgorithmTest1
Find numbers that appear only once in the list.

Input example:
[1, 2, 1, 3]

Output:
[2, 3]

## BasicAlgorithmTest2
Find the largest pair sum in the list.

Input example:
[5, 9, 7, 11]

Output:
20

## How to Run

Run BasicAlgorithmTest1:

```bash
javac BasicAlgorithmTest1.java
java BasicAlgorithmTest1
```

Run BasicAlgorithmTest2:

```bash
javac BasicAlgorithmTest2.java
java BasicAlgorithmTest2
```
## PatientMatchingAlgorithm
Match an incoming patient record against an existing patient record using 4 fields: name, date of birth, phone, and email.

Rules:
- Normalize name, phone, and email before comparison.
- 3 or 4 matched fields: AUTO_MATCH.
- 2 matched fields: REVIEW.
- Less than 2 matched fields: NO_MATCH.

Sample output:
```text
Sample 1: AUTO_MATCH
Sample 2: REVIEW
```

Run PatientMatchingAlgorithm:

```bash
javac patient-matching/PatientMatchingAlgorithm.java`r`njava -cp patient-matching PatientMatchingAlgorithm
```
