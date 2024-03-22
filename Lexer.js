// Lexer class
class Lexer {
    constructor(input) {
      this.input = input;
      this.position = 0;
      this.currentChar = this.input[this.position];
    }
  
    advance() {
      this.position++;
      this.currentChar = this.input[this.position];
    }
  
    skipWhitespace() {
      while (/\s/.test(this.currentChar)) {
        this.advance();
      }
    }
  
    getNextToken() {
      while (this.currentChar !== undefined) {
        if (/\s/.test(this.currentChar)) {
          this.skipWhitespace();
          continue;
        }
  
        // Implement tokenization logic based on language grammar CODE language specs 
        //sa files sa Prog Lang teams
  
        // Return EOF token if end of input is reached
        if (this.position >= this.input.length) {
          return new Token(TokenType.EOF, null);
        }
      }
  
      return new Token(TokenType.EOF, null);
    }
  }