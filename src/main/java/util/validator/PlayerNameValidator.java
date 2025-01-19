package util.validator;

import exception.WrongArgumentException;

import java.util.regex.Pattern;

public class PlayerNameValidator {

    private static final int MAX_NAME_LENGTH = 45;
    private static final int MIN_NAME_LENGTH = 2;

    public void validate(String playerOneName, String playerTwoName) {
        checkIfEquals(playerOneName, playerTwoName);
        validatePlayerName(playerOneName);
        validatePlayerName(playerTwoName);
    }

    private void checkIfEquals(String playerName, String otherPlayerName) {
        if(playerName.equals(otherPlayerName)) {
            throw new WrongArgumentException("Player names should not be equal");
        }
    }

    private void validatePlayerName(String playerName) {
        if(!checkHasNameAndSurname(playerName)) {
            throw new WrongArgumentException("PLayer name should contain name and surname separated by space");
        }

        if (!containsOnlyLatinAndHyphen(playerName)) {
            throw new WrongArgumentException("Name should contain only latin letters and hyphen symbol");
        }

        if(!checkHasRightLength(playerName)) {
            throw new WrongArgumentException("Name length should be in between from 2 to 45");
        }
    }

    private boolean checkHasNameAndSurname(String playerName) {
        String regex = "^\\S+\\s\\S+$";
        return Pattern.matches(regex, playerName);
    }

    private boolean containsOnlyLatinAndHyphen(String playerName) {
        String regex = "^[a-zA-Z-\\s]+$";
        return Pattern.matches(regex, playerName);
    }

    private boolean checkHasRightLength(String playerName) {
        return playerName.length() <= MAX_NAME_LENGTH && playerName.length() >= MIN_NAME_LENGTH;
    }


}
