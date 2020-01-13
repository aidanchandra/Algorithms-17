 /*
   Aidan Chandra
   Homework #
   Apr 5, 2018
 */
public class TreeBuilder {
    
    public static class parseException extends Exception {

        public parseException(String s) {
            // Call constructor of parent Exception
            super(s);
        }
    }
    
    private static boolean debug = true;
    private static Scanner.Token currentToken;
    private static Scanner scan;
    
    public static void main(String[] args) throws parseException {
        String expression = "x++x";
        scan = new Scanner(expression);
        currentToken = scan.next();
        System.out.println(parseAssignment().evaluate());
    }
    public Node evaluate(String expression) throws parseException{
        scan = new Scanner(expression);
        currentToken = scan.next();
        return parseAssignment();
    }

    

//    public boolean buildExpression(String expression) throws parseException{
//        if(debug) System.out.println("Expression to evaulate: " + expression);
//        scan = new Scanner(expression);
//        return parseAssignment();
//    }
    

    
    private static Node parseAtom() throws parseException{
        Node result = null;
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return result;
        }
        if(debug) System.out.println("CurrentToken in parseAtom: " + currentToken);
        switch (currentToken.kind()){
            case IDENTIFIER:
                
                Scanner.IdentifierToken a = (Scanner.IdentifierToken)currentToken;
                /*
                    If the variable is not already defined, have it default to zero
                */
                if(Runner.variables.get(a.name()) == null){
                    System.out.println("Note: Variable \"" + a.name() + "\" not defined - defaulting to 0");
                    Runner.variables.put(a.name(), 0);
                    result = new Variable(a.name(), 0);
                }
                else{
                    result = new Variable(a.name(), Runner.variables.get(a.name()));
                }
                currentToken = scan.next();
                break;
            case INTEGER:
                Scanner.IntegerToken b = (Scanner.IntegerToken)currentToken;
                result = new Constant(b.value());
                currentToken = scan.next();
                break;
            case PLUS_PLUS:
                if(scan.peek().kind() == Scanner.Token.Kind.IDENTIFIER){
                    Scanner.IdentifierToken c = (Scanner.IdentifierToken)scan.peek();
                    if(Runner.variables.get(c.name()) == null){
                        System.out.println("Note: Variable \"" + c.name() + "\" not defined - defaulting to 0");
                        Runner.variables.put(c.name(), 1);
                        result = new Variable(c.name(), 1);
                    }
                    else{
                        Operand op = new Variable(c.name(), Runner.variables.get(c.name()));
                        System.out.println("ASDFASDF" + Runner.variables.get(c.name()));
                        Prefix pre = new Prefix();
                        UnaryOperator opa = pre.new Incrament(op);
                        result = opa;
                    }
                    
                }
                else{
                    if(debug) System.out.println("EXPECTED IDENTIFIER FOLLOWING PP");
                    throw new parseException("Expected an itentifier following ++");
                }
                break;
            case MINUS_MINUS:
                if(scan.peek().kind() == Scanner.Token.Kind.IDENTIFIER){
                    Scanner.IdentifierToken c = (Scanner.IdentifierToken)scan.peek();
                    if(Runner.variables.get(c.name()) == null){
                        System.out.println("Note: Variable \"" + c.name() + "\" not defined - defaulting to 0");
                        Runner.variables.put(c.name(), -1);
                        result = new Variable(c.name(), -1);
                    }
                    else{
                        Operand op = new Variable(c.name(), Runner.variables.get(c.name()));
                        System.out.println("ASDFASDF" + Runner.variables.get(c.name()));
                        Prefix pre = new Prefix();
                        UnaryOperator opa = pre.new Decrement(op);
                        result = opa;
                    }
                }
                else{
                    if(debug) System.out.println("EXPECTED IDENTIFIER FOLLOWING MM");
                    throw new parseException("Expected an itentifier following --");
                }
                break;
            case LEFT_PARENTHESIS:
                if(debug) System.out.println("leftparen");
                currentToken = scan.next();
                result = parseExpression();
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
        if(debug) System.out.println("Coming out of parseAtom: " + currentToken.toString());
        if(debug) System.out.println("Result: " + result.format());
        return result;
    }

