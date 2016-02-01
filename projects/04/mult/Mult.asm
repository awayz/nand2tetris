// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

	@R2
	M=0 // R2=0
	
	@R0
	D=M
	@i
	M=D // i=R0
	@MINUS
	D;JLT // i < 0 ?

(POS)
	@i
	D=M	
	@END
	D;JEQ // i==0 ?
	
	
    @R1
    D=M
    @R2
    M=M+D // R2=R2+R1

    @i
	M=M-1 // i--

	@POS
    0;JMP

(MINUS)
	@i
	D=M	
	@END
	D;JEQ // i==0 ?

	@R1
    D=M
    @R2
    M=M-D // R2=R2-R1

    @i
	M=M+1 // i++

    @MINUS
    0;JMP

(END)
	@END
	0;JMP