package util;

public class Pair<FirstType, SecondType> {
    public FirstType  first;
    public SecondType second;

    public Pair(FirstType first, SecondType second) {
        this.first = first;
        this.second = second;
    }

    public FirstType first() {
        return (this.first);
    }

    public SecondType second() {
        return (this.second);
    }

    public static <FirstType, SecondType> Pair<FirstType, SecondType> make(FirstType firstVal,
                                    SecondType secVal) {
        return (new Pair<FirstType, SecondType>(firstVal, secVal));
    }
}
