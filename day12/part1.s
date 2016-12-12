# 
# Tiago Almeida 2016
#
# Advent of code day 12. 
# We are given a pseudo assembly ("assembunny") and asked for the 
# value of register A
#
# For the solution, I manually translated the input into x86 assembly.
# Under linux, compile and run with:
# as -g part1.s -o part1.o && ld -g part1.o -o part1
# 
# The program does not print the value of register A, you need to use
# gdbtui to inspect it :P To do it, do the following:
#
# gdbtui part1
# b 73 (Set a breakpoint on line 73)
# run
# info registers (rax contains the value you want)
#
.text

	             # we must export the entry point to the ELF linker or
.global _start   # loader. They conventionally recognize _start as their
	             # entry point. Use ld -e foo to override the default.
_start:
	nop
	# set all to 0
	movl $0, %eax
	movl $0, %ebx
	movl $0, %ecx
	movl $0, %edx  
	# start
	movl $1, %eax
	movl $1, %ebx
	movl $26, %edx
	jmp label5

label1:	
	movl $7, %ecx

label3:	
	inc %edx
	dec %ecx
	test %ecx, %ecx
	jne label3

label5:	
 	movl %eax, %ecx

label4:
	inc %eax
	dec %ebx
	test %ebx, %ebx
	jne label4
	movl %ecx, %ebx
	dec %edx
	test %edx, %edx
	jne label5
	movl $18, %ecx

label7:
 	movl $11, %edx

label6:	
	inc %eax
	dec %edx
	test %edx, %edx
	jne label6
	dec %ecx
	test %ecx, %ecx
	jne label7			
	# Print Hello world.
	movl    $len, %edx          # third argument: message length
	movl    $msg, %ecx          # second argument: pointer to message to write
	movl    $1, %ebx            # first argument: file handle (stdout)
	movl    $4, %eax            # system call number (sys_write)
	int     $0x80               # call kernel
	                        	# and exit
	movl    $0, %ebx            # first argument: exit code
	movl    $1, %eax            # system call number (sys_exit)
	int     $0x80               # call kernel

.data                           # section declaration

msg:
.ascii    "Hello, world!\n"   	# our dear string
len = . - msg                 	# length of our dear string

# The result is 900813 inverted