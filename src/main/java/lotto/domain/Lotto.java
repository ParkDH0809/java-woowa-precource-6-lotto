package lotto.domain;

import lotto.constants.ErrorMessage;
import lotto.constants.LottoRule;
import lotto.domain.winningNumber.BonusNumber;
import lotto.domain.winningNumber.FinalWinningNumber;
import lotto.domain.winningNumber.WinningNumber;

import java.util.Arrays;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers =numbers;
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateDuplicateNumbers(numbers);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LottoRule.LOTTO_NUMBER_COUNT.getNumber()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INVALID_NUMBER.getMessage());
        }
    }

    private void validateDuplicateNumbers(List<Integer> numbers) {
        if(numbers.size() != numbers.stream().distinct().count()){
            throw new IllegalArgumentException(ErrorMessage.ERROR_DUPLICATE_NUMBER.getMessage());
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public LottoRank compareLottoNumberWithFinalWinningNumber(FinalWinningNumber finalWinningNumber) {
        List<Integer> comparisonResult = Arrays.asList(0, 0);
        for(int number : numbers) {
            if(isDuplicateLottoNumberAndWinningNumber(number, finalWinningNumber.winningNumber())) {
                comparisonResult.set(0, comparisonResult.get(0) + 1);
            }
            if(isDuplicateLottoNumberAndBonusNumber(number, finalWinningNumber.bonusNumber())) {
                comparisonResult.set(1, 1);
            }
        }
        return LottoRank.getRank(comparisonResult);
    }

    private boolean isDuplicateLottoNumberAndWinningNumber(int number, WinningNumber winningNumber) {
        for(int currentWinningNumber : winningNumber.getWinningNumber()) {
            if(number == currentWinningNumber) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateLottoNumberAndBonusNumber(int number, BonusNumber bonusNumber) {
        if(number == bonusNumber.getBonusNumber()) {
            return true;
        }
        return false;
    }
}
