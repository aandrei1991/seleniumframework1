package enums;

public class userInfo {
    public enum TestData {
        EMAIL("test@gmail.com"),
        NUME("TestNUME"),
        PRENUME("TestPRENUME"),
        TELEFON("072111111"),
        ADRESA("Str. Alexandru Cel Bun Nr.6"),
        ORAS("Bucuresti");

        public final String value;

        TestData(String value) {
            this.value = value;
        }

    }
}