    private static Node parseFactor() throws parseException{
        
        Node result = null;
        
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return result;
        }
        
        
        if(debug) System.out.println("CurrentToken in parseFactor: " + currentToken);
        switch (currentToken.kind()){
            case PLUS:
                currentToken = scan.next();
                if(scan.peek().kind() == Scanner.Token.Kind.END_OF_LINE){
                    if(debug) System.out.println("EOL");
                    break;
                }
                Prefix pre = new Prefix();
                result = pre.new Identity(parseTerm());
                break;
            case MINUS:
                currentToken = scan.next();
                if(scan.peek().kind() == Scanner.Token.Kind.END_OF_LINE){
                    if(debug) System.out.println("EOL");
                    break;
                }
                Prefix prex = new Prefix();
                result = prex.new Negate(parseTerm());
                break;
            default:
                result = parseAtom();
                break;
        }
        if(debug) System.out.println("Coming out of parseFactor: " + currentToken);
        if(debug) System.out.println("Result: " + result.format());

        return result;
    }
    
    private static Node parseTerm() throws parseException{
        Node result = null;
        
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return result;
        }
        
        
        if(debug) System.out.println("CurrentToken in parseTerm: " + currentToken);

        result = parseFactor();
        //While it is not a multiply operator
        while((currentToken.kind() == Scanner.Token.Kind.SLASH || currentToken.kind() == Scanner.Token.Kind.STAR || currentToken.kind() == Scanner.Token.Kind.PERCENT) && currentToken.kind() != Scanner.Token.Kind.RIGHT_PARENTHESIS){
            Scanner.Token oldToken = currentToken;
            currentToken = scan.next();
            Arithmetic bop = new Arithmetic();
            if(oldToken.kind() == Scanner.Token.Kind.SLASH){
                Node a = parseFactor();
                if(a == null)
                    break;
                else
                    result = bop.new Divide(result, a);
            }
            if(oldToken.kind() == Scanner.Token.Kind.STAR){
                Node a = parseFactor();
                if(a == null)
                    break;
                else
                    result = bop.new Multiply(result, a);
            }
            if(oldToken.kind() == Scanner.Token.Kind.PERCENT){
                Node a = parseFactor();
                if(a == null)
                    break;
                else
                    result = bop.new Mod(result, a);
            }
            //WHAT DO DO ABOUT THE RIGHT PARENTHESES LMFAO 
            else{
                if(debug)System.out.println("it must be a parentheses - unsure");
            }
            //result = multiplyop(result, parseFactor());
            
        }
        if(debug) System.out.println("Coming out of parseTerm: " + currentToken);
        if(debug) System.out.println("Result: " + result.format());

        return result;
        
    }

    private static int parenCount = 0;
    private static Node parseExpression() throws parseException{
        Node result = null;
        if(currentToken.kind() == Scanner.Token.Kind.END_OF_LINE){
            if(debug) System.out.println("DONE!");
            return result;
        }
        if(debug) System.out.println("CurrentToken in parseExpression: " + currentToken);
        
        result = parseTerm();
        while((currentToken.kind() == Scanner.Token.Kind.PLUS || currentToken.kind() == Scanner.Token.Kind.MINUS) && currentToken.kind() != Scanner.Token.Kind.RIGHT_PARENTHESIS){
            Scanner.Token oldToken = currentToken;
            currentToken = scan.next();
            Arithmetic bop = new Arithmetic();
            if(oldToken.kind() == Scanner.Token.Kind.PLUS){
                Node a = parseTerm();
                if(a == null)
                    break;
                else
                    result = bop.new Add(result, a);
            }
            if(oldToken.kind() == Scanner.Token.Kind.MINUS){
                Node a = parseTerm();
                if(a == null)
                    break;
                else
                    result = bop.new Subtract(result, a);
            }
            else{
                if(debug) System.out.println("it must be a parentheses - unsure");
            }
        }
        if(currentToken.kind() == Scanner.Token.Kind.RIGHT_PARENTHESIS)
            parenCount++;
        if(parenCount >= 2){
            System.out.println("Too many parentheses - invalid");
            return result;
        }
        if(debug) System.out.println("Coming out of parseExpression: " + currentToken);
        if(debug) System.out.println("Result: " + result.format());
        return result;
    }

   
    
    private static Node parseAssignment() throws parseException{
        Node result = null;
        if(debug) System.out.println("Current token in parseAssignment: " + currentToken);
        Assignment a = new Assignment();
        switch(currentToken.kind()){
            case IDENTIFIER:
                switch(scan.peek().kind()){
                    
                    case MINUS_EQUAL:
                        Scanner.IdentifierToken varSix = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int numSix = 0;
                        try{
                            numSix = Runner.variables.get(varSix.name());
                        }
                        catch (NullPointerException e){
                            numSix = 0;
                            //System.out.println("Invalid expression");
                        }
                        Constant tmpF =  new Constant(parseAssignment().evaluate());
                        Variable varaSix = new Variable(varSix.name(), numSix);
                        result = a.new minusEqual(varaSix, tmpF);
                        break;
                    case PLUS_EQUAL:
                        Scanner.IdentifierToken varFive = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int numFive = 0;
                        try{
                            numFive = Runner.variables.get(varFive.name());
                        }
                        catch (NullPointerException e){
                            numFive = 0;
                            //System.out.println("Invalid expression");
                        }
                        Constant tmpE =  new Constant(parseAssignment().evaluate());
                        Variable varaFive = new Variable(varFive.name(), numFive);
                        result = a.new plusEqual(varaFive, tmpE);
                        break;
                    case STAR_EQUAL:
                        Scanner.IdentifierToken varFour = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int numFour = 0;
                        try{
                            numFour = Runner.variables.get(varFour.name());
                        }
                        catch (NullPointerException e){
                            numFour = 0;
                            //System.out.println("Invalid expression");
                        }
                        Constant tmpD =  new Constant(parseAssignment().evaluate());
                        Variable varaFour = new Variable(varFour.name(), numFour);
                        result = a.new timesEqual(varaFour, tmpD);
                        break;
                    case SLASH_EQUAL:
                        Scanner.IdentifierToken varThree = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int numThree = 0;
                        try{
                            numThree = Runner.variables.get(varThree.name());
                        }
                        catch (NullPointerException e){
                            numThree = 0;
                            //System.out.println("Invalid expression");
                        }
                        Constant tmpC =  new Constant(parseAssignment().evaluate());
                        Variable varaThree = new Variable(varThree.name(), numThree);
                        result = a.new divEqual(varaThree, tmpC);
                        break;
                    case PERCENT_EQUAL:
                        Scanner.IdentifierToken varTwo = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int numTwo = 0;
                        try{
                            numTwo = Runner.variables.get(varTwo.name());
                        }
                        catch (NullPointerException e){
                            numTwo = 0;
                            //System.out.println("Invalid expression");
                        }
                        Constant tmpB =  new Constant(parseAssignment().evaluate());
                        Variable varaTwo = new Variable(varTwo.name(), numTwo);
                        result = a.new modEqual(varaTwo, tmpB);
                        break;
                    case EQUAL:
                        Scanner.IdentifierToken varOne = (Scanner.IdentifierToken)currentToken;
                        scan.next();
                        currentToken = scan.next();
                        int num = 0;
                        try{
                            num = Runner.variables.get(varOne.name());
                        }
                        catch (NullPointerException e){
                            num = 0;
                            //System.out.println("Invalid expression");
                        }
                        //Variable varaOne = new Variable(varOne.name(), num);
                        Node temp = parseAssignment();
                        Constant aa = new Constant(temp.evaluate());
                        result = a.new Equal(new Variable(varOne.name(), num), aa);
                        break;
                    default:
                        Scanner.IdentifierToken var = (Scanner.IdentifierToken)currentToken;
                        try{
                            return new Constant(Runner.variables.get(var.name()));
                        }
                        catch (NullPointerException e){
                            throw new parseException("No vaiable defined as " + var.name());
                        }
                }
                break;
            default:
                result = parseExpression();
        }
        return result;
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
