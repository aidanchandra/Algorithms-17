
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;

public class ScannerOLD implements Iterator<Scanner.Token>, Iterable<Scanner.Token> {

    public static class Token {

        public static enum Kind {
            IDENTIFIER, //MUST START WITH LETTER, CONTAIN DIGITS LETTERS UNDERSCORES, NO TWO AND NOT START OR END WITH UNDERSCORE
            INTEGER, //ONLY NUMBERS AND UNDERSCORES, NO TWO CONSECUTIVE ONES AND NO START END
            PARENTHESES,
            ARITHMETIC,
            ASSIGNMENT,
            INVALID
        };
        private Kind kind;
        private int start;

        public Token(Kind kind, int start) {
            this.kind = kind;
            this.start = start;
        }

        public Kind kind() {// What kind of token is this one?
            return this.kind;
        }

        public int start() {// Starting character position
            return this.start;
        }
        // Override toString to return the characters making up the token
    }

    public static class IntegerToken extends Token {

        private int val;

        public IntegerToken(int start, int value) {
            super(Kind.INTEGER, start);
            this.val = value;
        }

        public int value() {// Override toString to return the value (converted to a string)
            return this.val;
        }
    }

    public static class IdentifierToken extends Token {

        private String id;

        public IdentifierToken(int start, String name) {
            super(Kind.IDENTIFIER, start);
            this.id = name;
        }

        public String name() {
            return this.id;
        }
        // Override toString to return the identifier name
    }

    private ArrayList <Token> list = new ArrayList();
    public Scanner(String expression) {
        
        System.out.println("Expression: " + expression);
        String numbers = "1234567890";
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String operators = "+-/*%=";
         
            for(int i = 0; i < expression.length(); i++){
                String a = expression.substring(i, i+1);
                String finalString = "";
                
                if(numbers.contains(a)){ //we have a number
                    int start = i;
                    boolean hadUnderscore = false;
                    boolean bad = false;
                    while((numbers.contains(a) || a.equals("_")) && i < expression.length()){
                        
                        //System.out.println("a: " + a);
                        
                        if(numbers.contains(a))
                            hadUnderscore = false;
                        
                        if(a.equals("_") && hadUnderscore){
                            bad = true;
                            System.out.println("Bad expression");
                        }
                        
                        if(a.equals("_") && !hadUnderscore)
                            hadUnderscore = true;
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);

                    }
                    finalString = expression.substring(start, i);
                    if(!bad)
                        list.add(new Token(Token.Kind.INTEGER, start));
                    if(bad)
                        list.add(new Token(Token.Kind.INVALID, start));
                    System.out.println(expression.substring(start, i));
                }
                
                
                
                else if(letters.contains(a.toLowerCase())){
                    int start = i;
                    boolean hadUnderscore = false;
                    boolean bad = false;
                    while((letters.contains(a) || a.equals("_") || numbers.contains(a)) && i < expression.length()){
                        

                        if(letters.contains(a))
                            hadUnderscore = false;
                        
                        if(a.equals("_") && hadUnderscore){
                            bad = true;
                            System.out.println("Bad expression");
                        }
                        
                        if(a.equals("_") && !hadUnderscore)
                            hadUnderscore = true;
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);

                    }
                    finalString = expression.substring(start, i);
                    if(!bad)
                        list.add(new Token(Token.Kind.INTEGER, start));
                    if(bad)
                        list.add(new Token(Token.Kind.INVALID, start));
                    System.out.println(expression.substring(start, i));
                }
                
                
                
                
                else if(a.equals("_")){
                    System.out.println("Floating _");
                    Token tmp = list.remove(list.size() - 1);
                    list.add(new Token(Token.Kind.INVALID, tmp.start()));
                }
                
                if(finalString.endsWith("_")){
                    System.out.println("ENDED WITH UNDERSCORE");
                    Token tmp = list.remove(list.size() - 1);
                    list.add(new Token(Token.Kind.INVALID, tmp.start()));
                }
                
                
                else if(operators.contains(a)){
                    int start = i;
                    int count = 0;
                    while(operators.contains(a) && i < expression.length()){
                        count++;
                        if(count == 3){
                            break;
                        }
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);
                    }
                    finalString = expression.substring(start, i);
                    if(finalString.contains("="))
                        list.add(new Token(Token.Kind.ASSIGNMENT, start));
                    else
                        list.add(new Token(Token.Kind.ARITHMETIC, start));
                    System.out.println(finalString);
                    if(count == 3){
                        i--;
                    }
                }
                
                else if(a.equals(")") ||  a.equals("(")){
                    if(i < expression.length()){
                        finalString = expression.substring(i, i+1);
                        list.add(new Token(Token.Kind.PARENTHESES, i));
                    }
                    System.out.println(finalString);
                }
                
            }
    }

    public boolean hasNext() {
        return list.size() > 0;
    }
    public Token next() {
        return list.remove(0);
    }

    public Iterator<Scanner.Token> iterator() {
        return this;
    }

    public static void main(String[] args) {
        Console console = System.console();
        String line = console.readLine();
        while (line.length() > 0) {
            Scanner scanner = new Scanner(line);
            for (Token token : scanner) {
                System.out.print(token);
                System.out.println(" at " + token.start());
                line = console.readLine();
            }
            line = console.readLine();
        }
    }
}
