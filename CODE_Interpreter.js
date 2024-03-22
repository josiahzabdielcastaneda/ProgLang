// Define token types
const TokenType = {
    BEGIN_CODE: 'BEGIN_CODE',
    END_CODE: 'END_CODE',
    INT: 'INT',
    CHAR: 'CHAR',
    BOOL: 'BOOL',
    FLOAT: 'FLOAT',
    IDENTIFIER: 'IDENTIFIER',
    OPERATOR: 'OPERATOR',
    KEYWORD: 'KEYWORD',
    STRING_LITERAL: 'STRING_LITERAL',
    EOF: 'EOF', //End Of File 
  };
  
// Define keywords
const Keywords = {
    BEGIN: 'BEGIN',
    END: 'END',
    IF: 'IF',
    ELSE: 'ELSE',
    DISPLAY: 'DISPLAY',
    SCAN: 'SCAN',
    TRUE: 'TRUE',
    FALSE: 'FALSE',
};

//test after doing everything
const input = `BEGIN CODE
        INT x, y, z=5
        CHAR a_1='n'
        BOOL t="TRUE"
        x=y=4
        a_1='c'
        DISPLAY: x & t & z & $ & a_1 & [#] & "last"
    END CODE`;

const interpreter = new Interpreter(input);
//to implement: execution logic