/**
 * This enumeration contains all the valid database types.
 */
public enum DatabaseTypeEnum {
    postgres("postgres"),
    mongodb("mongodb");

    private String text;

    /**
     * Constructor to accept String values to allow better identification of this enum
     * @param text The text to parse.
     */
    DatabaseTypeEnum(String text) {
        this.text = text;
    }

    public static DatabaseTypeEnum fromString(String str) throws IllegalArgumentException {
        for (DatabaseTypeEnum value : DatabaseTypeEnum.values()) {
            if (str.equalsIgnoreCase(value.text)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("The type %1s does not exist.", str));
    }
}
