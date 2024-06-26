import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    private final String input;
    private int lineNumber = 0;
    private String[] lines;
    private int linePosition = 0;
    private Token lastToken = null;
    private Map<String, Integer> variables = new HashMap<>(); // Store variable names and values

    public Map<String, Integer> getVariables(){
        return variables;
    }

    public Tokenizer(String input) {
        this.input = input;
        this.lines = input.split("\\$");
    }

    public Token getNextToken() {
        if (lineNumber >= lines.length) {
            return new Token(Token.TokenType.EOF, "");
        }
    
        String currentLine = lines[lineNumber].trim();
    
        // Skip empty lines and comments
        if (currentLine.isEmpty() || currentLine.startsWith("#")) {
            lineNumber++;
            return getNextToken();
        }
    
        // Handle variable declarations
        if (currentLine.contains("=")) {
            String[] parts = currentLine.split(",");
            for (String part : parts) {
                String[] variableDeclaration = part.split("=");
                String variableName = variableDeclaration[0].trim();
                int variableValue = Integer.parseInt(variableDeclaration[1].trim());
                variables.put(variableName, variableValue);
            }
            lineNumber++;
            return getNextToken();
        }
    
        // Skip whitespaces
        while (linePosition < currentLine.length() && Character.isWhitespace(currentLine.charAt(linePosition))) {
            linePosition++;
        }
    
        if (linePosition >= currentLine.length()) {
            // End of line reached, move to the next line
            lineNumber++;
            linePosition = 0;
            return getNextToken();
        }
    
        // Handle multi-word tokens
        if (currentLine.startsWith("BEGIN CODE")) {
            lineNumber++;
            linePosition = 0;
            return new Token(Token.TokenType.BEGIN_CODE, "BEGIN CODE");
        } else if (currentLine.startsWith("END CODE")) {
            lineNumber++;
            linePosition = 0;
            return new Token(Token.TokenType.END_CODE, "END CODE");
        }
    
        // Get the current token from the current line
        String currentToken = getCurrentTokenFromLine(currentLine);
    
        // Move the line position to the end of the current token
        linePosition += currentToken.length();
    
        // Determine token type based on the current token
        Token token = determineTokenType(currentToken);
    
        // Check if the token is a variable name and validate it
        if (token.type == Token.TokenType.VARIABLE_NAME && !isValidVariableName(token.value)) {
            throw new RuntimeException("Invalid variable name: " + token.value);
        }
    
        return token;
    }
    

    // Method to check if a variable name follows the grammar rules
    private boolean isValidVariableName(String name) {
        // Check if the variable name starts with a letter or an underscore and
        // followed by a letter, underscore, or digits
        return name.matches("[a-zA-Z_][a-zA-Z_0-9]*");
    }

    // Method to extract the current token from the current line
    private String getCurrentTokenFromLine(String line) {
        StringBuilder tokenBuilder = new StringBuilder();
        while (linePosition < line.length() && line.charAt(linePosition) != ',' && !Character.isWhitespace(line.charAt(linePosition))) {
            tokenBuilder.append(line.charAt(linePosition));
            linePosition++;
        }
        return tokenBuilder.toString();
    }

    // Method to determine the token type based on the current token
    private Token determineTokenType(String token) {
        // Handle other token types based on the current token
        switch (token) {
            case "[":
                return new Token(Token.TokenType.LBRACKET, "[");
            case "]":
                return new Token(Token.TokenType.RBRACKET, "]");
            case "&":
                return new Token(Token.TokenType.CONCATENATOR, "&");
            case ":":
                return new Token(Token.TokenType.COLON, ":");
            case "$":
                return new Token(Token.TokenType.NEWLINE, "$");
            case "+":
                return new Token(Token.TokenType.PLUS, "+");
            case "-":
                return new Token(Token.TokenType.MINUS, "-");
            case "*":
                return new Token(Token.TokenType.MUL, "*");
            case "/":
                return new Token(Token.TokenType.DIV, "/");
            case "%":
                return new Token(Token.TokenType.MOD, "%");
            case ">":
                return new Token(Token.TokenType.GREATER_THAN, ">");
            case ">=":
                return new Token(Token.TokenType.GREATER_THAN_OR_EQUAL_TO, ">=");
            case "<":
                return new Token(Token.TokenType.LESS_THAN, "<");
            case "<=":
                return new Token(Token.TokenType.LESS_THAN_OR_EQUAL_TO, "<=");
            case "==":
                return new Token(Token.TokenType.EQUAL, "==");
            case "=":
                return new Token(Token.TokenType.VAR_DECLARATION, "=");
            case "<>":
                return new Token(Token.TokenType.NOT_EQUAL, "<>");
            case "!":
                return new Token(Token.TokenType.NOT, "!");
            case "AND":
                return new Token(Token.TokenType.AND, "AND");
            case "OR":
                return new Token(Token.TokenType.OR, "OR");
            case "INT":
                return new Token(Token.TokenType.INT, "INT");
            case "CHAR":
                return new Token(Token.TokenType.CHAR, "CHAR");
            case "BOOL":
                return new Token(Token.TokenType.BOOL, "BOOL");
            case "FLOAT":
                return new Token(Token.TokenType.FLOAT, "FLOAT");
            case "DISPLAY:":
                return new Token(Token.TokenType.DISPLAY, "DISPLAY");
            default:
                 // Check if the token is an integer literal
                if (token.matches("\\d+")) {
                    return new Token(Token.TokenType.INT_LIT, token);
                }
                // Check if the token is a character literal
                else if (token.matches("'.'")) {
                    return new Token(Token.TokenType.CHAR_LIT, token);
                }
                // Check if the token is a boolean literal
                else if (token.matches("TRUE|FALSE")) {
                    return new Token(Token.TokenType.BOOL_LIT, token);
                }
                // Check if the token is a floating-point literal
                else if (token.matches("\\d+\\.\\d+")) {
                    return new Token(Token.TokenType.FLOAT_LIT, token);
                }
                // Handle variable names
                else {
                    return new Token(Token.TokenType.VARIABLE_NAME, token);
                }
        }
    }
}