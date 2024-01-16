package lang.lpse;

import lang.ParseException;
import lex.Lexer;

import java.io.IOException;

public class LPseParser {

    private Lexer lex;

    public LPseParser() {
    }

    public void parse(String filePath) throws IOException {
        lex = new Lexer(LPseTokens.DEFS);
        lex.readFile(filePath);
        lex.next();
        Prog();
        System.out.println("Parse succeeded.");
    }

    /**
     * Check the head token and, if it matches, advance to the next token.
     * @param type the token type that we expect
     * @return the text of the head token that was matched
     * @throws ParseException if the head token does not match.
     */
    //Prog         -> BEGIN Stm* END
    public void Prog() {
        eat("BEGIN");
        while(lex.tok().type != "END") {
            Stm();
        }
        eat("END");
    }

    public void Stm() {
        switch (lex.tok().type) {
            case "PRINTINT"://PRINTINT Exp SEMIC
            case "PRINTCHAR"://PRINTCHAR Exp SEMIC
                lex.next();
                Exp();
                eat("SEMIC");
                break;
            case "IF"://IF LBR Exp RBR Stm ELSE Stm ENDIF
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
            case "WHILE"://WHILE LBR Exp RBR DO Stm DONE
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
            case "ID"://ID ASSIGN Exp SEMIC
                eat("ID");
                eat("ASSIGN");
                Exp();
                eat("SEMIC");
                break;
            default:
                throw new ParseException(lex.tok(),"STM");
        }
    }

    private void Exp() {
        switch (lex.tok().type) {
            case "LBR"://LBR Exp RBR Opt
                lex.next();
                while(!(lex.tok().type == "RBR")) {
                    Exp();
                }
                eat("RBR");
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR") {
                    break;
                }
                Opt();
                break;
            case "ID"://INT Opt
            case "INT"://ID Opt
                Type();
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR") {
                    break;
                }
                Opt();
                break;
            default:
                throw new ParseException(lex.tok(),"EXP");
        }
    }
    private void Opt() {
        switch (lex.tok().type) {
            case "MINUS"://MINUS Exp
                eat("MINUS");
                Exp();
                break;
            case "TIMES"://TIMES Exp
                eat("TIMES");
                Exp();
                break;
            case "LESS"://LESS Exp
                eat("LESS");
                Exp();
                break;
            case "DIV"://DIV Exp
                eat("DIV");
                Exp();
                break;
            case "PLUS"://PLUS Exp
                eat("PLUS");
                Exp();
                break;
            case "LESSOREQUAL"://LESSOREQUAL Exp
                eat("LESSOREQUAL");
                Exp();
                break;
            case "MOREOREQUAL"://MOREOREQUAL Exp
                eat("MOREOREQUAL");
                Exp();
                break;
            case "MORE"://MOREOREQUAL Exp
                eat("MORE");
                Exp();
                break;
            case "EQUALS"://EQUALS Exp
                eat("EQUALS");
                Exp();
                break;
            default:
                throw new ParseException(lex.tok(), "OPT");
        }
    }

    private void Type() {
        switch(lex.tok().type) {
            case "INT":
                eat("INT");
                break;
            case "ID":
                eat("ID");
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
        LPseParser parser = new LPseParser();
        //parser.parse(args[0]);
        for (int i=0; i<80; ++i) {
            parser.parse("data/lpse/parser-tests/test"+ i +".lpse");
            System.out.println(i);
        }
    }
}
