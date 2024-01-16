package lang.lpse;

public final class LPseTokens {
    public static final String[][] DEFS = {
            // define your LPse tokens here
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
            //not checking if after the underscore there is an alphabetic character
            { "ID",         "^(?!\\$|0-9|printint|printchar|if|while|else|then|endif|do|done|begin|end)+[0-9a-zA-z\\$\\_]+"},//not checking underscore constraint
            { "PLUS", "\\+"},
            { "MINUS", "-"},
            { "TIMES", "\\*"},
            { "DIV", "\\/"},
            { "ASSIGN", "\\="},
            { "EQUALS", "\\=="},
            { "MOREOREQUAL", "\\=>"},
            { "LESSOREQUAL", "\\<="},
            { "MORE", "\\>"},
            { "LESS", "\\<"}

    };
}
