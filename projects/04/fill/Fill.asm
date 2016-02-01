// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

	@KBD
	D=A
	@k
	M=D // k = @KBD

(LOOP)
	@SCREEN
	D=A
	@addr
	M=D // addr = @SCREEN

	@4096
	D=A
	@count
	M=D // count = 4096

(IN)
	@count
	D=M
	@LOOP
	D;JEQ // count == 0?

	@color
	M=0

	@k
	A=M
	D=M
	@CLEAR
	D;JEQ // k == 0 ?

	@color
	M=-1 // black

(CLEAR)
	@color
	D=M
	@addr
	A=M
	M=D // RAM[addr] = 1111...1 or 0000...0	
	
	@addr
	M=M+1 // addr = addr + 32
	
	@count
	M=M-1 // count = count - 1

	@IN
	0;JMP

	@LOOP
	0;JMP