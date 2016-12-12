.text                           # section declaration

	                          	# we must export the entry point to the ELF linker or
.global _start              	# loader. They conventionally recognize _start as their
	                          	# entry point. Use ld -e foo to override the default.
_start:
	nop
	movl $1, %eax
	movl $1, %ebx
	movl $26, %edx
	#jnz %ecx label1
	jmp label5

label1:	
	movl $7, %ecx

label3:	
	inc %edx
	dec %ecx
	cmp %ecx, 0
	jnz label3
	#jnz %ecx label3

label5:	
 	movl %eax, %ecx

label4:
	inc %eax
	dec %ebx
	cmp %ebx, 0
	jnz label4
	movl %ecx, %ebx
	dec %edx
	cmp %edx, 0
	jnz label5
	movl $18, %ecx

label7:
 	movl $11, %edx

label6:	
	inc %eax
	dec %edx
	cmp %edx, 0
	jnz label6
	dec %ecx
	cmp %ecx, 0
	jnz label7			
	# write our string to stdout
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
