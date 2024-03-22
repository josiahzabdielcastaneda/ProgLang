// Interpreter class
class Interpreter {
    constructor(input) {
      this.lexer = new Lexer(input);
      this.currentToken = this.lexer.getNextToken();
    }

    //consume tokens to verify token type
    eat(expectedType) {
      if (this.currentToken.type === expectedType) {
        this.currentToken = this.lexer.getNextToken();
      } else {
        throw new Error(`Unexpected token type: ${this.currentToken.type}`);
      }
    }
  
    // Implement parsing and execution logic
  }
  