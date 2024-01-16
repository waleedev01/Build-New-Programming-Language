package lang.lpfun;

import lang.ParseException;
import lang.lpfun.LPfunTokens;
import lex.Lexer;

import java.io.IOException;

public class LPfunParser {

    private Lexer lex;

    public LPfunParser() {
    }

    public void parse(String filePath) throws IOException {
        lex = new Lexer(LPfunTokens.DEFS);
        lex.readFile(filePath);
        lex.next();
        Prog();
        System.out.println("Parse succeeded.");
    }

    /**
     * Check the head token and, if it matches, advance to the next token.
     * @param  the token type that we expect
     * @return the text of the head token that was matched
     * @throws ParseException if the head token does not match.
     */

    public void Prog() {
        eat("BEGIN");
        while(lex.tok().type != "END") {
            Stm();
        }
        eat("END");
        while (lex.tok().type == "FUNC"){
            Stm();
            if(lex.tok().type == "RCBR"){
                break;
            }
        }
    }

    public void Stm() {
        switch (lex.tok().type) {
            case "PRINTINT":
            case "PRINTCHAR":
            case "RETURN":
                lex.next();
                Exp();
                eat("SEMIC");
                break;
            case "IF":
                lex.next();
                eat("LBR");
                Exp();
                eat("RBR");
                eat("THEN");
                while(!(lex.tok().type == "ELSE")) {
                    Stm();
                }
                eat("ELSE");
                while(!(lex.tok().type == "ENDIF")) {
                    Stm();
                }
                eat("ENDIF");
                break;
            case "WHILE":
                lex.next();
                eat("LBR");
                Exp();
                eat("RBR");
                eat("DO");
                while (!(lex.tok().type == "DONE")) {
                    Stm();
                }
                eat("DONE");
                break;
            case "ID":
                eat("ID");
                if(lex.tok().type=="LBR"){
                    eat("LBR");
                    if(lex.tok().type!="RBR")
                        Exp();
                    eat("RBR");

                    eat("SEMIC");
                }
                else{
                eat("ASSIGN");
                Exp();
                eat("SEMIC");
                }
                break;
            case "FUNC":
                lex.next();
                eat("ID");
                eat("LBR");
                if(lex.tok().type!="RBR")
                    Exp();
                eat("RBR");
                eat("LCBR");
                while (!(lex.tok().type == "RCBR")) {
                    Stm();
                }
                eat("RCBR");
                break;
            default:
                throw new ParseException(lex.tok(), "STM");
        }
    }

    private void Exp() {
        switch (lex.tok().type) {
            case "LBR":
                lex.next();
                while(!(lex.tok().type == "RBR")) {
                    Exp();
                }
                eat("RBR");
                if(lex.tok().type=="COMMA"){
                    eat("COMMA");
                    Exp();
                }
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR") {
                    break;
                }
                Opt();
                break;
            case "ID":
                Type();
                if(lex.tok().type=="LBR"){
                    eat("LBR");
                    if(lex.tok().type!="RBR")
                        Exp();
                    eat("RBR");
                }

                if(lex.tok().type=="COMMA"){
                    eat("COMMA");
                    Exp();
                }

                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR" ) {
                    break;
                }
                Opt();
                break;
            case "INT":
                Type();
                if(lex.tok().type=="COMMA"){
                    eat("COMMA");
                    Exp();
                }
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR" ) {
                    break;
                }

                Opt();
                break;
            default:
                throw new ParseException(lex.tok(), "Exp");

        }
    }
    private void Opt() {
        switch (lex.tok().type) {
            case "MINUS":
                eat("MINUS");
                Exp();
                break;
            case "TIMES":
                eat("TIMES");
                Exp();
                break;
            case "LESS":
                eat("LESS");
                Exp();
                break;
            case "DIV":
                eat("DIV");
                Exp();
                break;
            case "PLUS":
                eat("PLUS");
                Exp();
                break;
            case "LESSOREQUAL":
                eat("LESSOREQUAL");
                Exp();
                break;
            case "MOREOREQUAL":
                eat("MOREOREQUAL");
                Exp();
                break;
            case "EQUALS":
                eat("EQUALS");
                Exp();
                break;
            default:
                throw new ParseException(lex.tok(), "Opt");
        }
    }

    private void Type() {
        switch(lex.tok().type) {
            case "ID":
                eat("ID");
                break;
            case "INT":
                eat("INT");
                break;
            default:
                throw new ParseException(lex.tok(), "TYPE");
        }
    }

    public String eat(String type) {
        if (type.equals(lex.tok().type)) {
            String image = lex.tok().image;
            lex.next();
            return image;
        } else {
            throw new ParseException(lex.tok(), type);
        }
    }



    public static void main(String[] args) throws IOException {
        lang.lpfun.LPfunParser parser = new lang.lpfun.LPfunParser();
        //parser.parse(args[0]);
        for (int i=0; i<80; ++i) {
            parser.parse("data/lpfun/parser-tests/test"+ i +".lpfun");
            System.out.println(i);
        }
    }
}
