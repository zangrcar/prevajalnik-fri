.data
_n:
  .space 8
_i:
  .space 8
_total:
  .space 8
_current:
  .space 8
_memo:
  .space 352
L0:
  .byte 72, 111, 119, 32, 109, 97, 110, 121, 32, 70, 105, 98, 111, 110, 97, 99, 99, 105, 32, 110, 117, 109, 98, 101, 114, 115, 63, 32
.align 3
L1:
  .byte 70, 105, 98, 111, 110, 97, 99, 99, 105, 32, 115, 101, 113, 117, 101, 110, 99, 101, 58
.align 3
L2:
  .byte 77, 101, 109, 111, 105, 122, 101, 100, 32, 108, 97, 115, 116, 32, 118, 97, 108, 117, 101, 58
.align 3
L3:
  .byte 83, 117, 109, 58
.align 3

.text
.globl main
main:
  addi x2, x2, -16
  sd x0, 0(x2)
  jal x1, _main
  ld x10, 0(x2)
  addi x2, x2, 16
  addi x17, x0, 10
  ecall

_printString:
  addi x2, x2, -80
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x9, 40(x2)
  sd x8, 48(x2)
  sd x1, 56(x2)
  addi x8, x2, 80
  jal x0, L4
L4:
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 0
  sd x5, 0(x6)
L6:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x7, x5
  bne x5, x0, L7
  jal x0, L8
L7:
  addi x6, x8, 0
  addi x5, x0, -16
  add x9, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  add x5, x7, x5
  lbu x5, 0(x5)
  sb x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  lbu x5, 0(x5)
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  ld x5, 0(x2)
  jal x0, L6
L8:
  addi x5, x0, 0
  jal x0, L5
L5:
  sd x5, 0(x8)
  ld x9, 40(x2)
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 56(x2)
  ld x8, 48(x2)
  addi x2, x2, 80
  jalr x0, x1, 0

_fib:
  addi x2, x2, -80
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x9, 40(x2)
  sd x10, 48(x2)
  sd x8, 56(x2)
  sd x1, 64(x2)
  addi x8, x2, 80
  jal x0, L9
L9:
  lui x7, %hi(_memo)
  addi x7, x7, %lo(_memo)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x0, x5
  xor x5, x6, x5
  sltu x5, x0, x5
  bne x5, x0, L14
  jal x0, L15
L14:
  addi x6, x8, 0
  addi x5, x0, -8
  add x9, x6, x5
  lui x7, %hi(_memo)
  addi x7, x7, %lo(_memo)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  jal x0, L16
L15:
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  slt x5, x5, x6
  xori x5, x5, 1
  bne x5, x0, L11
  jal x0, L12
L11:
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x7)
  jal x0, L13
L12:
  addi x6, x8, 0
  addi x5, x0, -8
  add x10, x6, x5
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _fib
  ld x6, 0(x2)
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x7, x8, 0
  addi x5, x0, 8
  add x5, x7, x5
  ld x7, 0(x5)
  addi x5, x0, 2
  sub x5, x7, x5
  sd x9, 0(x2)
  sd x5, 8(x2)
  jal x1, _fib
  ld x5, 0(x2)
  add x5, x6, x5
  sd x5, 0(x10)
L13:
  lui x7, %hi(_memo)
  addi x7, x7, %lo(_memo)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x7, x7, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x7)
L16:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  jal x0, L10
L10:
  sd x5, 0(x8)
  ld x10, 48(x2)
  ld x9, 40(x2)
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 64(x2)
  ld x8, 56(x2)
  addi x2, x2, 80
  jalr x0, x1, 0

_main:
  addi x2, x2, -72
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x7, 40(x2)
  sd x9, 48(x2)
  sd x8, 56(x2)
  sd x1, 64(x2)
  addi x8, x2, 72
  jal x0, L17
L17:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L0)
  addi x6, x6, %lo(L0)
  addi x5, x0, 28
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  sd x5, 0(x2)
  jal x1, _getint
  ld x5, 0(x2)
  sd x5, 0(x6)
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 0
  slt x5, x6, x5
  bne x5, x0, L19
  jal x0, L20
L19:
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 0
  sd x5, 0(x6)
L20:
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 44
  slt x5, x5, x6
  bne x5, x0, L21
  jal x0, L22
L21:
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 44
  sd x5, 0(x6)
L22:
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
L23:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 44
  slt x5, x6, x5
  bne x5, x0, L24
  jal x0, L25
L24:
  lui x7, %hi(_memo)
  addi x7, x7, %lo(_memo)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x6, x7, x5
  addi x5, x0, 1
  sub x5, x0, x5
  sd x5, 0(x6)
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L23
L25:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L1)
  addi x6, x6, %lo(L1)
  addi x5, x0, 19
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
  lui x6, %hi(_total)
  addi x6, x6, %lo(_total)
  addi x5, x0, 0
  sd x5, 0(x6)
L28:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  slt x5, x6, x5
  bne x5, x0, L29
  jal x0, L30
L29:
  lui x7, %hi(_current)
  addi x7, x7, %lo(_current)
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x5, 0(x5)
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _fib
  ld x5, 0(x2)
  sd x5, 0(x7)
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  lui x5, %hi(_current)
  addi x5, x5, %lo(_current)
  ld x5, 0(x5)
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  lui x7, %hi(_total)
  addi x7, x7, %lo(_total)
  lui x5, %hi(_total)
  addi x5, x5, %lo(_total)
  ld x6, 0(x5)
  lui x5, %hi(_current)
  addi x5, x5, %lo(_current)
  ld x5, 0(x5)
  add x5, x6, x5
  sd x5, 0(x7)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x7, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  slt x5, x7, x5
  bne x5, x0, L26
  jal x0, L27
L26:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 32
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
L27:
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L28
L30:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 0
  slt x5, x5, x6
  bne x5, x0, L31
  jal x0, L32
L31:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L2)
  addi x6, x6, %lo(L2)
  addi x5, x0, 20
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 32
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _fib
  ld x5, 0(x2)
  sd x9, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  ld x5, 0(x2)
L32:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L3)
  addi x6, x6, %lo(L3)
  addi x5, x0, 4
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 32
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  lui x5, %hi(_total)
  addi x5, x5, %lo(_total)
  ld x5, 0(x5)
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  addi x5, x0, 0
  jal x0, L18
L18:
  sd x5, 0(x8)
  ld x9, 48(x2)
  ld x7, 40(x2)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 64(x2)
  ld x8, 56(x2)
  addi x2, x2, 72
  jalr x0, x1, 0

_putint:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 1
  ecall
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_putchar:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 11
  ecall
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getint:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  addi x17, x0, 5
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getchar:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  addi x17, x0, 12
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_new:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 9
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_del:
  jalr x0, x1, 0

_exit:
  addi x17, x0, 10
  ecall
