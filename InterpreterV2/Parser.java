public class Parser {
    private final Tokenizer tokenizer;
    private Token currentToken;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.currentToken = tokenizer.getNextToken();
    }

    public void eat(Token.TokenType tokenType) {
        if (currentToken.type == tokenType) {
            currentToken = tokenizer.getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type + " " + currentToken.value);
        }
    }

    // Method to parse a single statement
    public void statement() {
        switch (currentToken.type) {
            case INT:
            case CHAR:
            case BOOL:
            case FLOAT:
                variableDeclaration();
                break;
            case DISPLAY:
                displayStatement();
                break;
            default:
                throw new RuntimeException("Invalid statement: " + currentToken.value + " " + currentToken.type);
        }
    }

    // Method to parse variable declaration
    public void variableDeclaration() {
        dataType();
        eat(Token.TokenType.VARIABLE_NAME); // Variable name
        if (currentToken.type == Token.TokenType.VAR_DECLARATION) {
            eat(Token.TokenType.VAR_DECLARATION); // Assignment operator
            expression(); // Parse expression
        }
        eat(Token.TokenType.NEWLINE); // End of statement
    }

    // Method to parse display statement
    public void displayStatement() {
        eat(Token.TokenType.DISPLAY); // Display keyword
        expression(); // Parse expression
        eat(Token.TokenType.NEWLINE); // End of statement
    }

    // Method to parse expression
    public void expression() {
        // Placeholder for expression parsing
    }

    // Method to parse data type
    public void dataType() {
        // Match data type and consume token
        switch (currentToken.type) {
            case INT:
            case CHAR:
            case BOOL:
            case FLOAT:
                eat(currentToken.type);
                break;
            default:
                throw new RuntimeException("Unexpected data type");
        }
    }

    // Method to parse BEGIN CODE block
    public void beginCodeBlock() {
        eat(Token.TokenType.BEGIN_CODE);
        while (currentToken.type != Token.TokenType.END_CODE) {
            statement(); // Parse statements
        }
        eat(Token.TokenType.END_CODE);
    }

    // Method to start parsing
    public void parse() {
        beginCodeBlock(); // Parse the main block
        eat(Token.TokenType.EOF); // Ensure all tokens are consumed
    }
}
