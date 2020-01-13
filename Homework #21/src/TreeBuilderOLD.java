 /*
   Aidan Chandra
   Homework #
   Apr 5, 2018
 */
public class TreeBuilderOLD{
    
    private static class parseException extends Exception {

        public parseException(String s) {
            // Call constructor of parent Exception
            super(s);
        }
    }
    
    private static boolean debug = true;
    private static Scanner.Token currentToken;
    private static Scanner scan;
    
    public static void main(String[] args) throws parseException {
        String expression = "3++";
        scan = new Scanner(expression);
        currentToken = scan.next();
        System.out.println(parseAssignment());
    }

    

    public boolean buildExpression(String expression) throws parseException{
        if(debug) System.out.println("Expression to evaulate: " + expression);
        scan = new Scanner(expression);
        return parseAssignment();
    }
    

    
    private static boolean parseAtom() throws parseException{
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return false;
        }
        if(debug) System.out.println("CurrentToken in parseAtom: " + currentToken);
        switch (currentToken.kind()){
            case IDENTIFIER:
                currentToken = scan.next();
                break;
            case INTEGER:
                currentToken = scan.next();
                break;
            case PLUS_PLUS:
            case MINUS_MINUS:
                scan.next();
                if(scan.peek().kind() == Scanner.Token.Kind.IDENTIFIER)
                    break;
                else{
                    if(debug) System.out.println("EXPECTED IDENTIFIER FOLLOWING PP/MM");
                    throw new parseException("Expected an itentifier following ++ or --");
                }
            case LEFT_PARENTHESIS:
                if(debug) System.out.println("leftparen");
                currentToken = scan.next();
                parseExpression();
                if(currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS){
                    currentToken = scan.next();
                    if(debug) System.out.println("We go the rightparen");
                    break;
                }
                else{
                    if(debug) System.out.println("EXPECTED CLOSING PARENTHESES - EXPRESSION INVALID");
                    throw new parseException("Expected a closing parentheses");
                }
            default:
                if(debug) System.out.println("Error - Expected an ID, INT, etc");
                throw new parseException("Expected an identifier, integer, an incramenting operation followed by an identifier, vice versa, or parenthesized expression");

        }
        if(debug) System.out.println("Coming out of parseAtom: " + currentToken);
        return true;
    }

    private static boolean parseFactor() throws parseException{
        
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return false;
        }
        
        
        if(debug) System.out.println("CurrentToken in parseFactor: " + currentToken);
        switch (currentToken.kind()){
            case PLUS:
            case MINUS:
                currentToken = scan.next();
                if(scan.peek().kind() == Scanner.Token.Kind.END_OF_LINE){
                    if(debug) System.out.println("EOL");
                    break;
                }
                parseTerm();
                break;
            default:
                parseAtom();
                break;
        }
        if(debug) System.out.println("Coming out of parseFactor: " + currentToken);
        return true;
    }
    
    private static boolean parseTerm() throws parseException{
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return false;
        }
        
        if(debug) System.out.println("CurrentToken in parseTerm: " + currentToken);

        parseFactor();
        //original: while(!(currentToken.kind() == Scanner.Token.Kind.SLASH || currentToken.kind() == Scanner.Token.Kind.STAR || currentToken.kind() == Scanner.Token.Kind.PERCENT || currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS)){
        while((currentToken.kind() == Scanner.Token.Kind.SLASH || currentToken.kind() == Scanner.Token.Kind.STAR || currentToken.kind() == Scanner.Token.Kind.PERCENT || currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS)){
            currentToken = scan.next();
            boolean a = parseFactor();
            if(!a)
                break;
        }
        if(debug) System.out.println("Coming out of parseTerm: " + currentToken);
        return true;
        
    }

    private static int parenCount = 0;
    private static boolean parseExpression() throws parseException{
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return false;
        }
        if(debug) System.out.println("CurrentToken in parseExpression: " + currentToken);
        
        parseTerm();
        while(!(currentToken.kind() == Scanner.Token.Kind.PLUS || currentToken.kind() == Scanner.Token.Kind.MINUS|| currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS)){
            currentToken = scan.next();
            boolean a = parseTerm();
            if(!a)
                break;
        }
        if(currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS)
            parenCount++;
        if(parenCount >= 2){
            System.out.println("Too many parentheses - invalid");
            return false;
        }
        if(debug) System.out.println("Coming out of parseExpression: " + currentToken);
        return true;
    }

    private static boolean parseAssignment() throws parseException{
        if(debug) System.out.println("Current token in parseAssignment: " + currentToken);
        switch(currentToken.kind()){
            case IDENTIFIER:
                switch(scan.peek().kind()){
                    case MINUS_EQUAL:
                    case PLUS_EQUAL:
                    case STAR_EQUAL:
                    case SLASH_EQUAL:
                    case PERCENT_EQUAL:
                    case EQUAL:
                        scan.next();
                        currentToken = scan.next();
                        parseAssignment();
                        break;
                    default:
                        parseExpression();
                }
                break;
            default:
                parseExpression();
        }
        return true;
    }
    
    private static Scanner catchUp(Scanner scan){
        Scanner returnable = new Scanner(scan.getExpression());
        Scanner.Token toLookFor = scan.peek();
        Scanner.Token current = returnable.peek();
        while(!scan.peek().toString().equals(returnable.peek().toString()))
            returnable.next();
        return returnable;
    }
}
