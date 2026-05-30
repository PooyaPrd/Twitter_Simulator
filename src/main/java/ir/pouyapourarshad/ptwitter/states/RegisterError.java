package ir.pouyapourarshad.ptwitter.states;

public enum RegisterError {
    EMPTY_REQUIRED_FIELDS,
    USERNAME_ALREADY_EXISTS,
    INVALID_EMAIL,
    INVALID_PHONE_NUMBER,
    WEAK_PASSWORD,
    PASSWORDS_DO_NOT_MATCH,
    BIRTHDAY_REQUIRED
}
