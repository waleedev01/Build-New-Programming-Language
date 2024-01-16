package lang.lpfun;

public final class LPfunTokens {

    public static final String[][] DEFS = {
            { "LBR",         "\\(" },
            { "RBR",         "\\)" },
            { "THEN",         "then" },
            { "ENDIF",         "endif" },
            { "DO",         "do" },
            { "DONE",        "done" },
            { "SEMIC",       ";" },
            { "BEGIN",       "begin" },
            { "NEWLINE",       "\\n" },
            { "END",         "end" },
            { "PRINTINT",    "printint" },
            { "PRINTCHAR",   "printchar" },
            { "IF",          "if" },
            { "WHILE",         "while" },
            { "ELSE",        "else" },
            { "INT",         "-?[0-9]+" },
            { "ID",         "^(?!\\$|0-9|printint|printchar|if|while|else|then|endif|do|done|begin|end|fun|return)+[0-9a-zA-z\\$\\_]+"},//not checking underscore constraint
            { "PLUS", "\\+"},
            { "MINUS", "-"},
            { "TIMES", "\\*"},
            { "DIV", "\\/"},
            { "ASSIGN", "\\="},
            { "EQUALS", "\\=="},
            { "MOREOREQUAL", "\\=>"},
            { "LESSOREQUAL", "\\<="},
            { "MORE", "\\>"},
            { "LESS", "\\<"},
            { "FUNC", "fun"},
            { "RETURN", "return"},
            { "LCBR", "\\{"},
            { "COMMA", "\\,"},
            { "RCBR", "\\}"}
    };
}
