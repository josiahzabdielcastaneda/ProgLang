public class Token {
    public enum TokenType {
        BEGIN_CODE,
        END_CODE,
        VAR_DECLARATION,
        VARIABLE_NAME,
        INT,
        INT_LIT,
        CHAR,
        CHAR_LIT,
        BOOL,
        BOOL_LIT,
        FLOAT,
        FLOAT_LIT,
        PLUS,
        MINUS,
        MUL,
        DIV,
        MOD,
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_OR_EQUAL_TO,
        LESS_THAN_OR_EQUAL_TO,
        EQUAL,
        NOT_EQUAL,
        AND,
        OR,
        NOT,
        POSITIVE,
        NEGATIVE,
        LPAREN,
        RPAREN,
        LBRACKET,
        RBRACKET,
        COMMENT,
        CONCATENATOR,
        COLON,
        DISPLAY,
        NEWLINE,
        EOF // End of file
    }

    public final TokenType type;
    public final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}
