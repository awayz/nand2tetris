@256
D=A
@SP
M=D
@Sys.init_return_address0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@0
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(Sys.init_return_address0)
(Class1.set)
@0
D=A
@R13
M=D
(Class1.set$INNER_LOOP1)
@R13
D=M
@Class1.set$INNER_LOOP_END2
D;JEQ
D=0
@SP
A=M
M=D
@SP
M=M+1
@R13
M=M-1
@Class1.set$INNER_LOOP1
0;JMP
(Class1.set$INNER_LOOP_END2)
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@Class1.0
M=D
@1
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@Class1.1
M=D
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@LCL
A=M-D
D=M
@R13
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@1
D=A
@LCL
A=M-D
D=M
@THAT
M=D
@2
D=A
@LCL
A=M-D
D=M
@THIS
M=D
@3
D=A
@LCL
A=M-D
D=M
@ARG
M=D
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@R13
A=M
0;JMP
(Class1.get)
@0
D=A
@R13
M=D
(Class1.get$INNER_LOOP3)
@R13
D=M
@Class1.get$INNER_LOOP_END4
D;JEQ
D=0
@SP
A=M
M=D
@SP
M=M+1
@R13
M=M-1
@Class1.get$INNER_LOOP3
0;JMP
(Class1.get$INNER_LOOP_END4)
@Class1.0
D=M
@SP
A=M
M=D
@SP
M=M+1
@Class1.1
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@SP
AM=M-1
M=M-D
@SP
M=M+1
@5
D=A
@LCL
A=M-D
D=M
@R13
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@1
D=A
@LCL
A=M-D
D=M
@THAT
M=D
@2
D=A
@LCL
A=M-D
D=M
@THIS
M=D
@3
D=A
@LCL
A=M-D
D=M
@ARG
M=D
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@R13
A=M
0;JMP
(Class2.set)
@0
D=A
@R13
M=D
(Class2.set$INNER_LOOP5)
@R13
D=M
@Class2.set$INNER_LOOP_END6
D;JEQ
D=0
@SP
A=M
M=D
@SP
M=M+1
@R13
M=M-1
@Class2.set$INNER_LOOP5
0;JMP
(Class2.set$INNER_LOOP_END6)
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@Class2.0
M=D
@1
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@Class2.1
M=D
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@LCL
A=M-D
D=M
@R13
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@1
D=A
@LCL
A=M-D
D=M
@THAT
M=D
@2
D=A
@LCL
A=M-D
D=M
@THIS
M=D
@3
D=A
@LCL
A=M-D
D=M
@ARG
M=D
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@R13
A=M
0;JMP
(Class2.get)
@0
D=A
@R13
M=D
(Class2.get$INNER_LOOP7)
@R13
D=M
@Class2.get$INNER_LOOP_END8
D;JEQ
D=0
@SP
A=M
M=D
@SP
M=M+1
@R13
M=M-1
@Class2.get$INNER_LOOP7
0;JMP
(Class2.get$INNER_LOOP_END8)
@Class2.0
D=M
@SP
A=M
M=D
@SP
M=M+1
@Class2.1
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
@SP
AM=M-1
M=M-D
@SP
M=M+1
@5
D=A
@LCL
A=M-D
D=M
@R13
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@1
D=A
@LCL
A=M-D
D=M
@THAT
M=D
@2
D=A
@LCL
A=M-D
D=M
@THIS
M=D
@3
D=A
@LCL
A=M-D
D=M
@ARG
M=D
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@R13
A=M
0;JMP
(Sys.init)
@0
D=A
@R13
M=D
(Sys.init$INNER_LOOP9)
@R13
D=M
@Sys.init$INNER_LOOP_END10
D;JEQ
D=0
@SP
A=M
M=D
@SP
M=M+1
@R13
M=M-1
@Sys.init$INNER_LOOP9
0;JMP
(Sys.init$INNER_LOOP_END10)
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
@8
D=A
@SP
A=M
M=D
@SP
M=M+1
@Class1.set_return_address11
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@2
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(Class1.set_return_address11)
@0
D=A
@R5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
@23
D=A
@SP
A=M
M=D
@SP
M=M+1
@15
D=A
@SP
A=M
M=D
@SP
M=M+1
@Class2.set_return_address12
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@2
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(Class2.set_return_address12)
@0
D=A
@R5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
@Class1.get_return_address13
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@0
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.get
0;JMP
(Class1.get_return_address13)
@Class2.get_return_address14
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@0
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.get
0;JMP
(Class2.get_return_address14)
(WHILE)
@WHILE
0;JMP
