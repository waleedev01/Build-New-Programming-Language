package lang.lpse;

import lang.ParseException;
import lex.Lexer;
import stackmachine.machine.SysCall;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class LPseCompiler {

    private Lexer lex;
    private int freshNameCounter;
    private PrintStream out;
    private Set<String> myIds = new HashSet<String>();//using hashset so there are no duplicates

    public LPseCompiler(PrintStream out) {
        this.out = out;
    }

    private String freshName(String prefix) {
        return "$" + prefix + "_" + (freshNameCounter++);
    }

    private void emit(String s) {
        out.println(s);
    }

    public void compile(String filePath) throws IOException {
        freshNameCounter = 0;
        lex = new Lexer(LPseTokens.DEFS);
        lex.readFile(filePath);
        lex.next();
        Prog();
    }

    public void Prog() {
        eat("BEGIN");
        while(lex.tok().type != "END") {
            Stm();
        }
        eat("END");
        emit("halt");
        emit(".data");
        for(String element : myIds){
            emit(element + ": 0");
        }
    }

    public void Stm() {
        switch (lex.tok().type) {//code from teeny weeny compiler solution
            case "PRINTINT":
                lex.next();
                Exp();
                eat("SEMIC");
                emit("push "+SysCall.OUT_DEC);
                emit("sysc");
                break;
            case "PRINTCHAR":
                lex.next();
                Exp();
                eat("SEMIC");
                emit("push "+SysCall.OUT_CHAR);
                emit("sysc");
                break;
            case "IF"://code from teeny weeny compiler solution
                lex.next();
                String ifFalseLabel = freshName("if_false");
                String ifEndLabel = freshName("if_end");
                eat("LBR");
                Exp();
                emit("push " + ifFalseLabel);
                emit("jump_z");
                eat("RBR");
                eat("THEN");
                while(lex.tok().type != "ELSE") {
                    Stm();
                }
                emit("push " + ifEndLabel);
                emit("jump");
                eat("ELSE");
                emit(ifFalseLabel + ":");
                while(lex.tok().type != "ENDIF") {
                    Stm();
                }
                eat("ENDIF");
                emit(ifEndLabel + ":");
                break;
            case "WHILE":
                lex.next();
                String startLoop = freshName("start_loop");
                String ifFalseLabelLoop = freshName("if_false");
                eat("LBR");
                emit(startLoop + ":");
                Exp();
                emit("push " + ifFalseLabelLoop);
                emit("jump_z");
                eat("RBR");
                eat("DO");
                while (lex.tok().type != "DONE") {
                    Stm();
                }
                emit("push " + startLoop);
                emit("jump");
                eat("DONE");
                emit(ifFalseLabelLoop + ":");
                break;
            case "ID":
                String id = eat("ID");
                myIds.add(id);
                eat("ASSIGN");
                Exp();
                eat("SEMIC");
                emit("push " + id);
                emit("store");
                break;
            default:
                throw new ParseException(lex.tok());
        }
    }

    private void Exp() {
        switch (lex.tok().type) {
            case "LBR":
                lex.next();
                while(lex.tok().type != "RBR") {
                    Exp();
                }
                eat("RBR");
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR") {
                    break;
                }
                Opt();
                break;
            case "ID":
            case "INT":
                Type();
                if(lex.tok().type=="SEMIC" || lex.tok().type=="RBR" ) {
                    break;
                }
                Opt();
                break;
            default:
                throw new ParseException(lex.tok());

        }
    }

    private void Opt() {
        switch (lex.tok().type) {
            case "PLUS"://PLUS Exp
                eat("PLUS");
                Exp();
                emit("add");
                break;
            case "MINUS"://MINUS Exp
                eat("MINUS");
                Exp();
                emit("sub");
                break;
            case "TIMES"://TIMES Exp
                eat("TIMES");
                Exp();
                emit("mul");
                break;
            case "DIV"://DIV Exp
                eat("DIV");
                Exp();
                emit("div");
                break;
            case "LESS"://LESS Exp
                eat("LESS");
                Exp();
                emit("sub");
                emit("test_n");
                break;
            case "LESSOREQUAL"://LESSOREQUAL Exp
                eat("LESSOREQUAL");
                Exp();
                emit("sub");
                emit("dup");
                emit("test_n");
                emit("test_z");
                emit("sub");
                emit("test_n");
                break;
            case "MOREOREQUAL"://MOREOREQUAL Exp
                eat("MOREOREQUAL");
                Exp();
                emit("swap");
                emit("sub");
                emit("dup");
                emit("test_n");
                emit("test_z");
                emit("sub");
                emit("test_n");
                break;
            case "MORE"://MOREOREQUAL Exp
                eat("MORE");
                Exp();
                emit("swap");
                emit("sub");
                emit("test_n");
                break;
            case "EQUALS"://EQUALS Exp
                eat("EQUALS");
                Exp();
                emit("sub");
                emit("test_z");
                break;
            default:
                throw new ParseException(lex.tok());
        }
    }

    private void Type() {
        switch(lex.tok().type) {
            case "INT":
                int value = Integer.parseInt(eat("INT"));
                emit("push " + value);
                break;
            case "ID":
                String id = eat("ID");
                emit("push " + id);
                emit("load");
                break;
            default:
                throw new ParseException(lex.tok());
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
        String inFilePath = args[0];
        if (args.length > 1) {
            try (PrintStream out = new PrintStream(new FileOutputStream(args[1]))) {
                LPseCompiler compiler = new LPseCompiler(out);
                compiler.compile(inFilePath);
            }
        } else {
            LPseCompiler compiler = new LPseCompiler(System.out);
            compiler.compile(inFilePath);
        }
    }
}
