import java.util.Map;

public class Parser {
    private final Tokenizer tokenizer;
    private Token currentToken;
    private Map<String, Integer> variables;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.variables = tokenizer.getVariables();
        this.currentToken = tokenizer.getNextToken();
    }

    public void eat(Token.TokenType tokenType) {
        if (currentToken.type == tokenType) {
            currentToken = tokenizer.getNextToken();
        } else if (tokenType == Token.TokenType.NEWLINE) {
            currentToken = tokenizer.getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type + " " + currentToken.value + " " + tokenType);
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
            case END_CODE:
            case NEWLINE:
            case EOF:
                break;
            default:
                // Skip comments
                if (currentToken.type == Token.TokenType.COMMENT) {
                    eat(Token.TokenType.COMMENT);
                } else {
                    throw new RuntimeException("Invalid statement: " + currentToken.toString());
                }
                break;
        }
    }

    // Method to parse variable declaration
    public void variableDeclaration() {
        dataType();
        String[] variableNames = currentToken.value.split(",");
        for (String variableName : variableNames) {
            eat(Token.TokenType.VARIABLE_NAME); // Variable name
            if (currentToken.type == Token.TokenType.VAR_DECLARATION) {
                eat(Token.TokenType.VAR_DECLARATION); // Assignment operator
                expression(); // Parse expression
            }
        }
        eat(Token.TokenType.NEWLINE); // End of statement
    }

    // Method to parse display statement
    public void displayStatement() {
        eat(Token.TokenType.DISPLAY); // Display keyword
        expression(); // Parse expression
    }

    // Method to parse expression
    public void expression() {
        switch (currentToken.type) {
            case INT_LIT:
            case CHAR_LIT:
            case BOOL_LIT:
            case FLOAT_LIT:
                System.out.println(currentToken.value); // Print literal value
                eat(currentToken.type); // Consume the literal token
                break;
            case VARIABLE_NAME:
                // Handle variable name
                String variableName = currentToken.value;
                if (variables.containsKey(variableName)) {
                    System.out.println(variables.get(variableName)); // Print variable value
                } else {
                    throw new RuntimeException("Variable '" + variableName + "' not found");
                }
                eat(Token.TokenType.VARIABLE_NAME); // Consume the variable name token
                break;
            case COMMENT:
            case END_CODE:
                break;
            default:
                throw new RuntimeException("Invalid expression: " + currentToken.toString());
        }
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
        System.out.println("Parse completed successfully.");
    }
}
