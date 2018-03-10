package main;

public enum CondCode {

    EQ, //0000 == Z
    NE, //0001 != !Z
    CS, //0010 CS or HS carry set/unsigned higher or same C
    CC, //0011 CC or LO carry clear/unsigned lower !C
    MI, //0100 minus/negative N
    PL, //0101 plus/positive or zero !N
    VS, //0110 overflow set V
    VC, //0111 overflow clear !V
    HI, //1000 unsigned higher C && !Z
    LS, //1001 unsigned lower or same !C || Z
    GE, //1010 signed >= N == V
    LT, //1011 signed < N != V
    GT, //1100 signed > !Z && (N==V)
    LE, //1101 LE signed <= Z || (N != V)
    AL  //1110 always true

}
