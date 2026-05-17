package commands;

public enum CommandEnum {
    OPEN,
    CLOSE,
    SAVE,
    SAVE_AS,
    HELP,
    EXIT,
    IMPORT,
    SHOWTABLES,
    DESCRIBE,
    PRINT,
    EXPORT,
    SELECT,
    ADDCOLUMN,
    UPDATE,
    DELETE,
    INSERT,
    INNERJOIN,
    RENAME,
    COUNT,
    AGGREGATE,
    NEXT,
    PREV;

    public static boolean contains(String test) {

        for (CommandEnum c : CommandEnum.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
